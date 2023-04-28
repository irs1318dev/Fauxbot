package frc.lib.robotprovider;

public enum CANdleVBatOutputMode
{
    /**
     * VBat output is on at full power, no modulation
     */
    On,

    /**
     * VBat output is off, no modulation
     */
    Off,

    /**
     * VBat output is on at the specified modulation
     */
    Modulated;
}