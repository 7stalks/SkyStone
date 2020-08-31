package org.firstinspires.ftc.teamcode;

public class GoBildaDrive {

    // introduce variables and members
    private RobotHardware robot;

    // constructor
    public GoBildaDrive(RobotHardware givenRobot) {
        robot = givenRobot;
    }

    // for the circle pad movement (rotation and strafing 360 degrees)
    // The Y is inverted (up gives -1, down gives 1). Input the raw stick movement,
    // the method corrects it
    public void circlepadMove(double leftStickY, double leftStickX, double rightStickX) {
        double correctedLeftStickY = -leftStickY;
        if (Math.abs(leftStickX) < robot.stickThres && Math.abs(correctedLeftStickY) < robot.stickThres
                && Math.abs(rightStickX) < robot.stickThres) {
            // Make sure that the circle pad sticks don't accidentally move the robot
            stop();
        } else {
            // Create the magnitude (or radius, r) and angle of the sticks
            double r = Math.hypot(leftStickX, correctedLeftStickY);
            double robotAngle = Math.atan2(correctedLeftStickY, leftStickX) - (Math.PI / 4);

            // Set power to the motors corresponding to the angle and magnitude
            final double RFront = r * Math.cos(robotAngle) + rightStickX;
            final double RBack = r * Math.sin(robotAngle) + rightStickX;
            final double LFront = r * Math.sin(robotAngle) - rightStickX;
            final double LBack = r * Math.cos(robotAngle) - rightStickX;
            robot.RightFront.setPower(RFront);
            robot.RightBack.setPower(RBack);
            robot.LeftFront.setPower(LFront);
            robot.LeftBack.setPower(LBack);
        }
    }

    public void dpadMove(boolean right, boolean up, boolean left, boolean down) {
        if (right) {
            robot.RightFront.setPower(-1);
            robot.RightBack.setPower(1);
            robot.LeftFront.setPower(1);
            robot.LeftBack.setPower(-1);
        } else if (up) {
            robot.RightFront.setPower(1);
            robot.RightBack.setPower(1);
            robot.LeftFront.setPower(1);
            robot.LeftBack.setPower(1);
        } else if (left) {
            robot.RightFront.setPower(1);
            robot.RightBack.setPower(-1);
            robot.LeftFront.setPower(-1);
            robot.LeftBack.setPower(1);
        } else if (down) {
            robot.RightFront.setPower(-1);
            robot.RightBack.setPower(-1);
            robot.LeftFront.setPower(-1);
            robot.LeftBack.setPower(-1);
        }
    }

    public void stop() {
        robot.RightFront.setPower(0);
        robot.RightBack.setPower(0);
        robot.LeftFront.setPower(0);
        robot.LeftBack.setPower(0);
    }

    public void motorTest(boolean up, boolean right, boolean down, boolean left) {
        if (up) {
            robot.RightFront.setPower(1);
        } else if (right) {
            robot.RightBack.setPower(1);
        } else if (down) {
            robot.LeftBack.setPower(1);
        } else if (left) {
            robot.LeftFront.setPower(1);
        } else {
            stop();
        }
    }
}
