package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class LeverArm {

    int position;
    int wanted;
    int Encoder;

    public void leverArmStay(RobotHardware robot, Telemetry telemetry) {
        if (robot.leverArm == null){
            return;
        }
        position = robot.leverArm.getCurrentPosition();
        if(position > wanted) {
            robot.leverArm.setPower(-.05);
            telemetry.addData("stay %.2d", wanted);
            telemetry.addData("Position %.2d", position);
            telemetry.update();
        }
        if(position < wanted) {
            robot.leverArm.setPower(.05);
            telemetry.addData("stay %.2d", wanted);
            telemetry.addData("Position %.2d", position);
            telemetry.update();
        }
    }

    public void moveLeverArmTest(RobotHardware robot, Telemetry telemetry, double distance) {
        telemetry.addData("Distance %.2d", distance);
        Encoder = robot.leverArm.getCurrentPosition();
        telemetry.addData("Encoder %.2d", Encoder);
        robot.leverArm.setPower(distance * .50);
        telemetry.update();
    }

    public void moveLeverArm(RobotHardware robot, Telemetry telemetry, double distance){

        int position = robot.leverArm.getCurrentPosition();
        if (telemetry != null) {
            telemetry.addData("Distance %.2d", distance);
            telemetry.addData("Position %.2d", position);
            telemetry.update();
        }

        if (distance > .5) {
            if (telemetry != null ) {
                telemetry.addData("Distance > .5", distance);
                telemetry.update();
            }
            if (position >= robot.ARM_UP_DISTANCE) {
                if (telemetry != null ) {
                    telemetry.addData("Position >= 1600", position);
                    telemetry.update();
                }
                robot.leverArm.setPower(0);
            }
            else if (position < robot.ARM_UP_DISTANCE) {
                if (telemetry != null) {
                    telemetry.addData("Position <= 1600", position);
                    telemetry.update();
                }
                robot.leverArm.setPower(.25);

            }
        }
        if (distance < -.5) {
            if (telemetry != null ) {
                telemetry.addData("Distance < -.5", distance);
                telemetry.update();
            }
            if (position <= 100) {
                if (telemetry != null) {
                    telemetry.addData("Position < 100", position);
                    telemetry.update();
                }
                robot.leverArm.setPower(0);
            }
            else if (position > 100) {
                if (telemetry != null) {
                    telemetry.addData("Position > 100", position);
                    telemetry.update();
                }
                robot.leverArm.setPower(-.25);
            }
        }
        wanted = robot.leverArm.getCurrentPosition();
    }

}
