// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmMotor extends SubsystemBase {
  /** Creates a new ArmMotor. */
  Spark ArmMotor;  
  public ArmMotor() {
    ArmMotor = new Spark(3);
  }

  public void SetArmSpeed(double speed) {
    ArmMotor.set(speed);
  }
  public void stop() {
    ArmMotor.set(0);
  }

}
