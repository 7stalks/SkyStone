package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;


/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Lever:                    "lever_arm"
 * Servo channel:  Clamp Rotator:            "clamp_rotator"
 * Servo channel:  Clamp:                    "clamp"
 * Servo channel:  Kicker:                   "kicker"
 */

public class RobotHardware {
    /* Public OpMode members. */

    public DcMotor  LeftFront;
    public DcMotor  RightFront;
    public DcMotor  LeftBack;
    public DcMotor  RightBack;
    public DcMotor  leverArm;

    public Servo    clampRotator;
    public Servo    clamp;
    public Servo    kicker;

    public static final double MID_SERVO = 0.5;
    public static final double CLAMP_OPEN_DISTANCE       =  0.4 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
    public static final double ARM_UP_DISTANCE  = 1600 ;
    public static final double CLAMP_CLOSE_DISTANCE = 0.75;
    public static final double CLAMP_ROTATOR_BEGINNING_SERVO = 0;
    public static final double KICKER_START = 0;
    public static final double MAXMOTORSPEED = 1;
    public static final double stickThres = 0.25;


    /* local OpMode members. */
    HardwareMap hardwareMap     =  null;
    private ElapsedTime period  = new ElapsedTime();


    /* Constructor */
    public RobotHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hardware_map, Telemetry telemetry) {

        // Save reference to Hardware map
        hardwareMap = hardware_map;

        // Define and Initialize Motor
        try {
            LeftFront = hardwareMap.get(DcMotor.class, "LeftFront");
            LeftFront.setDirection(DcMotor.Direction.FORWARD);
            LeftFront.setPower(0);
            LeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: left_front identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: left_front not plugged in");    //
            LeftFront = null;
        }

        try {
            RightFront = hardwareMap.get(DcMotor.class, "RightFront");
            RightFront.setDirection(DcMotor.Direction.REVERSE);
            RightFront.setPower(0);
            RightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: right_front identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: right_front not plugged in");    //
            RightFront = null;
        }
        try {
            LeftBack = hardwareMap.get(DcMotor.class, "LeftBack");
            LeftBack.setDirection(DcMotor.Direction.FORWARD);
            LeftBack.setPower(0);
            LeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: left_back identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: left_back not plugged in");    //
            LeftBack = null;
        }

        try {
            RightBack = hardwareMap.get(DcMotor.class, "RightBack");
            RightBack.setDirection(DcMotor.Direction.REVERSE);
            RightBack.setPower(0);
            RightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            telemetry.addData("Status", "Motor: right_back identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: right_back not plugged in");    //
            RightBack = null;
        }

        try {
            leverArm = hardwareMap.get(DcMotor.class, "leverArm");
            leverArm.setPower(0);
            telemetry.addData("Status", "Motor: lever_arm identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: lever_arm not plugged in");    //
            leverArm = null;

        }

        try {

            clampRotator = hardwareMap.get(Servo.class, "clamp_rotator");
            clampRotator.setPosition(CLAMP_ROTATOR_BEGINNING_SERVO);
            telemetry.addData("Status", "Servo: clamp_rotator identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: clamp_rotator not plugged in");    //
            clampRotator = null;
        }

        try {
            clamp = hardwareMap.get(Servo.class, "clamp");
            clamp.setPosition(CLAMP_OPEN_DISTANCE);
            telemetry.addData("Status", "Servo: clamp identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: clamp not plugged in");    //
            clamp = null;
        }

        try {
            kicker = hardwareMap.get(Servo.class, "kicker");
            kicker.setPosition(KICKER_START);
            telemetry.addData("Status", "Servo: kicker identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: kicker not plugged in");    //
            kicker = null;
        }


        telemetry.update();


    }
}
