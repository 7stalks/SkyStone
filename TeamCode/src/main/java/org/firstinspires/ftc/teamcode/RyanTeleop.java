package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;


@Autonomous(name = "RyanTeleop")
public class RyanTeleop extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();

    private void recognitionTelemetry() {
        
    }

    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);
        if (robot.tensorFlowEngine != null) {
            robot.tensorFlowEngine.activate();
        }

        waitForStart();

//        mecanum.mecanumLeft(9);
//        sleep(1250);
//        mecanum.mecanumNaught();
//        sleep(250);
//        mecanum.mecanumBackward(9);
//        sleep(400);

        while (opModeIsActive()) {

            if (robot.tensorFlowEngine != null) {
                List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                   for (Recognition recognition : updatedRecognitions) {
                             telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        if (recognition.getLabel() == robot.LABEL_SECOND_ELEMENT) {
                            telemetry.addLine("YAHOO!!");
                            telemetry.addData("Height:", recognition.getHeight());
                            telemetry.addData("Width:", recognition.getWidth());
                            float areaRatio = ((recognition.getWidth()*recognition.getHeight())/(recognition.getImageHeight()*recognition.getImageWidth()));
                            telemetry.addData("Stone area over image area:", areaRatio);
                            if (areaRatio >= .9) {
                                telemetry.addLine("Nailed it!");
                            }
                        } else {
//                            sleep(250);
//                            mecanum.mecanumForward(3);
//                            sleep(150);
                        }
                    }
                    telemetry.update();
                }
            }
        }
    }
}
