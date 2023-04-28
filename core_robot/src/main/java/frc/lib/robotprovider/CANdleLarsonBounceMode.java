package frc.lib.robotprovider;

/**
 * How the pocket of light behaves when it reaches the end of the strip
 */
public enum CANdleLarsonBounceMode
{
    /**
     * Bounce the pocket as soon as the first LED reaches the end of the strip
     */
    Front,

    /**
     * Bounce the pocket once it is midway through the end of the strip
     */
    Center,

    /**
     * Bounce the pocket once all the LEDs are off the strip
     */
    Back;
}