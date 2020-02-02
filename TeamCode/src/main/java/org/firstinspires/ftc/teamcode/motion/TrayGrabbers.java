package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class TrayGrabbers {

    public double OPEN = 1;
    public double CLOSE = 0;

    public void trayGrabberSetPosition(RobotHardware robot, double position) {
        robot.trayGrabberLeft.setPosition(position);
        robot.trayGrabberRight.setPosition(position);
    }

    public void trayGrabberMove(RobotHardware robot, boolean down) {
        if (down) {
            robot.trayGrabberLeft.setPosition(-1);
            robot.trayGrabberRight.setPosition(-1);
        } else {
            robot.trayGrabberLeft.setPosition(1);
            robot.trayGrabberRight.setPosition(1);
        }
    }
}