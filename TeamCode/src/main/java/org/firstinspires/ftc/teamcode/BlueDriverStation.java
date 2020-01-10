package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Autonomous.AngularMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.ArrayList;
import java.util.List;


@Autonomous(name = "BlueDriverStation")
public class BlueDriverStation extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    SkystoneNavigation nav = new SkystoneNavigation();
    MecanumDrive mecanumDrive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanumDrive);
    AngularMecanum angularMecanum = new AngularMecanum(robot, telemetry);

    double robotAngle;

    private void tangentTime(double X, double Y) {
        robotAngle = Math.atan((-905-X)/(1090-Y));
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        nav.skystoneNavigationInit(robot);

        waitForStart();

        nav.SkystoneNavigation(telemetry);

        while (opModeIsActive()) {
            nav.SkystoneNavigationNoTelemetry();
//            while (nav.Y < 1090) {
//                nav.SkystoneNavigationNoTelemetry();
//                telemetry.addData("Rotation:", nav.Rotation);
//                telemetry.addData("My X is", nav.X);
//                telemetry.addData("My Y is", nav.Y);
//                tangentTime(nav.X, nav.Y);
//                telemetry.addData("Tangent angle:", robotAngle);
//                angularMecanum.Left(robotAngle, .5, 0);
//                nav.SkystoneNavigationNoTelemetry();
//                telemetry.update();
//            }
//            mecanum.mecanumNaught();
            telemetry.addData("My X is", nav.X);
            telemetry.addData("My Y is", nav.Y);
            telemetry.addData("Rotation:", nav.Rotation);
            telemetry.update();
        }
    }
}