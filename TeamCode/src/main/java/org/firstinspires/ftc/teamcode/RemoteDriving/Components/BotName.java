package org.firstinspires.ftc.teamcode.RemoteDriving.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Represents the mechanism used for lifting the wobble arm
 * @author Raw Bacon Coders
 */

public class BotName extends BotNameComponentImplBase {

    private Servo wobbleArm = null;

    private final static String WOBBLE_ARM_NAME = "wobble_arm";

    /**
     * Constructor
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */

    public BotName(LinearOpMode opMode) {super(opMode);}

    /**
     * Hardware maps and sets modes of all motors
     */

    @Override

    public void init(){

        wobbleArm = (Servo) hardwareMap.servo.get(WOBBLE_ARM_NAME);

    }

    public void initAutonomous(){

        wobbleArm = (Servo) hardwareMap.servo.get(WOBBLE_ARM_NAME);
    }

    public void moveArm(){
        double downPosition = 0;
        double upPosition = 1;
        boolean armPosition = true;
        //true=arm is down, false = arm is up
        while (opModeIsActive()){
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

        }
    }
}
