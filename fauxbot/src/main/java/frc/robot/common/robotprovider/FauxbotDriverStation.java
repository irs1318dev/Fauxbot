package frc.robot.common.robotprovider;

public class FauxbotDriverStation implements IDriverStation
{
    public FauxbotDriverStation()
    {
    }

    public String getGameSpecificMessage()
    {
        return "LLL";
    }
}