package frc.robot.common.robotprovider;

public class FauxbotCANdle implements ICANdle
{
    public FauxbotCANdle(int deviceNumber)
    {
    }

    public void configBrightnessScalar(double brightness)
    {
    }

    public void configLEDType(CANdleLEDStripType type)
    {
    }

    public void configLOSBehavior(boolean disableWhenLOS)
    {
    }

    public void configStatusLedState(boolean disableWhenRunning)
    {
    }

    public void configVBatOutput(CANdleVBatOutputMode mode)
    {
    }

    public void modulateVBatOutput(double dutyCyclePercent)
    {
    }

    public void setLEDs(int r, int g, int b)
    {
    }

    public void setLEDs(int r, int g, int b, int w, int startIdx, int count)
    {
    }

    public void startTwinkleAnimation(int r, int g, int b, int w, double speed, int numLed, CANdleTwinklePercent divider)
    {
    }

    public void startTwinkleOffAnimation(int r, int g, int b, int w, double speed, int numLed, CANdleTwinklePercent divider)
    {
    }

    public void startStrobeAnimation(int r, int g, int b, int w, double speed, int numLed)
    {
    }

    public void startSingleFadeAnimation(int r, int g, int b, int w, double speed, int numLed)
    {
    }

    public void startRgbFadeAnimation(double brightness, double speed, int numLed)
    {
    }

    public void startRainbowAnimation(double brightness, double speed, int numLed)
    {
    }

    public void startLarsonAnimation(int r, int g, int b, int w, double speed, int numLed, CANdleLarsonBounceMode mode, int size)
    {
    }

    public void startFireAnimation(double brightness, double speed, int numLed, double sparking, double cooling)
    {
    }

    public void startColorFlowAnimation(int r, int g, int b, int w, double speed, int numLed, boolean forward)
    {
    }
}