package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.OldRobotHardware;

public class Clamp {

    public void setClamp(OldRobotHardware robot, boolean open, boolean close) {
        if (robot.clamp == null ) {
            return;
        }
        if (open) {
            robot.clamp.setPosition(robot.CLAMP_OPEN_DISTANCE);
        }

        if (close) {
            robot.clamp.setPosition(robot.CLAMP_CLOSE_DISTANCE);
        }
    }

    public void moveClampRotator(OldRobotHardware robot, double clamp_rotator_set) {
        if (robot.clampRotator == null ) {
            return;
        }

        if (clamp_rotator_set > 0) {
            robot.clampRotator.setPosition(.005 + robot.clampRotator.getPosition());
        } else {
            robot.clampRotator.setPosition(-.005 + robot.clampRotator.getPosition());
        }

    }
}
