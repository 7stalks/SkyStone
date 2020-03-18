package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@TeleOp(name = "OdometryTests")
public class OdometryTests extends LinearOpMode {

    OldRobotHardware robot = new OldRobotHardware(false);
    MecanumDrive mecanumDrive = new MecanumDrive();
    AutonomousMecanum auto = new AutonomousMecanum(robot, telemetry, mecanumDrive);

    BNO055IMU imu;

    public DcMotor OLeft;
    public DcMotor ORight;
    public DcMotor OMiddle;

    public void initOdometry(HardwareMap hardware_map, Telemetry telemetry){

        hardwareMap = hardware_map;

        // Left odometry wheel initialization
        try {
            OLeft = hardwareMap.get(DcMotor.class, "o_left");
            OLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            OLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addData("Good", "o_left identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Odometry: o_left not plugged in");    //
            OLeft = null;
        }

        // Right odometry wheel initialization
        try {
            ORight = hardwareMap.get(DcMotor.class, "o_right");
            ORight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            ORight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addData("Good", "o_right identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Odometry: o_right not plugged in");    //
            ORight = null;
        }

        // Middle odometry wheel initialization
        try {
            OMiddle = hardwareMap.get(DcMotor.class, "o_middle");
            OMiddle.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            OMiddle.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            telemetry.addData("Good", "o_middle identified");    //
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "Odometry: o_middle not plugged in");    //
            OMiddle = null;
        }
    }

    public void runOpMode() throws InterruptedException {

        // init odometry and mecanum drive
        initOdometry(hardwareMap, telemetry);
        robot.initMecanum(hardwareMap, telemetry);

        //// init the imu
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        double angle = imu.getAngularOrientation().firstAngle;
        ////

        waitForStart();

        // ONLY WORKS IF angle IS LESS THAN 270
        if (gamepad1.a) {
            while (angle < (angle + 90)) {

            }
        }
    }
}
