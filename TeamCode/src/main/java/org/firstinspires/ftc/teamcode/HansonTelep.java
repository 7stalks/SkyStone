package org.firstinspires.ftc.teamcode;

<<<<<<< Updated upstream
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "HansonTelep")
public class HansonTelep extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    int noSpeed=0
    public void mecanumDrive(double leftStickY, double leftStickX, double rightStickX) {

        if (leftStickY > .4 || leftStickY < -.4) {
            robot.RightFront.setPower(leftStickY);
            robot.LeftFront.setPower(leftStickY);
            robot.RightBack.setPower(leftStickY);
            robot.LeftBack.setPower(leftStickY);
        }
        if (leftStickX > .4 || leftStickX < -.4) {
            robot.LeftFront.setPower(leftStickX);
            robot.RightFront.setPower(-leftStickX);
            robot.LeftBack.setPower(-leftStickX);
            robot.RightBack.setPower(leftStickX);
        }
        if (leftStickX < .4 && leftStickX > -.4 || leftStickY < .4 && leftStickY > -.4) {
            robot.LeftFront.setPower(noSpeed);
            robot.RightFront.setPower(noSpeed);
            robot.LeftBack.setPower(noSpeed);
            robot.RightBack.setPower(noSpeed);
        }
        if (rightStickX < .4 || rightStickX > .4) {
            robot.LeftFront.setPower(rightStickX);
            robot.RightFront.setPower(rightStickX);
            robot.LeftBack.setPower(-rightStickX);
            robot.RightBack.setPower(-rightStickX);
        }
    }
    @Override
    public void runOpMode() {

        // Initialize, wait for start
        robot.init(hardwareMap, telemetry);
        waitForStart();

        // Begins while loop, updates telemetry
        while (opModeIsActive()) {
            telemetry.addData("Status:", "Started");
            telemetry.update();

            if (gamepad1.left_stick_y != 0 || gamepad1.left_stick_x != 0 || gamepad1.right_stick_x !=0) {
                mecanumDrive(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

            }
        }
    }
}


}
