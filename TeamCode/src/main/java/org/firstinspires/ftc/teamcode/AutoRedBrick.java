package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoRedBrick")
public class AutoRedBrick extends LinearOpMode {
    RobotHardware robot = new RobotHardware(true);

    @Override

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        waitForStart();
        robot.LeftFront.setPower(-.4);
        robot.RightFront.setPower(-.4);
        sleep(1200);
        robot.LeftFront.setPower(-.4);
        robot.RightFront.setPower(.4);
        sleep(1100);
        robot.LeftFront.setPower(-.4);
        robot.RightFront.setPower(-.4);
        sleep(2300);
        robot.LeftFront.setPower(0);
        robot.RightFront.setPower(0);

    }
}
