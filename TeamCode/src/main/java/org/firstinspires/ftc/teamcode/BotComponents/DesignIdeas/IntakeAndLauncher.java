package org.firstinspires.ftc.teamcode.BotComponents.DesignIdeas;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import static android.os.SystemClock.sleep;

import org.firstinspires.ftc.teamcode.BotComponents.DesignIdeas.Components.BotComponentImplBase;

/**
 * Represents the mechanisms for the intake and launcher systems
 * @author Raw Bacon Coders
 */

public class IntakeAndLauncher extends BotComponentImplBase {

    private DCMotor intake = null;
    private DCMotor launcher = null;
    private Servo hopper = null;
    private Servo transfer = null;

    private final static String INTAKE_NAME = "intake";
    private final static String LAUNCHER_NAME = "launcher";
    private final static String HOPPER_NAME = "hopper";
    private final static String TRANSFER_NAME = "transfer";

    /**
     * Constructor
     *
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */

    public IntakeAndLauncher (LinearOpMode opMode) {super(opMode);}

    /**
     * Hardware maps and sets modes of all motors
     */

    @Override

    public void init(){

        intake = (DCMotor) hardwareMap.dcMotor.get(INTAKE_NAME);
        launcher = (DCMotor) hardwareMap.dcMotor.get(LAUNCHER_NAME);
        hopper = (Servo) hardwareMap.servo.get(HOPPER_NAME);
        transfer = (Servo)hardwareMap.servo.get(TRANSFER_NAME);

    }

    public void initAutonomous(){

        intake = (DCMotor) hardwareMap.dcMotor.get(INTAKE_NAME);
        launcher = (DCMotor) hardwareMap.dcMotor.get(LAUNCHER_NAME);
        hopper = (Servo) hardwareMap.servo.get(HOPPER_NAME);
        transfer = (Servo)hardwareMap.servo.get(TRANSFER_NAME);

    }

    public void moveArm(){
        double hopperDownPosition = 0;
        double hopperUpPosition = 1;
        boolean hopperPosition = true;

        double transferInPosition = 0;
        double transferOutPosition = 1;
        //true = hopper is down, transfer is out
        // false = hopper is up, transfer is in

        boolean intakeStatus = false;
        boolean launcherStatus = false;
        //true = on, false = off

        while (opModeIsActive()) {
            //turns the intake on and off
            if (gamepad1.y) {
                if (intakeStatus = false) {
                    intake.setPower(1);
                    intakeStatus = true;
                } else if (intakeStatus = true) {
                    intake.setPower(0);
                    intakeStatus = false;
                }
            }
            //moves the hopper if the second driver hits the b button
            if (gamepad2.b) {
                if (hopperPosition = true) {
                    hopper.setPosition(hopperUpPosition);
                    hopperPosition = false;
                }
                else if (hopperPosition = false) {
                    hopper.setPosition(hopperDownPosition);
                    hopperPosition = true;
                }
            }
            //shoots 1 ring
            if (gamepad2.x) {
                transfer.setPosition(transferInPosition);
                sleep(1000);
                transfer.setPosition(transferOutPosition);
                }
            //turns the launcher on and off
            if (gamepad1.x) {
                if (launcherStatus = false) {
                    launcher.setPower(1);
                    launcherStatus = true;
                    }
                else if (launcherStatus = true) {
                    launcher.setPower(0);
                    launcherStatus = false;
                    }
                }

            }
        }
    }
