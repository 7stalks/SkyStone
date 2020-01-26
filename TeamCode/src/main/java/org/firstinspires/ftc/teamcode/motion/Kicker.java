package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class Kicker {

    public void KickerMove(RobotHardware robot) {
        if (robot.KickerServo != null) {
            robot.KickerServo.setPosition(0);
        }
    }

    public void KickerSet(RobotHardware robot, double position) {
        if (robot.KickerServo != null) {
            robot.KickerServo.setPosition(position);
        }
    }
}