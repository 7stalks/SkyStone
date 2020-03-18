package org.firstinspires.ftc.teamcode.oldteleops;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.autonomous.AngularMecanum;
import org.firstinspires.ftc.teamcode.autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.OldRobotHardware;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;


@Autonomous(name = "TfodTest")
@Disabled

public class TfodTest extends LinearOpMode {

    OldRobotHardware robot = new OldRobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    AngularMecanum angularMecanum = new AngularMecanum(robot, telemetry);
    boolean skystone = false;
    boolean skystoneArea = false;
    boolean skystoneFound = false;
    float areaRatio;
    double HorAngle;

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
                    telemetry.addData("Area Ratio", areaRatio);
                    if (areaRatio > .80) {
                        skystoneArea = true;
                        telemetry.addData("SkystoneArea", "True");
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
                sleep(60);
                mecanum.mecanumNaught();
                sleep(120);
            }
        }
    }

    private void simplekyStoneTFOD() {
        List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
        checkForStones(updatedRecognitions);
    }

//    angularMecanum.Left(.3216505544, 1, 0);
//    sleep(1000);
//    mecanum.mecanumNaught();
//
//        while (opModeIsActive()) {
//        simpleSkyStoneTFOD();
//        telemetry.update();
//    }

    private void stopAndGoMoveToSkystone() {
        mecanum.mecanumNaught();
        angularMecanum.Left(HorAngle, .65, 0);
        sleep(100);
        mecanum.mecanumNaught();
        sleep(100);
    }

    private void stopAndGoSkyStoneTFOD() {
        List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
        checkForStones(updatedRecognitions);
        if (skystoneArea) {
            mecanum.mecanumNaught();
            robot.tensorFlowEngine.deactivate();
            skystoneFound = true;
        } else if (skystone) {
            stopAndGoMoveToSkystone();
        } else if (!skystone) {
            mecanum.mecanumLeft(1);
            sleep(50);
            mecanum.mecanumNaught();
        }
    }

    private void slowMoveToSkystone() {
        mecanum.mecanumNaught();
        angularMecanum.Left(HorAngle, .4, 0);
        sleep(30);
    }

    private void slowSkyStoneTFOD() {
        List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
        checkForStones(updatedRecognitions);
        if (skystoneArea) {
            mecanum.mecanumNaught();
            robot.tensorFlowEngine.deactivate();
            skystoneFound = true;
        } else if (skystone) {
            slowMoveToSkystone();
        } else if (!skystone) {
            mecanum.mecanumLeft(1);
            sleep(50);
            mecanum.mecanumNaught();
            sleep(100);
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        robot.tensorFlowEngine.activate();

        waitForStart();

//        mecanum.mecanumLeft(1);
//        sleep(811  );
//        mecanum.mecanumNaught();
        List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
        checkForStones(updatedRecognitions);
//        while (opModeIsActive()) {
//            SkyStoneTFOD();
//            telemetry.update();
//        }
    }
}
