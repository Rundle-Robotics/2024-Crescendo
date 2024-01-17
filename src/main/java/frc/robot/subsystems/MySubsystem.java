package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command; //imports the Command class from the command package
import edu.wpi.first.wpilibj2.command.SubsystemBase; //imports the SubsystemBase class

//Subsystem is a collection of commands
public class MySubsystem extends SubsystemBase {
    //Creates a new MySubsystem
    public MySubsystem() {} //Constructor method called when this object is created

    //Example of a command factory method
    public Command exampleMethodCommand() {
        return runOnce(
            () -> {
                //one time action goes here
        });
    }

    //get whether a condition is true
    public boolean exampleCondition() {
        //check a boolean state, such as a digital sensor
        return false;
    }

    @Override
    public void periodic() {
        //Called every 20ms (Default)
    }

    @Override
    public void simulationPeriodic() {
        //this method is like periodic except only runs when running robot not simulation
    }
}
