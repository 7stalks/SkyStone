package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous (name = "ForIfYouNeedToRelax")
public class BackScratcherv1 extends LinearOpMode {
    RobotHardware robot = new RobotHardware(false);

    int j = 0;
    int sleepTime = 500;

    public void Scratch() {
        for (j = 0; j <= 68; j = j + 1) {
            robot.skystoneBack.setPosition(robot.SKYSTONE_GRABBER_DOWN);
            sleep(sleepTime);
            robot.skystoneBack.setPosition(robot.MID_SERVO);
            sleep(sleepTime);
        }
        telemetry.addData("Back", "..scratched");
}

    public void doubleScratch() {
        for (j = 0; j <= 68; j = j + 1) {
            robot.skystoneBack.setPosition(robot.SKYSTONE_GRABBER_DOWN);
            robot.skystoneFront.setPosition(robot.SKYSTONE_GRABBER_DOWN);
            sleep(sleepTime);
            robot.skystoneBack.setPosition(robot.MID_SERVO);
            robot.skystoneFront.setPosition(robot.MID_SERVO);
            sleep(sleepTime);
        }
        telemetry.addData("Back", "..scratched");
}

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                Scratch();
            }
            if (gamepad1.b) {
                doubleScratch();
            }

            telemetry.update();
        }
    }
}