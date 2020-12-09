package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * @author Raw Bacon Coders
 * Autonomous for robot
 */

@Autonomous(name = "Duncan NO WOBBLE")

public class DuncanAutoNoWobble extends DuncanBaseLinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void on_init(){
        duncan.drivetrain.initAutonomous();
       // duncan.il.initAutonomous();
        duncan.wobble.initAutonomous();
    }

    public void run(){

        runtime.reset();
        duncan.drivetrain.driveFor(84, 1, 10); //drive forwards 84 inches

    }

    public void on_stop(){
        duncan.drivetrain.stopWheels();
    }

}
