package org.firstinspires.ftc.teamcode;

public class RevHubStore {
    public double[] motorSpeeds; //Stores motorspeeds (was originally anArray)
    double[] anArray;
    public double[] motorEncoderPositions;
    public double[] motorVelocities;
    public double wobbleServoPosition = 0;
    public RevHubStore(){
        motorSpeeds = new double[4]; //Stores 4 current motor speeds
        motorEncoderPositions = new double[4];
        motorVelocities = new double[4];
        //Proppper coding practices
        
    }  

}
