package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Autonomous.AngularMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;

@Autonomous(name = "BlueMoveBothStones")
public class BlueMoveBothStones extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    AngularMecanum angularMecanum = new AngularMecanum(robot, telemetry);
    SkystoneNavigation nav = new SkystoneNavigation();
    Kicker kicker = new Kicker();


    // booleans and doubles
    boolean skystoneArea = false;
    boolean skystone = false;
    boolean skystoneFound = false;
    boolean skystoneGrabbed = false;
    boolean movedToOtherSideOne = false;
    boolean grabbedOtherStone = false;
    boolean movedToOtherSideTwo = false;
    boolean parked = false;
    double HorAngle;
    double areaRatio;


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
                    if (areaRatio > .80) {
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
    // If it sees a skystone, then it moves to itd
    private void SkyStoneTFOD() {
        robot.skystoneFront.setPosition(robot.SKYSTONE_GRABBER_DOWN_AUTONOMOUS + .01);
        robot.skystoneFrontRotator.setPosition(1);
        if (robot.tensorFlowEngine != null) {
            List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
            checkForStones(updatedRecognitions);
            if (skystoneArea) {
                mecanum.mecanumNaught();
                robot.tensorFlowEngine.deactivate();
                skystoneFound = true;
            } else if (skystone) {
                moveToSkystone();
            } else if (!skystone) {
                mecanum.mecanumLeft(.6);
                sleep(30);
                mecanum.mecanumNaught();
            }
        }
    }

    private void grabTheSkystone() {
        telemetry.addData("Status", "Grabbing skystone");
        telemetry.update();

        mecanum.mecanumRotate(-.8);
        sleep(60);
        mecanum.mecanumNaught();

        mecanum.mecanumFBack(1);
        sleep(100);
        mecanum.mecanumNaught();

        while (robot.digitalTouchSkystoneFront.getState() && opModeIsActive()) {
            mecanum.mecanumFLeft(.75);
        }
        mecanum.mecanumNaught();

        robot.skystoneFrontRotator.setPosition(robot.SKYSTONE_ROTATOR_DOWN - .01);
        sleep(400);

        robot.skystoneFront.setPosition(robot.MID_SERVO);
        sleep(400);

        mecanum.mecanumFRight(1);
        sleep(601);
        mecanum.mecanumNaught();

        mecanum.mecanumRotate(.8);
        sleep(30);
        mecanum.mecanumNaught();

        skystoneGrabbed = true;
    }

    private void moveToFrontAndThenBack() {
        telemetry.addData("Status", "Moving to other side");
        telemetry.update();

        mecanum.mecanumFullBack();
        sleep(2271);
        mecanum.mecanumNaught();

        robot.skystoneFront.setPosition(robot.SKYSTONE_GRABBER_DOWN_AUTONOMOUS);
        sleep(250);
        robot.skystoneFrontRotator.setPosition(robot.MID_SERVO);
        sleep(75);

        robot.skystoneFront.setPosition(robot.MID_SERVO);
        sleep(250);
        robot.skystoneFrontRotator.setPosition(robot.SKYSTONE_ROTATOR_DOWN);

        mecanum.mecanumRotate(-.8);
        sleep(25);
        mecanum.mecanumNaught();

        mecanum.mecanumFullFront();
        sleep(3395);
        mecanum.mecanumNaught();

        movedToOtherSideOne = true;
    }

    private void grabOtherStone() {
        mecanum.mecanumRotate(-.8);
        sleep(25);
        mecanum.mecanumNaught();

        mecanum.mecanumFRight(1);
        sleep(135);
        mecanum.mecanumNaught();

        mecanum.mecanumRotate(-.8);
        sleep(7);
        mecanum.mecanumNaught();

        robot.skystoneFront.setPosition(robot.SKYSTONE_GRABBER_DOWN_AUTONOMOUS + .01);
        sleep(50);
        robot.skystoneFrontRotator.setPosition(1);
        sleep(350);

        while (robot.digitalTouchSkystoneFront.getState() && opModeIsActive()) {
            mecanum.mecanumFLeft(.75);
        }
        mecanum.mecanumNaught();

        robot.skystoneFrontRotator.setPosition(robot.SKYSTONE_ROTATOR_DOWN - .01);
        sleep(400);

        robot.skystoneFront.setPosition(robot.MID_SERVO);
        sleep(400);

        mecanum.mecanumFRight(1);
        sleep(601);
        mecanum.mecanumNaught();

        mecanum.mecanumRotate(.8);
        sleep(10);
        mecanum.mecanumNaught();

        grabbedOtherStone = true;
    }

    private void moveToFrontButNotBack() {
        mecanum.mecanumFullBack();
        sleep(1851);
        mecanum.mecanumNaught();

        while ((robot.colorSensor.blue() < 178) && opModeIsActive()) {
            mecanum.mecanumFBack(.7);
        }
        mecanum.mecanumNaught();

        mecanum.mecanumFBack(1);
        sleep(1250);
        mecanum.mecanumNaught();

        robot.skystoneFront.setPosition(robot.SKYSTONE_GRABBER_DOWN_AUTONOMOUS);
        sleep(250);
        robot.skystoneFrontRotator.setPosition(robot.MID_SERVO);
        sleep(75);

        robot.skystoneFront.setPosition(robot.MID_SERVO);
        sleep(250);
        robot.skystoneFrontRotator.setPosition(robot.SKYSTONE_ROTATOR_DOWN);

        movedToOtherSideTwo = true;
    }

    private void park() {
        telemetry.addData("Status", "Parking");
        telemetry.update();

        mecanum.mecanumFullFront();
        sleep(1250);
        mecanum.mecanumNaught();

        parked = true;
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        robot.tensorFlowEngine.activate();
        telemetry.update();

        waitForStart();

        List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
        checkForStones(updatedRecognitions);

        while (opModeIsActive()) {
            kicker.KickerSet(robot, 0);
            if (!skystoneFound) {
                SkyStoneTFOD();
            } else if (skystoneFound && !skystoneGrabbed) {
                grabTheSkystone();
            } else if (skystoneGrabbed && !movedToOtherSideOne) {
                moveToFrontAndThenBack();
            } else if (movedToOtherSideOne && !grabbedOtherStone) {
                grabOtherStone();
            } else if (grabbedOtherStone && !movedToOtherSideTwo) {
                moveToFrontButNotBack();
            } else if (movedToOtherSideTwo && !parked) {
                park();
            } else if (parked) {
                break;
            }
            telemetry.update();
        }
    }
}