package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name = "Calibration de la Odometry")
public class OdometryCalibration extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    GoBildaDrive drive = new GoBildaDrive(robot);

    public void runOpMode() throws InterruptedException{

        robot.init(hardwareMap, telemetry);

        double slowpower = .3;

        telemetry.setMsTransmissionInterval(2);
        telemetry.addData("Status", "Waiting to be started");
        telemetry.update();

        double angle = robot.imu.getAngularOrientation().firstAngle;

        waitForStart();

        while (angle < 90 && opModeIsActive()) {

                robot.RightFront.setPower(slowpower);
                robot.LeftFront.setPower(-slowpower);
                robot.RightBack.setPower(slowpower);
                robot.LeftFront.setPower(-slowpower);

                telemetry.addData("IMU Angle", angle);
                telemetry.update();

                angle = robot.imu.getAngularOrientation().firstAngle;
            }
            drive.stop();

        }
    }
