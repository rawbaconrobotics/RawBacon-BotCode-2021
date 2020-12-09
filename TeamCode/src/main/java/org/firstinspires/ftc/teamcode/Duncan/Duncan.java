package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanDriveTrain;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanIntakeAndLauncher;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanWobbleArm;
import org.firstinspires.ftc.teamcode.Duncan.Components.RemoteDriving;
/**TODO: Add motion profiling to drivetrain (ramp up speed sample code)
 * TODO: Add bulk reads to the teleopactivated method, remove reads from components and pass in the values
 */

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
    LinearOpMode opMode;
    HardwareMap hwMap;
    /**
     * Runs the teleop on all components
     */
    public void teleOpActivated(){
        //add bulk reads here!!!!!!!!!
        drivetrain.wheelsTeleOp();
        wobble.moveArm();
        //il.runIntakeAndLauncher();
        hwMap = opMode.hardwareMap;
        /**@see org.firstinspires.ftc.robotcontroller.external.samples.ConceptMotorBulkRead */
    }

    /**
     * Constructor
     * @param opMode The opmode in use. use this keyword.
     */
    public Duncan(LinearOpMode opMode){
        drivetrain = new DuncanDriveTrain(opMode);
        wobble = new DuncanWobbleArm(opMode);
        //il = new DuncanIntakeAndLauncher(opMode);
        rdrive = new RemoteDriving(opMode);
        this.opMode = opMode;

    }
}
