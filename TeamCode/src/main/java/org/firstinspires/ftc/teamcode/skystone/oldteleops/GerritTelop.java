package org.firstinspires.ftc.teamcode.skystone.oldteleops;

        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

        import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;

@TeleOp(name="GerritTelop")
@Disabled

public class GerritTelop extends LinearOpMode {

    OldRobotHardware robot      = new OldRobotHardware(true);   // Use a Pushbot's hardware

    @Override
    public void runOpMode() {
        robot.init(hardwareMap, telemetry);

        if (robot.tensorFlowEngine != null) {
            robot.tensorFlowEngine.activate();
        }
        waitForStart();

    }
}

