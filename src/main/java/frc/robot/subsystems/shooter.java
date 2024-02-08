// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


/*
 * CREDITS:
 * Harrison and Zain Coded this
 * from Wonkanese Wanni Coding Company
 * 
 * 
 * 
 * 
 * 
 * THE JAMAL SHOOTER/LAUNCHER ROBOT
 */
public class shooter extends SubsystemBase {
  DigitalInput toplimitSwitch = new DigitalInput(0); 
  DigitalInput bottomlimitSwitch = new DigitalInput(1);
  /** Creates a new shooter. */
  Spark motor;
  public shooter() {
    motor = new Spark(3); // we dont know which channel
  }

  public void setSpeed(double speed) {
    if (speed > 0) {
      if (toplimitSwitch.get()) {
        // We are going up and top limit is tripped so stop
        motor.set(0);
      } else {
        // We are going up but top limit is not tripped so go at commanded speed
        motor.set(speed);
      }
    } else {
      if (bottomlimitSwitch.get()) {
        // We are going down and bottom limit is tripped so stop
        motor.set(0);
      } else {
        // We are going down but bottom limit is not tripped so go at commanded speed
        motor.set(speed);
      }
    }
  }
}
