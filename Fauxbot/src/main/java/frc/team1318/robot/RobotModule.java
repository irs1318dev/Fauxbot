package frc.team1318.robot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import frc.team1318.robot.common.IMechanism;
import frc.team1318.robot.common.MechanismManager;
import frc.team1318.robot.common.IDashboardLogger;
import frc.team1318.robot.common.SmartDashboardLogger;
import frc.team1318.robot.common.wpilib.IJoystick;
import frc.team1318.robot.common.wpilib.ITimer;
import frc.team1318.robot.common.wpilib.IWpilibProvider;
import frc.team1318.robot.common.wpilib.JoystickWrapper;
import frc.team1318.robot.common.wpilib.TimerWrapper;
import frc.team1318.robot.common.wpilib.WpilibProvider;
import frc.team1318.robot.driver.ButtonMap;
import frc.team1318.robot.driver.DriveTrainButtonMap;
import frc.team1318.robot.driver.ElevatorButtonMap;
import frc.team1318.robot.driver.GarageDoorButtonMap;
import frc.team1318.robot.driver.common.IButtonMap;
import frc.team1318.robot.mechanisms.DriveTrainMechanism;
import frc.team1318.robot.mechanisms.ElevatorMechanism;
import frc.team1318.robot.mechanisms.GarageDoorMechanism;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Named;

public class RobotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        this.bind(IWpilibProvider.class).to(WpilibProvider.class);
        this.bind(ITimer.class).to(TimerWrapper.class);
        this.bind(IButtonMap.class).to(GarageDoorButtonMap.class);
        //this.bind(IButtonMap.class).to(DriveTrainButtonMap.class);
        //this.bind(IButtonMap.class).to(ElevatorButtonMap.class);
    }

    @Singleton
    @Provides
    public MechanismManager getMechanismManager(Injector injector)
    {
        List<IMechanism> mechanismList = new ArrayList<>();
        mechanismList.add(injector.getInstance(GarageDoorMechanism.class));
        //mechanismList.add(injector.getInstance(DriveTrainMechanism.class));
        //mechanismList.add(injector.getInstance(ElevatorMechanism.class));
        return new MechanismManager(mechanismList);
    }

    @Singleton
    @Provides
    public IDashboardLogger getLogger()
    {
        IDashboardLogger logger = new SmartDashboardLogger();
        //        try
        //        {
        //            String fileName = String.format("/home/lvuser/%1$d.csv", Calendar.getInstance().getTime().getTime());
        //            IDashboardLogger csvLogger = new CSVLogger(fileName, new String[] { "r.time", "vision.mAngle", "vision.dist" });
        //            logger = new MultiLogger(logger, csvLogger);
        //        }
        //        catch (IOException e)
        //        {
        //            e.printStackTrace();
        //        }

        return logger;
    }
}
