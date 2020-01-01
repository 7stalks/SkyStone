package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@Autonomous(name = "AutoMecanumTest")
public class AutoMecanumTest extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();

    @Override
    public void runOpMode() {

        AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive, null);
        robot.init(hardwareMap, telemetry);
        waitForStart();

        while (opModeIsActive()) {
            mecanum.mecanumForward(4);
            mecanum.mecanumNaught();
            sleep(10000);
            break;
        }
    }
}
