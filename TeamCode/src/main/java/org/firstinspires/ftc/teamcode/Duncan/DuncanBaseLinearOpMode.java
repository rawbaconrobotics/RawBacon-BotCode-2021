package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class DuncanBaseLinearOpMode extends LinearOpMode{
    protected Duncan duncan;

    @Override
    public final void runOpMode() throws InterruptedException
    {
        duncan = new Duncan(this);
        //robot.startup();
        on_init();
        System.out.println("Initialized robot");
        waitForStart();
        System.out.println("Play has been pressed");
        run();
        System.out.println("Finished running the robot");
        on_stop();
        System.out.println("Robot Stopped!");
        //robot.shutdown();
    }

    public abstract void run();
    public abstract void on_init();
    public abstract void on_stop();

}
