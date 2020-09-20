package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;


@TeleOp(name = "Calibration de la Odometry")
public class OdometryCalibration extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    GoBildaDrive drive = new GoBildaDrive(robot);

    ElapsedTime timer = new ElapsedTime();

    double horizontalTickOffset = 0;

    // Text files to write the values to -- stored in robot controller Internal Storage\First\Settings
    File wheelBaseSeparationFile = AppUtil.getInstance().getSettingsFile("wheelBaseSeparation.txt");
    File horizontalTickOffsetFile = AppUtil.getInstance().getSettingsFile("horizontalTickOffset.txt");

    double encoderCountsPerIn = 307.699557;

    @Override
    public void runOpMode() throws InterruptedException{

        double angle = robot.imu.getAngularOrientation().firstAngle;

        robot.init(hardwareMap, telemetry);

        telemetry.setMsTransmissionInterval(2);
        telemetry.addData("Status", "Waiting to be started");
        telemetry.update();
        waitForStart();

        while (angle < 90 && opModeIsActive()) {

                robot.RightFront.setPower(robot.PIVOT_SPEED);
                robot.LeftFront.setPower(-robot.PIVOT_SPEED);
                robot.RightBack.setPower(robot.PIVOT_SPEED);
                robot.LeftFront.setPower(-robot.PIVOT_SPEED);
                if (angle < 60) {
                    drive.circlepadMove(robot.PIVOT_SPEED, 0, 0);
                } else {
                    drive.circlepadMove(robot.PIVOT_SPEED/2, 0, 0);
                }

                telemetry.addData("IMU Angle", angle);
                telemetry.update();

                angle = robot.imu.getAngularOrientation().firstAngle;
            }
        drive.stop();
        timer.reset();
        while(timer.milliseconds() < 1000 && opModeIsActive() ) {
            telemetry.addData("IMU Angle", angle);
            telemetry.update();
            }
        // Record IMU and encoder values to calculate the constants

        //Encoder difference (leftEncoder - rightEncoder)

        double encoderDifference = Math.abs(robot.OLeft.getCurrentPosition()) -
                Math.abs(robot.ORight.getCurrentPosition() );
        double verticalEncoderTickOffsetPerDegree = encoderDifference/angle;
        double wheelBaseSeparation = (2*90*verticalEncoderTickOffsetPerDegree) / (Math.PI * encoderCountsPerIn);

        horizontalTickOffset = robot.OMiddle.getCurrentPosition()/Math.toRadians(angle);

        // Write constants to the text files
        ReadWriteFile.writeFile(wheelBaseSeparationFile, String.valueOf(horizontalTickOffset));

        while(opModeIsActive()) {
            telemetry.addData("Odometry Calibration Status", "Calibration Success");
            telemetry.addData("Wheel Base Separation", wheelBaseSeparation);
            telemetry.addData("Horizontal Encoder Offset", horizontalTickOffsetFile);
            telemetry.addData("IMU angle", angle);
            telemetry.addData("Left Position", robot.OLeft.getCurrentPosition());
            telemetry.addData("Right Position", robot.ORight.getCurrentPosition());
            telemetry.addData("Middle Position", robot.OMiddle.getCurrentPosition());

            telemetry.update();
        }


        }
    }
