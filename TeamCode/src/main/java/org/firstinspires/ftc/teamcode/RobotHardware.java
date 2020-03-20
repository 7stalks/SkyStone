package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RobotHardware {

    HardwareMap hardwareMap = null;

    public DcMotor LeftFront;
    public DcMotor RightFront;
    public DcMotor LeftBack;
    public DcMotor RightBack;

    final public double stickThres = .2;

    public void init(HardwareMap hardware_map, Telemetry telemetry) {
        hardwareMap = hardware_map;
        try {
            LeftFront = hardwareMap.get(DcMotor.class, "left_front");
            LeftFront.setDirection(DcMotor.Direction.FORWARD);
            LeftFront.setPower(0);
            LeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: left_front identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: left_front not plugged in");    //
            LeftFront = null;
        }
        try {
            RightFront = hardwareMap.get(DcMotor.class, "right_front");
            RightFront.setDirection(DcMotor.Direction.REVERSE);
            RightFront.setPower(0);
            RightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: right_front identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: right_front not plugged in");    //
            RightFront = null;
        }
        try {
            LeftBack = hardwareMap.get(DcMotor.class, "left_back");
            LeftBack.setDirection(DcMotor.Direction.FORWARD);
            LeftBack.setPower(0);
            LeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: left_back identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: left_back not plugged in");    //
            LeftBack = null;
        }
        try {
            RightBack = hardwareMap.get(DcMotor.class, "right_back");
            RightBack.setDirection(DcMotor.Direction.REVERSE);
            RightBack.setPower(0);
            RightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: right_back identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: right_back not plugged in");    //
            RightBack = null;
        }
    }


    public void initMecanum(HardwareMap hardware_map, Telemetry telemetry) {
        hardwareMap = hardware_map;
        try {
            LeftFront = hardwareMap.get(DcMotor.class, "left_front");
            LeftFront.setDirection(DcMotor.Direction.FORWARD);
            LeftFront.setPower(0);
            LeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: left_front identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: left_front not plugged in");    //
            LeftFront = null;
        }
        try {
            RightFront = hardwareMap.get(DcMotor.class, "right_front");
            RightFront.setDirection(DcMotor.Direction.REVERSE);
            RightFront.setPower(0);
            RightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: right_front identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: right_front not plugged in");    //
            RightFront = null;
        }
        try {
            LeftBack = hardwareMap.get(DcMotor.class, "left_back");
            LeftBack.setDirection(DcMotor.Direction.FORWARD);
            LeftBack.setPower(0);
            LeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: left_back identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: left_back not plugged in");    //
            LeftBack = null;
        }
        try {
            RightBack = hardwareMap.get(DcMotor.class, "right_back");
            RightBack.setDirection(DcMotor.Direction.REVERSE);
            RightBack.setPower(0);
            RightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: right_back identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: right_back not plugged in");    //
            RightBack = null;
        }
    }
}
