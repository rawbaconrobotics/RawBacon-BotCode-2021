package org.firstinspires.ftc.teamcode.Duncan.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static android.os.SystemClock.sleep;

/**
 * Represents the mechanisms for the intake and launcher systems
 * @author Raw Bacon Coders
 */

public class DuncanIntakeAndLauncher extends DuncanComponentImplBase {

    private DcMotor intake = null;
    private DcMotor launcher = null;
    private Servo hopper = null;
    private Servo transfer = null;

    private final static String INTAKE_NAME = "deadwheel_left"; //plugged into deadwheel encoder port
    private final static String LAUNCHER_NAME = "deadwheel_right";
    private final static String HOPPER_NAME = "deadwheel_perp";
    private final static String TRANSFER_NAME = "transfer";

    final double hopperDownPosition = 0;
    final double hopperUpPosition = 1;

    final double transferInPosition = 0;
    final double transferOutPosition = 1;

    public enum HopperState {DOWN, UP, IDLE};
    public enum TransferState {OUT, IN, IDLE};
    public enum IntakeState {INTAKE, OUTTAKE, IDLE};
    public enum LauncherState {LAUNCHING, IDLE};



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
    public HopperState hopperState = HopperState.IDLE;
    public TransferState transferState = TransferState.IDLE;
    public IntakeState intakeState = IntakeState.IDLE;
    public LauncherState launcherState = LauncherState.IDLE;


    public void runIntakeAndLauncher(){


        while (opModeIsActive()) {

            switch (intakeState) {
                case IDLE:
                    if (gamepad1.left_trigger > 0.5){
                        intakeState = IntakeState.INTAKE;
                        intake.setPower(1);
                    }else if(gamepad1.right_trigger > 0.5){
                        intakeState = IntakeState.OUTTAKE;
                        intake.setPower(-1);
                    }
                    break;
                case INTAKE:
                    if (gamepad1.left_trigger < 0.5) {
                        intakeState = IntakeState.IDLE;
                        intake.setPower(0);
                    }
                    break;

                case OUTTAKE:
                    if (gamepad1.right_trigger < 0.5) {
                        intakeState = IntakeState.IDLE;
                        intake.setPower(0);
                    }
                    break;
            }



            switch (hopperState) {
                case UP:
                    hopper.setPosition(hopperUpPosition);
                    transfer.setPosition(transferInPosition);
                    hopperState = hopperState.IDLE;
                    transferState = transferState.IDLE;
                    break;
                case DOWN:
                    hopper.setPosition(hopperDownPosition);
                    transfer.setPosition(transferOutPosition);
                    hopperState = hopperState.IDLE;
                    transferState = transferState.IDLE;
                    break;
                case IDLE:
                    if(gamepad2.left_trigger > 0.5) {
                        hopperState = HopperState.UP;
                        transferState = TransferState.IN;
                    }
                    if(gamepad2.left_trigger < 0.5) {
                        hopperState = HopperState.DOWN;
                        transferState = TransferState.OUT;
                    }
                    break;

            }

            switch(launcherState){
                case IDLE:
                    if(gamepad2.x) {
                        launcherState = LauncherState.LAUNCHING;
                        launcher.setPower(1);
                    }
                    break;
                case LAUNCHING:
                    if(!gamepad2.x){
                        launcherState = LauncherState.IDLE;
                        launcher.setPower(0);
                    }
                    break;
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
