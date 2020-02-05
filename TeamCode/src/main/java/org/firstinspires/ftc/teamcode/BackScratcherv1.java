package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous (name = "ForIfYouNeedToRelax")
public class BackScratcherv1 extends LinearOpMode {
    RobotHardware robot = new RobotHardware(false);

    int j = 0;
    int sleepTime = 400;

    public void Scratch() {
        for (j = 0; j<=68; j = j + 1) {
            robot.skystoneBack.setPosition(robot.SKYSTONE_GRABBER_DOWN);
            sleep(sleepTime);
            robot.skystoneBack.setPosition(robot.MID_SERVO);
            sleep(sleepTime);
        }
    }

    public void doubleScratch() {
        for (j = 0; j<=68; j = j + 1)
        {
            robot.skystoneBack.setPosition(robot.SKYSTONE_GRABBER_DOWN);
            robot.skystoneFront.setPosition(robot.SKYSTONE_GRABBER_DOWN);
            sleep(sleepTime);
            robot.skystoneBack.setPosition(robot.MID_SERVO);
            robot.skystoneFront.setPosition(robot.MID_SERVO);
            sleep(sleepTime);
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();
        if (gamepad1.start) {
            Scratch();
        }
        if (gamepad1.guide) {
            doubleScratch();
        }

        while (opModeIsActive()) {
            telemetry.addData("Back", "..scratched");
                }
            telemetry.update();
        }
    }
