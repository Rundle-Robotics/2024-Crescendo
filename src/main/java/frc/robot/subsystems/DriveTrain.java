package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.OperatorConstants;
import com.revrobotics.RelativeEncoder;
public class DriveTrain {
    private double sin;
    private double cos;
    private double max;
    private double theta;
    private double frontLeft;
    private double frontRight;
    private double backLeft;
    private double backRight;
    public DriveTrain() {
         
    }
    
}