package frc.robot.common.robotprovider;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorSensorV3.RawColor;

import edu.wpi.first.wpilibj.I2C.Port;

import frc.robot.TuningConstants;

public class ColorSensorV3Wrapper implements IColorSensorV3
{
    private final ColorSensorChecker checker;

    private Thread checkerThread;

    public ColorSensorV3Wrapper()
    {
        this.checker = new ColorSensorChecker();
    }

    /**
     * Get the raw proximity value from the sensor ADC (11 bit). This value 
     * is largest when an object is close to the sensor and smallest when 
     * far away.
     * 
     * @return Proximity measurement value, ranging from 0 to 2047
     */
    @Override
    public int getProximity()
    {
        return this.checker.getProximity();
    }

    /**
     * Get the raw color values from their respective ADCs (20-bit).
     * 
     * @return ColorValues struct containing red, green, blue and IR values
     */
    @Override
    public RawColorRGBIR getRawColor()
    {
        return this.checker.getRawColor();
    }

    /**
     * Starts checking the color sensor for colors
     */
    public void start()
    {
        if (this.checkerThread != null)
        {
            return;
        }

        if (TuningConstants.THROW_EXCEPTIONS)
        {
            System.out.println("color checker start");
        }

        this.checkerThread = new Thread(this.checker);
        this.checkerThread.start();
    }

    /**
     * Stops checking the color sensor for colors
     */
    public void stop()
    {
        if (this.checkerThread == null)
        {
            return;
        }

        if (TuningConstants.THROW_EXCEPTIONS)
        {
            System.out.println("color checker stop");
        }

        this.checkerThread.interrupt();
        this.checkerThread = null;
    }

    private class ColorSensorChecker implements Runnable
    {
        private final ColorSensorV3 sensor;

        private Object lock;
        private int proximity;
        private RawColorRGBIR rawColor;

        public ColorSensorChecker()
        {
            this.sensor = new ColorSensorV3(Port.kOnboard);

            this.lock = new Object();
            this.proximity = 0;
            this.rawColor = null;
        }

        @Override
        public void run()
        {
            while (!Thread.interrupted())
            {
                int newProximity = this.sensor.getProximity();
                RawColor newSensorRawColor = this.sensor.getRawColor();
                RawColorRGBIR newRawColor = new RawColorRGBIR(newSensorRawColor.red, newSensorRawColor.green, newSensorRawColor.blue, newSensorRawColor.ir);
                synchronized (this.lock)
                {
                    this.proximity = newProximity;
                    this.rawColor = newRawColor;
                }

                if (TuningConstants.THROW_EXCEPTIONS)
                {
                    System.out.println("color sensor found: " + newSensorRawColor.red + "," + newSensorRawColor.green + "," + newSensorRawColor.blue);
                }

                try
                {
                    Thread.sleep(20);
                }
                catch (InterruptedException ex)
                {
                    break;
                }
            }
        }

        public int getProximity()
        {
            synchronized (this.lock)
            {
                return this.proximity;
            }
        }

        public RawColorRGBIR getRawColor()
        {
            synchronized (this.lock)
            {
                return this.rawColor;
            }
        }
    }
}
