package org.firstinspires.ftc.teamcode;

        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

        import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="GerritTelop")
public class GerritTelop extends LinearOpMode {

    RobotHardware robot      = new RobotHardware(true);   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {

        robot.init(hardwareMap,telemetry);
        robot.composeTelemetry(telemetry);
        waitForStart();

        while (opModeIsActive()) {
            telemetry.update();
        }
    }
}