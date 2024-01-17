package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ZainsCopy extends SubsystemBase {

    public ZainsCopy() {}


 public Command exampleMethodCommand() {
    return runOnce(
        () -> {
        
        });
}
    
public boolean exampleCondition() {
    return false;
    // This is a true and false (boolean) and is used for things like digital sensors
}

public void periodic() {
//This runs once every couple of milli seconds.
}

public void simulationPeriodic() {
//This is like periodic except it only runs when connected to robot (I think)
}
}



