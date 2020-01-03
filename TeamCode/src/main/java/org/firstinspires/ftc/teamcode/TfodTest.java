package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;


@Autonomous(name = "TfodTest")
public class TfodTest extends LinearOpMode {

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
                    if (areaRatio >= .975) {
                        telemetry.addLine("Moving in!");
                        skystoneArea = true;
                    }
                    HorAngle = recognition.estimateAngleToObject(AngleUnit.DEGREES);
                    telemetry.addData("HorizontalAngle:", HorAngle);
                }
            }
        }
    }

    public void moveToSkystone() {
        if (-1.5 <= HorAngle && HorAngle <= 1.5) {
            mecanum.mecanumLeft(.5);
            sleep(50);
        } else if (HorAngle < -2) {
            mecanum.mecanumBack(.4);
            sleep(10);
            telemetry.addData("Status", "Backing");
        } else if (HorAngle > 2) {
            mecanum.mecanumFront(.4);
            sleep(10);
            telemetry.addData("Status:", "Forwarding");
        }
    }

    public void grabSkystone() {
        telemetry.addData("Status:", "I should be grabbing the skystone now");
    }

    public void SkyStoneTFOD() {
        if (robot.tensorFlowEngine != null) {
            List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
            checkForStones(updatedRecognitions);
            if (skystoneArea) {
                mecanum.mecanumNaught();
                grabSkystone();
            } else if (skystone) {
                mecanum.mecanumNaught();
                moveToSkystone();
            } else {
                mecanum.mecanumLeft(.5);
            }
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        robot.tensorFlowEngine.activate();

        waitForStart();

        while (opModeIsActive()) {

            SkyStoneTFOD();
            telemetry.update();
        }
    }
}
