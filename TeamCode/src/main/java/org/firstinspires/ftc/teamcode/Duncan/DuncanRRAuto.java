package org.firstinspires.ftc.teamcode.Duncan;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.vision.UGContourRingPipeline;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

/**
 * The teleop
 * @auther Raw Bacon Coders
 */


@Autonomous(name="Odometry Duncan Auto")

public class DuncanRRAuto extends DuncanBaseLinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private static final int CAMERA_WIDTH = 320; // width  of wanted camera resolution
    private static final int CAMERA_HEIGHT = 240; // height of wanted camera resolution

    private static final int HORIZON = 100; // horizon value to tune

    private static final boolean DEBUG = false; // if debug is wanted, change to true

    private static final boolean USING_WEBCAM = true; // change to true if using webcam
    private static final String WEBCAM_NAME = "Webcam 1"; // insert webcam name from configuration if using webcam

    private UGContourRingPipeline pipeline;
    private OpenCvCamera camera;

    /**
     * Initializes the teleop
     */
    @Override
    public void on_init(){

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        duncan.drivetrain.init();

        int cameraMonitorViewId = this
                .hardwareMap
                .appContext
                .getResources().getIdentifier(
                        "cameraMonitorViewId",
                        "id",
                        hardwareMap.appContext.getPackageName()
                );
        if (USING_WEBCAM) {
            camera = OpenCvCameraFactory
                    .getInstance()
                    .createWebcam(hardwareMap.get(WebcamName.class, WEBCAM_NAME), cameraMonitorViewId);
        } else {
            camera = OpenCvCameraFactory
                    .getInstance()
                    .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        }
        pipeline = new UGContourRingPipeline(telemetry, DEBUG);
        camera.setPipeline(pipeline);

        UGContourRingPipeline.Config.setCAMERA_WIDTH(CAMERA_WIDTH);

        UGContourRingPipeline.Config.setHORIZON(HORIZON);

        camera.openCameraDeviceAsync(() -> camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.SIDEWAYS_RIGHT));

        FtcDashboard.getInstance().startCameraStream(camera, 0);
        FtcDashboard.getInstance().setImageQuality(50);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData(pipeline.getHeight().toString(), " < height of rings");
            telemetry.update();
        }

      //  duncan.wobble.init();

    }

    /**
     * Activates the teleop
     */
    @Override
    public void run(){
        camera.stopStreaming();
        camera.closeCameraDevice();
        //Trajectories end when bot needs to stop
        //adding a turn to a new trajectory: Trajectory traj2 = drive.trajectoryBuilder(traj1.end().plus(new Pose2d(0, 0, Math.toRadians(90))), false)

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //Pose2d startPose = new Pose2d(-63.0, -50.0, Math.toRadians(180));
        Pose2d startPose = new Pose2d(-63.0, -50.0, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        if((pipeline.getHeight() == pipeline.getHeight().ZERO) || (pipeline.getHeight() == pipeline.getHeight().ONE)) {
                /** Temporary fix for one ring auto! It will follow the zero ring path. **/
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .forward(10.0)
                    .splineTo(new Vector2d(-24.0, -60.0), Math.toRadians(0))
                    .forward(32.0)           // builder1.forward(25.0)
                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                    .lineTo(new Vector2d(-48.0, -60.0))
                    .build();

            Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                    .lineToLinearHeading(new Pose2d(-48.0, -24.0, 90.0))
                    .build();
//turn here
            Trajectory traj4 = drive.trajectoryBuilder(traj3.end().plus(new Pose2d(0, 0, Math.toRadians(-90))))
                    .splineTo(new Vector2d(-24.0, -12.0), Math.toRadians(0.0))
                    .splineTo(new Vector2d(12.0, -60.0), Math.toRadians(-90.0))
                    .build();

            Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                    .lineTo(new Vector2d(12.0, -30.0))
                    .build();

            drive.followTrajectory(traj1);
            drive.followTrajectory(traj2);
            drive.followTrajectory(traj3);
            drive.turn(Math.toRadians(-90));
            drive.followTrajectory(traj4);
            drive.followTrajectory(traj5);
            drive.turn(Math.toRadians(-90));
        }
        else if(pipeline.getHeight() == pipeline.getHeight().FOUR){
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .forward(10.0)
                    .splineTo(new Vector2d(-24.0, -60.0), Math.toRadians(0))
                    .forward(80.0)           // builder1.forward(25.0)
                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                    .lineTo(new Vector2d(-55.0, -60.0))
                    .build();

            Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                    .lineToLinearHeading(new Pose2d(-48.0, -24.0, 90.0))
                    .build();
//turn here
            Trajectory traj4 = drive.trajectoryBuilder(traj3.end().plus(new Pose2d(0, 0, Math.toRadians(-90))))
                    .splineTo(new Vector2d(-24.0, -12.0), Math.toRadians(0.0))
                    .splineTo(new Vector2d(60.0, -60.0), Math.toRadians(-90.0))
                    .splineTo(new Vector2d(60.0, -60.0).plus(new Vector2d(-30.0, 0.0)), Math.toRadians(-90.0))
                    .build();


            Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                    .strafeRight(4)
                    .build();
            Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
                    .lineToLinearHeading(new Pose2d(12, -30.0, Math.toRadians(0.0)))
                    .build();

            drive.followTrajectory(traj1);
            drive.followTrajectory(traj2);
            drive.followTrajectory(traj3);
            drive.turn(Math.toRadians(-90));
            drive.followTrajectory(traj4);
            drive.followTrajectory(traj5);
            drive.followTrajectory(traj6);

        }
        /*else if(pipeline.getHeight() == pipeline.getHeight().ONE) {
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .forward(10.0)
                    .splineTo(new Vector2d(-24.0, -60.0), Math.toRadians(0))
                    .forward(32.0)
                    // builder1.forward(25.0)

                    .build();
            Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                    .splineTo(new Vector2d(36.0, -36.0), Math.toRadians(0))
                    .build();

            Trajectory traj2pt5 = drive.trajectoryBuilder(traj2.end())
                    .back(10)
                    .build();
            Trajectory traj3 = drive.trajectoryBuilder(traj2pt5.end())
                    .lineToConstantHeading(new Vector2d(-24.0, -60.0))
                    .build();
            Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                    .lineToSplineHeading(new Pose2d(-48.0, -44.0, Math.toRadians(90)))
                    .build();
            drive.followTrajectory(traj1);
            drive.followTrajectory(traj2);
            drive.followTrajectory(traj2pt5);
            drive.followTrajectory(traj3);
            drive.followTrajectory(traj4);
        }*/
        else{
            telemetry.addData("broke", "rip");
            telemetry.update();
            sleep(10000);
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
