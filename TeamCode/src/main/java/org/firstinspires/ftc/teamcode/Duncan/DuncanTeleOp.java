package org.firstinspires.ftc.teamcode.Duncan;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * The teleop
 * @auther Raw Bacon Coders
 */


@TeleOp(name="OFFICIAL Duncan TeleOp")

public class DuncanTeleOp extends DuncanBaseLinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    /**
     * Initializes the teleop
     */
    @Override
    public void on_init(){
        duncan.drivetrain.init();
        duncan.wobble.init();
        //duncan.il.init();
        duncan.rdrive.init();
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
            duncan.teleOpActivated();
            duncan.rdrive.loop();
        }
    }

    /**
     * Stops the teleop
     */
    @Override
    public void on_stop() {
        duncan.rdrive.onStop();
        duncan.drivetrain.stopWheels();
    }
}
