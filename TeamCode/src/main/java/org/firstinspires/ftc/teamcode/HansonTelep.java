package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "HansonTelep")
public class HansonTelep extends LinearOpMode {

    RobotHardware robot = new RobotHardware();

    // Please move to RobotHardware

    // Have mecanumDrive be a function of (mecanumSpeed*leftstickx, etc)? Let mecanumSpeed be a double
    public void mecanumDrive(double leftStickY, double leftStickX, double rightStickX,
                             boolean incSpeed, boolean decSpeed) {
        int noSpeed=0;
        double Speed = .4;
        double rightRotX = .5;

        if ((gamepad1.dpad_up = true) && Speed < 1) {
            Speed = Speed + .2;
        }
        if ((gamepad1.dpad_down = true) && Speed > 0) {
            Speed = Speed - .2;
        }
        if (leftStickY >= robot.STICK_THRES || leftStickY <= -robot.STICK_THRES) {
            robot.RightFront.setPower(Speed * leftStickY);
            robot.LeftFront.setPower(Speed * leftStickY);
            robot.RightBack.setPower(Speed * leftStickY);
            robot.LeftBack.setPower(Speed * leftStickY);
        }
        if (leftStickX >= robot.STICK_THRES || leftStickX <= -robot.STICK_THRES) {
            robot.LeftFront.setPower(-Speed * leftStickX);
            robot.RightFront.setPower(Speed * leftStickX);
            robot.LeftBack.setPower(Speed * leftStickX);
            robot.RightBack.setPower(-Speed * leftStickX);
        }
        if (rightStickX >= robot.STICK_THRES || rightStickX <= -robot.STICK_THRES) {
            robot.LeftFront.setPower(rightRotX);
            robot.RightFront.setPower(-rightRotX);
            robot.LeftBack.setPower(rightRotX);
            robot.RightBack.setPower(-rightRotX);
        }
        else {
            robot.LeftFront.setPower(noSpeed);
            robot.RightFront.setPower(noSpeed);
            robot.LeftBack.setPower(noSpeed);
            robot.RightBack.setPower(noSpeed);
        }

        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double RightX = gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) + RightX;
        final double v2 = r * Math.sin(robotAngle) - RightX;
        final double v3 = r * Math.sin(robotAngle) + RightX;
        final double v4 = r * Math.cos(robotAngle) - RightX;

        robot.LeftFront.setPower(v1);
        robot.RightFront.setPower(v2);
        robot.LeftBack.setPower(v3);
        robot.RightBack.setPower(v4);
    // Turn this into a double and put it into mecanumDrive's input (be sure to define it before
    // mecanumDrive though


    }
    @Override
    public void runOpMode() {

        // Initialize, wait for start
        robot.init(hardwareMap, telemetry);
        waitForStart();

        // Begins while loop, updates telemetry
        while (opModeIsActive()) {
            telemetry.addData("Status:", "Started");
            telemetry.update();

            mecanumDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.dpad_up, gamepad1.dpad_down);
            telemetry.addData("Y Value:", gamepad1.left_stick_y);
            telemetry.addData("X Value", gamepad1.left_stick_x);
            telemetry.addData("Rotate Value:", gamepad1.right_stick_x);
            telemetry.update();


            }
        }
    }

