package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Autonomous.AngularMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.motion.Clamp;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.LeverArm;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;


@Autonomous(name = "BlueDriverStation")
public class BlueDriverStation extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    Clamp clamp = new Clamp();
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    LeverArm lever_arm = new LeverArm();
    AngularMecanum angularMecanum = new AngularMecanum(robot, telemetry);
    SkystoneNavigation nav = new SkystoneNavigation();
    Kicker kicker = new Kicker();
    boolean skystoneArea = false;
    boolean skystone = false;
    double HorAngle;
    double areaRatio;
    boolean skystoneFound = false;
    boolean skystoneGrabbed = false;
    boolean movedToPlate = false;
    boolean notToCornerYet = true;
    boolean notThereYet2 = true;
    boolean placedStone = false;
    boolean notStraightYet = true;
    double robotAngle;
    boolean cantFindPicture = false;
    boolean notToPlate = true;
    boolean notStraight = false;
    boolean movedTray = false;

    // Looks for stones and tells whether or not its a skystone
    // If it sees a skystone, it gets the horizontal angle to it
    // When close enough, it sets skystoneArea to true
    public void checkForStones(List<Recognition> updatedRecognitions) {
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            // step through the list of recognitions and display boundary info.
            for (Recognition recognition : updatedRecognitions) {
                if (recognition.getLabel() == robot.LABEL_SECOND_ELEMENT) {
                    skystone = true;
                    HorAngle = recognition.estimateAngleToObject(AngleUnit.RADIANS);
                    telemetry.addData("HorizontalAngle:", HorAngle);
                    areaRatio = ((recognition.getWidth() * recognition.getHeight()) / (recognition.getImageHeight() * recognition.getImageWidth()));
                    if (areaRatio > .95) {
                        skystoneArea = true;
                    }
                }
            }
        }
    }

    // Plugs in the horizontal ange from checkForStones, moves towards it
    public void moveToSkystone() {
        mecanum.mecanumNaught();
        angularMecanum.Left(HorAngle, .65, 0);
        sleep(30);
    }

    // Is a conglomeration of checkForStones and moveToSkystone
    // If it sees something, it checks for stones.
    // If the area of the stone is large enough, it deactivates tensor and moves to next case.
    // If it sees a skystone, then it moves to it
    private void SkyStoneTFOD() {
        if (robot.tensorFlowEngine != null) {
            List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
            checkForStones(updatedRecognitions);
            if (skystoneArea) {
                mecanum.mecanumNaught();
                robot.tensorFlowEngine.deactivate();
                skystoneFound = true;
            } else if (skystone) {
                moveToSkystone();
            }
        }
    }

    // Drives forward until touch sensor is activated
    public void driveUntilTouch() {
        while (robot.digitalTouch.getState())
            mecanum.mecanumFront(.95);
        if (!robot.digitalTouch.getState()) {
            mecanum.mecanumNaught();
        }
    }

    // Grabs the skystone from the position
    public void grabSkystone() {
        mecanum.mecanumRotate(-.8);
        sleep(2050);
        mecanum.mecanumNaught();
        mecanum.mecanumBack(.95);
        sleep(1250);
        mecanum.mecanumNaught();
        mecanum.mecanumRight(.95);
        sleep(2200);
        mecanum.mecanumNaught();
        driveUntilTouch();
        skystoneGrabbed = true;
    }

    // Provides the angle for the coordinate (-905, 1090)
    private void tangentTime(double X, double Y) {
        robotAngle = Math.atan((-905-X)/(1090-Y));
    }

    // Provides the angle for the coordinate (835, 1110)
    // NOT BEING USED, NEED TO DELETE THIS
    private void tangentTime2(double X, double Y) {
        if (Y > 1110) {
            robotAngle = Math.atan((835 - X) / (Y - 1110));
        } else if (Y <= 1110) {
            robotAngle = Math.atan((835-X)/(1110-Y));
        }
    }
    private void pictureFront() {
        mecanum.mecanumFront(.8);
        sleep(600);
        mecanum.mecanumNaught();
        sleep(300);
        nav.SkystoneNavigationNoTelemetry();
    }

    private void pictureBack() {
        mecanum.mecanumBack(.8);
        sleep(500);
        mecanum.mecanumNaught();
        sleep(300);
        nav.SkystoneNavigationNoTelemetry();
    }

    public void moveToPlate1() {
        while (nav.Y < 1090) {
            // robot is now just off of center isle.
            nav.SkystoneNavigationNoTelemetry();
            telemetry.addData("Rotation:", nav.Rotation);
            telemetry.addData("My X is", nav.X);
            telemetry.addData("My Y is", nav.Y);
            tangentTime(nav.X, nav.Y);
            telemetry.addData("Tangent angle:", robotAngle);
            angularMecanum.Left(robotAngle, .7, 0);
            nav.SkystoneNavigationNoTelemetry();
            telemetry.update();
        }
        if (nav.Y >= 1090) {
            mecanum.mecanumNaught();
            notToCornerYet = false;
            telemetry.addData("Status:", "In da corner!");
            telemetry.update();
        }
    }

    public void roundSelfOut() {
        while ((nav.Rotation < 176 && nav.Rotation > 1) || (nav.Rotation > -176 && nav.Rotation < -1)) {
            if (nav.Rotation > 1) {
                mecanum.mecanumRotate(-.8);
                sleep(20);
                mecanum.mecanumNaught();
                sleep(95);
            } else if (nav.Rotation < -1) {
                mecanum.mecanumRotate(.8);
                sleep(20);
                mecanum.mecanumNaught();
                sleep(95);
            }
            nav.SkystoneNavigationNoTelemetry();
        }
    }

    public void actuallyMoveToPlate() {
        mecanum.mecanumFront(1);
        sleep(5800);
        mecanum.mecanumNaught();
        sleep(30);
        nav.SkystoneNavigationNoTelemetry();
        telemetry.addData("Rotation:", nav.Rotation);
        telemetry.addData("My X is", nav.X);
        telemetry.addData("My Y is", nav.Y);
        telemetry.update();
        if (nav.X > 910 && nav.X < 950) {
            notToPlate = false;
        }
        while (notToPlate) {
            if (nav.X < 910) {
                mecanum.mecanumFront(.85);
                sleep(40);
                mecanum.mecanumNaught();
                sleep(80);
                nav.SkystoneNavigationNoTelemetry();
            } else if (nav.X > 950) {
                mecanum.mecanumBack(.85);
                sleep(40);
                mecanum.mecanumNaught();
                sleep(80);
                nav.SkystoneNavigationNoTelemetry();
            } else if (nav.X < 950 && nav.X > 910){
                notToPlate = false;
                break;
            }
        }
    }

    // Moves out of the position moved to at the end of grabSkystone
    // Tries to look at the picture; if it can't see it, it moves back and forth 10 times
    // If it can see the photo then it moves to the point (-905, 1090)
    public void moveToPlate() {
        mecanum.mecanumBack(.8);
        sleep(1300);
        mecanum.mecanumNaught();
        kicker.KickerSet(robot, .45);
        mecanum.mecanumFront(.8);
        sleep(350);
        mecanum.mecanumNaught();
        while (notToCornerYet) {
            nav.SkystoneNavigationNoTelemetry();
            int counter = 0;
            while (nav.X == 0 && nav.Y == 0) {
                telemetry.addData("EMERGENCY:", "CANNOT FIND PICTURE");
                pictureFront();
                if (nav.X != 0 && nav.Y != 0) {
                    break;
                }
                pictureBack();
                if (nav.X != 0 && nav.Y != 0) {
                    break;
                }
                counter += 1;
                if (counter > 10){ break;}
            }
            moveToPlate1();
        }
        nav.SkystoneNavigationNoTelemetry();
//        mecanum.mecanumRotate(-.8);
//        sleep(10);
//        mecanum.mecanumNaught();
//        roundSelfOut();
        if (notStraight) {
            throw new IllegalArgumentException("NOT STRAIGHT ENOUGH");
        }
        actuallyMoveToPlate();
        mecanum.mecanumFront(1);
        sleep(975);
        mecanum.mecanumNaught();
        mecanum.mecanumRotate(.8);
        sleep(1025);
        mecanum.mecanumNaught();
        movedToPlate = true;
    }

    public void placeTheStone() {
        telemetry.addData("Status:", "Placing");
        telemetry.update();
        while (robot.leverArm.getCurrentPosition() < 1297) {
            lever_arm.moveLeverArm(robot, telemetry, .75);
            while (robot.clampRotator.getPosition() < 1) {
                clamp.moveClampRotator(robot, 1);
            }
        }
        clamp.setClamp(robot, true, false);
        sleep(297);
        lever_arm.moveLeverArm(robot, telemetry, -1);
        sleep(997);
        clamp.moveClampRotator(robot, -1);
        sleep(175);
//        while (robot.leverArm.getCurrentPosition() > 750) {
//            lever_arm.moveLeverArm(robot, telemetry, -.5);
//            while (robot.clampRotator.getPosition() > -1) {
//                clamp.moveClampRotator(robot, -1);
//            }
//        }
        lever_arm.leverArmStay(robot, telemetry);
        placedStone = true;
    }

    public void moveToLine() {
        telemetry.addData("Status", "Moving tray");
        telemetry.update();
        mecanum.mecanumRight(1);
        sleep(2250);
        mecanum.mecanumNaught();
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        robot.tensorFlowEngine.activate();
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            kicker.KickerSet(robot, 0);
            if (!skystoneFound) {
                SkyStoneTFOD();
            } else if (skystoneFound && !skystoneGrabbed) {
                nav.skystoneNavigationInit(robot);
                telemetry.addData("Status:", "skystone nav init");
                telemetry.update();
                grabSkystone();
            } else if (skystoneGrabbed && !movedToPlate) {
                clamp.setClamp(robot, false, true);
                moveToPlate();
            } else if (movedToPlate && !placedStone) {
                placeTheStone();
            } else if (placedStone && !movedTray) {
                moveToLine();
            }
            telemetry.update();
        }
    }
}

// Note that nav.skystoneNavigationInit shouldn't be done in a while loop
// We don't want it init-ing over and over and over