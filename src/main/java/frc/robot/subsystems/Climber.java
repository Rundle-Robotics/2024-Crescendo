package frc.robot.subsystems;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Climber {
    private CANSparkMax Motor1;
    private CANSparkMax Motor2;
    private RelativeEncoder m1;
    private RelativeEncoder m2;
    private boolean Up;
    public Climber() {
        if (Up == true) {
            Motor1.set(2);
            Motor2.set(2);
            
        }
    }
}

