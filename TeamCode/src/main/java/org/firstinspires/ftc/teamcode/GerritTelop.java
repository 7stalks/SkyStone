package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motion.LeverMovement;


@TeleOp(name="GerritTelop")
public class GerritTelop extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    LeverMovement leverMovement = new LeverMovement();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, telemetry);
        boolean a = true;
        waitForStart();
        int counter = 0;

        // Couple thoughts.  What is it we want to do..
        // When I let go of the stick I need it to freeze to the last known position.
        // I don't want to do anything more than that.  I wouldn't worry about the button A.
        // What I would suggest is that if left_stick_y == 0 and right_stick_y == 0 then stay!
        // Now the leverArmStay is simply going to take the currentPosition.  And we need to compare
        // that to our last recorded postion....
        // So...
        // You will need a top

        while (opModeIsActive()) {

            // I would remove this
            if (gamepad2.a) {
                leverMovement.leverArmStay(robot);
            }
            if (! a){
                leverMovement.leverArmStay2(robot);
            }
            // I would remove this

            if (gamepad2.left_stick_y > .1 || gamepad2.left_stick_y < -.1) {
                leverMovement.moveLeverArm(robot, -gamepad2.left_stick_y);
            }
            else {
                // This not goes to leverArmStay(getCurrentPosition) and remove setPower..
                robot.leverArm.setPower(0);
            }
        }
    }
}