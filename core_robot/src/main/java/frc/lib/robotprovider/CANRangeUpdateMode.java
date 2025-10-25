package frc.lib.robotprovider;

public enum CANRangeUpdateMode
{
    /** Uses long-range detection mode and user-specified update frequency. */
    LongRangeUserFreq,

    /** Updates distance/proximity at 100hz using short-range detection mode. */
    ShortRange100Hz,

    /** Uses short-range detection mode for improved detection under high ambient infrared light conditions. */
    ShortRangeUserFreq;
}
