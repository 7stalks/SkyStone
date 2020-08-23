package org.firstinspires.ftc.teamcode.skystone.skystone_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;
import org.firstinspires.ftc.teamcode.skystone.motion.LeverArm;

@TeleOp(name="ArmTest")


public class ArmTest extends LinearOpMode {

    OldRobotHardware robot = new OldRobotHardware(false);
    LeverArm lever_arm = new LeverArm();
    double armPosition;
    public DcMotor leverArm;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, telemetry);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                robot.leverArm.setTargetPosition(900);
                robot.leverArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.leverArm.setPower(.45);
            } else if (gamepad1.b) {
                robot.leverArm.setTargetPosition(500);
                robot.leverArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                robot.leverArm.setPower(.45);
            }
        }
    }
}