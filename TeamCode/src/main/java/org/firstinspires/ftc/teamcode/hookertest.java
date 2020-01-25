package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motion.Hookers;
import org.firstinspires.ftc.teamcode.motion.LeverArm;

@TeleOp(name="hookertest")

public class hookertest extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    Hookers hookers = new Hookers();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, telemetry);

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_stick_y != 0) {
                robot.hookLeft.setPosition(gamepad1.right_stick_y);
                robot.hookRight.setPosition(gamepad1.right_stick_y);
            }
        }
    }
}