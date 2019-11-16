package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoBlueBuild")
public class AutoBlueBuild extends LinearOpMode {

    RobotHardware robot = new RobotHardware();

    @Override

    public void runOpMode() throws InterruptedException {
        waitForStart();
        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(1500);
        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(-1);
        sleep(2000);
        robot.leftDrive.setPower(1);
        robot.rightDrive.setPower(1);
        sleep(3000);
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        
    }
}
