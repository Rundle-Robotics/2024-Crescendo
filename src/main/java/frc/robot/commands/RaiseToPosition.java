// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMotor;

public class RaiseToPosition extends Command {

  private ArmMotor arm;
  /** Creates a new RaiseToPosition. */
  public RaiseToPosition(ArmMotor arm) {

    this.arm = arm;

    addRequirements(arm);

  
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    arm.SetArmSpeed(-0.1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    arm.SetArmSpeed(0);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return arm.getTopLimitSwitch()==false;
  }
}
