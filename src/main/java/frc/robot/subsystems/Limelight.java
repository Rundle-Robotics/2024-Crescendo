package frc.robot.subsystems;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Limelight extends SubsystemBase {

  private static NetworkTable table;

  private double tx;
	private double ty;
	private double ta;
	private double tshort;
	private double tlong;
	private double[] targetpose_cameraspace;
	private double apriltagid;
	NetworkTableEntry pipeln;
	public int pLine = 1; 
	public int oldPline = 1;

	private final SendableChooser<Integer> pipelineChooser = new SendableChooser<>();

  /** Creates a new Limelight. */
  public Limelight() {

    table = NetworkTableInstance.getDefault().getTable("limelight");

	pipeln = table.getEntry("getpipe");

	pipelineChooser.setDefaultOption("Red Tag 1", 0); 
    pipelineChooser.addOption("Red Tag 2", 1);
    pipelineChooser.addOption("Red Tag 3", 2);
    pipelineChooser.addOption("Red Loading Zone Tag 5", 5);

    pipelineChooser.addOption("Blue Tag 6", 4);
    pipelineChooser.addOption("Blue Tag 7", 6);
    pipelineChooser.addOption("Blue Tag 8", 7);
    pipelineChooser.addOption("Blue Loading Zone Tag 4", 5);

    pipelineChooser.addOption("any april tag", 8);


    SmartDashboard.putData("Choose Pipeline", pipelineChooser);
		
  }

  public void enableLimelight() {
		table.getEntry("ledMode").setNumber(3);
	}

	public void disableLimelight() {
		table.getEntry("ledMode").setNumber(1);
	}

  @Override
  public void periodic() {

    tx = table.getEntry("tx").getDouble(0.0);
		ty = table.getEntry("tv").getDouble(0.0);
		ta = table.getEntry("ta").getDouble(0.0);
		tshort = table.getEntry("tshort").getDouble(0.0);
		tlong = table.getEntry("tlong").getDouble(0.0);
		targetpose_cameraspace = table.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);

		apriltagid = table.getEntry("tid").getDouble(0.0);

		SmartDashboard.putNumber("x offset", tx);
		SmartDashboard.putNumber("y offset", ty);
		SmartDashboard.putNumber("area", ta);

		SmartDashboard.putNumber("TId", apriltagid);

		SmartDashboard.putNumber("tshort", tshort);
		SmartDashboard.putNumber("tlong", tlong);
		SmartDashboard.putNumber("targetpose", targetpose_cameraspace[5]);


		double tgtSeen = table.getEntry("tv").getDouble(0.0);
		if (tgtSeen == 1.0) {
		  SmartDashboard.putBoolean("Target Seen", true);
		  SmartDashboard.putNumber("target Id", table.getEntry("tid").getDouble(0.0));
	
		}
		// TODO get tgt ID  - based of pipeline
		else {
		  SmartDashboard.putBoolean("Target Seen", false);
		  SmartDashboard.putNumber("target Id", -99);
		}

		//ADD THE PIPELINE NUMBERS AND MAKE PIPELINES TAG 
		
        //likely only need two tags for this to work, maybe 3 - dont want to track them all. 

	

    pLine = pipelineChooser.getSelected().intValue();

	if (pLine != oldPline)
	{
		NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pLine);
		oldPline = pLine;
	}

    // This method will be called once per scheduler run
  }

  public void putTargetPoseDataonSmartDashboard() {
		targetpose_cameraspace = getTARGETPOSECAMERA();
		SmartDashboard.putNumberArray("TPArray", targetpose_cameraspace);
		SmartDashboard.putNumber("TP0", targetpose_cameraspace[0]);
		SmartDashboard.putNumber("TP1", targetpose_cameraspace[1]);
		SmartDashboard.putNumber("TP2", targetpose_cameraspace[2]);
		SmartDashboard.putNumber("TP3", targetpose_cameraspace[3]);
		SmartDashboard.putNumber("TP4", targetpose_cameraspace[4]);
		SmartDashboard.putNumber("TP5", targetpose_cameraspace[5]);
	}

  public double getTX() {
		return table.getEntry("tx").getDouble(0.0);
	}

	public double getTV() {
		return table.getEntry("tv").getDouble(0.0);
	}

	public double getTA() {
		return table.getEntry("ta").getDouble(0.0);
	}

	public double getTSHORT() {
		return table.getEntry("tshort").getDouble(0.0);
	}

	public double getTLONG() {
		return table.getEntry("tlong").getDouble(0.0);
	}

	public double[] getTARGETPOSECAMERA() {
		return table.getEntry("targetpose_cameraspace").getDoubleArray(new double[6]);
	}

	public void limelightCenter() {

	}

	public void setPipeline(int n) {
		table.getEntry("pipeline").setNumber(n);
	}

}