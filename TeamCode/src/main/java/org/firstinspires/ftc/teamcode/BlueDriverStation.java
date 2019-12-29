package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

@Autonomous (name = "BlueDriverStation")
public class BlueDriverStation extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);

        // Part of SkyStone Navigation Init
        SkystoneNavigation skystoneNav = new SkystoneNavigation();
        //
        skystoneNav.skystoneNavigationInit(robot);
        //
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(skystoneNav.targetsSkyStone);
        //

        waitForStart();

        skystoneNav.targetsSkyStone.activate();
        while (!isStopRequested()) {

            skystoneNav.SkystoneNavigation(telemetry);
            telemetry.update();
        }
        // Disable Tracking when we are done;
        skystoneNav.targetsSkyStone.deactivate();
    }
}
