package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.TrayGrabbers;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@Autonomous(name = "RedTray")
public class RedTray extends LinearOpMode {
    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    Kicker kicker = new Kicker();
    TrayGrabbers grabbers = new TrayGrabbers();
    boolean moveTray = false;
    int frontTime = 1600;
    int backTime = 2450;
    int stayTime = 700;
    int strafeMoveTime = 4000;

    public void AutoTrayStart() {
        mecanum.mecanumFRight(1);
        sleep(1480);
        mecanum.mecanumNaught();

        mecanum.mecanumFFront(1);
        sleep(frontTime);
        mecanum.mecanumNaught();

        grabbers.trayGrabberMove(robot, grabbers.CLOSE);
        sleep(stayTime);

        mecanum.mecanumFullBack();
        sleep(backTime);
        mecanum.mecanumNaught();

        grabbers.trayGrabberMove(robot, grabbers.OPEN);
        sleep(stayTime);

        mecanum.mecanumFLeft(1);
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