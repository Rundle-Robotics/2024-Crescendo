// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AutoStrafe;
import frc.robot.commands.AutoY;
import frc.robot.commands.IntakeReverseCommand;

import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.FineTune;
import frc.robot.commands.LimeTrackMecanum;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.StopShooter;
import frc.robot.commands.IntakeShooterCommand;
import frc.robot.commands.LowerToPosition;
import frc.robot.commands.RaiseToPosition;

import frc.robot.subsystems.ArmMotor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.JamalShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.hal.FRCNetComm.tInstances;
import edu.wpi.first.math.kinematics.SwerveDriveWheelPositions;
import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_DriveTrain = new DriveTrain();
  private final Limelight m_limelight = new Limelight();
  public final Intake m_intake = new Intake();
  public final ArmMotor m_armmotor = new ArmMotor();
  public final JamalShooter m_shootermotor = new JamalShooter();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  

  private static final String doNothing = "Do Nothing";
  private static final String shoot = "Shoot";
  private static final String driveBackwardsAndShoot = "Drive Backwards And Shoot";

  private static final String moveBack = "Move Back";

  private static final String timedTurn = "Timed Turn";
  private static final String backThenForward = "Back Then Forward";

  private String autoSelected;
  private static UsbCamera cam;

  SendableChooser<String> m_autoChooser = new SendableChooser<>();

 


  // Replace with CommandPS4Controller or CommandJoystick if needed
  public static final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public static final CommandXboxController m_operatorController = 
      new CommandXboxController(OperatorConstants.SECONDARY_CONTROLLER_PORT);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
  
    m_autoChooser.setDefaultOption("Do Nothing", doNothing);
    m_autoChooser.addOption("Shoot", shoot);
    m_autoChooser.addOption("Drive Backwards And Shoot", driveBackwardsAndShoot);
    m_autoChooser.addOption("Move Back", moveBack);
    m_autoChooser.addOption("Timed Turn", timedTurn);
    m_autoChooser.addOption("Back Then Forward", backThenForward);
    m_autoChooser.addOption("Right", "SMBR");
    m_autoChooser.addOption("Left", "SMBL");

    SmartDashboard.putData(m_autoChooser);
    configureBindings();

    cam = CameraServer.startAutomaticCapture(0);
    
    

 
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    m_DriveTrain.setDefaultCommand(
      new RunCommand(
        ()->
        m_DriveTrain.mecanumDrive(-m_driverController.getLeftX(), m_driverController.getRightX()/1.3,
         -m_driverController.getLeftY()), m_DriveTrain));

    m_driverController
        .b()
        .whileTrue(
            new LimeTrackMecanum(m_DriveTrain, m_limelight));

    m_driverController
        .rightTrigger()
        .whileTrue(new FineTune(m_DriveTrain));
    m_operatorController
        .rightBumper()
        .whileTrue(
            new RaiseToPosition(m_armmotor));
     m_operatorController
        .leftBumper()
        .whileTrue(
            new LowerToPosition(m_armmotor));
        // x is intake
    m_operatorController
            .rightTrigger()
            .whileTrue(
              new RunCommand(
              () ->
              m_intake.setspeed(1),
              m_intake
              )
              .handleInterrupt(()-> m_intake.stop()));
        // y is eject
      m_operatorController
            .leftTrigger()
            .whileTrue(
              new RunCommand(
              () ->
              m_intake.setspeed(-1),
              m_intake
              )
              .handleInterrupt(()-> m_intake.stop()));

          // left bumper and right bumper to move arm, will reset after not held
          // left 
          // right
            // will use right and left triggers for shooter
            // right
        m_operatorController
              .y()
              .whileTrue(
                new RunCommand(
                  () ->
                  // placeholder for shooter speed, test then change
                  m_shootermotor.shooterSpeed(0.79),
                  m_shootermotor
                  )
                  .handleInterrupt(()-> m_shootermotor.stop()));
            //left
        m_operatorController
            .x()
              .whileTrue(
                new RunCommand(
                  ()->
                  m_shootermotor.shooterSpeed(-0.4),
                  m_shootermotor
                  )
                  .handleInterrupt(()-> m_shootermotor.stop()));


        m_operatorController
        .a()
        .whileTrue(
          

          new ShooterCommand(m_shootermotor)
          .withTimeout(0.5)
          .andThen(new IntakeShooterCommand(m_intake))
          
          .handleInterrupt(() -> m_shootermotor.stop())
         
         
        );

        // m_operatorController.povUp().whileTrue(
        //   new RaiseToPosition(m_armmotor)
        //   .andThen(() ->m_intake.setspeed(0.1))
        //   .withTimeout(1)
        //   .andThen (() -> m_intake.stop())
        //   .withTimeout(3)
        //   .andThen(() -> m_intake.setspeed(-0.5))
        //   .withTimeout(3)
        //   .andThen(() -> m_intake.stop())
        //   .handleInterrupt(()-> m_armmotor.stop())
        //   .handleInterrupt(()-> m_intake.stop()2
        // );

        m_operatorController.povUp().onTrue(
        //   new RaiseToPosition(m_armmotor)
        //   .andThen(() -> m_intake.setspeed(1))
        //   .withTimeout(2)
        //   .andThen(() -> m_intake.stop())
        //   .handleInterrupt(() -> m_intake.stop())
        //   .handleInterrupt(() -> m_intake.stop())
        // );
        
 
          new SequentialCommandGroup(

            new ParallelCommandGroup(
               new RaiseToPosition(m_armmotor),
               
               new SequentialCommandGroup(
                  new WaitCommand(2),
                  new StartEndCommand(() -> m_intake.setspeed(1), () -> m_intake.stop(), m_intake)
                  .withTimeout(0.15)     //time to intake. Will stop intaking after the time specified
               )
            ),

            // will not reach this until both the arm has raised and the intake has done its pause and spin
            new WaitCommand(0.2),
      
            new StartEndCommand(() -> m_intake.setspeed(-1), () -> m_intake.stop(), m_intake)
            .withTimeout(1)
            
          ).handleInterrupt(
            () -> {m_intake.stop(); m_armmotor.stop();}
          )

        );
              
        

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    Command com = null;
    autoSelected = m_autoChooser.getSelected();

    switch(autoSelected) 
    {
      case doNothing:
        return (new LowerToPosition(m_armmotor)
        .withTimeout(4)
        .handleInterrupt(()->m_armmotor.stop())

        );

      case "Shoot":
        return new SequentialCommandGroup(
          new ShooterCommand(m_shootermotor),
          new WaitCommand(1),
          new IntakeShooterCommand(m_intake),
          new WaitCommand(1),
          new StopShooter(m_shootermotor, m_intake)
        );
        

      case driveBackwardsAndShoot:
       return ((new ShooterCommand(m_shootermotor))
        .withTimeout(1)
        .andThen(new IntakeShooterCommand(m_intake))
        .withTimeout(3)
        .andThen(() -> m_shootermotor.stop())
        .andThen(new AutoY(0, -60, 0, m_DriveTrain))
        
       
        
        .handleInterrupt(() -> m_shootermotor.stop())
        
        

        );
      case moveBack:
      return ((new ShooterCommand(m_shootermotor))
        .withTimeout(1)
        .andThen(new IntakeShooterCommand(m_intake))
        .withTimeout(3)
        .andThen(() -> m_shootermotor.stop())
        .andThen(new AutoY(0, -60, 0, m_DriveTrain))
        .withTimeout(4)
        .andThen(new LowerToPosition(m_armmotor)
        .withTimeout(5)

        .andThen((new IntakeShooterCommand(m_intake))
        .withTimeout(2)
        .handleInterrupt(()->m_intake.stop()))

        .andThen(new RaiseToPosition(m_armmotor))
        .andThen(new AutoY(0, 60, 0, m_DriveTrain))
        .handleInterrupt(()->m_armmotor.stop())
        )
        // .withTimeout(2)
        // .andThen(((new IntakeShooterCommand(m_intake)))
        // .withTimeout(2)
        // .andThen(()->m_intake.stop()))
        .handleInterrupt(()-> m_intake.stop())
        .handleInterrupt(() -> AutoY.stop())
        .handleInterrupt(() -> m_shootermotor.stop())
        );

        

      case timedTurn:
        return ((new AutoStrafe(0, 0, 1, m_DriveTrain))
        .andThen(new WaitCommand(1))
        .handleInterrupt(() -> m_DriveTrain.stop()));
      
      case backThenForward:
        return ((new AutoY(0, -20, 0, m_DriveTrain))
        .andThen(new WaitCommand(1))
        .andThen(new AutoY(0, 20, 0, m_DriveTrain))
        .andThen(new WaitCommand(1))
        );

      case "SMBR":
      // for right side
      return (new ShooterCommand(m_shootermotor))
        .withTimeout(1)
        .andThen(new IntakeShooterCommand(m_intake))
        .withTimeout(3)
        .andThen(() -> m_shootermotor.stop())
        .andThen(new AutoY(0, -20, 0, m_DriveTrain))
        .andThen(new AutoStrafe(0, 0, 1, m_DriveTrain))
        .andThen(new WaitCommand(.2))
        .andThen(() -> m_DriveTrain.stop())
        .andThen(new AutoStrafe(0, .2, 0, m_DriveTrain))
        
        .handleInterrupt(() -> m_shootermotor.stop())
        .handleInterrupt(() -> AutoY.stop());
        
      case "SMBL":
      // for left side
      return (new ShooterCommand(m_shootermotor))
        .withTimeout(1)
        .andThen(new IntakeShooterCommand(m_intake))
        .withTimeout(3)
        .andThen(() -> m_shootermotor.stop())
        .andThen(new AutoY(0, -20, 0, m_DriveTrain))
        .andThen(new AutoStrafe(0, 0, -1, m_DriveTrain))
        .andThen(new WaitCommand(.2))
        .andThen(() -> m_DriveTrain.stop())
        .andThen(new AutoStrafe(0, .2, 0, m_DriveTrain))
        
        .handleInterrupt(() -> m_shootermotor.stop())
        .handleInterrupt(() -> AutoY.stop());
    }

    System.out.println(com);
    System.out.println(autoSelected);
    return com;
  }
}


  
