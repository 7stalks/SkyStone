package org.firstinspires.ftc.teamcode.skystone.motion;

import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;

public class Kicker {

    public void KickerMove(OldRobotHardware robot) {
        if (robot.KickerServo != null) {
            robot.KickerServo.setPosition(0);
        }
    }

    public void KickerSet(OldRobotHardware robot, double position) {
        if (robot.KickerServo != null) {
            robot.KickerServo.setPosition(position);
        }
    }
}