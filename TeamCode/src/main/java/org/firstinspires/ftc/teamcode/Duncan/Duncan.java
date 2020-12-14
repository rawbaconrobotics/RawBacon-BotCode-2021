package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanDriveTrain;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanIntakeAndLauncher;
import org.firstinspires.ftc.teamcode.Duncan.Components.DuncanWobbleArm;
import org.firstinspires.ftc.teamcode.Duncan.Components.RemoteDriving;
import org.firstinspires.ftc.teamcode.RevHubStore;

import java.util.List;
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
    private long      e1, e2, e3, e4; // Encoder Values
    private double    v1, v2, v3, v4; // Velocities
    /**
     * Runs the teleop on all components
     */

     public void getMotorsAndEverything(){
         //Gets the values of the motor encoder positions and the velocity of the motors by calling them from the DuncanDriveTrain class
         RevHubStore hubValues = new RevHubStore();
         hubValues.motorEncoderPositions[0] = drivetrain.leftDriveBack.getCurrentPosition();
         hubValues.motorEncoderPositions[1] = drivetrain.rightDriveBack.getCurrentPosition();
         hubValues.motorEncoderPositions[2] = drivetrain.leftDriveFront.getCurrentPosition();
         hubValues.motorEncoderPositions[3] = drivetrain.rightDriveFront.getCurrentPosition();
         hubValues.motorSpeeds[0] = drivetrain.leftDriveBack.getPower();
         hubValues.motorSpeeds[1] = drivetrain.rightDriveBack.getPower();
         hubValues.motorSpeeds[2] = drivetrain.leftDriveFront.getPower();
         hubValues.motorSpeeds[3] = drivetrain.rightDriveFront.getPower();
         //Gets the wobble arm servo position
         hubValues.wobbleServoPosition = wobble.wobbleArm.getPosition();

     }
    public void teleOpActivated(){
        //add bulk reads here!!!!!!!!! - partially done, still need to change the names in components and such to the new values
        drivetrain.wheelsTeleOp();
        wobble.moveArm();
        //il.runIntakeAndLauncher();
        for (LynxModule module : allHubs) {
            module.clearBulkCache();
        }
    }
    List<LynxModule> allHubs;
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

        hwMap = opMode.hardwareMap;
        /**@see org.firstinspires.ftc.robotcontroller.external.samples.ConceptMotorBulkRead */

        List<LynxModule> allHubs = hwMap.getAll(LynxModule.class); //Defines allHubs from the Hardware Map
        int anInt = 0;
        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
            module.clearBulkCache(); //Clears the BulkCache which is necessary due to it being in manual mode

        }

    }
}
