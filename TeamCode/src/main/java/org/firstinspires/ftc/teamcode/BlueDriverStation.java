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
        nav.skystoneNavigationInit(robot);
        mecanum.mecanumRotate(-.8);
        sleep(1951);
        mecanum.mecanumNaught();
        mecanum.mecanumFBack(.95);
        sleep(777);
        mecanum.mecanumNaught();
        mecanum.mecanumFRight(.95);
        sleep(1077);
        mecanum.mecanumNaught();
        driveUntilTouch();
        skystoneGrabbed = true;
    }

    // Provides the angle for the coordinate (-905, 1090)
    private void tangentTime(double X, double Y) {
        robotAngle = Math.atan((-905-X)/(1090-Y));
    }

    private void pictureFront() {
        mecanum.mecanumFFront(.8);
        sleep(300);
        mecanum.mecanumNaught();
        sleep(300);
        nav.SkystoneNavigationNoTelemetry();
    }

    private void pictureBack() {
        mecanum.mecanumFBack(.8);
        sleep(250);
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

    public void actuallyMoveToPlate() {
        mecanum.mecanumFFront(1);
        sleep(3479);
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
                mecanum.mecanumFront(.425);
                sleep(40);
                mecanum.mecanumNaught();
                sleep(80);
                nav.SkystoneNavigationNoTelemetry();
            } else if (nav.X > 950) {
                mecanum.mecanumBack(.425);
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
        mecanum.mecanumFBack(.8);
        sleep(750);
        mecanum.mecanumNaught();
        kicker.KickerSet(robot, .45);
        mecanum.mecanumFront(.8);
        sleep(450);
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
        mecanum.mecanumRotate(-.8);
        sleep(20);
        mecanum.mecanumNaught();
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
        sleep(915);
        mecanum.mecanumNaught();
        movedToPlate = true;
    }

    public void placeTheStone() {
        telemetry.addData("Status", "Placing");
        telemetry.update();
        while (robot.leverArm.getCurrentPosition() < 1397) {
            lever_arm.moveLeverArm(robot, telemetry, .625);
            while (robot.clampRotator.getPosition() < 1) {
                clamp.moveClampRotator(robot, 1);
            }
        }
        clamp.setClamp(robot, true, false);
        sleep(257);


        while (robot.leverArm.getCurrentPosition() > 500) {
            lever_arm.moveLeverArm(robot, telemetry, -.75);
            while (robot.clampRotator.getPosition() > 0.1) {
                clamp.moveClampRotator(robot, 0);
            }
        }
        robot.leverArm.setPower(0.0);
        robot.KickerServo.setPosition(robot.MID_SERVO);
        placedStone = true;
    }

    public void moveToLine() {

        robot.KickerServo.setPosition(robot.MID_SERVO);
        telemetry.addData("Status", "Moving tray");
        telemetry.update();
        mecanum.mecanumFRight(1);
        sleep(3100);
        mecanum.mecanumNaught();
        movedTray = true;
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
                grabSkystone();
            } else if (skystoneGrabbed && !movedToPlate) {
                clamp.setClamp(robot, false, true);
                moveToPlate();
            } else if (movedToPlate && !placedStone) {
                placeTheStone();
            } else if (placedStone && !movedTray) {
                moveToLine();
            } else if (movedTray) {
                telemetry.addData("Ryan Rocks", "..sorta");
            }
            telemetry.update();
        }
    }
}

// Note that nav.skystoneNavigationInit shouldn't be done in a while loop
// We don't want it init-ing over and over and over