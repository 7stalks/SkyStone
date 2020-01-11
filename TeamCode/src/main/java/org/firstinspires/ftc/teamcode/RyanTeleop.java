package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;

import java.util.List;


@Autonomous(name = "RyanTeleop")
public class RyanTeleop extends LinearOpMode {

    RobotHardware robot = new RobotHardware(true);
    SkystoneNavigation nav = new SkystoneNavigation();

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        nav.skystoneNavigationInit(robot);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            nav.SkystoneNavigationNoTelemetry();
            telemetry.addData("X", nav.X);
            telemetry.addData("Y", nav.Y);
            telemetry.addData("Rotation", nav.Rotation);
            telemetry.update();
        }
    }
}

