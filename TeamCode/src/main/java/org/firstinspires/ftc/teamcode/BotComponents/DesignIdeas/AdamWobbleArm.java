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
 * Represents the four wheel mechanum drive on the bot
 * @author Raw Bacon Coders
 */

public class AdamWobbleArm extends BotComponentImplBase {

    private Servo wobbleArm = null;

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

        wobbleArm = (Servo) hardwareMap.servo.get(WOBBLE_ARM_NAME);

    }

    public void initAutonomous(){

        wobbleArm = (Servo) hardwareMap.servo.get(WOBBLE_ARM_NAME);
    }

    public void moveArm(){
        double downPosition = 0;
        double upPosition = 1;
        boolean armPosition = true;
        if (opModeIsActive()){
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
