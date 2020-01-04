package org.firstinspires.ftc.teamcode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class AngularMecanum {

    RobotHardware robot;
    double robobAngle;

    public AngularMecanum(RobotHardware robob) {
        robot = robob;
    }

    public void angularMecanum(double robotAngle, double power, double rotation) {

        robobAngle = (Math.PI / 4) - robotAngle;

        final double RFarquaad = Math.cos(robobAngle*power) + rotation;
        final double RBridget = Math.sin(robobAngle*power) + rotation;
        final double LFrancisco = Math.sin(robobAngle*power) - rotation;
        final double LBoomer = Math.cos(robobAngle*power) - rotation;
        robot.RightFront.setPower (RFarquaad);
        robot.RightBack.setPower (RBridget);
        robot.LeftFront.setPower (LFrancisco);
        robot.LeftBack.setPower (LBoomer);
    }
}
