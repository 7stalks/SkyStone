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
    double speedVal = .5;
    double robotAngle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;
    final double stickThres = .25;
    final double noSpeed = 0;

    if (leftStickX >= stickThres || leftStickX <= -stickThres
            || leftStickY >= stickThres || leftStickY <= -stickThres
            || rightStickX >= stickThres || rightStickX <= - stickThres
            || (incSpeed)
            || (decSpeed)) {
        if ((incSpeed == true)) {
            speedVal = speedVal + .25;
        }
        if ((decSpeed == true) && speedVal >= .25) {
            speedVal = speedVal - .25;
        }
        if (speedVal >= 1) {
            speedVal = 1;
        }
        if (speedVal <= .25) {
            speedVal = .25;
        }
        final double LFarquaad = speedVal*r*Math.cos(robotAngle) + rightStickX;
        final double LBridget = speedVal*r*Math.sin(robotAngle) + rightStickX;
        final double RFrancisco = speedVal*r*Math.sin(robotAngle) - rightStickX;
        final double RBoomer = speedVal*r*Math.cos(robotAngle) - rightStickX;
        robot.LeftFront.setPower (LFarquaad);
        robot.LeftBack.setPower (LBridget);
        robot.RightFront.setPower (RFrancisco);
        robot.RightBack.setPower (RBoomer);

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

