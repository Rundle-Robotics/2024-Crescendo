

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

  /** Creates a new Intake. */
    static Spark M1;
  public Intake() {
    M1 = new Spark(2);
  }
  public void setspeed(double speed) {
    M1.set(speed);
  }
  public static void stop() {
    M1.set(0);
    
  }

 
}
