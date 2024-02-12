package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
public class LimeTrackMecanum extends Command {

  private Limelight limelight;
  private DriveTrain drivetrain;

  private final double CENTER_DISTANCE = 0;
	private final double TARGET_AREA_CUTOFF = 7;
	private final double CENTER_DEADBAND = 1.0;
	private final double YAW_DEADBAND = 10;
	private final double TARGET_YAW = 0;
	private final double SPEED = 0.15;
  private final double strafe = 0;
  

  boolean finite;


  public LimeTrackMecanum(DriveTrain drivetrain, Limelight limelight) {
		this.drivetrain = drivetrain;
		this.limelight = limelight;

		addRequirements(drivetrain);
		addRequirements(limelight);
	}

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    
		finite = false;

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double yaw = limelight.getTARGETPOSECAMERA()[5];

    limelight.putTargetPoseDataonSmartDashboard();

    if (limelight.getTV() == 0) {
      drivetrain.mecanumDrive(0, 0.15, 0);

      // strafe is x forward speed is y and rotation is turn inside


    } else{

      boolean right = limelight.getTX() > (CENTER_DEADBAND + CENTER_DISTANCE);
      boolean left = limelight.getTX() < (CENTER_DISTANCE - CENTER_DEADBAND);
      boolean tooFar = limelight.getTA() < (TARGET_AREA_CUTOFF);
      boolean notAligned = true; //placeholder

      double speed = tooFar ? SPEED : 0;
      //double rotation = right ? -SPEED : left ? SPEED : 0;
      double rotation = right ? (limelight.getTX() * 0.008): (left ? (limelight.getTX() * 0.008) : 0); 
      
      // henry code - rotation code above wouldn't work, would rotate opposite way
      // could make it multiplied by -0.008 but even then the speed would be too 
      // high and over correct multiple times
      // also TX is + or - so you dont need ? and : for each case
      // double rotation = limelight.getTX() / -60 + .1;

      
      double strafe = 0; //placeholder 

      finite = !right && !left && !tooFar;

      drivetrain.mecanumDrive(strafe, rotation, -speed);


    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

   System.out.println("command done");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finite;
  }
}
