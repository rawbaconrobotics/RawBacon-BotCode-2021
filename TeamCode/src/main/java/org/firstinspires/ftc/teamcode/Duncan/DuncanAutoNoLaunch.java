package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * @author Raw Bacon Coders
 * Autonomous for robot
 */

@Autonomous(name = "Duncan NO LAUNCH")

public class DuncanAutoNoLaunch extends DuncanBaseLinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void on_init(){
        duncan.drivetrain.initAutonomous();
        duncan.il.initAutonomous();
        duncan.wobble.initAutonomous();
    }

    public void run(){

        runtime.reset();
        duncan.wobble.autoArm(false); //move the wobble arm down
        sleep(500);
        duncan.drivetrain.driveFor(96, 1, 10); //drive forwards 96 inches
        sleep(500);
        duncan.drivetrain.driveFor(-12, 1, 5); //drive backwards 12 inches

    }

    public void on_stop(){
        duncan.drivetrain.stopWheels();
    }

}
