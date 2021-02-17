package org.firstinspires.ftc.teamcode.Duncan.Components;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.TimeUnit;

import static android.os.SystemClock.sleep;

/**
 * Represents the mechanisms for the intake and launcher systems
 * @author Raw Bacon Coders
 */
@Config
public class DuncanIntakeAndLauncher extends DuncanComponentImplBase {

    private DcMotor intake = null;
    private DcMotor launcher = null;
    private Servo hopper = null;
    private Servo transfer = null;

    private final static String INTAKE_NAME = "intake"; //plugged into deadwheel encoder port
    private final static String LAUNCHER_NAME = "launcher";
    private final static String HOPPER_NAME = "hopper";
    private final static String TRANSFER_NAME = "transfer";

    public static double hopperDownPosition = 0.2;
    public static double hopperUpPosition = 0.53;

    public static double transferInPosition = 0.4;
    public static double transferOutPosition = 0.65;

    enum HopperState {DOWN, UP};
    enum TransferState {OUT, IN, IDLE};
    enum IntakeState {INTAKE, OUTTAKE, IDLE};
    enum LauncherState {LAUNCHING, IDLE};



    /**
     * Constructor
     *
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */

    public DuncanIntakeAndLauncher(LinearOpMode opMode) {super(opMode);}

    /**
     * Hardware maps and sets modes of all motors
     */

    @Override
    public void init(){

        intake = hardwareMap.dcMotor.get(INTAKE_NAME);
        launcher = hardwareMap.dcMotor.get(LAUNCHER_NAME);
        hopper = hardwareMap.servo.get(HOPPER_NAME);
        transfer = hardwareMap.servo.get(TRANSFER_NAME);

        intake.setPower(0);
        launcher.setPower(0);
        hopper.setPosition(hopperDownPosition);
        transfer.setPosition(transferOutPosition);

    }

    public void initAutonomous(){

        intake = hardwareMap.dcMotor.get(INTAKE_NAME);
        launcher = hardwareMap.dcMotor.get(LAUNCHER_NAME);
        hopper = hardwareMap.servo.get(HOPPER_NAME);
        transfer = hardwareMap.servo.get(TRANSFER_NAME);

        intake.setPower(0);
        launcher.setPower(0);
        hopper.setPosition(hopperUpPosition);
        transfer.setPosition(transferOutPosition);

    }
    public HopperState hopperState = HopperState.UP;
    public TransferState transferState = TransferState.IDLE;
    public IntakeState intakeState = IntakeState.IDLE;
    public LauncherState launcherState = LauncherState.IDLE;
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime launchTime = new ElapsedTime();
    private ElapsedTime intakeTime = new ElapsedTime();


    public void runIntakeAndLauncher(){



            switch (intakeState) {
                case IDLE:
                    if (gamepad2.left_trigger > 0.5 && (intakeTime.time() > 0.5)){
                        intakeState = IntakeState.INTAKE;
                        intakeTime.reset();
                        intake.setPower(1);
                    }else if(gamepad2.right_trigger > 0.5 && (intakeTime.time() > 0.5)){
                        intakeState = IntakeState.OUTTAKE;
                        intake.setPower(-1);
                        intakeTime.reset();
                    }
                    break;
                case INTAKE:
                    if (gamepad2.left_trigger > 0.5 && (intakeTime.time() > 0.5)) {
                        intakeState = IntakeState.IDLE;
                        intake.setPower(0);
                        intakeTime.reset();
                    }
                    break;
                case OUTTAKE:
                    if (gamepad2.right_trigger > 0.5 && (intakeTime.time() > 0.5)) {
                        intakeState = IntakeState.IDLE;
                        intake.setPower(0);
                        intakeTime.reset();
                    }
                    break;
            }


            switch (hopperState) {
                case UP:
                    if(gamepad2.dpad_down) {
                        hopper.setPosition(hopperDownPosition);
                        hopperState = HopperState.DOWN;
                    }
                    break;
                case DOWN:
                    if(gamepad2.dpad_up) {
                        hopper.setPosition(hopperUpPosition);
                        hopperState = HopperState.UP;
                    }
                    break;
            }

            switch (transferState) {
                case IN:
                    transfer.setPosition(transferInPosition);
                    if(runtime.time() > 0.5){
                        transferState = transferState.OUT;
                    }
                    break;
                case OUT:
                    transfer.setPosition(transferOutPosition);
                    transferState = transferState.IDLE;
                    break;
                case IDLE:
                    if(gamepad1.right_trigger > 0.5) {
                        runtime.reset();
                        transferState = TransferState.IN;
                    }
                    break;
//
            }



            switch(launcherState){
                case IDLE:
                    if(gamepad2.right_bumper && (launchTime.time() > 0.5)) {
                        launcherState = LauncherState.LAUNCHING;
                        launchTime.reset();
                        launcher.setPower(0.6);
                    }
                    break;
                case LAUNCHING:
                    if(gamepad2.right_bumper && (launchTime.time() > 0.5)){
                        launcherState = LauncherState.IDLE;
                        launchTime.reset();
                        launcher.setPower(0);
                    }
                    break;
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
