package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "HansonTelep")
public class HansonTelep extends LinearOpMode {

    RobotHardware robot = new RobotHardware();

    // Please move to RobotHardware
    int noSpeed=0;

    // Have mecanumDrive be a function of (mecanumSpeed*leftstickx, etc)? Let mecanumSpeed be a double
    public void mecanumDrive(double leftStickY, double leftStickX, double rightStickX) {

        if (leftStickY > robot.STICK_THRES || leftStickY < -robot.STICK_THRES) {
            robot.RightFront.setPower(leftStickY);
            robot.LeftFront.setPower(leftStickY);
            robot.RightBack.setPower(leftStickY);
            robot.LeftBack.setPower(leftStickY);
        }
        else if (leftStickX > robot.STICK_THRES || leftStickX < -robot.STICK_THRES) {
            robot.LeftFront.setPower(-leftStickX);
            robot.RightFront.setPower(leftStickX);
            robot.LeftBack.setPower(leftStickX);
            robot.RightBack.setPower(-leftStickX);
        }
        else if (rightStickX > robot.STICK_THRES || rightStickX < -robot.STICK_THRES) {
            robot.LeftFront.setPower(rightStickX);
            robot.RightFront.setPower(-rightStickX);
            robot.LeftBack.setPower(rightStickX);
            robot.RightBack.setPower(-rightStickX);
        }
        else {
            robot.LeftFront.setPower(noSpeed);
            robot.RightFront.setPower(noSpeed);
            robot.LeftBack.setPower(noSpeed);
            robot.RightBack.setPower(noSpeed);
        }
    }

    // Turn this into a double and put it into mecanumDrive's input (be sure to define it before
    // mecanumDrive though
    public void mecanumSpeed (boolean incSpeed, boolean decSpeed) {
    }

    // Delete mecanumLoseSpeed
    public void mecanumLoseSpeed (boolean decSpeed) {
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

            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x !=0) {
                mecanumDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            }
            if ((gamepad1.dpad_up=true ) || (gamepad1.dpad_down=true)) {
                mecanumSpeed(gamepad1.dpad_up, gamepad1.dpad_up);
            }
        }
    }
}
