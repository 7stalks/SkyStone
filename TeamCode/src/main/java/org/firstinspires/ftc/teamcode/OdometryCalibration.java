package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
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

        robot.init(hardwareMap, telemetry);
        double angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC.reverse(), AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

        telemetry.setMsTransmissionInterval(2);
        telemetry.addData("Status", "Waiting to be started");
        telemetry.update();
        waitForStart();

//        while (opModeIsActive()) {
//            angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC.reverse(), AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
//            telemetry.addData("angle", angle);
//            telemetry.update();
//        }

//        telemetry.addData("angle", angle);
//        telemetry.addData("is imu calibrated", robot.imu.isSystemCalibrated());
//        telemetry.update();
//        sleep(10000);

        double angleInit = robot.imu.getAngularOrientation(AxesReference.INTRINSIC.reverse(), AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        while (angle < (angleInit + 90) && opModeIsActive()) {

//                robot.RightFront.setPower(robot.PIVOT_SPEED);
//                robot.LeftFront.setPower(-robot.PIVOT_SPEED);
//                robot.RightBack.setPower(robot.PIVOT_SPEED);
//                robot.LeftFront.setPower(-robot.PIVOT_SPEED);
                if (angle < (angleInit + 60)) {
                    drive.circlepadMove(0, 0, robot.PIVOT_SPEED);
                } else {
                    drive.circlepadMove(0, 0, robot.PIVOT_SPEED/2);
                }

                telemetry.addData("IMU Angle", angle);
                telemetry.update();

                angle = robot.imu.getAngularOrientation(AxesReference.INTRINSIC.reverse(), AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
            }
        drive.stop();
        timer.reset();
        while(timer.milliseconds() < 1000 && opModeIsActive() ) {
            telemetry.addData("IMU Angle", angle);
            telemetry.update();
            }
        // Record IMU and encoder values to calculate the constants

        //Encoder difference (leftEncoder - rightEncoder)

        double encoderDifference = Math.abs(robot.OLeft.getCurrentPosition()) +
                Math.abs(robot.ORight.getCurrentPosition() );
        double verticalEncoderTickOffsetPerDegree = encoderDifference/(angle-angleInit);
        double wheelBaseSeparation = (2*90*verticalEncoderTickOffsetPerDegree) / (Math.PI * encoderCountsPerIn);

        horizontalTickOffset = robot.OMiddle.getCurrentPosition()/Math.toRadians(robot.imu.getAngularOrientation(AxesReference.INTRINSIC.reverse(), AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);

        // Write constants to the text files
        ReadWriteFile.writeFile(wheelBaseSeparationFile, String.valueOf(wheelBaseSeparation));
        ReadWriteFile.writeFile(horizontalTickOffsetFile, String.valueOf(horizontalTickOffset));

        while(opModeIsActive()) {
            telemetry.addData("Odometry Calibration Status", "Calibration Success");
            telemetry.addData("Wheel Base Separation", wheelBaseSeparation);
            telemetry.addData("base separation location", wheelBaseSeparationFile);
            telemetry.addData("Horizontal Encoder Offset", horizontalTickOffset);
            telemetry.addData("offset file locationm", horizontalTickOffsetFile);
            telemetry.addData("IMU angle", angle);
            telemetry.addData("Left Position", robot.OLeft.getCurrentPosition());
            telemetry.addData("Right Position", robot.ORight.getCurrentPosition());
            telemetry.addData("Middle Position", robot.OMiddle.getCurrentPosition());

            telemetry.update();
        }


        }
    }
