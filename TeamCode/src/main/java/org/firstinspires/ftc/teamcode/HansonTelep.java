package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "HansonTelep")
public class HansonTelep extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    double speedVal         = .5;
    final double stickThres = .25;
    final double noSpeed    = 0;

    public void mecanumDrive(double leftStickY, double leftStickX, double rightStickX,
                             boolean incSpeed, boolean decSpeed) {

        double r = Math.hypot (leftStickX, leftStickY);
        double robotAngle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;

        if (leftStickX >= stickThres || leftStickX <= -stickThres
            || leftStickY >= stickThres || leftStickY <= -stickThres
            || rightStickX >= stickThres || rightStickX <= - stickThres
            || (incSpeed)
            || (decSpeed)) {

            if ((incSpeed)) {
            speedVal = speedVal + .25;
            }
            if ((decSpeed) && speedVal > .25) {
            speedVal = speedVal - .25;
            }
            if (speedVal >= 1) {
            speedVal = 1;
            }
            if (speedVal <= .25) {
            speedVal = .25;
            }

            final double RFarquaad = speedVal*r*Math.cos(robotAngle) + rightStickX;
            final double RBridget = speedVal*r*Math.sin(robotAngle) + rightStickX;
            final double LFrancisco = speedVal*r*Math.sin(robotAngle) - rightStickX;
            final double LBoomer = speedVal*r*Math.cos(robotAngle) - rightStickX;
            robot.RightFront.setPower (RFarquaad);
            robot.RightBack.setPower (RBridget);
            robot.LeftFront.setPower (LFrancisco);
            robot.LeftBack.setPower (LBoomer);
            telemetry.addLine("im working power on");
            telemetry.addData("RFarquaad", RFarquaad);
            telemetry.addData("LFrancisco", LFrancisco);
            telemetry.addData("LBoomer", LBoomer);
            telemetry.addData("RBridget", RBridget);
            telemetry.update();
        }

        else {
            robot.LeftFront.setPower (noSpeed);
            robot.LeftBack.setPower (noSpeed);
            robot.RightFront.setPower (noSpeed);
            robot.RightBack.setPower (noSpeed);
            telemetry.addLine("im working power off");
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
//ur dumb lol
