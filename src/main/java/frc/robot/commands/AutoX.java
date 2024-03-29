// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.DriveTrain;

public class AutoX extends Command {
  double x;
  DriveTrain meca;

  PIDController m1pid;
  PIDController m2pid;

  double init1;
  double init2;

  double targ1;
  double targ2;

  double cur1;
  double cur2;

  double speed;
  /** Creates a new AutoX. */
  public AutoX(double x2, DriveTrain mecanumDrive) {
    // Use addRequirements() here to declare subsystem dependencies.
    x = x2;
    meca = mecanumDrive;

    addRequirements(mecanumDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

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
