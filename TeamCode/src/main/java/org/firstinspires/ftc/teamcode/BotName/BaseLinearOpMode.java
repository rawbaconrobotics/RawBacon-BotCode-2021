package org.firstinspires.ftc.teamcode.BotName;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class BaseLinearOpMode extends LinearOpMode{
    protected BotName botName;
    public boolean init = true;

    @Override
    public final void runOpMode() throws InterruptedException
    {
        botName = new BotName(this);
        //robot.startup();
        on_init();
        System.out.println("Initialized robot");
        waitForStart();
        System.out.println("Play has been pressed");
        init = false;
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
