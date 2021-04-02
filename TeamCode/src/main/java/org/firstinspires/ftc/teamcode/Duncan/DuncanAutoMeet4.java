package org.firstinspires.ftc.teamcode.Duncan;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.arcrobotics.ftclib.vision.UGContourRingPipeline;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Duncan.Components.RevHubStore;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

/**
 * The teleop
 * @auther Raw Bacon Coders
 */


@Autonomous(name="OFFICIAL Duncan Auto", preselectTeleOp="OFFICIAL Duncan TeleOp")

public class DuncanAutoMeet4 extends DuncanBaseLinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private static final int CAMERA_WIDTH = 320; // width  of wanted camera resolution
    private static final int CAMERA_HEIGHT = 240; // height of wanted camera resolution

    private static final int HORIZON = 75; // horizon value to tune

    private static final boolean DEBUG = false; // if debug is wanted, change to true

    private static final boolean USING_WEBCAM = true; // change to true if using webcam
    private static final String WEBCAM_NAME = "Webcam 1"; // insert webcam name from configuration if using webcam

    private UGContourRingPipeline pipeline;
    private OpenCvCamera camera;

    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(75, 0, 30, 12.2);


    /**
     * Initializes the teleop
     */
    @Override
    public void on_init(){

        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        duncan.drivetrain.init();
        duncan.il.initAutonomous();
        duncan.wobble.initAutonomous();

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

        camera.openCameraDeviceAsync(() -> camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT));

        FtcDashboard.getInstance().startCameraStream(camera, 0);
        FtcDashboard.getInstance().setImageQuality(50);

        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData(pipeline.getHeight().toString(), " < height of rings");
            telemetry.update();
        }


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
        Pose2d startPose = new Pose2d(-63.0, -15.0, Math.toRadians(0));

        drive.setPoseEstimate(startPose);
//get rid of .FOUR!
        if((pipeline.getHeight() == pipeline.getHeight().ZERO)) {
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-23.0, -9), Math.toRadians(-1.1))
                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                    .strafeRight(9.0)
                    .build();

            Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                    .strafeRight(7.0)
                    .build();
//turn here
            Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                    .splineTo(new Vector2d(6.0, -44.0), Math.toRadians(-90))
                    .addTemporalMarker(0.3, () -> {
                        duncan.wobble.wobbleArm.setPower(-1);

                    })
                    .addTemporalMarker(2, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })
                    .build();

            Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                    .back(3)
                    .build();
            Trajectory traj6 = drive.trajectoryBuilder(traj5.end().plus(new Pose2d(0.0, 0.0, Math.toRadians(-90))))
                    .addTemporalMarker(0.1, () -> {
                        duncan.wobble.wobbleArm.setPower(-1);

         })
                   .addTemporalMarker(1.1, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })
                    .forward(42)
                    .build();
            Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
                    .addTemporalMarker(0.1, () -> {
                        duncan.wobble.wobbleArm.setPower(1);

                    })
                    .addTemporalMarker(1.2, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })
                    //.back(42)
                    .lineToLinearHeading(new Pose2d((traj6.end().getX()+42), (traj6.end().getY()+7), Math.toRadians(-90)))
                    .build();
            Trajectory traj8 = drive.trajectoryBuilder(traj7.end())
                    .back(6)
                    .build();
            //turn on launcher
            duncan.il.launcher.setVelocity(1415);
            drive.followTrajectory(traj1);
            //raise hopper, wait one second, launch ring
            duncan.il.hopper.setPosition(0.45);
            sleep(1000);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            //strafe right again
            drive.followTrajectory(traj2);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            //strafe right again
            drive.followTrajectory(traj3);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            drive.followTrajectory(traj4);
            duncan.wobble.grabber.setPosition(0);
            sleep(1000);
            //drive.turn(Math.toRadians(-90));
            //drive.followTrajectory(traj4);
            drive.followTrajectory(traj5);
            drive.turn(Math.toRadians(-90));
            drive.followTrajectory(traj6);
            duncan.wobble.grabber.setPosition(0.75);
            sleep(500);
            drive.followTrajectory(traj7);
            duncan.wobble.grabber.setPosition(0);
            sleep(500);
            drive.followTrajectory(traj8);
        }
        else if(pipeline.getHeight() == pipeline.getHeight().ONE){
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-23.0, -9), Math.toRadians(-1.1))
                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                    .strafeRight(9.0)
                    .build();

            Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                    .strafeRight(7.0)
                    .build();

            Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                    .splineTo(new Vector2d(24.0, -38.0), Math.toRadians(0))
                    .addTemporalMarker(0.3, () -> {
                        duncan.wobble.wobbleArm.setPower(-1);

                    })
                    .addTemporalMarker(2.2, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })
                    .build();

            Trajectory traj5 = drive.trajectoryBuilder(traj4.end())
                    .lineToLinearHeading(new Pose2d((traj4.end().getX()-52), (traj4.end().getY()-3), Math.toRadians(-179)))
                    .addTemporalMarker(0.1, () -> {
                        duncan.wobble.wobbleArm.setPower(-1);

                    })
                    .addTemporalMarker(0.9, () -> {
                        duncan.wobble.wobbleArm.setPower(0);

                    })
                    .build();

            Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
                  .forward(7)
                  .build();
            Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
                    .lineToLinearHeading(new Pose2d((traj6.end().getX()+49), (traj6.end().getY()+5), Math.toRadians(0)))
                    .addTemporalMarker(0.1, () -> {
                        duncan.wobble.wobbleArm.setPower(1);

                    })
                    .addTemporalMarker(1.0, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })
                    .build();

            Trajectory traj8 = drive.trajectoryBuilder(traj7.end())
                    .back(6)
                    .build();


      //      duncan.il.launcher.setVelocity(1470);
      //      drive.followTrajectory(traj1);
            //raise hopper, wait one second, launch ring
//            duncan.il.hopper.setPosition(0.45);
//            sleep(1000);
//            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
//            sleep(500);
//            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
//            sleep(3500);
//            //strafe right again
//            drive.followTrajectory(traj2);
//            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
//            sleep(500);
//            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
//            //strafe right again
//            drive.followTrajectory(traj3);
//            sleep(1000);
//            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
//            sleep(500);
//            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            duncan.il.launcher.setVelocity(1415);
            drive.followTrajectory(traj1);
            //raise hopper, wait one second, launch ring
            duncan.il.hopper.setPosition(0.45);
            sleep(1000);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            //strafe right again
            drive.followTrajectory(traj2);
            sleep(500);

            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            //strafe right again
            drive.followTrajectory(traj3);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            drive.followTrajectory(traj4);
            duncan.wobble.grabber.setPosition(0);
            sleep(1000);
            //drive.turn(Math.toRadians(-90));
            //drive.followTrajectory(traj4);
            drive.followTrajectory(traj5);
            drive.followTrajectory(traj6);
            duncan.wobble.grabber.setPosition(0.75);
            sleep(500);
            drive.followTrajectory(traj7);
            duncan.wobble.wobbleArm.setPower(-1);
            sleep(900);
            duncan.wobble.wobbleArm.setPower(0);
            duncan.wobble.grabber.setPosition(0);
            drive.followTrajectory(traj8);
        }
        else if(pipeline.getHeight() == pipeline.getHeight().FOUR){
            Trajectory traj1 = drive.trajectoryBuilder(startPose)
                    .splineTo(new Vector2d(-23.0, -9), Math.toRadians(-1.1))
                    .build();

            Trajectory traj2 = drive.trajectoryBuilder(traj1.end())
                    .strafeRight(9.0)
                    .build();

            Trajectory traj3 = drive.trajectoryBuilder(traj2.end())
                    .strafeRight(7.0)
                    .build();
//turn here
            Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
                    .splineTo(new Vector2d(50.0, -59.0), Math.toRadians(0.0))
                    .addTemporalMarker(0.3, () -> {
                        duncan.wobble.wobbleArm.setPower(-1);

                    })
                    .addTemporalMarker(2, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })

                    .build();
            Trajectory traj4pt5 = drive.trajectoryBuilder(traj4.end())
                    .lineTo(new Vector2d((traj4.end().getX()-6), (traj4.end().getY()+6)))

                    .build();
            Trajectory traj5 = drive.trajectoryBuilder(traj4pt5.end())
                    .addTemporalMarker(0.1, () -> {
                        duncan.wobble.wobbleArm.setPower(-1);

                    })
                    .addTemporalMarker(0.9, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })
                    //.lineToLinearHeading(new Pose2d(-28.0, -43, Math.toRadians(180.0)))
                    .lineToLinearHeading(new Pose2d(-28.0, -43, Math.toRadians(180.0)))
                    .build();
            Trajectory traj6 = drive.trajectoryBuilder(traj5.end())
                    .forward(8)
                    .build();
            Trajectory traj7 = drive.trajectoryBuilder(traj6.end())
                    .lineToLinearHeading(new Pose2d(44.0, -58.0, Math.toRadians(360.0)+ 1E-6))
                    .addTemporalMarker(0.1, () -> {
                        duncan.wobble.wobbleArm.setPower(1);

                    })
                    .addTemporalMarker(1.0, () -> {
                        duncan.wobble.wobbleArm.setPower(0);
                    })
                    .build();
            Trajectory traj8 = drive.trajectoryBuilder(traj7.end())
                    .back(30)
                    .build();

//            duncan.il.launcher.setVelocity(1470);
//            drive.followTrajectory(traj1);
//            //raise hopper, wait one second, launch ring
//            duncan.il.hopper.setPosition(0.45);
//            sleep(1000);
//            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
//            sleep(500);
//            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
//            sleep(1000);
//            //strafe right again
//            drive.followTrajectory(traj2);
//            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
//            sleep(500);
//            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
//            sleep(1000);
//            drive.followTrajectory(traj3);
//            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
//            sleep(500);
//            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
//            sleep(1000);
            duncan.il.launcher.setVelocity(1415);
            drive.followTrajectory(traj1);
            //raise hopper, wait one second, launch ring
            duncan.il.hopper.setPosition(0.45);
            sleep(1000);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            //strafe right again
            drive.followTrajectory(traj2);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            //strafe right again
            drive.followTrajectory(traj3);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferInPosition);
            sleep(500);
            duncan.il.transfer.setPosition(duncan.il.transferOutPosition);
            sleep(800);
            drive.followTrajectory(traj4);
            duncan.wobble.grabber.setPosition(0);
            drive.followTrajectory(traj4pt5);
            drive.followTrajectory(traj5);
            drive.followTrajectory(traj6);
            duncan.wobble.grabber.setPosition(0.75);
            sleep(500);
            drive.followTrajectory(traj7);
            duncan.wobble.grabber.setPosition(0);
            drive.followTrajectory(traj8);

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
        duncan.il.launcher.setPower(0);
        duncan.rdrive.onStop();
    }

    public RevHubStore autonomousBulkRead(){
        RevHubStore hubValues = new RevHubStore();
        hubValues.launcherPosition = duncan.il.launcher.getCurrentPosition();
        return hubValues;
    }
}
