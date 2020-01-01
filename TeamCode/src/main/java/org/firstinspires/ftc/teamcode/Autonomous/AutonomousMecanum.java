package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

public class AutonomousMecanum {

    public void mecanumLeft(MecanumDrive mecanum_drive, Telemetry telemetry, RobotHardware robot, LinearOpMode runOpMode, long inches) {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, -.9, 0, false, false);
        runOpMode.sleep(500*inches);
    }
}
