package org.firstinspires.ftc.teamcode.Duncan.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Represents the mechanism used for lifting the wobble arm
 * @author Raw Bacon Coders
 */

public class DuncanWobbleArm extends DuncanComponentImplBase {

    private Servo wobbleArm = null;

    private final static String WOBBLE_ARM_NAME = "wobble_arm";

    double downPosition = 0;
    double upPosition = 1;
    boolean armPosition = true;
    public enum ArmState { IDLE, DOWN, UP }
    //true=arm is down, false = arm is up

    /**
     * Constructor
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */

    public DuncanWobbleArm(LinearOpMode opMode) {super(opMode);}

    /**
     * Hardware maps and sets modes of all motors
     */

    @Override

    public void init(){
        wobbleArm = (Servo) hardwareMap.servo.get(WOBBLE_ARM_NAME);
    }

    public void initAutonomous(){
        wobbleArm = (Servo) hardwareMap.servo.get(WOBBLE_ARM_NAME);
        wobbleArm.setPosition(upPosition);
    }

    public void moveArm(){
        ArmState armState = ArmState.IDLE;

        while (opModeIsActive()) {

            //anything inside the brackets after switch(autoState) defines the robot's action at each state
            switch (armState) {
                //case IDLE is equivalent to if(liftState == LiftState.IDLE)
                case IDLE:
                    if (gamepad1.b) armState = armState.UP;
                    if (gamepad1.a) armState = armState.DOWN;
                    break;

                case UP:
                    //  if(topTouchSensor.isPressed()){
                    wobbleArm.setPosition(upPosition);
                    //}
                    armState = armState.IDLE;

                    //else liftMotor.setPower(1);
                    break;
                case DOWN:
                    wobbleArm.setPosition(downPosition);
                    armState = armState.IDLE;
            }



/*

            if (gamepad2.a){

                if (armPosition = true){
                    wobbleArm.setPosition(upPosition);
                    armPosition = false;
                }
                else if (armPosition = false){
                    wobbleArm.setPosition(downPosition);
                    armPosition = true;
                }
            }

        */
        }
    }
    
    public void autoArm(boolean direction){
        //true = up
        //false = down
        if (direction == true){
            wobbleArm.setPosition(upPosition);
        }
        else if (direction == false){
            wobbleArm.setPosition((downPosition));
        }
    }
}
