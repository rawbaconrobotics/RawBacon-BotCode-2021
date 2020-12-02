package org.firstinspires.ftc.teamcode.Duncan.Components;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Represents the tape measure
 * @author Raw Bacon Coders
 */

public class DuncanTapeMeasure extends DuncanComponentImplBase {
    private final static String TAPE_MEASURE_NAME = "bd_tape_measure";


    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private CRServo tapeMeasureServo = null;
    double measurePower = 0;
    double previousPower = 0;



    /**
     * Overrides the opMode method
     */
    public DuncanTapeMeasure(LinearOpMode opMode) {
        super(opMode);
    }

    /**
     * Initializes the tape measure
     */
    @Override
    public void init() {
        tapeMeasureServo = hardwareMap.crservo.get(TAPE_MEASURE_NAME);
    }

    /**
     * Initializes the tape measure
     */

    @Override
    public void initAutonomous() {
        tapeMeasureServo = hardwareMap.crservo.get(TAPE_MEASURE_NAME);
    }

    /**
     * extends the tape measure accoriding to the joystick input
     */
    public void tapeMeasureTeleOp() {

        measurePower = gamepad2.right_stick_y;
        if (opModeIsActive()) {
            if (measurePower != previousPower) {
                tapeMeasureServo.setPower(measurePower);
                previousPower = measurePower;
            }
        }
    }

}