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
        double Speed = .5;
        float leftY;
        float leftX;
        double rightX = .5;
        if (leftStickY >= robot.STICK_THRES || leftStickY <= -robot.STICK_THRES) {
            robot.RightFront.setPower(Speed);
            robot.LeftFront.setPower(Speed);
            robot.RightBack.setPower(Speed);
            robot.LeftBack.setPower(Speed);
        }
        else if (leftStickX >= robot.STICK_THRES || leftStickX <= -robot.STICK_THRES) {
            robot.LeftFront.setPower(-Speed);
            robot.RightFront.setPower(Speed);
            robot.LeftBack.setPower(Speed);
            robot.RightBack.setPower(-Speed);
        }
        else if (rightStickX >= robot.STICK_THRES || rightStickX <= -robot.STICK_THRES) {
            robot.LeftFront.setPower(rightX);
            robot.RightFront.setPower(-rightX);
            robot.LeftBack.setPower(rightX);
            robot.RightBack.setPower(-rightX);
        }
        else {
            robot.LeftFront.setPower(noSpeed);
            robot.RightFront.setPower(noSpeed);
            robot.LeftBack.setPower(noSpeed);
            robot.RightBack.setPower(noSpeed);
        }
        if (gamepad1.dpad_up || Speed < 1) {
            Speed = Speed + .1;
        }
        if (gamepad1.dpad_down || Speed > 0) {
            Speed = Speed + .1;
        }
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

