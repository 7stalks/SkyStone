package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotHardware;

@Autonomous(name = "AngularMecanumTest")
@Disabled
public class AngularMecanumTest extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
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