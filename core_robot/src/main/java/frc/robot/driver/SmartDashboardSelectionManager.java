package frc.robot.driver;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import frc.lib.robotprovider.*;

@Singleton
public class SmartDashboardSelectionManager
{
    private final ISendableChooser<StartPosition> positionChooser;
    private final ISendableChooser<AutoRoutine> routineChooser;

    public enum StartPosition
    {
        Mid,
        Load,
        Guard
    }

    public enum AutoRoutine
    {
        None,
        Place,
        Taxi,
        Charge,
        OnePlusTaxi,
        OnePlusCharge,
        OnePlusPickup,
        OnePickupCharge,
        OnePlusOne,
        ThreePiece
    }

    /**
     * Initializes a new SmartDashboardSelectionManager
     */
    @Inject
    public SmartDashboardSelectionManager(
        IRobotProvider provider)
    {
        INetworkTableProvider networkTableProvider = provider.getNetworkTableProvider();

        this.routineChooser = networkTableProvider.getSendableChooser();
        this.routineChooser.addDefault("None", AutoRoutine.None);
        this.routineChooser.addObject("Taxi", AutoRoutine.Taxi);
        this.routineChooser.addObject("Charge", AutoRoutine.Charge);
        this.routineChooser.addObject("One Pickup", AutoRoutine.OnePlusPickup);
        this.routineChooser.addObject("One Plus Taxi", AutoRoutine.OnePlusTaxi);
        this.routineChooser.addObject("One Plus Charge", AutoRoutine.OnePlusCharge);
        this.routineChooser.addObject("One Pickup Charge", AutoRoutine.OnePickupCharge);
        this.routineChooser.addObject("One Plus One", AutoRoutine.OnePlusOne);
        this.routineChooser.addObject("Three Piece", AutoRoutine.ThreePiece);
        this.routineChooser.addObject("Place", AutoRoutine.Place);
        networkTableProvider.addChooser("Auto Routine", this.routineChooser);

        this.positionChooser = networkTableProvider.getSendableChooser();
        this.positionChooser.addDefault("middle", StartPosition.Mid);
        this.positionChooser.addObject("load", StartPosition.Load);
        this.positionChooser.addObject("guard", StartPosition.Guard);
        networkTableProvider.addChooser("Start Position", this.positionChooser);
    }

    public StartPosition getSelectedStartPosition()
    {
        return SmartDashboardSelectionManager.GetSelectedOrDefault(this.positionChooser, StartPosition.Mid);
    }

    public AutoRoutine getSelectedAutoRoutine()
    {
        return SmartDashboardSelectionManager.GetSelectedOrDefault(this.routineChooser, AutoRoutine.None);
    }

    private static <T> T GetSelectedOrDefault(ISendableChooser<T> chooser, T defaultValue)
    {
        T selected = chooser.getSelected();
        if (selected == null)
        {
            selected = defaultValue;
        }

        return selected;
    }
}
