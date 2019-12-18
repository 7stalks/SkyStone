package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="GerritTelop")

public class GerritTelop extends LinearOpMode {

    public DcMotor leverArm;

    @Override

    public void runOpMode() {

        leverArm = hardwareMap.get(DcMotor.class, "lever_arm");
        leverArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {

            leverArm.setPower(.3 * gamepad1.left_stick_y);
        }
    }
}