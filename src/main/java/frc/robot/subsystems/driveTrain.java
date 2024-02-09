// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class driveTrain extends SubsystemBase {
  /** Creates a new driveTrain. */

  //assuming the motors are can spark max 
  private Spark frontLeft;
  private Spark frontRight;
  private Spark backLeft;
  private Spark backRight;
  public driveTrain() {
    frontLeft = new Spark(1);
    frontRight = new Spark(2);
    backLeft = new Spark(3);
    backRight = new Spark(4);

    frontLeft.addFollower(backLeft);
    frontRight.addFollower(backRight);
  }

  public void drive(double x, double y) {
    double leftY = 1;
    double rightY = 1;
    if (x < 0) {
      leftY = -1;
    } else if (x > 1) {
      rightY = -1;
    } else {
      rightY = 0;
      leftY = 0;
    }
    frontLeft.set(y * leftY);
    frontRight.set(y * rightY);
  }
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }
}
