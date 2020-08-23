package org.firstinspires.ftc.teamcode.skystone.motion;

import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;

public class DriveTrain {

    public void drive_train(OldRobotHardware robot, double leftStickX, double leftStickY, double rightStickX) {

        if (leftStickX > .4 || leftStickX < -.4) {
            robot.LeftFront.setPower(leftStickX/2);
        }
        if (rightStickX > .4 || rightStickX < -.4) {
            robot.RightFront.setPower(rightStickX/2);
        }
        if (rightStickX < .4 && rightStickX > -.4) {
            robot.RightFront.setPower(0);
        }
        if (leftStickX < .4 && leftStickX > -.4) {
            robot.LeftFront.setPower(0);
        }
    }


}
//useless lol