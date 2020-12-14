package org.firstinspires.ftc.teamcode.Duncan.Components;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RevHubStore;

/**
 * Represents the four wheel mechanum drive on the bot
 * @author Raw Bacon Coders
 */
public class DuncanDriveTrain extends DuncanComponentImplBase {

    public DcMotor leftDriveBack = null;
    public DcMotor rightDriveBack = null;
    public DcMotor leftDriveFront = null;
    public DcMotor rightDriveFront = null;

    private final static String FRONTRIGHT_WHEEL_NAME = "right_drive_front";
    private final static String FRONTLEFT_WHEEL_NAME = "left_drive_front";
    private final static String BACKRIGHT_WHEEL_NAME = "right_drive_back";
    private final static String BACKLEFT_WHEEL_NAME = "left_drive_back";

    private static final double COUNTS_PER_MOTOR_REV = 1120; //1440
    private static final double DRIVE_GEAR_REDUCTION = 1.0;
    private static final double WHEEL_DIAMETER_INCHES = 4.0;
    private static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    private ElapsedTime runtime = new ElapsedTime();

    boolean speedModeOn = false;

    /**
     * Constructor
     *
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */
    public DuncanDriveTrain(LinearOpMode opMode) {
        super(opMode);
    }

    /**
     * Hardware maps and sets modes of all motors
     */
    @Override
    public void init() {



        leftDriveFront  = hardwareMap.get(DcMotor.class, FRONTLEFT_WHEEL_NAME);
        leftDriveBack  = hardwareMap.get(DcMotor.class, BACKLEFT_WHEEL_NAME);
        rightDriveFront  = hardwareMap.get(DcMotor.class, FRONTRIGHT_WHEEL_NAME);
        rightDriveBack = hardwareMap.get(DcMotor.class, BACKRIGHT_WHEEL_NAME);
        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);
        leftDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);


        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void initAutonomous(){

        leftDriveBack = (DcMotorEx) hardwareMap.dcMotor.get(BACKLEFT_WHEEL_NAME);
        leftDriveFront = (DcMotorEx) hardwareMap.dcMotor.get(FRONTLEFT_WHEEL_NAME);
        rightDriveBack = (DcMotorEx) hardwareMap.dcMotor.get(BACKRIGHT_WHEEL_NAME);
        rightDriveFront = (DcMotorEx) hardwareMap.dcMotor.get(FRONTRIGHT_WHEEL_NAME);

        leftDriveBack.setDirection(DcMotor.Direction.FORWARD);
        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);

        leftDriveBack.setPower(0);
        leftDriveFront.setPower(0);
        rightDriveBack.setPower(0);
        rightDriveFront.setPower(0);

        leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        runUsingEncoders();

    }

    public void stopWheels(){
        leftDriveBack.setPower(0);
        leftDriveFront.setPower(0);
        rightDriveBack.setPower(0);
        rightDriveFront.setPower(0);
    }

    /**
     * Reformats input and runs the {@link #mechanumTeleOp} method
     */
    public double drive = -1;
    public double turn = -1;

    public void wheelsTeleOp(RevHubStore motors) {

        drive = -gamepad1.left_stick_y;
        turn  =  gamepad1.right_stick_x;
        double leftPower = Range.clip(drive + turn, -1.0, 1.0) ;
        double rightPower = Range.clip(drive - turn, -1.0, 1.0) ;

       // mechanumTeleOp(gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x);
        leftDriveFront.setPower(leftPower);
        leftDriveBack.setPower(leftPower);
        rightDriveFront.setPower(rightPower);
        rightDriveBack.setPower(rightPower);


    }

    /**
     * Adds the forward movement, strafing, and rotation together, normalizes them, and sets the motor powers appropriatly
     *
     * @param x        the strafing speed, with -1 being full speed left, and 1 being full speed right
     * @param y        the forward speed, from -1 to 1
     * @param rotation the rotation speed, with -1 being full speed left, and 1 being full speed right
     */
    public void mechanumTeleOp(double x, double y, double rotation) {
        double wheelSpeeds[] = new double[4];

        wheelSpeeds[0] = x + y + rotation;
        wheelSpeeds[1] = -x + y - rotation;
        wheelSpeeds[2] = -x + y + rotation;
        wheelSpeeds[3] = x + y - rotation;

        //normalize(wheelSpeeds);
    }

    public void driveFor(double distance, double speed, double timeoutS){
        if (opModeIsActive()){
            int targetDistLeft;
            int targetDistRight;
            targetDistLeft = leftDriveFront.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);
            targetDistRight = rightDriveFront.getCurrentPosition() + (int) (distance * COUNTS_PER_INCH);

            leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            runUsingEncoders();

            runtime.reset();

            leftDriveBack.setPower(speed);
            leftDriveFront.setPower(speed);
            rightDriveBack.setPower(speed);
            rightDriveFront.setPower(speed);

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    ((Math.abs((leftDriveBack.getCurrentPosition() + leftDriveFront.getCurrentPosition() + rightDriveBack.getCurrentPosition() + rightDriveFront.getCurrentPosition()) / 4)) < (Math.abs((distance * COUNTS_PER_INCH))))) {
                telemetry.addData("Left Front",  " Position: %7d", leftDriveFront.getCurrentPosition());
                telemetry.addData("Right Front",  " Position: %7d", rightDriveFront.getCurrentPosition());
                telemetry.addData("Left Back",  " Position: %7d", leftDriveBack.getCurrentPosition());
                telemetry.addData("Right Back",  " Position: %7d", rightDriveBack.getCurrentPosition());
                telemetry.update();
            }

            leftDriveBack.setPower(0);
            leftDriveFront.setPower(0);
            rightDriveBack.setPower(0);
            rightDriveFront.setPower(0);

            System.out.println("ROBOT STOPPED");
            System.out.println("RUN USING ENCODERS METHOD RAN");
        }
    }

    public void strafeFor(double distance, double speed, double timeoutS) {
        if (opModeIsActive()) {


            leftDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDriveBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDriveFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            runUsingEncoders();
            runtime.reset();

            if (distance < 0) {
                leftDriveBack.setPower(speed);
                leftDriveFront.setPower(-speed);
                rightDriveBack.setPower(-speed);
                rightDriveFront.setPower(speed);
            }
            else {
                leftDriveBack.setPower(-speed);
                leftDriveFront.setPower(speed);
                rightDriveBack.setPower(speed);
                rightDriveFront.setPower(-speed);
            }

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (((Math.abs(leftDriveBack.getCurrentPosition()) + Math.abs(leftDriveFront.getCurrentPosition()) + Math.abs(rightDriveBack.getCurrentPosition()) + Math.abs(rightDriveFront.getCurrentPosition())) / 4)) < (Math.abs((distance * COUNTS_PER_INCH)))) {

                telemetry.addData("Left Front",  " Position: %7d", leftDriveFront.getCurrentPosition());
                telemetry.addData("Right Front",  " Position: %7d", rightDriveFront.getCurrentPosition());
                telemetry.addData("Left Back",  " Position: %7d", leftDriveBack.getCurrentPosition());
                telemetry.addData("Right Back",  " Position: %7d", rightDriveBack.getCurrentPosition());
                telemetry.update();

                leftDriveBack.setPower(0);
                leftDriveFront.setPower(0);
                rightDriveBack.setPower(0);
                rightDriveFront.setPower(0);

                System.out.println("ROBOT STOPPED");
                System.out.println("RUN USING ENCODERS METHOD RAN");
            }
        }
    }


    public void turnFor(double degrees, double power, double timeoutS){

        double distance = 1*degrees;

        // rotate until turn is completed.
        if (degrees < 0) {
            //right turn
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (((Math.abs(leftDriveBack.getCurrentPosition()) + Math.abs(leftDriveFront.getCurrentPosition()) + Math.abs(rightDriveBack.getCurrentPosition()) + Math.abs(rightDriveFront.getCurrentPosition())) / 4)) < (Math.abs((distance * COUNTS_PER_INCH)))) {
                leftDriveBack.setPower(1);
                leftDriveFront.setPower(1);
                rightDriveBack.setPower(-1);
                rightDriveFront.setPower(-1);

                telemetry.addData("Angle wanted", degrees);
                telemetry.update();

            }

        } else if (degrees > 0) {   // left turn.
            while (opModeIsActive() && (runtime.seconds() < timeoutS) &&
                    (((Math.abs(leftDriveBack.getCurrentPosition()) + Math.abs(leftDriveFront.getCurrentPosition()) + Math.abs(rightDriveBack.getCurrentPosition()) + Math.abs(rightDriveFront.getCurrentPosition())) / 4)) < (Math.abs((distance * COUNTS_PER_INCH)))) {
                leftDriveBack.setPower(1);
                leftDriveFront.setPower(1);
                rightDriveBack.setPower(-1);
                rightDriveFront.setPower(-1);

                telemetry.addData("Angle wanted", degrees);
                telemetry.update();
            }
        }




    }

    public void runUsingEncoders() {
        System.out.println("ABOUT TO SET RUNUSINGENCODERS DIRECTLY...");

        leftDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        System.out.println("SET IT DIRECTLY!!!!!!!!");
    }

// "IMU"


    }
