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
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;

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


    public void getNavStuff() {
        skystoneNav.skystoneNavigationInit(robot);
        skystoneNav.targetsSkyStone.activate();
        skystoneNav.SkystoneNavigation(telemetry);
        skystoneNav.targetsSkyStone.deactivate();
    }

    public void getTFODStuff(){
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tensorFlowEngine = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, robot.vuforia);
        tensorFlowEngine.loadModelFromAsset(robot.TFOD_MODEL_ASSET, robot.LABEL_FIRST_ELEMENT,
                robot.LABEL_SECOND_ELEMENT);
        telemetry.addData("Status", "Tensor Flow Object Detection Initialized");
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);



    }
}
