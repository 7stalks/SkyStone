package org.firstinspires.ftc.teamcode.motion;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotHardware;


public class MecanumDrive {

    double robotAngle;
    final double stickThres = .25;

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void mecanumSmall (RobotHardware robot, boolean smallUp, boolean smallRight, boolean smallDown, boolean smallLeft){
        final double smallMove = .6;
        final double noSpeed = 0;
        if ((smallUp) || (smallRight) || (smallDown) || (smallLeft)) {
            if (smallUp) {
                robot.RightFront.setPower (-smallMove);
                robot.RightBack.setPower (-smallMove);
                robot.LeftFront.setPower (-smallMove);
                robot.LeftBack.setPower (-smallMove);
                sleep(10);
                robot.LeftFront.setPower (noSpeed);
                robot.LeftBack.setPower (noSpeed);
                robot.RightFront.setPower (noSpeed);
                robot.RightBack.setPower (noSpeed);
                sleep(200);
            }
        }
        //hahaha
        else if (smallRight) {
            robot.RightFront.setPower (smallMove);
            robot.RightBack.setPower (-smallMove);
            robot.LeftFront.setPower (-smallMove);
            robot.LeftBack.setPower (smallMove);
            sleep(10);
            robot.LeftFront.setPower (noSpeed);
            robot.LeftBack.setPower (noSpeed);
            robot.RightFront.setPower (noSpeed);
            robot.RightBack.setPower (noSpeed);
            sleep(200);
        }
        else if (smallLeft) {
            robot.RightFront.setPower (-smallMove);
            robot.RightBack.setPower (smallMove);
            robot.LeftFront.setPower (smallMove);
            robot.LeftBack.setPower (-smallMove);
            sleep(10);
            robot.LeftFront.setPower (noSpeed);
            robot.LeftBack.setPower (noSpeed);
            robot.RightFront.setPower (noSpeed);
            robot.RightBack.setPower (noSpeed);
            sleep(200);
        }
        else if (smallDown) {
            robot.RightFront.setPower (smallMove);
            robot.RightBack.setPower (smallMove);
            robot.LeftFront.setPower (smallMove);
            robot.LeftBack.setPower (smallMove);
            sleep(10);
            robot.LeftFront.setPower (noSpeed);
            robot.LeftBack.setPower (noSpeed);
            robot.RightFront.setPower (noSpeed);
            robot.RightBack.setPower (noSpeed);
            sleep(200);
        }
    }
    private void mecanumMove(Telemetry telemetry, RobotHardware robot, double leftStickY, double leftStickX, double rightStickX,
                             boolean incSpeed, boolean decSpeed) {

        if (leftStickX >= stickThres || leftStickX <= -stickThres
                || leftStickY >= stickThres || leftStickY <= -stickThres
                || rightStickX >= stickThres || rightStickX <= - stickThres
                || (incSpeed) || (decSpeed)) {


            double r = Math.hypot (leftStickX, leftStickY);
        robotAngle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;

            final double RFarquaad = r*Math.cos(robotAngle) + rightStickX;
            final double RBridget = r*Math.sin(robotAngle) + rightStickX;
            final double LFrancisco = r*Math.sin(robotAngle) - rightStickX;
            final double LBoomer = r*Math.cos(robotAngle) - rightStickX;
            robot.RightFront.setPower (RFarquaad);
            robot.RightBack.setPower (RBridget);
            robot.LeftFront.setPower (LFrancisco);
            robot.LeftBack.setPower (LBoomer);
//            telemetry.addLine("im working power on");
//            telemetry.addData("RFarquaad", RFarquaad);
//            telemetry.addData("LFrancisco", LFrancisco);
//            telemetry.addData("LBoomer", LBoomer);
//            telemetry.addData("RBridget", RBridget);
//            telemetry.update();
        } else {
            robot.LeftFront.setPower (robot.noSpeed);
            robot.LeftBack.setPower (robot.noSpeed);
            robot.RightFront.setPower (robot.noSpeed);
            robot.RightBack.setPower (robot.noSpeed);
//            telemetry.addLine("im working power off");
//            telemetry.update();
        }

    }

    public void mecanumDrive(Telemetry telemetry, RobotHardware robot, double leftStickY, double leftStickX, double rightStickX,
                             boolean incSpeed, boolean decSpeed) {
        mecanumMove(telemetry, robot, leftStickY, leftStickX, rightStickX, incSpeed, decSpeed);
    }

    public void mecanumDriveFast(Telemetry telemetry, RobotHardware robot, double leftStickY, double leftStickX, double rightStickX,
                             boolean incSpeed, boolean decSpeed) {
        mecanumMove(telemetry, robot, leftStickY, leftStickX, rightStickX, incSpeed, decSpeed);
    }

}
