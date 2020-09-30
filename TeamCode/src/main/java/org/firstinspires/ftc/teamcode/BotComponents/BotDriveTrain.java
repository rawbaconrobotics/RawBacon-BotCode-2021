package org.firstinspires.ftc.teamcode.BotComponents;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.BotComponents.BotComponentImplBase;

import static android.os.SystemClock.sleep;

/**
 * Represents the four wheel mechanum drive on the bot
 * @author Raw Bacon Coders
 */
public class BotDriveTrain extends BotComponentImplBase {

    private DcMotorEx leftDriveBack = null;
    private DcMotorEx rightDriveBack = null;
    private DcMotorEx leftDriveFront = null;
    private DcMotorEx rightDriveFront = null;

    private final static String FRONTRIGHT_WHEEL_NAME = "right_drive_front";
    private final static String FRONTLEFT_WHEEL_NAME = "left_drive_front";
    private final static String BACKRIGHT_WHEEL_NAME = "right_drive_back";
    private final static String BACKLEFT_WHEEL_NAME = "left_drive_back";

    /**
     * Constructor
     *
     * @param opMode This parameter takes in a LinearOpMode as the variable opMode.
     */
    public BotDriveTrain(LinearOpMode opMode) {
        super(opMode);
    }

    /**
     * Hardware maps and sets modes of all motors
     */
    @Override
    public void init() {

        leftDriveBack = (DcMotorEx) hardwareMap.dcMotor.get(BACKLEFT_WHEEL_NAME);
        rightDriveBack = (DcMotorEx) hardwareMap.dcMotor.get(BACKRIGHT_WHEEL_NAME);
        leftDriveFront = (DcMotorEx) hardwareMap.dcMotor.get(FRONTLEFT_WHEEL_NAME);
        rightDriveFront = (DcMotorEx) hardwareMap.dcMotor.get(FRONTRIGHT_WHEEL_NAME);


        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);

        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void initAutonomous(){

        leftDriveBack = (DcMotorEx) hardwareMap.dcMotor.get(BACKLEFT_WHEEL_NAME);
        rightDriveBack = (DcMotorEx) hardwareMap.dcMotor.get(BACKRIGHT_WHEEL_NAME);
        leftDriveFront = (DcMotorEx) hardwareMap.dcMotor.get(FRONTLEFT_WHEEL_NAME);
        rightDriveFront = (DcMotorEx) hardwareMap.dcMotor.get(FRONTRIGHT_WHEEL_NAME);


        leftDriveFront.setDirection(DcMotor.Direction.REVERSE);
        rightDriveFront.setDirection(DcMotor.Direction.FORWARD);
        leftDriveBack.setDirection(DcMotor.Direction.FORWARD);
        rightDriveBack.setDirection(DcMotor.Direction.REVERSE);

        leftDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }







}
