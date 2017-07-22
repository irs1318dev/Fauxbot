package org.usfirst.frc.team1318.robot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import org.usfirst.frc.team1318.robot.common.IMechanism;
import org.usfirst.frc.team1318.robot.common.IDashboardLogger;
import org.usfirst.frc.team1318.robot.common.SmartDashboardLogger;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.DigitalInputWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.EncoderWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IDigitalInput;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IEncoder;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IJoystick;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.IMotor;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.ITimer;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.JoystickWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.TalonWrapper;
import org.usfirst.frc.team1318.robot.common.wpilibmocks.TimerWrapper;
import org.usfirst.frc.team1318.robot.driver.ButtonMap;
import org.usfirst.frc.team1318.robot.driver.IButtonMap;
import org.usfirst.frc.team1318.robot.elevator.ElevatorMechanism;
import org.usfirst.frc.team1318.robot.garagedoor.GarageDoorMechanism;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class RobotModule extends AbstractModule
{
    @Override
    protected void configure()
    {
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

    @Singleton
    @Provides
    public IButtonMap getButtonMap()
    {
        return new ButtonMap();
    }

    @Singleton
    @Provides
    public ITimer getTimer()
    {
        return new TimerWrapper();
    }

    @Singleton
    @Provides
    public MechanismManager getMechanismManager(Injector injector)
    {
        List<IMechanism> mechanismList = new ArrayList<>();
        mechanismList.add(injector.getInstance(GarageDoorMechanism.class));
        //mechanismList.add(injector.getInstance(ElevatorMechanism.class));
        return new MechanismManager(mechanismList);
    }

    @Singleton
    @Provides
    @Named("USER_DRIVER_JOYSTICK")
    public IJoystick getDriverJoystick()
    {
        return new JoystickWrapper(ElectronicsConstants.JOYSTICK_DRIVER_PORT);
    }

    @Singleton
    @Provides
    @Named("USER_CODRIVER_JOYSTICK")
    public IJoystick getCoDriverJoystick()
    {
        return new JoystickWrapper(ElectronicsConstants.JOYSTICK_CO_DRIVER_PORT);
    }

    @Singleton
    @Provides
    @Named("AUTO_DIP_SWITCH_A")
    public IDigitalInput getAutoDipSwitchA()
    {
        return new DigitalInputWrapper(ElectronicsConstants.AUTO_DIP_SWITCH_A_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("GARAGEDOOR_MOTOR")
    public IMotor getGarageDoorMotor()
    {
        return new TalonWrapper(ElectronicsConstants.GARAGEDOOR_MOTOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("GARAGEDOOR_THROUGHBEAM_SENSOR")
    public IDigitalInput getGarageDoorThroughBeamSensor()
    {
        return new DigitalInputWrapper(ElectronicsConstants.GARAGEDOOR_THROUGHBEAMSENSOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("GARAGEDOOR_OPEN_SENSOR")
    public IDigitalInput getGarageDoorOpenSensor()
    {
        return new DigitalInputWrapper(ElectronicsConstants.GARAGEDOOR_OPENSENSOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("GARAGEDOOR_CLOSED_SENSOR")
    public IDigitalInput getGarageDoorClosedSensor()
    {
        return new DigitalInputWrapper(ElectronicsConstants.GARAGEDOOR_CLOSEDSENSOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("ELEVATOR_MOTOR")
    public IMotor getElevatorMotor()
    {
        return new TalonWrapper(ElectronicsConstants.ELEVATOR_MOTOR_CHANNEL);
    }

    @Singleton
    @Provides
    @Named("ELEVATOR_ENCODER")
    public IEncoder getElevatorEncoder()
    {
        return new EncoderWrapper(ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_A, ElectronicsConstants.ELEVATOR_ENCODER_CHANNEL_B);
    }
}
