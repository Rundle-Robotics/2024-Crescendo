// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.LimeTrackMecanum;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.IntakeShooterCommand;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmMotor;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.JamalShooter;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.math.kinematics.SwerveDriveWheelPositions;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
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
  private static UsbCamera IntakeCAM;
  private static NetworkTableEntry cameraSelection;
  private final DriveTrain m_DriveTrain = new DriveTrain();
  private final Limelight m_limelight = new Limelight();
  public final Intake m_intake = new Intake();
  public final ArmMotor m_armmotor = new ArmMotor();
  public final JamalShooter m_shootermotor = new JamalShooter();
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  public static final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public static final CommandXboxController m_operatorController = 
      new CommandXboxController(OperatorConstants.SECONDARY_CONTROLLER_PORT);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    IntakeCAM = CameraServer.startAutomaticCapture(OperatorConstants.ARM_CAMERA_PORT);
    cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
    // Configure the trigger bindings
    configureBindings();
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
    m_driverController.start().onTrue(
				new StartEndCommand(
						() -> cameraSelection.setString(IntakeCAM.getName()),
						() -> cameraSelection.setString(IntakeCAM.getName())));
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
        // x is intake
    m_operatorController
            .rightBumper()
            .whileTrue(
              new RunCommand(
              () ->
              m_intake.setspeed(0.9),
              m_intake
              )
              .handleInterrupt(()-> m_intake.stop()));
        // y is eject
      m_operatorController
            .leftBumper()
            .whileTrue(
              new RunCommand(
              () ->
              m_intake.setspeed(-0.7 ),
              m_intake
              )
              .handleInterrupt(()-> m_intake.stop()));

          // left bumper and right bumper to move arm, will reset after not held
          // left 
      m_operatorController
            .leftTrigger()
            .whileTrue(
              new RunCommand(
                () ->
                m_armmotor.SetArmSpeed(0.3),
                m_armmotor
                )
                .handleInterrupt(()-> m_armmotor.stop()));
          // right
      m_operatorController
            .rightTrigger()
            .whileTrue(
              new RunCommand(
                () ->
                m_armmotor.SetArmSpeed(-0.3),
                m_armmotor
                )
                .handleInterrupt(()-> m_armmotor.stop()));

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
            // left
        m_operatorController
              .x()
              .whileTrue(
                new RunCommand(
                  ()->
                  m_shootermotor.shooterSpeed(-0.4),
                  m_shootermotor
                  )
                  .handleInterrupt(()-> m_shootermotor.stop()));


        m_operatorController.a().whileTrue(
          new ShooterCommand(m_shootermotor)
          .withTimeout(1)
          .andThen(new IntakeShooterCommand(m_intake))
          .handleInterrupt(() -> m_shootermotor.stop())
        );
              
        

    // m_driverController.x().onTrue(

    //   new RunCommand(
    //     () -> 
    //     m_limitJamal.setMotorSpeed(m_limitJamal.getTopLimitSwitch() ? -0.7 : 0.7),
    //     m_limitJamal
    //   )
    //   .until(() -> m_limitJamal.getTopLimitSwitch() ? m_limitJamal.getBottomLimitSwitch() : m_limitJamal.getTopLimitSwitch())
    //   .handleInterrupt(() -> m_limitJamal.stop())
    // );

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
    // An example command will be run in autonomous
  }
}
