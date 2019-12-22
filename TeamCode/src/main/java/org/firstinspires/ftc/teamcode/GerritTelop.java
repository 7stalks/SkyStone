package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motion.LeverArm;

@TeleOp(name="GerritTelop")
public class GerritTelop extends LinearOpMode {

    RobotHardware robot      = new RobotHardware();   // Use a Pushbot's hardware
    LeverArm lever_arm = new LeverArm();
    int counter       = 0;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);

        waitForStart();


        while (opModeIsActive()) {
            telemetry.addData("Counter", counter);
            lever_arm.moveLeverArmTest(robot, telemetry, gamepad2.left_stick_y);
            counter += 1;
        }
    }
}