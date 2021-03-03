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
public class EncoderTest extends LinearOpMode
{
    private final static String FRONTRIGHT_WHEEL_NAME = "right_drive_front";
    private final static String FRONTLEFT_WHEEL_NAME = "left_drive_front";
    private final static String BACKRIGHT_WHEEL_NAME = "right_drive_back";
    private final static String BACKLEFT_WHEEL_NAME = "left_drive_back";
    private final static String OMNI_LEFT = "deadwheel_left";
    private final static String OMNI_RIGHT = "deadwheel_right";
    private final static String OMNI_PERP = "intake";


    private DcMotor leftDrive = null;
    private DcMotor leftDriveBack = null;
    private DcMotor rightDriveBack = null;
    private DcMotor rightDrive = null;
    private DcMotor omniLeft = null;
    private DcMotor omniRight = null;
    private DcMotor omniPerp = null;

    @Override
    public void runOpMode()
    {

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        leftDrive  = hardwareMap.get(DcMotor.class, FRONTLEFT_WHEEL_NAME);
        leftDriveBack  = hardwareMap.get(DcMotor.class, BACKLEFT_WHEEL_NAME);
        rightDrive  = hardwareMap.get(DcMotor.class, FRONTRIGHT_WHEEL_NAME);
        rightDriveBack = hardwareMap.get(DcMotor.class, BACKRIGHT_WHEEL_NAME);
        omniLeft = hardwareMap.get(DcMotor.class, OMNI_LEFT);
       omniRight = hardwareMap.get(DcMotor.class, OMNI_RIGHT);
        omniPerp = hardwareMap.get(DcMotor.class, OMNI_PERP);

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        omniLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        omniRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        omniPerp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        omniLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        omniRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       omniPerp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);


        telemetry.addLine("Waiting for start");
        telemetry.update();

        waitForStart();

        DcMotor motors[] = {leftDrive, leftDriveBack, rightDrive, rightDriveBack};

        leftDrive  = hardwareMap.get(DcMotor.class, FRONTLEFT_WHEEL_NAME);
        leftDriveBack  = hardwareMap.get(DcMotor.class, BACKLEFT_WHEEL_NAME);
        rightDrive  = hardwareMap.get(DcMotor.class, FRONTRIGHT_WHEEL_NAME);
        rightDriveBack = hardwareMap.get(DcMotor.class, BACKRIGHT_WHEEL_NAME);
        omniLeft = hardwareMap.get(DcMotor.class, OMNI_LEFT);
        omniRight = hardwareMap.get(DcMotor.class, OMNI_RIGHT);
        omniPerp = hardwareMap.get(DcMotor.class, OMNI_PERP);

        while (opModeIsActive())
        {


            telemetry.addData("Left Front", leftDrive.getCurrentPosition());
            telemetry.addData("Left Back", leftDriveBack.getCurrentPosition());
            telemetry.addData("Right Front", rightDrive.getCurrentPosition());
            telemetry.addData("Right Back", rightDriveBack.getCurrentPosition());
            telemetry.addData("Omni Left", omniLeft.getCurrentPosition());
            telemetry.addData("Omni Right", omniRight.getCurrentPosition());
            telemetry.addData("Omni Perpendicular", omniPerp.getCurrentPosition());



            if(gamepad1.a)
            {
                leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //        omniLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //        omniRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       //         omniPerp.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


                leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                omniLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                omniRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                omniPerp.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                sleep(500);

                for(DcMotor motor : motors){
                    motor.setPower(0.5);
                }
                while(Math.abs(leftDrive.getCurrentPosition()) < 5000 || Math.abs(leftDriveBack.getCurrentPosition()) < 5000 || Math.abs(rightDrive.getCurrentPosition()) < 5000 || Math.abs(rightDriveBack.getCurrentPosition()) < 5000){
                    telemetry.addData("Left Front", leftDrive.getCurrentPosition());
                    telemetry.addData("Left Back", leftDriveBack.getCurrentPosition());
                    telemetry.addData("Right Front", rightDrive.getCurrentPosition());
                    telemetry.addData("Right Back", rightDriveBack.getCurrentPosition());
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


