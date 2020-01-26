package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.internal.vuforia.VuforiaException;


/**
 * This is NOT an opmode.
 * <p>
 * This class can be used to define all the specific hardware for a single robot.
 * <p>
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 * <p>
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Lever:                    "lever_arm"
 * Servo channel:  Clamp Rotator:            "clamp_rotator"
 * Servo channel:  Clamp:                    "clamp"
 * Servo channel:  Kicker:                   "Kicker"
 */

public class RobotHardware {
    /* Public OpMode members. */

    public DcMotor LeftFront;
    public DcMotor RightFront;
    public DcMotor LeftBack;
    public DcMotor RightBack;
    public DcMotor leverArm;

    public Servo clampRotator;
    public Servo clamp;
    public Servo KickerServo;
    public Servo handsOn;
    public Servo hansen;
    public Servo hansenRotator;
    public Servo hookRight;
    public Servo hookLeft;


    public ColorSensor colorSensor;
//    public DistanceSensor colorDistance;
    public BNO055IMU imu;
    public DigitalChannel digitalTouchFunnel;
    public DigitalChannel digitalTouchHansen;
    public Orientation angles;
    public Acceleration gravity;


    public static final double MID_SERVO = 0.5;
    public static final double CLAMP_OPEN_DISTANCE = 0.325;
    public static final double ARM_UP_POWER = 0.45;
    public static final double ARM_DOWN_POWER = -0.45;
    public static final double ARM_UP_DISTANCE = 1600;
    public static final double CLAMP_CLOSE_DISTANCE = 0.75;
    public static final double CLAMP_ROTATOR_BEGINNING_SERVO = 0;
    public static final double KICKER_START = 0;
    public static final double MAXMOTORSPEED = 1;
    public static final double STICK_THRES = 0.6;
    public static final double KICKER_PRESS = 1;
    public static final double stickThres = .25;
    public static final double noSpeed = 0;
    public static final double smallMove = .6;
    public static final double smallRot = .6;

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    public static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    public static final float bridgeZ = 6.42f * mmPerInch;
    public static final float bridgeY = 23 * mmPerInch;
    public static final float bridgeX = 5.18f * mmPerInch;
    public static final float bridgeRotY = 59;                                 // Units are degrees
    public static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    public static final float halfField = 72 * mmPerInch;
    public static final float quadField  = 36 * mmPerInch;

    public static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    public static final String LABEL_FIRST_ELEMENT = "Stone";
    public static final String LABEL_SECOND_ELEMENT = "Skystone";


    private static final String VUFORIA_KEY = "AXl4o5z/////AAABmQyBF0iAaUTcguyLoBFeK1A7RHUVrQdTS" +
            "sPDqn4DelLm7BtbLuahVuZvBzuq5tPGrvi7D25P3xRzVgT1d+cADoNAMxuRVZs24o87S6gH0mM+Q/OrrQr5" +
            "7pTiumNffyuzBI728d+XgQJImM0rBxGcpwej8Ok0ZSCNIzzxVNf06dRwLEwu6jf0mCiA9yyffMFzreeL8UR" +
            "wm/xxuDsYxY7NrVtjlmslMTiu3nAUboaDP8jkhKvl8623x57MhYt4hof+iegRYjJzt+Knb5m5SfY5urWFGF" +
            "sLjZ4dqAhzXNiJmmKbKojUfjgvUld91gWm0UOXHkoezBuBVnLFasNmChD2uxpGGGeNdW1MvGitjFEvckKJ";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    public VuforiaLocalizer vuforia;
    public boolean rightCamera;

    /**
     * {@link #tensorFlowEngine} is the variable we will use to store our instance of the TensorFlow Object
     * Detection engine.
     */
    public TFObjectDetector tensorFlowEngine;


    /* local OpMode members. */
    HardwareMap hardwareMap = null;
    private ElapsedTime period = new ElapsedTime();


    /* Constructor */
    public RobotHardware(boolean right_camera) {

        if (right_camera) {
            rightCamera = true;
        } else {
            rightCamera = false;
        }
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hardware_map, Telemetry telemetry) {

        // Save reference to Hardware map
        hardwareMap = hardware_map;

        // Define and Initialize Motor
        try {
            hookRight = hardwareMap.get(Servo.class, "hookerRight");
            hookRight.setDirection(Servo.Direction.REVERSE);
            hookRight.setPosition(1);
            telemetry.addData("Status", "Servo: hook right identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: hook right not plugged in");    //
            hookRight = null;
        }

        try {
            hookLeft = hardwareMap.get(Servo.class, "hookerLeft");
            hookLeft.setPosition(1);
            telemetry.addData("Status", "Servo: hook left identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: hook left not plugged in");    //
            hookLeft = null;
        }

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
            leverArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leverArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
            KickerServo.setPosition(MID_SERVO);
            telemetry.addData("Status", "Servo: kicker identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: kicker not plugged in");    //
            KickerServo = null;
        }

        try {
            handsOn = hardwareMap.get(Servo.class, "hands_on");
            handsOn.setPosition(MID_SERVO);
            telemetry.addData("Status", "Servo: hands_on identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: hands_on not plugged in");    //
            handsOn = null;
        }

        try {
            hansen = hardwareMap.get(Servo.class, "hansen");
            hansen.setPosition(.4275);
            telemetry.addData("Status", "Servo: hansen identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: hansen not plugged in");
            hansen = null;
        }

        try {
            hansenRotator = hardwareMap.get(Servo.class, "hansen_rotator");
            hansenRotator.setPosition(.7);
            telemetry.addData("Status", "Servo: hansen identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: hansen not plugged in");
            hansenRotator = null;
        }

        try {
            colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
//            colorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");
            telemetry.addData("Status", "sensor: color sensor identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "sensor: color sensor not plugged in");    //
            colorSensor = null;
//            colorDistance = null;
        }


        initVuforia(telemetry, rightCamera);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTFOD(telemetry);
        } else {
            telemetry.addData("Warning", "Tensor Flow Object Detection not compatible");
        }

        try {
            digitalTouchFunnel = hardwareMap.get(DigitalChannel.class, "digital_touch_funnel");
            digitalTouchFunnel.setMode(DigitalChannel.Mode.INPUT);
            telemetry.addData("Status", "sensor: funnel toucb identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "sensor: funnel touch not plugged in");    //
            digitalTouchFunnel = null;
        }

        try {
            digitalTouchHansen = hardwareMap.get(DigitalChannel.class, "digital_touch_hansen");
            digitalTouchHansen.setMode(DigitalChannel.Mode.INPUT);
            telemetry.addData("Status", "sensor: hansen touch identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "sensor: hansen touch not plugged in");    //
            digitalTouchHansen = null;
        }

        telemetry.update();
    }

    private void initVuforia(Telemetry telemetry, boolean rightCamera) {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        try {
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
            parameters.vuforiaLicenseKey = VUFORIA_KEY;

            if (rightCamera) {
                parameters.cameraName = hardwareMap.get(WebcamName.class, "right_camera");
            } else {
                parameters.cameraName = hardwareMap.get(WebcamName.class, "left_camera");
            }

            vuforia = ClassFactory.getInstance().createVuforia(parameters);
            telemetry.addData("Status", "Vuforia Initialized");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: Vuforia not enabled");    //
            vuforia = null;
        } catch (VuforiaException err) {
            telemetry.addData("Warning", "Servo: Vuforia Exception - not enabled");    //
            vuforia = null;
        }
    }

    private void initTFOD(Telemetry telemetry) {
        /* Initialize Tensor Flow Object Detection */
        if (vuforia != null) {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfodParameters.minimumConfidence = 0.7;
            tensorFlowEngine = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
            tensorFlowEngine.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
            telemetry.addData("Status", "Tensor Flow Object Detection Initialized");
        } else {
            telemetry.addData("Status", "Tensor Flow Object Detection not Initialized");
        }
    }
}