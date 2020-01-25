package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.motion.LeverArm;

@TeleOp(name="ArmTest")

public class ArmTest extends LinearOpMode {

    LeverArm leverArm = new LeverArm();
    double armPosition;
    RobotHardware robot = new RobotHardware(false);

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, telemetry);

        waitForStart();

        robot.leverArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.leverArm.setTargetPosition(800);

        while (opModeIsActive()) {
            robot.leverArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.leverArm.setTargetPosition(800);
            armPosition = robot.leverArm.getCurrentPosition();
            telemetry.addData("Arm Position", armPosition);
            telemetry.update();
        }
    }
}