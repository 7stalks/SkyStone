package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class TrayGrabbers {

    public double OPEN = 1;
    public double CLOSE = 0;

    public void grabTray(RobotHardware robot, double position) {
        robot.trayGrabberLeft.setPosition(position);
        robot.trayGrabberRight.setPosition(position);
    }
}