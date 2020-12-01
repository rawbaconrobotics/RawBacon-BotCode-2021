package org.firstinspires.ftc.teamcode.BotName;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Raw Bacon Coders
 * Autonomous for robot
 */

@Autonomous(name = "BotName Auto")

public class BotNameAuto extends BotNameBaseLinearOpMode{

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void on_init(){
        botName.drivetrain.initAutonomous();
        botName.il.initAutonomous();
        botName.wobble.initAutonomous();
    }

    public void run(){
        //driveFor(distance, speed, timeoutS)
        //strafeFor(distance, speed, timeoutS), negative distance is left
        //autoLaunch(height), height can just be 1 for now
        //drivetrain is the drivetrain, il, is the intake/launcher
        runtime.reset();
        botName.drivetrain.driveFor(86, 1, 5);

        botName.drivetrain.driveFor(-12, 1, 5);

        botName.drivetrain.strafeFor(-8, 1, 5); // Maybe 8?
        botName.drivetrain.strafeFor(-8, 1, 5);
        botName.drivetrain.strafeFor(-8, 1, 5);


    }

    public void on_stop(){
        botName.drivetrain.stopWheels();
    }

}
