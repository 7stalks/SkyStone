package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

@Autonomous(name = "AutoTray")
public class AutoTray extends LinearOpMode {
    RobotHardware robot = new RobotHardware(false);
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    Kicker kicker = new Kicker();
    boolean moveTray = false;
    int moveTime = 0;
    int stayTime = 0;
    int strafeMove = 0;

    public void AutoTrayStart() {
        if (moveTray = true) {
            mecanum.mecanumFFront(1);
            sleep(moveTime);
            mecanum.mecanumNaught();
            kicker.KickerSet(robot, 1);
            sleep(stayTime );
            mecanum.mecanumFBack(1);
            sleep(moveTime);
            mecanum.mecanumNaught();
            kicker.KickerSet(robot, 0);
            sleep(stayTime);
            mecanum.mecanumFLeft(1);
            sleep(strafeMove);
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            kicker.KickerSet(robot, 0);

            moveTray = true;
        }
    }
}