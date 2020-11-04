package org.firstinspires.ftc.teamcode.BotName;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.BotName.Components.AdamWobbleArm;
import org.firstinspires.ftc.teamcode.BotName.Components.BotDriveTrain;
import org.firstinspires.ftc.teamcode.BotName.Components.IntakeAndLauncher;

/**
 * Organizes the various components on the robot
 * @author Raw Bacon Coders
 */

public class BotName {

    //Initialize new components
    public BotDriveTrain drivetrain;
    public AdamWobbleArm wobble;
    public IntakeAndLauncher il;

    /**
     * Runs the teleop on all components
     */
    public void teleOpActivated(){
        drivetrain.wheelsTeleOp();
        wobble.moveArm();
        il.runIntakeAndLauncher();
    }

    /**
     * Constructor
     * @param opMode The opmode in use. use this keyword.
     */
    public BotName (LinearOpMode opMode){
        drivetrain = new BotDriveTrain(opMode);
        wobble = new AdamWobbleArm(opMode);
        il = new IntakeAndLauncher(opMode);
    }
}
