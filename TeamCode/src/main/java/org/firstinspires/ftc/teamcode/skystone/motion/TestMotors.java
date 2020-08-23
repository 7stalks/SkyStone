package org.firstinspires.ftc.teamcode.skystone.motion;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;


    @TeleOp(name = "Test", group = "Robot")
    public class TestMotors extends LinearOpMode {
        OldRobotHardware robot = new OldRobotHardware(true);

        @Override
        public void runOpMode() {
            robot.init(hardwareMap, telemetry);

            waitForStart();

            while (opModeIsActive()) {

                if (gamepad1.left_stick_y != 0) {
                    robot.LeftFront.setPower(gamepad1.left_stick_y);
                }
                if (gamepad1.right_stick_y != 0) {
                    robot.RightFront.setPower(gamepad1.right_stick_y);
                }
                if (gamepad2.left_stick_y != 0) {
                    robot.LeftBack.setPower(gamepad2.left_stick_y);
                }
                if (gamepad2.right_stick_y != 0) {
                    robot.RightBack.setPower(gamepad2.right_stick_y);
                } else {
                    robot.LeftFront.setPower(0);
                    robot.RightFront.setPower(0);
                    robot.LeftBack.setPower(0);
                    robot.RightBack.setPower(0);
                }
            }
        }
    }
