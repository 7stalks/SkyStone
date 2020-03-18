package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@TeleOp(name = "Chassis Teleop", group = "Robot")
public class ChassisTeleop extends LinearOpMode {

    OldRobotHardware robot = new OldRobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    boolean speedUp = true;

    @Override
    public void runOpMode() {

        robot.initMecanum(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                speedUp = true;
            } else if (gamepad1.b) {
                speedUp = false;
            }

            if (!speedUp) {
                mecanum_drive.mecanumDrive(
                        telemetry, robot,
                        gamepad1.left_stick_y, gamepad1.left_stick_x,
                        gamepad1.right_stick_x,
                        gamepad1.dpad_up, gamepad1.dpad_right, gamepad1.dpad_down, gamepad1.dpad_left);
            } else {
                mecanum_drive.mecanumDriveFast(telemetry, robot,
                        gamepad1.left_stick_y, gamepad1.left_stick_x,
                        gamepad1.right_stick_x,
                        gamepad1.dpad_up, gamepad1.dpad_right, gamepad1.dpad_down, gamepad1.dpad_left);
            }
        }
    }
}