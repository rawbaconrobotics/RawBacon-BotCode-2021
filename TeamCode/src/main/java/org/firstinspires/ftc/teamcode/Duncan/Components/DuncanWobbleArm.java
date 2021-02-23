package org.firstinspires.ftc.teamcode.Duncan.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Represents the mechanism used for lifting the wobble arm
 * @author Raw Bacon Coders
 */

public class DuncanWobbleArm extends DuncanComponentImplBase {

    public CRServo wobbleArm = null;
    public Servo grabber = null;

    private final static String WOBBLE_ARM_NAME = "wobble_arm";
    private final static String GRABBER_NAME = "grabber";

    double downPosition = 0;
    double upPosition = 1;
    double inPosition = 0.75;
    double outPosition = 0;
    public enum GrabberState {IDLE, IN, OUT}

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
        wobbleArm = hardwareMap.crservo.get(WOBBLE_ARM_NAME);
        grabber = hardwareMap.servo.get(GRABBER_NAME);
        wobbleArm.setPower(0);
    }

    public void initAutonomous(){
        wobbleArm = hardwareMap.crservo.get(WOBBLE_ARM_NAME);
        grabber = hardwareMap.servo.get(GRABBER_NAME);
        wobbleArm.setPower(0);
    }

    public GrabberState grabberState = GrabberState.IDLE; //Globalized



    public void grabWobbleGoal (){

            wobbleArm.setPower(0.6 * (gamepad2.left_stick_y));


        switch (grabberState) {

            case IDLE:
                if (gamepad2.b) grabberState = grabberState.IN;
                if (gamepad2.a) grabberState = grabberState.OUT;
                break;

            case IN:
                grabber.setPosition(inPosition);

                grabberState = grabberState.IDLE;
                break;

            case OUT:
                grabber.setPosition(outPosition);
                grabberState = grabberState.IDLE;
        }


    }
    //
    /*public void autoArm(boolean direction){
        //true = up
        //false = down
        if (direction == true){
    //      wobbleArm.setPosition(upPosition);
        }
        else if (direction == false){
    //        wobbleArm.setPosition((downPosition));
        }
    }

     */
    public void stopWobble(){
        wobbleArm.setPower(0);
    }
}
