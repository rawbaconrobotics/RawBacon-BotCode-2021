package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class ASampleOpMode extends OpMode {
    //Just a sample OpMode class
    //Use @Teleop() for Teleop (Driving) or @Autonomous() for Auto (Driverless)
    @Override
    public void init() {
        telemetry.addData("Hello World", "!");
    }

    @Override
    public void loop() { //Put gamepad input stuff in loop

    }
}