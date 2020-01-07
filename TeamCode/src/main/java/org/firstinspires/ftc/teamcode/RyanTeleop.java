package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.Autonomous.AngularMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousMecanum;
import org.firstinspires.ftc.teamcode.Autonomous.SkystoneNavigation;
import org.firstinspires.ftc.teamcode.motion.Clamp;
import org.firstinspires.ftc.teamcode.motion.Kicker;
import org.firstinspires.ftc.teamcode.motion.MecanumDrive;

import java.util.List;


@Autonomous(name = "RyanTeleop")
public class RyanTeleop extends LinearOpMode {

    RobotHardware robot = new RobotHardware(false);
    Clamp clamp = new Clamp();
    MecanumDrive mecanum_drive = new MecanumDrive();
    AutonomousMecanum mecanum = new AutonomousMecanum(robot, telemetry, mecanum_drive);
    AngularMecanum angularMecanum = new AngularMecanum(robot, telemetry);
    SkystoneNavigation nav = new SkystoneNavigation();
    Kicker kicker = new Kicker();
    boolean skystoneArea = false;
    boolean skystone = false;
    double HorAngle;
    double areaRatio;
    boolean skystoneFound = false;
    boolean skystoneGrabbed = false;
    boolean movedToPlate = false;
    boolean notThereYet = true;
    boolean notThereYet2 = true;
    boolean placedStone = false;
    double robotAngle;
    boolean cantFindPicture = false;

    public void checkForStones(List<Recognition> updatedRecognitions) {
        if (updatedRecognitions != null) {
            telemetry.addData("# Object Detected", updatedRecognitions.size());
            // step through the list of recognitions and display boundary info.
            for (Recognition recognition : updatedRecognitions) {
                if (recognition.getLabel() == robot.LABEL_SECOND_ELEMENT) {
                    skystone = true;
                    HorAngle = recognition.estimateAngleToObject(AngleUnit.RADIANS);
                    telemetry.addData("HorizontalAngle:", HorAngle);
                    areaRatio = ((recognition.getWidth() * recognition.getHeight()) / (recognition.getImageHeight() * recognition.getImageWidth()));
                    if (areaRatio > .95) {
                        skystoneArea = true;
                    }
                }
            }
        }
    }

    public void moveToSkystone() {
        mecanum.mecanumNaught();
        angularMecanum.Left(HorAngle, .65, 0);
        sleep(30);
    }

    public void SkyStoneTFOD() {
        if (robot.tensorFlowEngine != null) {
            List<Recognition> updatedRecognitions = robot.tensorFlowEngine.getUpdatedRecognitions();
            checkForStones(updatedRecognitions);

            if (skystoneArea) {
                mecanum.mecanumNaught();
                robot.tensorFlowEngine.deactivate();
                skystoneFound = true;
            } else if (skystone) {
                moveToSkystone();
            }
        }
    }

    public void driveUntilTouch() {
        while (robot.digitalTouch.getState())
        mecanum.mecanumFront(.8);
        if (!robot.digitalTouch.getState()) {
            mecanum.mecanumNaught();
        }
    }

    public void grabSkystone() {
        mecanum.mecanumRotate(-.8);
        sleep(1925);
        mecanum.mecanumNaught();
        nav.SkystoneNavigation(telemetry);
        telemetry.update();
        mecanum.mecanumBack(.95);
        sleep(1000);
        mecanum.mecanumNaught();
        mecanum.mecanumRight(.95);
        sleep(2000);
        mecanum.mecanumNaught();
        driveUntilTouch();
        skystoneGrabbed = true;
    }

    private void tangentTime(double X, double Y) {
        robotAngle = Math.atan((-905-X)/(1090-Y));
    }

    private void tangentTime2(double X, double Y) {
        if (Y > 1110) {
            robotAngle = Math.atan((835 - X) / (Y - 1110));
        } else if (Y <= 1110) {
            robotAngle = Math.atan((835-X)/(1110-Y));
        }
    }

    public void moveToPlate() {
        mecanum.mecanumBack(.8);
        sleep(325);
        mecanum.mecanumNaught();
        mecanum.mecanumRotate(-.8);
        sleep(60);
        mecanum.mecanumNaught();
        mecanum.mecanumLeft(.8);
        sleep(50);
        mecanum.mecanumNaught();
        while (notThereYet) {
            nav.SkystoneNavigationNoTelemetry();
            while (nav.X == 0 && nav.Y == 0)
                telemetry.addData("EMERGENCY:", "CANNOT FIND PICTURE");
                mecanum.mecanumFront(.8);
                sleep(75);
                nav.SkystoneNavigationNoTelemetry();
                sleep(75);
                nav.SkystoneNavigationNoTelemetry();
                sleep(75);
                nav.SkystoneNavigationNoTelemetry();
                mecanum.mecanumNaught();
                mecanum.mecanumBack(.8);
                sleep(75);
                nav.SkystoneNavigationNoTelemetry();
                sleep(75);
                nav.SkystoneNavigationNoTelemetry();
                sleep(75);
                nav.SkystoneNavigationNoTelemetry();
                mecanum.mecanumNaught();
            }
            while (nav.Y < 1090) {
                nav.SkystoneNavigationNoTelemetry();
                telemetry.addData("Rotation:", nav.Rotation);
                telemetry.addData("My X is", nav.X);
                telemetry.addData("My Y is", nav.Y);
                tangentTime(nav.X, nav.Y);
                telemetry.addData("Tangent angle:", robotAngle);
                angularMecanum.Left(robotAngle, .6, 0);
                nav.SkystoneNavigationNoTelemetry();
                telemetry.update();
            }
            if (nav.Y >= 1090) {
                mecanum.mecanumNaught();
                notThereYet = false;

            }
        }
    }
    public void moveToPlate2() {
        while (nav.X < 835) {
            nav.SkystoneNavigationNoTelemetry();
            telemetry.addData("Rotation:", nav.Rotation);
            telemetry.addData("My X is", nav.X);
            telemetry.addData("My Y is", nav.Y);
            tangentTime2(nav.X, nav.Y);
            telemetry.addData("Tangent angle:", robotAngle);
            angularMecanum.Left(robotAngle, 1, 0);
            nav.SkystoneNavigationNoTelemetry();
            telemetry.update();
        }
        if (nav.X >= 835) {
            mecanum.mecanumNaught();
        }
    }

    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap, telemetry);
        telemetry.update();
        nav.skystoneNavigationInit(robot);
        waitForStart();

        clamp.setClamp(robot, false, true);

        while (opModeIsActive()) {

                moveToPlate2();
        }
    }
}
// Note that nav.skystoneNavigationInit shouldn't be done in a while loop
// We don't want it init-ing over and over and over