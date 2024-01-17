package frc.robot.commands;

import frc.robot.subsystems.MySubsystem;
import edu.wpi.first.wpilibj2.command.Command;

//command are for one specific robot action
public class MyCommand extends Command {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final MySubsystem m_subsystem;

    public MyCommand(MySubsystem subsystem) {
        m_subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        //called when initialized
    }

    @Override
    public void execute() {
        //like the periodic function 
    }

    //called when the command ends 
    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

}