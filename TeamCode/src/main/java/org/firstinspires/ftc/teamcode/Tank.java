package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TANK")
public class Tank extends LinearOpMode {

    RobotHardware robot = new RobotHardware(true);

    public void tankDrive(double left,double right) throws InterruptedException {
        if (gamepad1.left_stick_y >= .1 || gamepad1.left_stick_y <= -.1) {
            robot.LeftFront.setPower(left / 2);
        }
        if (gamepad1.right_stick_y >= .1 || gamepad1.right_stick_y <= -.1) {
            robot.RightFront.setPower(right / 2);
        }
        robot.LeftFront.setPower(0);
        robot.RightFront.setPower(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        waitForStart();
        int counter = 0;
        while (opModeIsActive()) {

            if(gamepad1.left_stick_y != 0 || gamepad1.right_stick_y != 0) {
                tankDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
            }
        }
    }
}
