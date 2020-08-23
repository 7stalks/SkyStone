package org.firstinspires.ftc.teamcode.skystone.motion;
import org.firstinspires.ftc.teamcode.skystone.OldRobotHardware;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="RobotTeleop2", group="Robot")
public class RobotTeleop2 extends LinearOpMode {

    /* Declare OpMode members. */
    OldRobotHardware robot = new OldRobotHardware(false);   // Use a Pushbot's hardware
    MecanumDrive mecanum_drive = new MecanumDrive();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap, telemetry);

        if (robot.tensorFlowEngine != null) {
            robot.tensorFlowEngine.activate();
        }

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

//            mecanum_drive.mecanumDrive(
//                    telemetry, robot,
//                    gamepad1.left_stick_y, gamepad1.left_stick_x,
//                    gamepad1.right_stick_x, gamepad1.dpad_up, gamepad1.dpad_down);
//            // Might need to put speedVal above "while (opModeIsActive())"
            // Might also want to add "dpad_up" and "dpad_down" into the if statement
            //}


        }
    }
}
