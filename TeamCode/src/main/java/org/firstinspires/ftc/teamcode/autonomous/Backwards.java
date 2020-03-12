package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;


@Autonomous(name = "Backwards")
@Disabled

public class Backwards extends LinearOpMode {

    RobotHardware robot = new RobotHardware(true);
    SkystoneNavigation nav = new SkystoneNavigation();
    MecanumDrive meca = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, meca);

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
//        nav.skystoneNavigationInit(robot);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            sleep(10000);
            mecanum.mecanumBack(.8);
            sleep(2500);
            mecanum.mecanumNaught();
            break;
//            nav.SkystoneNavigationNoTelemetry();
//            telemetry.addData("X", nav.X);
//            telemetry.addData("Y", nav.Y);
//            telemetry.addData("Rotation", nav.Rotation);
//            telemetry.update();
        }
    }
}

