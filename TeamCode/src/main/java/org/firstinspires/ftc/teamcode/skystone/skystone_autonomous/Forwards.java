package org.firstinspires.ftc.teamcode.skystone.skystone_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;
import org.firstinspires.ftc.teamcode.skystone.motion.MecanumDrive;


@Autonomous(name = "Forwards")

public class Forwards extends LinearOpMode {

    OldRobotHardware robot = new OldRobotHardware(true);
    SkystoneNavigation nav = new SkystoneNavigation();
    MecanumDrive meca = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, meca);

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
//        nav.skystoneNavigationInit(robot);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
//            sleep(10000);
            mecanum.mecanumFront(.8);
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

