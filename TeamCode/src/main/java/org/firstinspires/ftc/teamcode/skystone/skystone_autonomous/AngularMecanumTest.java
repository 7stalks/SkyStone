package org.firstinspires.ftc.teamcode.skystone.skystone_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;

@Autonomous(name = "AngularMecanumTest")
@Disabled
public class AngularMecanumTest extends LinearOpMode {

    OldRobotHardware robot = new OldRobotHardware(false);
    AngularMecanum angularMecanum = new AngularMecanum(robot, telemetry);

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);

        waitForStart();
        while (opModeIsActive()) {
            angularMecanum.Left(-(Math.PI / 4), .6, 0);
            telemetry.update();
        }
    }
}