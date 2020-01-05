package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class Kicker {

    public void KickerMove(RobotHardware robot) {

        robot.KickerServo.setPosition(1);

    }

    public void KickerSet(RobotHardware robot, double position) {
        robot.KickerServo.setPosition(position);
    }
}