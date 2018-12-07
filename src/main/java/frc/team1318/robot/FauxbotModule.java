package frc.team1318.robot;

import javax.inject.Singleton;

import frc.team1318.robot.common.*;
import frc.team1318.robot.common.robotprovider.*;
import frc.team1318.robot.driver.*;
import frc.team1318.robot.driver.common.IButtonMap;
import frc.team1318.robot.simulation.*;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class FauxbotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        this.bind(IRealWorldSimulator.class).to(ElevatorSimulator.class);
        this.bind(IRobotProvider.class).to(FauxbotProvider.class);
        this.bind(ITimer.class).to(FauxbotTimer.class);
        this.bind(IButtonMap.class).to(ElevatorButtonMap.class);
    }

    @Singleton
    @Provides
    public MechanismManager getMechanismManager(Injector injector)
    {
        return new MechanismManager(TuningConstants.GetActiveMechanisms(injector));
    }

    @Singleton
    @Provides
    public IDashboardLogger getLogger()
    {
        IDashboardLogger logger = new ConsoleDashboardLogger();
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
