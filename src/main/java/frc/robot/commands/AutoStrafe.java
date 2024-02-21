// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.DriveTrain;

public class AutoStrafe extends Command {
  private DriveTrain m_DriveTrain;
  private double X;
  private double Y;
  private double Turn;
  /** Creates a new AutoStrafe. */
  public AutoStrafe(double x, double y, double turn, DriveTrain drivetrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_DriveTrain = drivetrain;

    addRequirements(drivetrain);
    X = x;
    Y = y;
    Turn = turn;
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_DriveTrain.mecanumDrive(X,Turn,Y);

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
