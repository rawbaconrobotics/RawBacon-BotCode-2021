package org.firstinspires.ftc.teamcode.Duncan;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Duncan.Components.RemoteDriving;

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
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        duncan.drivetrain.init();
        duncan.wobble.init();
       // duncan.il.init();
        duncan.rdrive.init();

    }

    Telemetry.Item telePercent = telemetry.addData("Phone battery", -1);
    Telemetry.Item teleVoltage = telemetry.addData("Robot Battery Voltage", -1);
    Telemetry.Item teleDrive = telemetry.addData("Drive", -1);
    Telemetry.Item teleTurn = telemetry.addData("turn", -1);

    void addTelemetry(){

        telemetry.addData("Phone battery", duncan.rdrive.percentage);
        telemetry.addData("Robot Battery Voltage", duncan.rdrive.voltage);
        telemetry.addData("Drive", duncan.drivetrain.drive);
        telemetry.addData("Turn", duncan.drivetrain.turn);

        telemetry.update();

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
            //duncan.rdrive.loop();
            //telemetry.clear();
            addTelemetry();
        }
    }
    /**
     * Stops the teleop
     */
    @Override
    public void on_stop() {
        duncan.rdrive.onStop();
    }
}
