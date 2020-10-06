package org.firstinspires.ftc.teamcode.BotComponents.DesignIdeas;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.BotComponents.Components.BotComponentImplBase;

/**
 * Represents the four wheel mechanum drive on the bot
 * @author Raw Bacon Coders
 */

public class AdamWobbleArm extends BotComponentImplBase {

    private CRServo wobbleArm = null;

    private final static String WOBBLE_ARM_NAME = "wobble_arm";

    /**
     * Constructor
     *
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */

    public AdamWobbleArm (LinearOpMode opMode) {super(opMode);}

    /**
     * Hardware maps and sets modes of all motors
     */

    @Override

    public void init(){

        wobbleArm = (CRServo) hardwareMap.crservo.get(WOBBLE_ARM_NAME);

    }

    public void initAutonomous(){

        wobbleArm = (CRServo) hardwareMap.crservo.get(WOBBLE_ARM_NAME);
    }

    public void moveArm(){

        if (gamepad2.left_stick_y > 0){
            wobbleArm.setPower(0.5);
        }
        else if (gamepad2.left_stick_y < 0){
            wobbleArm.setPower(-0.5);
        }
        else{
            wobbleArm.setPower(0);
        }
    }
}
