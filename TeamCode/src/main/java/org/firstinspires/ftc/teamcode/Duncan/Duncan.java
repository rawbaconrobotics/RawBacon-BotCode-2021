package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanDriveTrain;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanIntakeAndLauncher;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanWobbleArm;
import org.firstinspires.ftc.teamcode.Duncan.Components.RemoteDriving;
import org.firstinspires.ftc.teamcode.Duncan.Components.RevHubStore;

import java.util.List;
/**TODO: Add motion profiling to drivetrain (ramp up speed sample code)
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

     public RevHubStore getMotorsAndEverything(){


         //Gets the values of the motor encoder positions and the velocity of the motors by calling them from the DuncanDriveTrain class
         RevHubStore hubValues = new RevHubStore();
         hubValues.motorEncoderPositions[0] = drivetrain.leftDriveBack.getCurrentPosition();
         hubValues.motorEncoderPositions[1] = drivetrain.rightDriveBack.getCurrentPosition();
         hubValues.motorEncoderPositions[2] = drivetrain.leftDriveFront.getCurrentPosition();
         hubValues.motorEncoderPositions[3] = drivetrain.rightDriveFront.getCurrentPosition();
         hubValues.motorPowers[0] = drivetrain.leftDriveBack.getPower();
         hubValues.motorPowers[1] = drivetrain.rightDriveBack.getPower();
         hubValues.motorPowers[2] = drivetrain.leftDriveFront.getPower();
         hubValues.motorPowers[3] = drivetrain.rightDriveFront.getPower();
         hubValues.motorVelocities[0] = drivetrain.leftDriveBack.getVelocity();
         hubValues.motorVelocities[1] = drivetrain.rightDriveBack.getVelocity();
         hubValues.motorVelocities[2] = drivetrain.leftDriveFront.getVelocity();
         hubValues.motorVelocities[3] = drivetrain.rightDriveFront.getVelocity();
         //Gets the wobble goal grabber position
         hubValues.wobbleGrabberPosition = wobble.grabber.getPosition();
         return hubValues;
     }
    public void teleOpActivated(){
      //   RevHubStore hubValues = getMotorsAndEverything();

        drivetrain.wheelsTeleOp();
        wobble.grabWobbleGoal();
        il.runIntakeAndLauncher();
     /*   for (LynxModule module : allHubs) {
            module.clearBulkCache();
        }
    */
    }

    List<LynxModule> allHubs;
    /**
     * Constructor
     * @param opMode The opmode in use. use this keyword.
     */
    public Duncan(LinearOpMode opMode){
        drivetrain = new DuncanDriveTrain(opMode);
        wobble = new DuncanWobbleArm(opMode);
        il = new DuncanIntakeAndLauncher(opMode);
        rdrive = new RemoteDriving(opMode);
        this.opMode = opMode;

        hwMap = opMode.hardwareMap;

     //   allHubs = hwMap.getAll(LynxModule.class); //Defines allHubs from the Hardware Map

     /*   for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            module.clearBulkCache(); //Clears the BulkCache which is necessary due to it being in manual mode

        }
*/
    }
}
