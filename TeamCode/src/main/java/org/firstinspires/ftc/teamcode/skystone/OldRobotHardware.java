package org.firstinspires.ftc.teamcode.skystone;

import android.media.MicrophoneInfo;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
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

public class OldRobotHardware {
    /* Public OpMode members. */

    public DcMotor LeftFront;
    public DcMotor RightFront;
    public DcMotor LeftBack;
    public DcMotor RightBack;
    public DcMotor leverArm;

    public Servo clampRotator;
    public Servo clamp;
    public Servo KickerServo;
    public Servo skystoneBack;
    public Servo skystoneFront;
    public Servo skystoneFrontRotator;
    public Servo skystoneBackRotator;
    public Servo trayGrabberRight;
    public Servo trayGrabberLeft;


    public ColorSensor colorSensor;
//    public DistanceSensor colorDistance;
    public BNO055IMU imu;
    public DigitalChannel digitalTouchFunnel;
    public DigitalChannel digitalTouchSkystoneFront;
    public DigitalChannel digitalTouchSkystoneBack;
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
    public static final double SKYSTONE_GRABBER_DOWN = .125;
    public static final double SKYSTONE_GRABBER_DOWN_AUTONOMOUS = .1645;
    public static final double SKYSTONE_ROTATOR_DOWN = .29;

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
    public OldRobotHardware(boolean right_camera) {

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
            trayGrabberRight = hardwareMap.get(Servo.class, "tray_grabber_right");
            trayGrabberRight.setDirection(Servo.Direction.REVERSE);
            trayGrabberRight.setPosition(1);
            telemetry.addData("Status", "Servo: tray_grabber_right identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: tray_grabber_right not plugged in");    //
            trayGrabberRight = null;
        }

        try {
            trayGrabberLeft = hardwareMap.get(Servo.class, "tray_grabber_left");
            trayGrabberLeft.setPosition(1);
            telemetry.addData("Status", "Servo: tray_grabber_left identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: tray_grabber_left not plugged in");    //
            trayGrabberLeft = null;
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
            skystoneBack = hardwareMap.get(Servo.class, "skystone_back");
            skystoneBack.setDirection(Servo.Direction.REVERSE);
            skystoneBack.setPosition(MID_SERVO);
            telemetry.addData("Status", "Servo: skystone_back identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: skystone_back not plugged in");    //
            skystoneBack = null;
        }

        try {
            skystoneBackRotator = hardwareMap.get(Servo.class, "skystone_back_rotator");
            skystoneBackRotator.setPosition(0);
            telemetry.addData("Status", "Servo: skystone_back_rotator identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: skystone_back_rotator not plugged in");    //
            skystoneBackRotator = null;
        }

        try {
            skystoneFront = hardwareMap.get(Servo.class, "skystone_front");
            skystoneFront.setPosition(MID_SERVO);
            telemetry.addData("Status", "Servo: skystone_front identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: skystone_front not plugged in");
            skystoneFront = null;
        }

        try {
            skystoneFrontRotator = hardwareMap.get(Servo.class, "skystone_front_rotator");
            skystoneFrontRotator.setDirection(Servo.Direction.REVERSE);
            skystoneFrontRotator.setPosition(0);
            telemetry.addData("Status", "Servo: skystone_front_rotator identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Servo: skystone_front_rotator not plugged in");
            skystoneFrontRotator = null;
        }

        try {
            colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
            telemetry.addData("Status", "sensor: color sensor identified");
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "sensor: color sensor not plugged in");    //
            colorSensor = null;
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
            digitalTouchSkystoneFront = hardwareMap.get(DigitalChannel.class, "digital_touch_skystone_front");
            digitalTouchSkystoneFront.setMode(DigitalChannel.Mode.INPUT);
            telemetry.addData("Status", "sensor: digital_touch_skystone_front touch identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "sensor: digital_touch_skystone_front touch not plugged in");    //
            digitalTouchSkystoneFront = null;
        }

        try {
            digitalTouchSkystoneBack = hardwareMap.get(DigitalChannel.class, "digital_touch_skystone_back");
            digitalTouchSkystoneBack.setMode(DigitalChannel.Mode.INPUT);
            telemetry.addData("Status", "sensor: digital_touch_skystone_back touch identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "sensor: digital_touch_skystone_back touch not plugged in");    //
            digitalTouchSkystoneBack = null;
        }

        initVuforia(telemetry, rightCamera);

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTFOD(telemetry);
        } else {
            telemetry.addData("Warning", "Tensor Flow Object Detection not compatible");
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

    public void initMecanum(HardwareMap hardware_map, Telemetry telemetry){
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

//    public void leverInit(HardwareMap hardware_map, Telemetry telemetry) {
//        hardwareMap = hardware_map;
//        try {
//            leverArm = hardwareMap.get(DcMotor.class, "lever_arm");
//            leverArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            telemetry.addData("Status", "Motor: leverArm identified");    //
//        } catch (IllegalArgumentException err) {
//            telemetry.addData("Warning", "Motor: leverArm not plugged in");    //
//            leverArm = null;
//        }
//    }
}