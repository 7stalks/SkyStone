package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Autonomous.AngularMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;

@Autonomous(name = "BlueTest")
@Disabled

public class BlueTest extends LinearOpMode {

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

        // modify
        mecanum.mecanumFFront(1);
        sleep(343);
        mecanum.mecanumNaught();

        // modify
        mecanum.mecanumFLeft(1);
        sleep(491);
        mecanum.mecanumNaught();

        robot.handsOn.setPosition(1);
        sleep(691);

        // modify
        mecanum.mecanumFRight(1);
        sleep(741);
        mecanum.mecanumNaught();

        // delete?
//        mecanum.mecanumRotate(-.8);
//        sleep(35);
//        mecanum.mecanumNaught();

        skystoneGrabbed = true;
    }

    private void moveToFrontAndThenBack() {
        telemetry.addData("Status", "Moving to other side");
        telemetry.update();

        // modify
        mecanum.mecanumFullBack();
        sleep(2450);
        mecanum.mecanumNaught();
        robot.handsOn.setPosition(.45);
        sleep(600);

        mecanum.mecanumRotate(-.95);
        sleep(70);
        mecanum.mecanumNaught();

        // modify
        mecanum.mecanumFullFront();
        sleep(3595);
        mecanum.mecanumNaught();

        movedToOtherSideOne = true;
    }

    private void grabOtherStone() {

        // NOT SURE!!!
//        mecanum.mecanumRotate(-.8);
//        sleep(47);
//        mecanum.mecanumNaught();

        mecanum.mecanumLeft(1);
        sleep(733);
        mecanum.mecanumNaught();

        robot.handsOn.setPosition(1);
        sleep(700);

        mecanum.mecanumRight(1);
        sleep(817);
        mecanum.mecanumNaught();

        grabbedOtherStone = true;
    }

    private void moveToFrontButNotBack() {
        mecanum.mecanumFullBack();
        sleep(3251);
        mecanum.mecanumNaught();

        robot.handsOn.setPosition(.45);
        sleep(700);

        movedToOtherSideTwo = true;
    }

    private void park() {
        telemetry.addData("Status", "Parking");
        telemetry.update();

        mecanum.mecanumFullFront();
        sleep(673);
        mecanum.mecanumNaught();

        parked = true;
    }

    private void setRight() {

    }

    private void setMiddle() {

    }

    private void setLeft() {

    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        robot.tensorFlowEngine.activate();
        telemetry.update();

        waitForStart();

        List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
        checkForStones(updatedRecognitions);

        if (HorAngle > .8) {

        } else if (HorAngle < .8 && HorAngle > -.8) {

        } else if (HorAngle < -.8) {

        }

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
