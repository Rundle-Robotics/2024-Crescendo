// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class JamalShooter extends SubsystemBase {
  /** Creates a new Shooter. */
  static CANSparkMax shooterJamalMotor;
  public JamalShooter() {
    shooterJamalMotor = new CANSparkMax(7, MotorType.kBrushless);
  }

  public void shooterSpeed(double speed) {
    shooterJamalMotor.set(speed);
  }
  public static void stop() { shooterJamalMotor.set(0); }
}
