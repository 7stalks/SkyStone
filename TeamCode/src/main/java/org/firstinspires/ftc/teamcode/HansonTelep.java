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
    final double LFarquaad = Math.cos(robotAngle) + rightStickX;
    final double LBridget = Math.sin(robotAngle) + rightStickX;
    final double RFrancisco = Math.sin(robotAngle) - rightStickX;
    final double RBoomer = Math.cos(robotAngle) - rightStickX;


    if (leftStickX >= .4 || leftStickX <= -.4
            || leftStickY >= .4 || leftStickY <= -.4
            || rightStickX >=.4 || rightStickX <= -.4) {
        robot.LeftFront.setPower (LFarquaad);
        robot.LeftBack.setPower (LBridget);
        robot.RightFront.setPower (RFrancisco);
        robot.RightBack.setPower (RBoomer);
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

