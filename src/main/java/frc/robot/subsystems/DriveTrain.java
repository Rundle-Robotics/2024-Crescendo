package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.OperatorConstants;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.RobotContainer;
import frc.robot.Constants;
import com.revrobotics.CANSparkLowLevel;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
public class DriveTrain extends SubsystemBase {
    private double sin;
    private double cos;
    private double max;
    private double theta;
    private double turn;
    private double power;
    private CANSparkMax FLmotor;
    private CANSparkMax FRmotor;
    private CANSparkMax BLmotor;
    private CANSparkMax BRmotor;
    private double frontLeft;
    private double frontRight;
    private double backLeft;
    private double backRight;
    private RelativeEncoder frontLefte;
    private RelativeEncoder frontRighte;
    private RelativeEncoder backLefe;
    private RelativeEncoder backRighte;
    private double x;
    private double y;
    public DriveTrain() {
        FLmotor = new CANSparkMax(1, MotorType.kBrushless);
        FRmotor = new CANSparkMax(2, MotorType.kBrushless);
        BLmotor = new CANSparkMax(3, MotorType.kBrushless);
        BRmotor = new CANSparkMax(4, MotorType.kBrushless);

    }
    @Override
    public void periodic() {
        x = RobotContainer.m_driverController.getRawAxis(OperatorConstants.XBOX_RIGHT_X_AXIS);
        y = RobotContainer.m_driverController.getRawAxis(OperatorConstants.XBOX_RIGHT_Y_AXIS);
        turn = RobotContainer.m_driverController.getRawAxis(OperatorConstants.XBOX_LEFT_X_AXIS);
        theta = Math.atan2(y, x);
        power = Math.hypot(x, y);
        mecanumDrive(power, turn);
    }
    
        
    public void mecanumDrive(double powerInside, double turnInside) {
        sin = Math.sin(theta - Math.PI/4);
        cos = Math.cos(theta - Math.PI/4);
        max = Math.max(Math.abs(sin), Math.abs(cos));
    

        frontLeft = power * cos/max + turnInside;
        backLeft = power * sin/max + turnInside;
        frontRight = power * sin/max - turnInside;
        backRight = power * cos/max - turnInside;
        if ((power + Math.abs(turn) > 1)) {
            frontLeft /= powerInside + turnInside;
            backLeft /= powerInside + turnInside;
            frontRight /= powerInside + turnInside;
            backRight /= powerInside + turnInside;
        }
        // set speed to motors
        FLmotor.set(frontLeft);
        BLmotor.set(backLeft);
        FRmotor.set(frontRight);
        BRmotor.set(backRight);

    }
    
}