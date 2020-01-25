package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class Hookers {

    public void Hooker (RobotHardware robot) {
        if (robot.hookLeft != null) {
            robot.hookLeft.setPosition(1);
        }
        if (robot.hookRight != null) {
            robot.hookRight.setPosition(1);
        }
    }
}