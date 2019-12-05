package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "HansonTelep")
public class HansonTelep extends LinearOpMode {

    RobotHardware robot = new RobotHardware();

    // Please move to RobotHardware
    int noSpeed=0;
    double leftY = gamepad1.left_stick_y - .75;
    double leftX = gamepad1.left_stick_x - .75;
    double rightX = gamepad1.right_stick_x - .5;
    // Have mecanumDrive be a function of (mecanumSpeed*leftstickx, etc)? Let mecanumSpeed be a double
    public void mecanumDrive(double leftStickY, double leftStickX, double rightStickX) {

        if (leftStickY >= robot.STICK_THRES || leftStickY <= -robot.STICK_THRES) {
            robot.RightFront.setPower(leftY);
            robot.LeftFront.setPower(leftY);
            robot.RightBack.setPower(leftY);
            robot.LeftBack.setPower(leftY);
        }
        else if (leftStickX >= robot.STICK_THRES || leftStickX <= -robot.STICK_THRES) {
            robot.LeftFront.setPower(-leftX);
            robot.RightFront.setPower(leftX);
            robot.LeftBack.setPower(leftX);
            robot.RightBack.setPower(-leftX);
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
    }

    // Turn this into a double and put it into mecanumDrive's input (be sure to define it before
    // mecanumDrive though
    public void mecanumSpeed (boolean incSpeed, boolean decSpeed) {
        if (incSpeed==true) {
            leftX = leftX + .25;
            leftY = leftY + .25;
        }
        if (decSpeed==true) {
            leftX = leftX - .25;
            leftY = leftY - .25;
        }
        if (leftX >= 1) {
            leftX=1;
            telemetry.addData("SpeedX:", "Max");
        }
        if (leftX <= -1) {
            leftX=-1;
            telemetry.addData("SpeedX:", "Max");
        }
        if (leftY >= 1) {
            leftY=1;
            telemetry.addData("SpeedY:", "Max");
        }
        if (leftY <= -1) {
            leftY=-1;
            telemetry.addData("SpeedY:", "Min");
        }
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

            mecanumDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            telemetry.addData("Y Value:", gamepad1.left_stick_y);
            telemetry.addData("X Value", gamepad1.left_stick_x);
            telemetry.addData("Rotate Value:", gamepad1.right_stick_x);
            telemetry.update();

            if ((gamepad1.dpad_up==true ) || (gamepad1.dpad_down==true)) {
                mecanumSpeed(gamepad1.dpad_up, gamepad1.dpad_down);

            }


        }
    }
}
