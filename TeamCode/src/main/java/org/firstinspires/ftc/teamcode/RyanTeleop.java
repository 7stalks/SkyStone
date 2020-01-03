package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;


@Autonomous(name = "RyanTeleop")
public class RyanTeleop extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    boolean skystone = false;
    boolean skystoneArea = false;
    float areaRatio;
    double HorAngle;

    public void checkForStones(List<Recognition> updatedRecognitions) {
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            // step through the list of recognitions and display boundary info.
            for (Recognition recognition : updatedRecognitions) {
                if (recognition.getLabel() == robot.LABEL_SECOND_ELEMENT) {
                    telemetry.addLine("YAHOO!!");
                    skystone = true;
                    areaRatio = ((recognition.getWidth() * recognition.getHeight()) / (recognition.getImageHeight() * recognition.getImageWidth()));
                    telemetry.addData("Stone area over image area:", areaRatio);
                    if (areaRatio >= .9) {
                        telemetry.addLine("Moving in!");
                        skystoneArea = true;
                    }
                    HorAngle = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                    telemetry.addData("HorizontalAngle:", HorAngle);
                }
            }
            telemetry.update();
        }
    }

    public void moveToSkystone() {
        if (HorAngle >= 1) {
            mecanum.mecanumFront(.4);
        } else if (HorAngle <= -1) {
            mecanum.mecanumBack(.4);
        } else if (HorAngle > -1 && HorAngle < 1) {
            mecanum.mecanumLeft(.4);
        }
    }

    public void grabSkystone() {
        telemetry.addLine("I should be grabbing the skystone now");
        telemetry.update();
    }

    public void SkyStoneTFOD() {
        if (robot.tensorFlowEngine != null) {
            List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
            checkForStones(updatedRecognitions);
            if (skystoneArea) {
                mecanum.mecanumNaught();
                grabSkystone();
                robot.tensorFlowEngine.deactivate();
            } else if (skystone) {
                mecanum.mecanumNaught();
                moveToSkystone();
            } else {
                mecanum.mecanumLeft(.55);
            }
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        robot.tensorFlowEngine.activate();

        waitForStart();

//        mecanum.mecanumLeft(.9);
//        sleep(1250);
//        mecanum.mecanumNaught();
//        sleep(100);
//        mecanum.mecanumBack(.9);
//        sleep(900);
//        mecanum.mecanumNaught();
//        sleep(50);

        while (opModeIsActive()) {

            SkyStoneTFOD();
        }
    }
}
