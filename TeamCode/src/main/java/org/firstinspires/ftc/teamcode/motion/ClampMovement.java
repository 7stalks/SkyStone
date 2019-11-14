package org.firstinspires.ftc.teamcode.motion;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class ClampMovement {

    boolean clamp_open = true;  // TODO UNUSED REMOVE ME

    public void setClamp(RobotHardware robot, boolean open, boolean close) {
        // Formatting matters..  We indent 4 spaces for a very good reason so it's legible.
        // Those grey lines help you find the flow control through the code.
        // Functionally this is great - We need to adjust MID_SERVO -  I would rename this as
        // CLAMP_OPEN_POSITION
        if (open) {
            robot.clamp.setPosition(robot.MID_SERVO);
        }

        if (close) {
            robot.clamp.setPosition(robot.CLAMP_CLOSE_DISTANCE);
        }
    }

    public void moveClampRotator(RobotHardware robot, double clamp_rotator_set) {
        robot.clampRotator.setPosition((.0001*clamp_rotator_set) + robot.clampRotator.getPosition());
    }

    public void runOpMode() {};

}
