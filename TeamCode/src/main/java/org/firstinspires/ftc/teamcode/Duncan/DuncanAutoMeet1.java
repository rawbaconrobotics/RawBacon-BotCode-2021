package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * @author Raw Bacon Coders
 * Autonomous for robot
 */

@Autonomous(name = "Duncan MEET 1")


public class DuncanAutoMeet1 extends DuncanBaseLinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void on_init(){
        duncan.drivetrain.initAutonomous();
        //duncan.il.initAutonomous(); //null pointer
        duncan.wobble.initAutonomous();
    }

    public void run(){

        runtime.reset();
        duncan.drivetrain.driveFor(120, 1, 10); //drive forwards 120 inches
        sleep(500);
        duncan.drivetrain.driveFor(-116, 1, 10); //drive backwards 116 inches
        sleep(500);
        duncan.drivetrain.strafeFor(24, 1, 5); //strafe right 24 inches
        sleep(500);
        duncan.drivetrain.driveFor(96, 1, 10); //drive forwards 116 inches
        sleep(500);

    }

    public void on_stop(){
        duncan.drivetrain.stopWheels();
    }

}
