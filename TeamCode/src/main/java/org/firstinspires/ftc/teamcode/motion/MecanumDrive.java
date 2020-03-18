package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OldRobotHardware;


public class MecanumDrive {

    double robotAngle;
    double speedVal = 1.0;

    public void rotateSmall (OldRobotHardware robot, float rotRight, float rotLeft) {
            if (rotRight >= .2) {
                robot.RightFront.setPower(robot.smallRot);
                robot.RightBack.setPower(robot.smallRot);
                robot.LeftFront.setPower(-robot.smallRot);
                robot.LeftBack.setPower(-robot.smallRot);
                robot.sleep(40);
                robot.LeftFront.setPower(robot.noSpeed);
                robot.LeftBack.setPower(robot.noSpeed);
                robot.RightFront.setPower(robot.noSpeed);
                robot.RightBack.setPower(robot.noSpeed);
                robot.sleep(130);
            }
            else if (rotLeft >= .2) {
                robot.RightFront.setPower(-robot.smallRot);
                robot.RightBack.setPower(-robot.smallRot);
                robot.LeftFront.setPower(robot.smallRot);
                robot.LeftBack.setPower(robot.smallRot);
                robot.sleep(40);
                robot.LeftFront.setPower(robot.noSpeed);
                robot.LeftBack.setPower(robot.noSpeed);
                robot.RightFront.setPower(robot.noSpeed);
                robot.RightBack.setPower(robot.noSpeed);
                robot.sleep(130);
           }
        }

        // nailed it

    public void mecanumSmall (OldRobotHardware robot, boolean smallUp, boolean smallRight, boolean smallDown, boolean smallLeft) {
        if ((smallUp) || (smallRight) || (smallDown) || (smallLeft)){
            if (smallUp) {
                robot.RightFront.setPower(-robot.smallMove);
                robot.RightBack.setPower(-robot.smallMove);
                robot.LeftFront.setPower(-robot.smallMove);
                robot.LeftBack.setPower(-robot.smallMove);
                robot.sleep(35);
                robot.LeftFront.setPower(robot.noSpeed);
                robot.LeftBack.setPower(robot.noSpeed);
                robot.RightFront.setPower(robot.noSpeed);
                robot.RightBack.setPower(robot.noSpeed);
                robot.sleep(150);
            }
            else if (smallRight) {
                robot.RightFront.setPower(robot.smallMove);
                robot.RightBack.setPower(-robot.smallMove);
                robot.LeftFront.setPower(-robot.smallMove);
                robot.LeftBack.setPower(robot.smallMove);
                robot.sleep(35);
                robot.LeftFront.setPower(robot.noSpeed);
                robot.LeftBack.setPower(robot.noSpeed);
                robot.RightFront.setPower(robot.noSpeed);
                robot.RightBack.setPower(robot.noSpeed);
                robot.sleep(150);
            } else if (smallLeft) {
                robot.RightFront.setPower(-robot.smallMove);
                robot.RightBack.setPower(robot.smallMove);
                robot.LeftFront.setPower(robot.smallMove);
                robot.LeftBack.setPower(-robot.smallMove);
                robot.sleep(35);
                robot.LeftFront.setPower(robot.noSpeed);
                robot.LeftBack.setPower(robot.noSpeed);
                robot.RightFront.setPower(robot.noSpeed);
                robot.RightBack.setPower(robot.noSpeed);
                robot.sleep(150);
            } else if (smallDown) {
                robot.RightFront.setPower(robot.smallMove);
                robot.RightBack.setPower(robot.smallMove);
                robot.LeftFront.setPower(robot.smallMove);
                robot.LeftBack.setPower(robot.smallMove);
                robot.sleep(35);
                robot.LeftFront.setPower(robot.noSpeed);
                robot.LeftBack.setPower(robot.noSpeed);
                robot.RightFront.setPower(robot.noSpeed);
                robot.RightBack.setPower(robot.noSpeed);
                robot.sleep(150);
            }
        }
    }
    private void mecanumMove(Telemetry telemetry, OldRobotHardware robot, double leftStickY, double leftStickX, double rightStickX,
                             boolean fullUp, boolean fullRight, boolean fullDown, boolean fullLeft, double speedVal) {

        double r = Math.hypot (leftStickX, leftStickY);
        robotAngle = Math.atan2(leftStickY, leftStickX) - Math.PI / 4;
        if ((fullUp) || (fullRight) || (fullDown) || (fullLeft)) {
            if (fullUp) {
                robot.RightFront.setPower(-1);
                robot.RightBack.setPower(-1);
                robot.LeftFront.setPower(-1);
                robot.LeftBack.setPower(-1);
//                telemetry.addLine("IM GOING FASTER FORWARD");
//                telemetry.update();
            }

            else if (fullRight){
                robot.RightFront.setPower(1);
                robot.RightBack.setPower(-1);
                robot.LeftFront.setPower(-1);
                robot.LeftBack.setPower(1);
//                telemetry.addLine("IM GOING FASTER RIGHT");
//                telemetry.update();
            }
            else if (fullLeft){
                robot.RightFront.setPower(-1);
                robot.RightBack.setPower(1);
                robot.LeftFront.setPower(1);
                robot.LeftBack.setPower(-1);
//                telemetry.addLine("IM GOING FASTER LEFT");
//                telemetry.update();
            }
            else if (fullDown) {
                robot.RightFront.setPower(1);
                robot.RightBack.setPower(1);
                robot.LeftFront.setPower(1);
                robot.LeftBack.setPower(1);
//                telemetry.addLine("IM GOING FASTER DOWN");
//                telemetry.update();

            }
        } else if (leftStickX >= robot.stickThres || leftStickX <= -robot.stickThres
                || leftStickY >= robot.stickThres || leftStickY <= -robot.stickThres
                || rightStickX >= robot.stickThres || rightStickX <= -robot.stickThres) {

            final double RFarquaad = speedVal*r*Math.cos(robotAngle) + rightStickX;
            final double RBridget = speedVal*r*Math.sin(robotAngle) + rightStickX;
            final double LFrancisco = speedVal*r*Math.sin(robotAngle) - rightStickX;
            final double LBoomer = speedVal*r*Math.cos(robotAngle) - rightStickX;
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
        }

        else {
            robot.LeftFront.setPower (robot.noSpeed);
            robot.LeftBack.setPower (robot.noSpeed);
            robot.RightFront.setPower (robot.noSpeed);
            robot.RightBack.setPower (robot.noSpeed);
//            telemetry.addLine("im working power off");
//            telemetry.update();
        }
    }

    public void mecanumDrive(Telemetry telemetry, OldRobotHardware robot, double leftStickY, double leftStickX, double rightStickX, boolean fullUp, boolean fullRight, boolean fullDown, boolean fullLeft) {
        speedVal = .5;
        mecanumMove(telemetry, robot, leftStickY, leftStickX, rightStickX, fullUp, fullRight, fullDown, fullLeft, speedVal);
    }

    public void mecanumDriveFast (Telemetry telemetry, OldRobotHardware robot, double leftStickY, double leftStickX, double rightStickX, boolean fullUp, boolean fullRight, boolean fullDown, boolean fullLeft) {
        speedVal = 1.0;
        mecanumMove(telemetry, robot, leftStickY, leftStickX, rightStickX, fullUp, fullRight, fullDown, fullLeft, speedVal);
    }
}