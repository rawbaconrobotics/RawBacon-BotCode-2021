package org.firstinspires.ftc.teamcode.Duncan;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;

/**
 * The teleop
 * @auther Raw Bacon Coders
 */


@Autonomous(name="Odometry Duncan Auto")

public class DuncanRRAuto extends DuncanBaseLinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    /**
     * Initializes the teleop
     */
    @Override
    public void on_init(){
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        duncan.drivetrain.init();
      //  duncan.wobble.init();

    }

    /**
     * Activates the teleop
     */
    @Override
    public void run(){
        //Trajectories end when bot needs to stop
        //adding a turn to a new trajectory: Trajectory traj2 = drive.trajectoryBuilder(traj1.end().plus(new Pose2d(0, 0, Math.toRadians(90))), false)

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //Pose2d startPose = new Pose2d(-63.0, -50.0, Math.toRadians(180));
        Pose2d startPose = new Pose2d(-63.0, -50.0, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

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
        Trajectory traj4 = drive.trajectoryBuilder(traj3.end())
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
        drive.turn(Math.toRadians(90));

    }
    /**
     * Stops the teleop
     */
    @Override
    public void on_stop() {
        duncan.rdrive.onStop();
    }
}
