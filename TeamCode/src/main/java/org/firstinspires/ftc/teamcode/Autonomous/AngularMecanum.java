package org.firstinspires.ftc.teamcode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotHardware;

public class AngularMecanum {

    RobotHardware robot;
    Telemetry telemetry;
    double robobAngle;

    public AngularMecanum(RobotHardware robob, Telemetry telemetryhard) {
        robot = robob;
        telemetry = telemetryhard;
    }

    public void Left(double robotAngle, double power, double rotation) {

        robobAngle = ((3*Math.PI) / 4) + robotAngle;

        final double RFarquaad = Math.cos(robobAngle) + rotation;
        final double RBridget = Math.sin(robobAngle) + rotation;
        final double LFrancisco = Math.sin(robobAngle) - rotation;
        final double LBoomer = Math.cos(robobAngle) - rotation;
        robot.RightFront.setPower (-RFarquaad*power);
        robot.RightBack.setPower (RBridget*power);
        robot.LeftFront.setPower (-LFrancisco*power);
        robot.LeftBack.setPower (LBoomer*power);
        telemetry.addData("RFarquaad", RFarquaad);
        telemetry.addData("LFrancisco", LFrancisco);
        telemetry.addData("LBoomer", LBoomer);
        telemetry.addData("RBridget", RBridget);
    }

    public void Forward(double robotAngle, double power, double rotation) {

        robobAngle = (Math.PI / 4) + robotAngle;

        final double RFarquaad = Math.cos(robobAngle) + rotation;
        final double RBridget = Math.sin(robobAngle) + rotation;
        final double LFrancisco = Math.sin(robobAngle) - rotation;
        final double LBoomer = Math.cos(robobAngle) - rotation;
        robot.RightFront.setPower (RFarquaad*power);
        robot.RightBack.setPower (RBridget*power);
        robot.LeftFront.setPower (LFrancisco*power);
        robot.LeftBack.setPower (LBoomer*power);
        telemetry.addData("RFarquaad", RFarquaad);
        telemetry.addData("LFrancisco", LFrancisco);
        telemetry.addData("LBoomer", LBoomer);
        telemetry.addData("RBridget", RBridget);
    }
}
