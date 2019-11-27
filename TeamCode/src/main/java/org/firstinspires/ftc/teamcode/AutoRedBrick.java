package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoRedBrick")
public class AutoRedBrick extends LinearOpMode {
    RobotHardware robot = new RobotHardware();

    @Override

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        waitForStart();
        robot.leftDrive.setPower(-.4);
        robot.rightDrive.setPower(-.4);
        sleep(1200);
        robot.leftDrive.setPower(-.4);
        robot.rightDrive.setPower(.4);
        sleep(1100);
        robot.leftDrive.setPower(-.4);
        robot.rightDrive.setPower(-.4);
        sleep(2300);
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);

    }
}
