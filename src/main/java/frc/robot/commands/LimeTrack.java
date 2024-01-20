package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
public class LimeTrack extends Command {

  private Limelight limelight;
  private Drivetrain drivetrain;

  private final double CENTER_DISTANCE = -10.2;
	private final double TARGET_AREA_CUTOFF = 1.2;
	private final double CENTER_DEADBAND = 5;
	private final double YAW_DEADBAND = 10;
	private final double TARGET_YAW = 0;
	private final double SPEED = 0.45;
  

  boolean finite;


  /** Creates a new LimeTrack. */
  public LimeTrack() {

    

    // Use addRequirements() here to declare subsystem dependencies.
  }

  public LimeTrack(Drivetrain drivetrain, Limelight limelight) {
		this.drivetrain = drivetrain;
		this.limelight = limelight;

		addRequirements(drivetrain);
		addRequirements(limelight);
	}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    limelight.enableLimelight();

		limelight.setPipeline(0); // Pipeline 0 is for AprilTag detection

		finite = false;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double yaw = limelight.getTARGETPOSECAMERA()[5];

    limelight.putTargetPoseDataonSmartDashboard();

    if (limelight.getTV() == 0) {
      drivetrain.arcadeDrive(0,0.45);


    } else{

      boolean right = limelight.getTX() > (CENTER_DEADBAND + CENTER_DISTANCE);
      boolean left = limelight.getTX() < (CENTER_DISTANCE - CENTER_DEADBAND);
      boolean tooFar = limelight.getTA() < (TARGET_AREA_CUTOFF);

      double speed = tooFar ? SPEED : 0;
      double rotation = right ? SPEED : left ? -SPEED : 0;

      finite = !right && !left && !tooFar;

      drivetrain.arcadeDrive(speed, rotation);


    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

    limelight.disableLimelight();

		limelight.setPipeline(2); 
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finite;
  }
}