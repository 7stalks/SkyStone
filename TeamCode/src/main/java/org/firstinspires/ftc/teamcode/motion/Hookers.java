package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class Hookers {

    public void Hooker (RobotHardware robot, double position) {
        if (robot.hookLeft != null) {
            robot.hookLeft.setPosition(position);
        }
        if (robot.hookRight != null) {
            robot.hookRight.setPosition(position);
        }
    }
}