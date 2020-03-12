package org.firstinspires.ftc.teamcode.oldteleops;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.motion.Clamp;
import org.firstinspires.ftc.teamcode.motion.TrayGrabbers;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.LeverArm;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;

@TeleOp(name = "RedRobotTeleop:)", group = "Robot")
public class RedRobotTeleop extends LinearOpMode {

    /* Declare OpMode members. */
    RobotHardware robot = new RobotHardware(false);   // Use a Pushbot's hardware
    LeverArm lever_arm = new LeverArm();
    Clamp clamp = new Clamp();
    MecanumDrive mecanum_drive = new MecanumDrive();
    MecanumDrive mecanum_small = new MecanumDrive();
    MecanumDrive rotate_small = new MecanumDrive();
    Kicker kicker = new Kicker();
    TrayGrabbers trayGrabbers = new TrayGrabbers();
    boolean speedUp = false;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);

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

            // skystoneBack rotator & skystoneBack
            // Grabber down = clamp controler A
            // Grabber up = clamp controller X
            if (robot.skystoneBack != null && robot.skystoneBackRotator != null) {
                if (gamepad2.y) {
                    robot.skystoneBack.setPosition(robot.SKYSTONE_GRABBER_DOWN);
                    sleep(375);
                    robot.skystoneBackRotator.setPosition(robot.MID_SERVO);
                } else if (gamepad2.b) {
                    robot.skystoneBack.setPosition(robot.MID_SERVO);
                    sleep(375);
                    robot.skystoneBackRotator.setPosition(robot.SKYSTONE_ROTATOR_DOWN);
                } else if (gamepad2.a) {
                    robot.skystoneBackRotator.setPosition(robot.MID_SERVO);
                    sleep(375);
                    robot.skystoneBack.setPosition(robot.SKYSTONE_GRABBER_DOWN);
                } else if (gamepad2.x) {
                    robot.skystoneBackRotator.setPosition(robot.SKYSTONE_ROTATOR_DOWN);
                    sleep(375);
                    robot.skystoneBack.setPosition(robot.MID_SERVO);
                }
            }

            // skystoneFront rotator & skystoneFront = clamp controller Y
            // Grabber down = clamp controller B
            // Grabber up = clamp controller Y
            if (robot.skystoneFrontRotator != null) {
                if (gamepad1.y) {
                    robot.skystoneFrontRotator.setPosition(robot.MID_SERVO);
                    sleep(400);
                    robot.skystoneFrontRotator.setPosition(0);
                }
            }

            // trayGrabbers = driver controller X
            if (robot.trayGrabberLeft != null || robot.trayGrabberRight != null) {
                trayGrabbers.trayGrabberMove(robot, gamepad1.x);
            }

            // Else if RIGHT TRIGGER is clicked, also close kicker
            if (gamepad1.right_trigger > 0) {
                kicker.KickerMove(robot);
            } else {
                if (robot.KickerServo != null) {
                    robot.KickerServo.setPosition(1);
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
        }
    }
}