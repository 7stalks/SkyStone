package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.motion.Kicker;

public class PermeetTeleop {

    RobotHardware robot = new RobotHardware();
    Kicker kicker = new Kicker();
    double POS;

    public void Kicker (gamepad1.right_trigger){
        robot.init(hardwareMap, telemetry);
        telemetry.addData( "Hi there ", "Permeet" );
        telemetry.update();

            POS = robot.kicker.getPosition();
            telemetry.addData("%.2d",POS);
            telemetry.update();

            if (gamepad1.right_trigger > 0 ) {
                robot.kicker.setPosition(robot.KICKER_PRESS);

            } else {
                robot.kicker.setPosition(robot.KICKER_START);
            }


        }
    }

