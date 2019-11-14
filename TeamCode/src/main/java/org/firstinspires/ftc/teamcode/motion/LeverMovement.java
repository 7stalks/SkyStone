package org.firstinspires.ftc.teamcode.motion;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RobotHardware;

public class LeverMovement extends LinearOpMode {

    int position;  // TODO This should be private and make this explicit it means nothing to me. robot_current_position?
    float now;     // TODO This should be private and make this explicit.  robot_last_known_position?
    boolean a;     // TODO I don't think you need this at all.


    public void moveLeverArm(RobotHardware robot, double distance) {
        //
        telemetry.addData("Value %.2d", distance);
        position = robot.leverArm.getCurrentPosition() + 1;
        telemetry.addData("position%.2d", position);
        telemetry.update();

        if (distance > .1) {
            if (position >= robot.ARM_UP_DISTANCE) {
                robot.leverArm.setPower(0);
            }
            if (position >= 900) { // TODO - Shouldn't this be a elseif?
                robot.leverArm.setPower(-.27);
            }
            if (position <= robot.ARM_UP_DISTANCE) {  // TODO - Shouldn't this be a elseif?
                robot.leverArm.setPower(.35);
            }
        }
        if (distance < -.1) {
            if (position <= 100) {
                robot.leverArm.setPower(0);
            }
            if (position <= 900) {  // TODO - Shouldn't this be a elseif?
                robot.leverArm.setPower(.27);
            }
            if (position >= 100) {   // TODO - Shouldn't this be a elseif?
                robot.leverArm.setPower(-.35);
            }
        }
        // We need to record our last position
    }


    public void leverArmStay(RobotHardware robot) {
        //
        // Completely foreign to me?  Assuming not complete thought.
        //
        if (a = true) {
            a = false;
            sleep(10);
            now = robot.leverArm.getCurrentPosition();
        }
        if (a = false){
            a = true;

        }
    }
    public void leverArmStay2(RobotHardware robot) {
        //
        // This is absolutely the right approach.
        //
        position = robot.leverArm.getCurrentPosition();
        telemetry.addData("position%.2d", position);
        if (position > now) {
            robot.leverArm.setPower(-.4);
        }
        if (position < now) {  // TODO else if..
            robot.leverArm.setPower(.4);
        }  // TODO ELSE set power 0

    }


    public void runOpMode() {};


}
