package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.motion.Clamp;
import org.firstinspires.ftc.teamcode.motion.Hookers;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.LeverArm;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;

@TeleOp(name="RobotTeleop:)", group="Robot")
public class RobotTelop extends LinearOpMode {

    /* Declare OpMode members. */
    RobotHardware robot = new RobotHardware(false);   // Use a Pushbot's hardware
    LeverArm lever_arm = new LeverArm();
    Clamp clamp = new Clamp();
    MecanumDrive mecanum_drive = new MecanumDrive();
    MecanumDrive mecanum_small = new MecanumDrive();
    MecanumDrive rotate_small = new MecanumDrive();
    Kicker kicker = new Kicker();
    Hookers hookers = new Hookers();
    boolean speedUp = false;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);

        if (robot.tensorFlowEngine != null) {
            robot.tensorFlowEngine.activate();
        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Establishes the speed value boolean. Mecanum controller A to speedup, B to slow down.
            if (gamepad1.a) {
                speedUp = true;
            } else if (gamepad1.b) {
                speedUp = false;
            }

            // Does mecanumDrive (either fast or normal based on above) and inputs several buttons
            // If the buttons aren't pressed then the function does nothing
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

            // clamp controller D PAD
            // does mecanumSmall based on the inputs from the clamp driver's controller
            mecanum_small.mecanumSmall(
                    robot, gamepad2.dpad_up, gamepad2.dpad_right, gamepad2.dpad_down, gamepad2.dpad_left);

            rotate_small.rotateSmall(
                    robot, gamepad2.right_trigger, gamepad2.left_trigger);

            // handsOn = clamp controller X
            if (robot.handsOn != null){
                if (gamepad2.x) {
                    robot.handsOn.setPosition(1);
                } else {
                    robot.handsOn.setPosition(robot.MID_SERVO);
                }
            }

            // hookers = driver controller X
            if (robot.hookLeft != null || robot.hookRight != null) {
                if (gamepad1.x) {
                    hookers.Hooker(robot);
                } else {
                    robot.hookRight.setPosition(robot.MID_SERVO);
                    robot.hookLeft.setPosition(robot.MID_SERVO);
                }
            }

            // hansen = clamp controller A
            if (robot.hansen != null) {
                if (gamepad2.a) {
                    robot.hansen.setPosition(.075);
                } else {
                    robot.hansen.setPosition(.425);
                }
            }

            // hansen rotator = clamp controller B
            if (robot.hansenRotator != null) {
                if (gamepad2.b) {
                    robot.hansenRotator.setPosition(0);
                } else {
                    robot.hansenRotator.setPosition(.5);
                }
            }

            // If touch funnel is activated, close kicker
            // Else if RIGHT TRIGGER is clicked, also close kicker
            if (robot.digitalTouchFunnel != null) {
                if (gamepad1.right_trigger > 0 || !robot.digitalTouchFunnel.getState()) {
                    kicker.KickerMove(robot);
                } else {
                    if (robot.KickerServo != null) {
                        robot.KickerServo.setPosition(0);
                    }
                }
            } else {
                if (gamepad1.right_trigger > 0) {
                    kicker.KickerMove(robot);
                } else {
                    if (robot.KickerServo != null) {
                        robot.KickerServo.setPosition(0);
                    }
                }
            }

            // stay lever arm
            if (gamepad2.left_stick_y < .5 && gamepad2.left_stick_y > -.5) {
                lever_arm.leverArmStay(robot, telemetry);
            }

            // lever arm = clamp controller LEFT STICK Y
            if (gamepad2.left_stick_y > .5 || gamepad2.left_stick_y < -.5) {
                lever_arm.moveLeverArm(robot, telemetry, -gamepad2.left_stick_y);
            }

            // clamp = clamp controller BUMPERS
            // LEFT = OPEN, RIGHT = CLOSE
            if (gamepad2.left_bumper || gamepad2.right_bumper) {
                clamp.setClamp(robot, gamepad2.left_bumper, gamepad2.right_bumper);
            }

            // clamp rotator = clamp controller RIGHT STICK Y
            if (gamepad2.right_stick_y != 0) {
                clamp.moveClampRotator(robot, -gamepad2.right_stick_y);
            }

            // tensor flow
            if (robot.tensorFlowEngine != null) {
                List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                    }
                    telemetry.update();
                }
            }
        }

        if (robot.tensorFlowEngine != null) {
            robot.tensorFlowEngine.shutdown();
        }
    }
}

