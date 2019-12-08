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
    double r = Math.hypot (leftStickX, leftStickY);
    double robotAngle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;
    final double LFarquaad = r*Math.cos(robotAngle) + rightStickX;
    final double LBridget = r*Math.sin(robotAngle) + rightStickX;
    final double RFrancisco = r*Math.sin(robotAngle) - rightStickX;
    final double RBoomer = r*Math.cos(robotAngle) - rightStickX;
    final double stickThres = .25;
    final double noSpeed = 0;
    final double modSpeed = 2/3;
    final double slowSpeed =  1/3;
    final double fastasfrickSpeed = 1;
    if (leftStickX >= stickThres || leftStickX <= -stickThres
            || leftStickY >= stickThres || leftStickY <= -stickThres
            || rightStickX >= stickThres || rightStickX <= - stickThres) {
        robot.LeftFront.setPower (modSpeed * LFarquaad);
        robot.LeftBack.setPower (modSpeed * LBridget);
        robot.RightFront.setPower (modSpeed * RFrancisco);
        robot.RightBack.setPower (modSpeed * RBoomer);
    }
        if (leftStickX >= stickThres || leftStickX <= -stickThres
                || leftStickY >= stickThres || leftStickY <= -stickThres
                || rightStickX >= stickThres || rightStickX <= - stickThres
                && incSpeed == true) {
            robot.LeftFront.setPower (fastasfrickSpeed * LFarquaad);
            robot.LeftBack.setPower (fastasfrickSpeed * LBridget);
            robot.RightFront.setPower (fastasfrickSpeed * RFrancisco);
            robot.RightBack.setPower (fastasfrickSpeed * RBoomer);
            }
    else {
        robot.LeftFront.setPower (noSpeed);
        robot.LeftBack.setPower (noSpeed);
        robot.RightFront.setPower (noSpeed);
        robot.RightBack.setPower (noSpeed);
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

            mecanumDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, gamepad1.dpad_up, gamepad1.dpad_down);
            telemetry.addData("Y Value:", gamepad1.left_stick_y);
            telemetry.addData("X Value", gamepad1.left_stick_x);
            telemetry.addData("Rotate Value:", gamepad1.right_stick_x);
            telemetry.update();


            }
        }
    }

