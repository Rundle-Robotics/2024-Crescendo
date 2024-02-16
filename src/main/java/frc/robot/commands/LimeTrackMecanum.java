package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
public class LimeTrackMecanum extends Command {

  private Limelight limelight;
  private DriveTrain drivetrain;

  private final double CENTER_DISTANCE = 0;
	private final double TARGET_AREA_CUTOFF = 7;
	private final double CENTER_DEADBAND = 0.05;
	private final double SPEED = 0.15;
  
  

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
    

    limelight.putTargetPoseDataonSmartDashboard();

    if (limelight.getTV() == 0) {
      drivetrain.mecanumDrive(0, 0.15, 0);

      


    } else{

      boolean right = limelight.getTX() > (CENTER_DEADBAND + CENTER_DISTANCE);
      boolean left = limelight.getTX() < (CENTER_DISTANCE - CENTER_DEADBAND);
      boolean tooFar = limelight.getTA() < (TARGET_AREA_CUTOFF);
      boolean notAlignedRight= limelight.getTARGETPOSECAMERA()[5] > (CENTER_DEADBAND + CENTER_DISTANCE);
      boolean notAlignedLeft= limelight.getTARGETPOSECAMERA()[5] < (CENTER_DISTANCE - CENTER_DEADBAND);
       

      double speed = tooFar ? SPEED : 0;
      //double rotation = right ? -SPEED : left ? SPEED : 0;
      double strafe = right ? limelight.getTX() * 0.008: left ? limelight.getTX() * 0.008 : 0; 

      double rotation = notAlignedRight ? limelight.getTARGETPOSECAMERA()[5] * 0.05 : notAlignedLeft ? limelight.getTARGETPOSECAMERA()[5] * 0.05 : 0;

      finite = !right && !left && !tooFar && !notAlignedRight && !notAlignedLeft;

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
