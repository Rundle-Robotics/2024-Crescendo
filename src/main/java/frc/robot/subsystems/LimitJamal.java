// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimitJamal extends SubsystemBase {
  /** Creates a new LimitJamal. */
  public LimitJamal() {}

DigitalInput toplimitSwitch = new DigitalInput(0);
DigitalInput bottomlimitSwitch = new DigitalInput(1);
Spark armMotor = new Spark(1);


public void setMotorSpeed(double speed) {
    if (speed < 0) {
        if (toplimitSwitch.get()) {
            // We are going up and top limit is tripped so stop
            armMotor.set(0);
        } else {
            // We are going up but top limit is not tripped so go at commanded speed
            armMotor.set(speed);
        }
    } else {
        if (bottomlimitSwitch.get()) {
            // We are going down and bottom limit is tripped so stop
            armMotor.set(0);
            // We are going down but bottom limit is not tripped so go at commanded speed
            armMotor.set(speed);
        }
    }
}

    public boolean getTopLimitSwitch() {
        return toplimitSwitch.get();
    }

    public boolean getBottomLimitSwitch() {
        return bottomlimitSwitch.get();
    }
    public void stop() {
        armMotor.set(0);
    }
}
