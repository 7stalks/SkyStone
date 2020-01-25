package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Color Sensor")
@Disabled

public class CSensorTelop extends LinearOpMode {

    RobotHardware robot = new RobotHardware(true);

    @Override
    public void runOpMode() {

        // Initialize, wait for start
        robot.init(hardwareMap, telemetry);
        waitForStart();

        // Begins while loop, updates telemetry
        while (opModeIsActive()) {
            if (robot.colorSensor != null){
                telemetry.addData("Color Sensor", robot.colorSensor.alpha());
            } else {
                telemetry.addData("Color Sensor", "C R A P!");
            }
            telemetry.update();
            sleep(1000);
        }
    }
}

