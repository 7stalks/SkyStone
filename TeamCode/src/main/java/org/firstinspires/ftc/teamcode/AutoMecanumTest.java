package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;

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
