package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@Autonomous(name = "AutoMecanumTest")
@Disabled

public class AutoMecanumTest extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanumDrive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanumDrive);

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            mecanum.mecanumFront(.6);
            sleep(2000);
            mecanum.mecanumLeft(.6);
            sleep(2000);
            mecanum.mecanumBack(.6);
            sleep(2000);
            mecanum.mecanumRight(.6);
            sleep(2000);
        }
    }
}