package org.firstinspires.ftc.teamcode.BotName.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static android.os.SystemClock.sleep;

/**
 * Represents the mechanisms for the intake and launcher systems
 * @author Raw Bacon Coders
 */

public class BotNameIntakeAndLauncher extends BotNameComponentImplBase {

    private DcMotor intake = null;
    private DcMotor launcher = null;
    private Servo hopper = null;
    private Servo transfer = null;

    private final static String INTAKE_NAME = "intake";
    private final static String LAUNCHER_NAME = "launcher";
    private final static String HOPPER_NAME = "hopper";
    private final static String TRANSFER_NAME = "transfer";

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

    /**
     * Constructor
     *
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */

    public BotNameIntakeAndLauncher(LinearOpMode opMode) {super(opMode);}

    /**
     * Hardware maps and sets modes of all motors
     */

    @Override

    public void init(){

        intake = hardwareMap.dcMotor.get(INTAKE_NAME);
        launcher = hardwareMap.dcMotor.get(LAUNCHER_NAME);
        hopper = hardwareMap.servo.get(HOPPER_NAME);
        transfer = hardwareMap.servo.get(TRANSFER_NAME);

    }

    public void initAutonomous(){

        intake = hardwareMap.dcMotor.get(INTAKE_NAME);
        launcher = hardwareMap.dcMotor.get(LAUNCHER_NAME);
        hopper = hardwareMap.servo.get(HOPPER_NAME);
        transfer = hardwareMap.servo.get(TRANSFER_NAME);

    }

    public void runIntakeAndLauncher(){


        while (opModeIsActive()) {
            //turns the intake on and off
            if (gamepad1.left_trigger == 1) {
                if (intakeStatus = false) {
                    intake.setPower(1);
                    intakeStatus = true;
                } else if (intakeStatus = true) {
                    intake.setPower(0);
                    intakeStatus = false;
                }
            }

            //moves the hopper if the second driver hits the b button
            while (gamepad2.left_trigger == 1) {
                hopperPosition = false;
            }

            while (gamepad2.left_trigger == 0){
                hopperPosition = true;
            }
            if (hopperPosition = true){
                if (gamepad2.left_trigger == 1){
                    hopper.setPosition(hopperUpPosition);
                }
            }
            if (hopperPosition = false) {
                    hopper.setPosition(hopperDownPosition);
                }

            //shoots 1 ring
            if (gamepad2.right_trigger == 1) {
                transfer.setPosition(transferInPosition);
                sleep(1000);
                transfer.setPosition(transferOutPosition);
                }

            //turns the launcher on and off
            if (gamepad2.x) {
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

        public void autoLaunch(double height){
        if (height == 1){
            launcher.setPower(1);
            transfer.setPosition(transferInPosition);
            sleep(1000);
            transfer.setPosition(transferOutPosition);
            sleep(1000);
            launcher.setPower(0);
        }
        else if (height == 2){
            launcher.setPower(1);
            transfer.setPosition(transferInPosition);
            sleep(1000);
            transfer.setPosition(transferOutPosition);
            sleep(1000);
            launcher.setPower(0);
        }
        else if (height == 3){
            launcher.setPower(1);
            transfer.setPosition(transferInPosition);
            sleep(1000);
            transfer.setPosition(transferOutPosition);
            sleep(1000);
            launcher.setPower(0);
        }

        }
    }
