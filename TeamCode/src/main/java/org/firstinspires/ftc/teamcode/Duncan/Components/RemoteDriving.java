package org.firstinspires.ftc.teamcode.Duncan.Components;

import android.os.BatteryManager;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera2;
import org.openftc.easyopencv.OpenCvWebcam;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static android.content.Context.BATTERY_SERVICE;

public class RemoteDriving extends DuncanComponentImplBase {
    //OpenCvInternalCamera2 phoneCam;
    OpenCvWebcam phoneCam;

    FtcDashboard dashboard = FtcDashboard.getInstance();
    boolean needsReset = false;
    BatteryManager bm = (BatteryManager) hardwareMap.appContext.getSystemService(BATTERY_SERVICE);
    public int percentage = -1;

    public RemoteDriving(LinearOpMode opMode) {
        super(opMode);
    }

    double getBatteryVoltage() {
        double result = Double.POSITIVE_INFINITY;
        for (VoltageSensor sensor : hardwareMap.voltageSensor) {
            double voltage = sensor.getVoltage();
            if (voltage > 0) {
                result = Math.min(result, voltage);
            }
        }
        return result; }
    public double voltage = 0;
    public void init(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.FRONT, cameraMonitorViewId);
        //phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.BACK);
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        voltage = getBatteryVoltage();

        /**We disabled the pipeline right now*/
        //phoneCam.setPipeline(new SamplePipeline());
        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened()
            {
                /*
                 * specify the rotation that the camera is used in. This is so that the image
                 * from the camera sensor can be rotated such that it is always displayed with the image upright.
                 * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                 * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                 * away from the user.
                 */
                phoneCam.startStreaming(960, 720, OpenCvCameraRotation.UPRIGHT);
                //phoneCam.setFlashlightEnabled(true);
            }
        });

        FtcDashboard.getInstance().startCameraStream(phoneCam, 0);
        FtcDashboard.getInstance().setImageQuality(50);
      //  telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());


    }

    public void initAutonomous(){

    }
    Telemetry.Item telePercent = telemetry.addData("Phone battery", -1);
    Telemetry.Item teleVoltage = telemetry.addData("Robot Battery Voltage", -1);
    public void loop(){
        telePercent.setValue(percentage);
        teleVoltage.setValue(voltage);
        telemetry.update();

        /** phoneCam.stopStreaming();**/
        //phoneCam.closeCameraDevice();
      }
    public void onStop(){
        FtcDashboard.getInstance().stopCameraStream();
    }

    private static void logGamepad(Telemetry telemetry, Gamepad gamepad, String prefix) {
        telemetry.addData(prefix + "Synthetic",
                gamepad.getGamepadId() == Gamepad.ID_UNASSOCIATED);
        for (Field field : gamepad.getClass().getFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;

            try {
                telemetry.addData(prefix + field.getName(), field.get(gamepad));
            } catch (IllegalAccessException e) {
                // ignore for now
            }
        }
    }


}
