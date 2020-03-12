package org.firstinspires.ftc.teamcode.oldteleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotHardware;
import org.firstinspires.ftc.teamcode.motion.Kicker;

@TeleOp(name = "PermeetTelop")
@Disabled

public class PermeetTelop extends LinearOpMode {

    RobotHardware robot = new RobotHardware(true);
    Kicker kicker = new Kicker();

    @Override
    public void runOpMode() {

        // Initialize, wait for start
        robot.init(hardwareMap, telemetry);
        waitForStart();

        // Begins while loop, updates telemetry
        while (opModeIsActive()) {
            if (gamepad1.right_trigger > 0) {
                telemetry.addData("Right Trigger", "Hit");
                telemetry.update();
                kicker.KickerMove(robot);
            } else {
                telemetry.addData("Right Trigger", "Off");
                telemetry.update();
                kicker.KickerSet(robot, -1);
            }
        }
    }
}