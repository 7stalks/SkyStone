package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.motion.Clamp;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.LeverArm;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@TeleOp(name="RobotTeleop:)", group="Robot")
public class RobotTelop extends LinearOpMode {

    /* Declare OpMode members. */
    RobotHardware robot      = new RobotHardware();   // Use a Pushbot's hardware
    LeverArm lever_arm = new LeverArm();
    Clamp clamp = new Clamp();
    MecanumDrive mecanum_drive = new MecanumDrive();
    Kicker kicker = new Kicker();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x !=0) {
                mecanum_drive.mecanumDrive(telemetry, robot, gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad2.right_stick_x,
                        gamepad1.dpad_up, gamepad1.dpad_down);
                // Might need to put speedVal above "while (opModeIsActive())"
                // Might also want to add "dpad_up" and "dpad_down" into the if statement
            }
            if (gamepad1.right_trigger > 0 ) {
                kicker.KickerMove(robot);
            }
            else {
                robot.KickerServo.setPosition(-1);
            }
            if (gamepad2.left_stick_y < .5 && gamepad2.left_stick_y > -.5) {
                lever_arm.leverArmStay(robot, telemetry);
            }
            if (gamepad2.left_stick_y > .5 || gamepad2.left_stick_y < -.5) {
                lever_arm.moveLeverArm(robot, telemetry, -gamepad2.left_stick_y);
            }
            if (gamepad2.left_bumper || gamepad2.right_bumper) {
                clamp.setClamp(robot, gamepad2.left_bumper, gamepad2.right_bumper);
            }
            if (gamepad2.right_stick_y != 0) {
                clamp.moveClampRotator(robot, -gamepad2.right_stick_y);
            }
        }
    }
}
