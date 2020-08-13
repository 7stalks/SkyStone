package org.firstinspires.ftc.teamcode.skystone_autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.OldRobotHardware;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@TeleOp(name = "AutonomousTest")
public class AutonomousTesting extends LinearOpMode {
    OldRobotHardware robot = new OldRobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);

    // while touch sensor isn't touched, emits 0
    public void leftUntilTouch() {
        robot.skystoneBack.setPosition(robot.SKYSTONE_GRABBER_DOWN_AUTONOMOUS);
        while (robot.digitalTouchSkystoneBack.getState()) {
            mecanum.mecanumFLeft(.85);
        }
        mecanum.mecanumNaught();
        robot.skystoneBackRotator.setPosition(robot.SKYSTONE_ROTATOR_DOWN);
        sleep(400);
        robot.skystoneBack.setPosition(robot.MID_SERVO);
        sleep(400);
        mecanum.mecanumFRight(1);
        sleep(650);
        mecanum.mecanumNaught();
        sleep(10000);
    }

    public void driveUntilRed() {
        while (robot.colorSensor.red() < 200) {
            telemetry.addData("Red", robot.colorSensor.red());
            telemetry.addData("Blue", robot.colorSensor.blue());
            telemetry.addData("Green", robot.colorSensor.green());
            telemetry.addData("Light", robot.colorSensor.alpha());
            telemetry.addData("Hue", robot.colorSensor.argb());
            telemetry.update();
            mecanum.mecanumFFront(.7);
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();
        driveUntilRed();
        mecanum.mecanumNaught();
    }
}