package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanDriveTrain;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanIntakeAndLauncher;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanWobbleArm;
import org.firstinspires.ftc.teamcode.Duncan.Components.RemoteDriving;

/**
 * Organizes the various components on the robot
 * @author Raw Bacon Coders
 */

public class Duncan {

    //Initialize new components
    DuncanDriveTrain drivetrain;
    DuncanWobbleArm wobble;
    DuncanIntakeAndLauncher il;
    RemoteDriving rdrive;


    /**
     * Runs the teleop on all components
     */
    public void teleOpActivated(){
        //add bulk reads here!!!!!!!!!
        drivetrain.wheelsTeleOp();
        wobble.moveArm();
        il.runIntakeAndLauncher();
    }

    /**
     * Constructor
     * @param opMode The opmode in use. use this keyword.
     */
    public Duncan(LinearOpMode opMode){
        drivetrain = new DuncanDriveTrain(opMode);
        wobble = new DuncanWobbleArm(opMode);
        il = new DuncanIntakeAndLauncher(opMode);
        rdrive = new RemoteDriving(opMode);

    }
}
