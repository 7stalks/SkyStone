package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;


@Autonomous(name = "RyanTeleop")
public class RyanTeleop extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();

    private void mecanumLeft(double power) {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, -power, 0, false, false);
    }

    private void mecanumNaught() {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, 0, 0, false, false);
    }

    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);
        if (robot.tensorFlowEngine != null) {
            robot.tensorFlowEngine.activate();
        }

        waitForStart();

        mecanumLeft(.9);
        sleep(750);
        mecanumNaught();

        while (opModeIsActive()) {

            if (robot.tensorFlowEngine != null) {
                List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                   for (Recognition recognition : updatedRecognitions) {
                             telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        if (recognition.getLabel() == robot.LABEL_SECOND_ELEMENT) {
                            telemetry.addLine("YAHOO!!");
                            double bottom = recognition.getBottom();
                            double top = recognition.getTop();
                            double right = recognition.getRight();
                            double left = recognition.getLeft();
                        }
                    }
                    telemetry.update();
                }
            }
        }
    }
}
