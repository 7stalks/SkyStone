package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

public class AutonomousMecanum {

    RobotHardware robot;
    Telemetry telemetry;
    MecanumDrive mecanum_drive;

    public AutonomousMecanum(RobotHardware robob, Telemetry telemetryhard, MecanumDrive mecanumFly) {
        robot = robob;
        telemetry = telemetryhard;
        mecanum_drive = mecanumFly;
    }

    public void mecanumLeft(double power) {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, -power, 0, false, false, false, false);
    }

    public void mecanumFLeft (double power) {
        mecanum_drive.mecanumDriveFast(telemetry, robot, 0, -power, 0, false, false, false, false);
    }

    public void mecanumRight(double power) {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, power, 0, false, false, false, false);
    }

    public void mecanumFRight (double power) {
        mecanum_drive.mecanumDriveFast(telemetry, robot, 0, power, 0, false, false, false, false);

    }

    public void mecanumNaught() {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, 0, 0, false, false, false, false);
    }

    public void mecanumBack(double power) {
        mecanum_drive.mecanumDrive(telemetry, robot, power, 0, 0, false, false, false, false);
    }

    public void mecanumFBack(double power) {
        mecanum_drive.mecanumDriveFast(telemetry, robot, power, 0, 0, false, false, false, false);
    }


    public void mecanumFront(double power) {
        mecanum_drive.mecanumDrive(telemetry, robot, -power, 0, 0, false, false, false, false);
    }

    public void mecanumFFront(double power) {
        mecanum_drive.mecanumDriveFast(telemetry, robot, -power, 0, 0, false, false, false, false);
    }

    public void mecanumRotate (double directionPower) {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, 0, directionPower, false, false, false, false);
    }

    public void mecanumFullFront () {
        mecanum_drive.mecanumDrive(telemetry, robot, 0, 0, 0, true, false, false, false);
    }
}
