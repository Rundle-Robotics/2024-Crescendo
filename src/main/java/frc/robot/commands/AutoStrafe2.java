// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

public class AutoStrafe2 extends Command {

  static double x;
  double y;
  double turn;
  static DriveTrain meca;

  double initialPosX;
  double targetPosX;
  double currentX;

  double initialPosY;
  double targetPosY;
  double currentY;

  PIDController pidx;
  PIDController pidy;

  double yspeed;

  /** Creates a new AutoStrafe2. */
  public AutoStrafe2(double x2, double y2, double turn2, DriveTrain mecanumDrive) {
    // Use addRequirements() here to declare subsystem dependencies.
    x = x2;
    y = y2;
    turn = turn2;
    meca = mecanumDrive;

    addRequirements(mecanumDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initialPosY = meca.getMotorPosition(1);
    targetPosY = initialPosY+y;

    pidx = new PIDController(0.1, 0.5, 0.1);
    pidy = new PIDController(0.01, 0.01, 0.01);
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentY = meca.getMotorPosition(1);
    
    yspeed = pidy.calculate(currentY, targetPosY);
    if (Math.abs(yspeed) > 0.5) {
      yspeed = 0.5 * Math.signum(yspeed);
    }
    SmartDashboard.putNumber("y speed", yspeed);
    SmartDashboard.putBoolean("at setpoint", pidy.atSetpoint());

    SmartDashboard.putNumber("current pos", currentY);
    SmartDashboard.putNumber("target pos", targetPosY);

    meca.mecanumDrive(0, 0, -yspeed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    meca.mecanumDrive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return pidy.atSetpoint();
  }

  public static void stop() {
    meca.mecanumDrive(0, 0, 0);
  }
}
