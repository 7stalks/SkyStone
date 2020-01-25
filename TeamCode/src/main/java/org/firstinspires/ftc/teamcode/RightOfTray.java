package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@Autonomous(name = "RightOfTray")
public class RightOfTray extends LinearOpMode {
    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    Kicker kicker = new Kicker();
    boolean moveTray = false;
    int frontTime = 1525;
    int backTime = 1950;
    int stayTime = 700;
    int strafeMoveTime = 3000;

    public void AutoTrayStart() {
        mecanum.mecanumFLeft(1);
        sleep(469);
        mecanum.mecanumNaught();

        robot.KickerServo.setPosition(0);

        mecanum.mecanumFFront(1);
        sleep(frontTime);
        mecanum.mecanumNaught();

        kicker.KickerSet(robot, 1);
        sleep(stayTime);

        mecanum.mecanumFBack(1);
        sleep(backTime);
        mecanum.mecanumNaught();

        kicker.KickerSet(robot, 0);
        sleep(stayTime);

        mecanum.mecanumFRight(1);
        sleep(strafeMoveTime);
        mecanum.mecanumNaught();
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();
        AutoTrayStart();
    }
}