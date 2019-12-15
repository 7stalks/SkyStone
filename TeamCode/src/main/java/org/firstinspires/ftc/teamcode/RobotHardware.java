package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;


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
 * Servo channel:  Kicker:                   "Kicker"
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
    public Servo KickerServo;

    public static final double MID_SERVO = 0.5;
    public static final double CLAMP_OPEN_DISTANCE =  0.4 ;
    public static final double ARM_UP_POWER =  0.45 ;
    public static final double ARM_DOWN_POWER = -0.45 ;
    public static final double ARM_UP_DISTANCE = 1600 ;
    public static final double CLAMP_CLOSE_DISTANCE = 0.75;
    public static final double CLAMP_ROTATOR_BEGINNING_SERVO = 0;
    public static final double KICKER_START = 0;
    public static final double MAXMOTORSPEED = 1;
    public static final double STICK_THRES = 0.6;
    public static final double KICKER_PRESS = 1;
    public static final double stickThres = .25;
    public static final double noSpeed = 0;



    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";


    private static final String VUFORIA_KEY = "AXl4o5z/////AAABmQyBF0iAaUTcguyLoBFeK1A7RHUVrQdTS" +
            "sPDqn4DelLm7BtbLuahVuZvBzuq5tPGrvi7D25P3xRzVgT1d+cADoNAMxuRVZs24o87S6gH0mM+Q/OrrQr5" +
            "7pTiumNffyuzBI728d+XgQJImM0rBxGcpwej8Ok0ZSCNIzzxVNf06dRwLEwu6jf0mCiA9yyffMFzreeL8UR" +
            "wm/xxuDsYxY7NrVtjlmslMTiu3nAUboaDP8jkhKvl8623x57MhYt4hof+iegRYjJzt+Knb5m5SfY5urWFGF" +
            "sLjZ4dqAhzXNiJmmKbKojUfjgvUld91gWm0UOXHkoezBuBVnLFasNmChD2uxpGGGeNdW1MvGitjFEvckKJ";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tensorFlowEngine} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    public TFObjectDetector tensorFlowEngine;


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

        try {
            leverArm = hardwareMap.get(DcMotor.class, "lever_arm");
            leverArm.setPower(0);
            leverArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            telemetry.addData("Status", "Motor: leverArm identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Motor: leverArm not plugged in");    //
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
            KickerServo = hardwareMap.get(Servo.class, "kicker");
            KickerServo.setPosition(KICKER_START);
            telemetry.addData("Status", "Servo: kicker identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: kicker not plugged in");    //
            KickerServo = null;
        }

        initVuforia(telemetry);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTFOD(telemetry);
        } else {
            telemetry.addData("Warning", "Tensor Flow Object Detection not compatible");
        }

        telemetry.update();

    }

    private void initVuforia(Telemetry telemetry) {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "left_camera");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        telemetry.addData("Status", "Vuforia Initialized");

    }


    private void initTFOD(Telemetry telemetry) {
        /* Initialize Tensor Flow Object Detection */
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tensorFlowEngine = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tensorFlowEngine.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
        telemetry.addData("Status", "Tensor Flow Object Detection Initialized");
    }
}
