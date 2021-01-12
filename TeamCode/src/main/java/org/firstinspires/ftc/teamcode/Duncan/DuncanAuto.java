package org.firstinspires.ftc.teamcode.Duncan;

import com.arcrobotics.ftclib.vision.UGRectDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * @author Raw Bacon Coders
 * Autonomous for robot
 */

@Autonomous(name = "Duncan Auto")
@Disabled

public class DuncanAuto extends DuncanBaseLinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void on_init(){
        duncan.drivetrain.initAutonomous();
     //   duncan.il.initAutonomous();
        duncan.wobble.initAutonomous();
    }

    public void run(){

        runtime.reset();
       // duncan.wobble.autoArm(false); //move the wobble arm down
        sleep(500);
        duncan.drivetrain.driveFor(96, 1, 10); //drive forwards 96 inches
        sleep(500);
        duncan.drivetrain.driveFor(-12, 1, 5); //drive backwards 12 inches
        sleep(500);
        duncan.drivetrain.strafeFor(-(30.95), 1, 5); //strafe left 30.95 inches
        sleep(500);
     //   duncan.il.autoLaunch(1); //launch a ring at the first powershot
     //   sleep(500);
        duncan.drivetrain.strafeFor(-8, 1, 5); //strafe left 8 inches
     //   sleep(500);
     //   duncan.il.autoLaunch(1); //launch a ring at the second powershot
        sleep(500);
        duncan.drivetrain.strafeFor(-8, 1, 5); //strafe left 8 inches
      //  sleep(500);
      //  duncan.il.autoLaunch(1); //launch a ring at the third powershot

    }

    public void on_stop(){
        duncan.drivetrain.stopWheels();
    }

}
