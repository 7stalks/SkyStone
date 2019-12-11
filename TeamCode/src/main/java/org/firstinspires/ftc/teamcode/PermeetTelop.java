package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motion.Kicker;

@TeleOp(name = "PermeetTelop")
public class PermeetTelop extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    Kicker kicker = new Kicker();

    @Override
    public void runOpMode() {

        // Initialize, wait for start
        robot.init(hardwareMap, telemetry);
        waitForStart();

        // Begins while loop, updates telemetry
        while (opModeIsActive()) {
            telemetry.addData("Status:", "Started");
            telemetry.update();
            if (gamepad1.right_trigger > 0 ) {
                kicker.KickerMove(robot);
            }
            else {
                robot.KickerServo.setPosition(-1);
            }
        }
    }
}
