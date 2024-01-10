// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LoadMotor extends SubsystemBase {

  private CANSparkMax loadMotor;


  double loadMotorPower;

  private RelativeEncoder loadMotorEncoder;
  /** Creates a new LoadMotor. */
  public LoadMotor() {

    loadMotor = new CANSparkMax(5, MotorType.kBrushless);
    loadMotorEncoder = loadMotor.getEncoder();
    loadMotorPower = RobotContainer.driverController.getRightTriggerAxis()/4;
  }

  @Override
  public void periodic() {

    loadMotor.set(loadMotorPower);
    // This method will be called once per scheduler run
  }

  public double getBackLeftPosition() {
		return loadMotorEncoder.getPosition();
	}

  public void stop() {
		loadMotor.set(0);
		
	}
}
