package frc.lib.robotprovider;

public class FauxbotCompressor implements ICompressor
{
    public FauxbotCompressor(PneumaticsModuleType moduleType)
    {
    }

    public FauxbotCompressor(int module, PneumaticsModuleType moduleType)
    {
    }

    public void enableAnalog(double minPressurePSI, double maxPressurePSI)
    {
    }

    public void enableHybrid(double minPressurePSI, double maxPressurePSI)
    {
    }

    public void enableDigital()
    {
    }

    public double getPressure()
    {
        return 0.0;
    }

    public void disable()
    {
    }
}
