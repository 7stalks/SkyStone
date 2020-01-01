package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous(name = "BlueDriverStation")
public class BlueDriverStation extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    public TFObjectDetector tensorFlowEngine;
    SkystoneNavigation skystoneNav = new SkystoneNavigation();
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    boolean skystone = false;


    public void getNavStuff() {
        skystoneNav.skystoneNavigationInit(robot);
        skystoneNav.targetsSkyStone.activate();
        skystoneNav.SkystoneNavigation(telemetry);
        skystoneNav.targetsSkyStone.deactivate();
    }

    public void getTFODStuff() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tensorFlowEngine = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, robot.vuforia);
        tensorFlowEngine.loadModelFromAsset(robot.TFOD_MODEL_ASSET, robot.LABEL_FIRST_ELEMENT,
                robot.LABEL_SECOND_ELEMENT);
        telemetry.addData("Status", "Tensor Flow Object Detection Initialized");
    }

    public void checkForStones(List<Recognition> updatedRecognitions) {
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            // step through the list of recognitions and display boundary info.
            for (Recognition recognition : updatedRecognitions) {
                if (recognition.getLabel() == robot.LABEL_FIRST_ELEMENT) {
                    telemetry.addLine("Womp womp, no stone");
                }
                if (recognition.getLabel() == robot.LABEL_SECOND_ELEMENT) {
                    telemetry.addLine("YAHOO!!");
                    telemetry.addData("Height:", recognition.getHeight());
                    telemetry.addData("Width:", recognition.getWidth());
                    float areaRatio = ((recognition.getWidth() * recognition.getHeight()) / (recognition.getImageHeight() * recognition.getImageWidth()));
                    telemetry.addData("Stone area over image area:", areaRatio);
                    if (areaRatio >= .9) {
                        telemetry.addLine("Nailed it!");
                    }
                }
            }
            telemetry.update();
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        if (robot.tensorFlowEngine != null) {
            robot.tensorFlowEngine.activate();

            waitForStart();

            mecanum.mecanumLeft(.9);
            sleep(1250);
            mecanum.mecanumNaught();
            sleep(250);
            mecanum.mecanumBack(.9);
            sleep(800);
            mecanum.mecanumNaught();

            while (opModeIsActive()) {

                if (robot.tensorFlowEngine != null) {
                    List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel() == robot.LABEL_SECOND_ELEMENT) {
                                telemetry.addLine("YAHOO!!");
                                telemetry.addData("Height:", recognition.getHeight());
                                telemetry.addData("Width:", recognition.getWidth());
                                float areaRatio = ((recognition.getWidth() * recognition.getHeight()) / (recognition.getImageHeight() * recognition.getImageWidth()));
                                telemetry.addData("Stone area over image area:", areaRatio);
                                if (areaRatio >= .9) {
                                    telemetry.addLine("Nailed it!");
                                }
                            }
                        }
                        telemetry.update();
                    }
                    // MAKE OWN FUNCTION!
                }
            }
        }
    }
}
