package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motion.LeverArm;

@TeleOp(name="GerritTelop")
public class GerritTelop extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    LeverArm lever_arm = new LeverArm();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        waitForStart();
        int position;
        int wanted;
        double distance;

        while (opModeIsActive()) {
            distance = gamepad2.left_stick_y;
            telemetry.addData("Value %.2d", distance);
            position = robot.leverArm.getCurrentPosition();
            telemetry.addData("position%.2d", position);
            telemetry.update();

            if (distance > .5) {
                if (position >= robot.ARM_UP_DISTANCE) {
                    telemetry.addData("Stopping %.2d", position);
                    telemetry.update();
                    robot.leverArm.setPower(0);
                }
                else if (position >= 900) {
                    telemetry.addData("Slowing %.2d", position);
                    telemetry.update();
                    robot.leverArm.setPower(-.02);
                }
                else if (position <= robot.ARM_UP_DISTANCE) {
                    telemetry.addData("Speeding %.2d", position);
                    telemetry.update();
                    robot.leverArm.setPower(.4);
                }
            }
            else if (distance < -.5) {
                if (position <= 100) {
                    telemetry.addData("Stopping < .5 distance %.2d", position);
                    telemetry.update();
                    robot.leverArm.setPower(0);
                }
                else if (position <= 700) {
                    telemetry.addData("Slowing < .5 distance %.2d", position);
                    telemetry.update();
                    robot.leverArm.setPower(.012);
                }
                else if (position >= 100) {
                    telemetry.addData("Speeding < .5 distance %.2d", position);
                    telemetry.update();
                    robot.leverArm.setPower(-.4);
                }
            }
            wanted = robot.leverArm.getCurrentPosition();
            position = robot.leverArm.getCurrentPosition();
            if (position > wanted) {
                telemetry.addData("Staying still > wanted  %.2d", position);
                telemetry.update();
                robot.leverArm.setPower(-.35);
            }
            else if (position < wanted) {
                telemetry.addData("Staying still < wanted  %.2d", position);
                telemetry.update();
                robot.leverArm.setPower(.35);
            }
        }
    }
}