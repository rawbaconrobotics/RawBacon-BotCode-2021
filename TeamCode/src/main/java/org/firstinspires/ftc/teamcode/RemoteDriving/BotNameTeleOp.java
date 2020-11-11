package org.firstinspires.ftc.teamcode.RemoteDriving;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * The teleop
 * @auther Raw Bacon Coders
 */


@TeleOp(name="OFFICIAL TeleOp")
@Disabled

public class BotNameTeleOp extends BotNameBaseLinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    /**
     * Initializes the teleop
     */
    @Override
    public void on_init(){
        botName.drivetrain.init();
        botName.wobble.init();
        botName.il.init();
    }

    /**
     * Activates the teleop
     */
    @Override
    public void run(){
        runtime.reset();
        telemetry.addData("Runtime Reset", "Complete");
        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()){
            botName.teleOpActivated();
        }
    }

    /**
     * Stops the teleop
     */
    @Override
    public void on_stop() {
    }
}
