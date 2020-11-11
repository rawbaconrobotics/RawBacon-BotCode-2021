package org.firstinspires.ftc.teamcode.RemoteDriving;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RemoteDriving.Components.BotNameDriveTrain;
import org.firstinspires.ftc.teamcode.RemoteDriving.Components.BotNameIntakeAndLauncher;

/**
 * Organizes the various components on the robot
 * @author Raw Bacon Coders
 */

public class BotName {

    //Initialize new components
    public BotNameDriveTrain drivetrain;
    public org.firstinspires.ftc.teamcode.RemoteDriving.Components.BotName wobble;
    public BotNameIntakeAndLauncher il;

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
        drivetrain = new BotNameDriveTrain(opMode);
        wobble = new org.firstinspires.ftc.teamcode.RemoteDriving.Components.BotName(opMode);
        il = new BotNameIntakeAndLauncher(opMode);
    }
}
