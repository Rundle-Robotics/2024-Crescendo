// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmMotor extends SubsystemBase {

  DigitalInput toplimitSwitch = new DigitalInput(0);
  DigitalInput bottomlimitSwitch = new DigitalInput(1);

 
  /** Creates a new ArmMotor. */
  CANSparkMax armMotorOne;  
  CANSparkMax armMotorTwo;
  public ArmMotor() {
    armMotorOne = new CANSparkMax(5, MotorType.kBrushless);
    armMotorTwo = new CANSparkMax(6, MotorType.kBrushless);
    
  }

  public void SetArmSpeed(double speed) {

   boolean top = getTopLimitSwitch();
   boolean bottom = getBottomLimitSwitch();

   if (top == false && speed < 0){
    speed = 0;
   }
   if (bottom == false&& speed > 0){ 
    speed = 0; 
   }

   armMotorOne.set(speed);
   armMotorTwo.set(-speed);
  }

    public boolean getTopLimitSwitch() {
        return toplimitSwitch.get();
    }

    public boolean getBottomLimitSwitch() {
        return bottomlimitSwitch.get();
    }
    public void stop() {
        armMotorOne.set(0);
        armMotorTwo.set(0);
    }
}
