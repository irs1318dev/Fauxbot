package frc.robot;

import java.io.IOException;
import java.lang.Class;
import java.lang.reflect.*;
import java.util.*;

import com.google.inject.Injector;

import frc.robot.common.*;
import frc.robot.common.robotprovider.*;

public class SettingsManager
{
    public static List<IMechanism> getActiveMechanisms(Injector injector)
    {
        List<IMechanism> mechanismList = new ArrayList<IMechanism>();
        // mechanismList.add(injector.getInstance(SomeMechanism.class));
        return mechanismList;
    }

    public static ILogger getLogger(Injector injector)
    {
        ISmartDashboardLogger smartDashboardLogger = injector.getInstance(ISmartDashboardLogger.class);
        if (!TuningConstants.LOG_TO_FILE)
        {
            return smartDashboardLogger;
        }

        IRobotProvider robotProvider = injector.getInstance(IRobotProvider.class);
        IDriverStation driverStation = robotProvider.getDriverStation();
        MatchType matchType = driverStation.getMatchType();
        if (matchType == MatchType.None && TuningConstants.LOG_FILE_ONLY_COMPETITION_MATCHES)
        {
            return smartDashboardLogger;
        }

        // NI's Linux RTOS automatically mounts USB Sticks as /U/ and /V/, so long as they are formatted as FAT32
        // https://knowledge.ni.com/KnowledgeArticleDetails?id=kA00Z000000P7iaSAC&l=en-US
        // https://www.ni.com/en-us/support/documentation/supplemental/18/file-system-compatibility-with-the-ni-linux-real-time-os.html
        IFile rootDirectory = injector.getInstance(IFile.class);
        rootDirectory.open("/U/");
        if (!rootDirectory.exists() || rootDirectory.getFreeSpace() < TuningConstants.LOG_FILE_REQUIRED_FREE_SPACE)
        {
            return smartDashboardLogger;
        }

        String eventName = driverStation.getEventName();
        int matchNumber = driverStation.getMatchNumber();
        int replayNumber = driverStation.getReplayNumber();
        Alliance alliance = driverStation.getAlliance();
        int location = driverStation.getLocation();
        IFile file;
        if (eventName == null ||
            matchType == MatchType.None ||
            matchNumber == 0 ||
            alliance == Alliance.Invalid ||
            location <= 0 ||
            location >= 4)
        {
            if (TuningConstants.LOG_FILE_ONLY_COMPETITION_MATCHES)
            {
                // strange 
                return smartDashboardLogger;
            }
            else
            {
                // don't know important information, let's just put it under the root of the USB stick
                IFile directory = injector.getInstance(IFile.class);
                directory.open("/U/other/");
                directory.mkdir();

                file = injector.getInstance(IFile.class);
                file.open(String.format("/U/other/%1$d.csv", Calendar.getInstance().getTime().getTime()));
                if (file.exists())
                {
                    // file already exists
                    return smartDashboardLogger;
                }
            }
        }
        else
        {
            String directoryPath = String.format("/U/%1$d - %2$s/", TuningConstants.CALENDAR_YEAR, eventName);
            IFile directory = injector.getInstance(IFile.class);
            directory.open(directoryPath);
            directory.mkdir();

            // name the file a la "/U/2020 - Glacier Peak/Q03 (R2).auto.csv" or "/U/2020 - Glacier Peak/Q12R1 (B3).tele.csv"
            RobotMode mode = driverStation.getMode();
            file = injector.getInstance(IFile.class);
            String fileName =
                String.format(
                    "%1$s%2$s%3$02d%4$s (%5$s%6$d).%7$s.csv",
                    directoryPath,
                    matchType.value,
                    matchNumber,
                    replayNumber == 0 ? "" : String.format("R%1$d", replayNumber),
                    alliance.value,
                    location,
                    mode.toString().toLowerCase());

            file.open(fileName);
            if (file.exists())
            {
                for (int i = 2; i <= 5; i++)
                {
                    // start adding .2, .3, etc. to the name, a la "/U/2020 - Glacier Peak/Q03 (R2).2.csv"
                    fileName =
                        String.format(
                            "%1$s%2$s%3$02d%4$s (%5$s%6$d).%7$s.%8$d.csv",
                            directoryPath,
                            matchType.value,
                            matchNumber,
                            replayNumber == 0 ? "" : String.format("R%1$d", replayNumber),
                            alliance.value,
                            location,
                            mode.toString().toLowerCase(),
                            i);

                    file.open(fileName);
                    if (!file.exists())
                    {
                        break;
                    }
                }
            }
        }

        try
        {
            CSVLogger csvLogger = new CSVLogger(file.openWriter());
            return new MultiLogger(csvLogger, smartDashboardLogger);
        }
        catch (IOException ex)
        {
            return smartDashboardLogger;
        }
    }

    public static void initAndUpdatePreferences(IPreferences preferences, Class<?> type)
    {
        Field[] fields = type.getFields();
        for (Field field : fields)
        {
            String fieldName = field.getName();
            SettingType fieldType = SettingsManager.getSettingType(field.getType());

            try
            {
                Object initialValue = field.get(null);

                switch (fieldType)
                {
                    case Boolean:
                        preferences.initBoolean(fieldName, (boolean)initialValue);
                        field.set(null, preferences.getBoolean(fieldName, (boolean)initialValue));
                        break;

                    case Integer:
                        preferences.initInt(fieldName, (int)initialValue);
                        field.set(null, preferences.getInt(fieldName, (int)initialValue));
                        break;

                    case Long:
                        preferences.initLong(fieldName, (long)initialValue);
                        field.set(null, preferences.getLong(fieldName, (long)initialValue));
                        break;

                    case Double:
                        preferences.initDouble(fieldName, (double)initialValue);
                        field.set(null, preferences.getDouble(fieldName, (double)initialValue));
                        break;

                    case String:
                        preferences.initString(fieldName, (String)initialValue);
                        field.set(null, preferences.getString(fieldName, (String)initialValue));
                        break;
                }
            }
            catch (IllegalAccessException ex)
            {
                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    throw new RuntimeException("Error with field " + fieldName, ex);
                }
            }
        }
    }

    private static SettingType getSettingType(Class<?> valueClass)
    {
        for (SettingType settingType : SettingType.values())
        {
            if (valueClass == settingType.getSettingClass())
            {
                return settingType;
            }
        }

        throw new RuntimeException("Unsupported type " + valueClass.getName());
    }

    private enum SettingType
    {
        Boolean(boolean.class),
        Integer(int.class),
        Long(long.class),
        Double(double.class),
        String(String.class);

        private final Class<?> settingClass;
        private SettingType(Class<?> settingClass)
        {
            this.settingClass = settingClass;
        }

        public Class<?> getSettingClass()
        {
            return this.settingClass;
        }
    }
}
