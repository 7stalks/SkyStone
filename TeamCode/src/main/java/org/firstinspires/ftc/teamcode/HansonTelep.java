package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;

@TeleOp(name = "HansonTelep")
public class HansonTelep extends LinearOpMode {

    RobotHardware robot = new RobotHardware(true);
    I2cAddr address = new I2cAddr(2);
    int red;
    int blue;
    int green;

    @Override
    public void runOpMode() {

        // Initialize, wait for start
//        robot.init(hardwareMap, telemetry);
        try {
            robot.colorSensor = hardwareMap.get(ColorSensor.class, "sensor_color");
            telemetry.addData("Status", "sensor: color sensor identified");    //
            robot.colorSensor.setI2cAddress(address);
        } catch (IllegalArgumentException err) {
            telemetry.addData("Warning", "sensor: color sensor not plugged in");    //
            robot.colorSensor = null;
        }
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            red = robot.colorSensor.red();
            telemetry.addData("RED NECK", red);
            blue = robot.colorSensor.blue();
            telemetry.addData("BLUE", blue);
            green = robot.colorSensor.green();
            telemetry.addData("GREEN", green);
            telemetry.update();
        }
    }
}
