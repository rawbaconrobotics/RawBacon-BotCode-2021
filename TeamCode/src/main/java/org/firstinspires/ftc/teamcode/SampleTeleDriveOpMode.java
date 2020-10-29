package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/*
 * This sample demonstrates how to stream frames from Vuforia to the dashboard. Make sure to fill in
 * your Vuforia key below and select the 'Camera' preset on top right of the dashboard. This sample
 * also works for UVCs with slight adjustments.
 */
@Autonomous(name="Vuforia Test", group="Iterative Opmode")
public class SampleTeleDriveOpMode extends LinearOpMode {

    public static final String VUFORIA_LICENSE_KEY = "ASX0h5P/////AAABmRSjJSDG9k6QgCPHTUuoh/soPfgx3VR1JxvBhAp7cgQBWPKmsMGKcYho59KRFxQ41Jgp6nFnYIcuLPkKk1Ru4Y5Ba2cNpdp6/rrip+nShU7X627GDeyugYqlDL6enp8NIwwvdALFeg724/nXsW7bqkQliRAX92DkAaxzqz7Wv/g6JMycZtQxPf8g1ufm+jy0CCBl78iUpLBhIRlhxaSBa/8u5Os7ZxlwER8TsVcX3zgkpOajyD8/dCxLqek5+8GJMRO2eiiJLnuSbQzcbpbEBRpGIpzYVUSy/bdP1ANKypAKlbapavLHycuodFjhhukeMw1NYkr4WP6vGRjWB+W/QUPIz8HKCq+zhzOTeYyZm9MJ";


    @Override
    public void runOpMode() throws InterruptedException {
        // gives Vuforia more time to exit before the watchdog notices
        msStuckDetectStop = 2500;

        VuforiaLocalizer.Parameters vuforiaParams = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        vuforiaParams.vuforiaLicenseKey = VUFORIA_LICENSE_KEY;
        vuforiaParams.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforia = ClassFactory.getInstance().createVuforia(vuforiaParams);

        FtcDashboard.getInstance().startCameraStream(vuforia, 0);

        waitForStart();

        while (opModeIsActive());
    }
}