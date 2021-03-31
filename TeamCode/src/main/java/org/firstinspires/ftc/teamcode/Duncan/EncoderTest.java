package org.firstinspires.ftc.teamcode.Duncan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.broadcom.BroadcomColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import org.openftc.easyopencv.OpenCvInternalCamera2Impl;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static android.content.Context.BATTERY_SERVICE;

@TeleOp(name="Encoder Test")
public class EncoderTest extends DuncanBaseLinearOpMode
{
    DcMotor motors[];
    DcMotor omniLeft, omniRight, omniPerp;
    @Override
    public void on_init(){

        omniLeft = hardwareMap.dcMotor.get("deadwheel_left");
        omniRight = hardwareMap.dcMotor.get("deadwheel_right");
        omniPerp = hardwareMap.dcMotor.get("intake");


        duncan.drivetrain.init();
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        duncan.drivetrain.leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duncan.drivetrain.leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duncan.drivetrain.rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duncan.drivetrain.rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        omniLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        omniRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        omniPerp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        duncan.drivetrain.leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        duncan.drivetrain.leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        duncan.drivetrain.rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        duncan.drivetrain.rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        omniLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        omniRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        omniPerp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motors = new DcMotor[] {duncan.drivetrain.leftDriveFront, duncan.drivetrain.leftDriveBack, duncan.drivetrain.rightDriveFront, duncan.drivetrain.rightDriveBack};
    }

    @Override
    public void on_stop() {

    }

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void run()
    {



        while (opModeIsActive())
        {


            telemetry.addData("Left Front", duncan.drivetrain.leftDriveFront.getCurrentPosition());
            telemetry.addData("Left Back", duncan.drivetrain.leftDriveBack.getCurrentPosition());
            telemetry.addData("Right Front", duncan.drivetrain.rightDriveFront.getCurrentPosition());
            telemetry.addData("Right Back", duncan.drivetrain.rightDriveBack.getCurrentPosition());
            telemetry.addData("Omni Left", omniLeft.getCurrentPosition());
            telemetry.addData("Omni Right", omniRight.getCurrentPosition());
            telemetry.addData("Omni Perpendicular", omniPerp.getCurrentPosition());



            if(gamepad1.a)
            {
                duncan.drivetrain.leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                duncan.drivetrain.leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                duncan.drivetrain.rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                duncan.drivetrain.rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //        omniLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //        omniRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       //         omniPerp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                duncan.drivetrain.leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                duncan.drivetrain.leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                duncan.drivetrain.rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                duncan.drivetrain.rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                omniLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                omniRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                omniPerp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                sleep(500);

                for(DcMotor motor : motors){
                    motor.setPower(0.5);
                }
                runtime.reset();
                while(runtime.time() < 3){
                    telemetry.addData("Left Front", duncan.drivetrain.leftDriveFront.getCurrentPosition());
                    telemetry.addData("Left Back", duncan.drivetrain.leftDriveBack.getCurrentPosition());
                    telemetry.addData("Right Front", duncan.drivetrain.rightDriveFront.getCurrentPosition());
                    telemetry.addData("Right Back", duncan.drivetrain.rightDriveBack.getCurrentPosition());
                    telemetry.addData("Omni Left", omniLeft.getCurrentPosition());
                    telemetry.addData("Omni Right", omniRight.getCurrentPosition());
                    telemetry.addData("Omni Perpendicular", omniPerp.getCurrentPosition());
                    telemetry.addData("MOVING TO", "5000 TICKS");
                    telemetry.update();

                /** Invert left omni and perp omni encoder ticks
                */

                }
                for(DcMotor motor : motors){
                    motor.setPower(0);
                }

            //phoneCam.closeCameraDevice();
             }
            telemetry.update();

            /*
             * For the purposes of this sample, throttle ourselves to 10Hz loop to avoid burning
             * excess CPU cycles for no reason. (By default, telemetry is only sent to the DS at 4Hz
             * anyway). Of course in a real OpMode you will likely not want to do this.
             */
        }
    }


}


