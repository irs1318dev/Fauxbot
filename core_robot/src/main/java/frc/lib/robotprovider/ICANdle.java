package frc.lib.robotprovider;

/**
 * Represents a CANdle device
 */
public interface ICANdle
{
    /**
     * Gets the Voltage of VBat as measured by CANdle
     * @return Voltage of VBat
     */
    double getBusVoltage();

    /**
     * Gets the Voltage of the 5V line as measured by CANdle
     * @return Voltage of the 5V line
     */
    double get5VRailVoltage();

    /**
     * Gets the low-side current as measured by CANdle
     * @return Current in Amps
     */
    double getCurrent();

    /**
     * Gets the temperature of the CANdle in Celcius
     * @return Temperature in Celcius
     */
    double getTemperature();

    /**
     * Gets the maximum number of simultaneous animations this version of CANdle firmware supports.
     * If you specify an animation slot >= to this return, Phoenix will error out.
     * You can also get the maximum count from a self-test snapshot.
     * @return Maximum number of simultaneous animations this version of firmware supports.
     */
    int getMaxSimultaneousAnimationCount();

    /**
     * Configures the brightness scalar to be applied to every LED output.
     * This value is bounded to [0, 1].
     * 
     * Setting this to 1 will allow the LEDs to function at max brightness.
     * Setting this to 0.5 will scale all values to half their applied value.
     * Setting this to 0 will turn off the LEDs.
     * 
     * Forcing the LEDs off this way may be useful in certain testing circumstances 
     * but is generally not necessary. Self-test (Tuner) may be used to verify what 
     * the effective scalar is in case user forgot to restore the scalar to a 
     * non-zero value.
     * 
     * @param brightness Value from [0, 1] that will scale the LED output.
     */
    void configBrightnessScalar(double brightness);

    /**
     * Configures the type of LED the CANdle controls
     * @param type The type of the LEDs the CANdle controls
     */
    void configLEDType(CANdleLEDStripType type);

    /**
     * Configures what the CANdle should do if it loses communications to the Controller
     * @param disableWhenLOS Set to true to disable the LEDs on Loss of Signal.
     */
    void configLOSBehavior(boolean disableWhenLOS);

    /**
     * Configures how the status led will behave when the CANdle is actively controlling LEDs
     * If the CANdle is LOS or not actively commanded a value, it will always turn on its status LED.
     * @param disableWhenRunning Disables the status LED when the CANdle is running
     */
    void configStatusLedState(boolean disableWhenRunning);

    /**
     * Configures how the VBat Output will behave
     * @param mode VBat Output Behavior
     */
    void configVBatOutput(CANdleVBatOutputMode mode);

    /**
     * Modulates the VBat output to the specified duty cycle percentage
     * This function will only do something if the CANdle's VBatOutput is configured to Modulated
     * @param dutyCyclePercent The duty cycle of the output modulation [0, 1]
     */
    void modulateVBatOutput(double dutyCyclePercent);

    /**
     * Sets a block of LEDs to the specified color. This will apply to the first 255 LEDs.
     * @param r The amount of Red to set, range is [0, 255]
     * @param g The amount of Green to set, range is [0, 255]
     * @param b The amount of Blue to set, range is [0, 255]
     */
    void setLEDs(int r, int g, int b);

    /**
     * Sets a block of LEDs to the specified color
     * @param r The amount of Red to set, range is [0, 255]
     * @param g The amount of Green to set, range is [0, 255]
     * @param b The amount of Blue to set, range is [0, 255]
     * @param w The amount of White to set, range is [0, 255]. This only applies for LED strips with white in them.
     * @param startIdx Where to start setting the LEDs 
     * @param count The number of LEDs to apply this to
     */
    void setLEDs(int r, int g, int b, int w, int startIdx, int count);

    /**
     * start a TwinkleAnimation that randomly turns LEDs on and off to a certain color 
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param r How much red should the color have [0, 255]
     * @param g How much green should the color have [0, 255]
     * @param b How much blue should the color have [0, 255]
     * @param w How much white should the color have [0, 255]
     * @param speed How fast should the color travel the strip [0, 1]
     * @param numLed How many LEDs the CANdle controls
     * @param divider What percentage of LEDs can be on at any point
     * @param ledOffset Where to start the animation
     */
    void startTwinkleAnimation(int animSlot, int r, int g, int b, int w, double speed, int numLed, CANdleTwinklePercent divider, int ledOffset);

    /**
     * start a TwinkleOffAnimation that randomly turns on LEDs, until it reaches the maximum count and turns them all off
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param r How much red should the color have [0, 255]
     * @param g How much green should the color have [0, 255]
     * @param b How much blue should the color have [0, 255]
     * @param w How much white should the color have [0, 255]
     * @param speed How fast should the color travel the strip [0, 1]
     * @param numLed How many LEDs the CANdle controls
     * @param divider What percentage of LEDs can be on at any point
     * @param ledOffset Where to start the animation
     */
    void startTwinkleOffAnimation(int animSlot, int r, int g, int b, int w, double speed, int numLed, CANdleTwinklePercent divider, int ledOffset);

    /**
     * start a StrobeAnimation that strobes the LEDs a specified color
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param r How much red should the color have [0, 255]
     * @param g How much green should the color have [0, 255]
     * @param b How much blue should the color have [0, 255]
     * @param w How much white should the color have [0, 255]
     * @param speed How fast should the color travel the strip [0, 1]
     * @param numLed How many LEDs the CANdle controls
     * @param ledOffset Where to start the animation
     */
    void startStrobeAnimation(int animSlot, int r, int g, int b, int w, double speed, int numLed, int ledOffset);

    /**
     * start a SingleFadeAnimation that fades into and out of a specified color
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param r How much red should the color have [0, 255]
     * @param g How much green should the color have [0, 255]
     * @param b How much blue should the color have [0, 255]
     * @param w How much white should the color have [0, 255]
     * @param speed How fast should the color travel the strip [0, 1]
     * @param numLed How many LEDs the CANdle controls
     * @param ledOffset Where to start the animation
     */
    void startSingleFadeAnimation(int animSlot, int r, int g, int b, int w, double speed, int numLed, int ledOffset);

    /**
     * start an RgbFadeAnimation that fades all the LEDs of a strip simultaneously between Red, Green, and Blue
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param brightness How bright the LEDs are [0, 1]
     * @param speed How fast the LEDs fade between Red, Green, and Blue [0, 1]
     * @param numLed How many LEDs are controlled by the CANdle
     * @param ledOffset Where to start the animation
     */
    void startRgbFadeAnimation(int animSlot, double brightness, double speed, int numLed, int ledOffset);

    /**
     * start a RainbowAnimation that creates a rainbow throughout all the LEDs
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param brightness The brightness of the LEDs [0, 1]
     * @param speed How fast the rainbow travels through the leds [0, 1]
     * @param numLed How many LEDs are controlled by the CANdle
     * @param reverseDirection True to reverse the animation direction, so instead of going "toward" the CANdle, it will go "away" from the CANdle.
     * @param ledOffset Where to start the animation
     */
    void startRainbowAnimation(int animSlot, double brightness, double speed, int numLed, boolean reverseDirection, int ledOffset);

    /**
     * start a LarsonAnimation that sends a pocket of light across the LED strip.
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param r How much red should the color have [0, 255]
     * @param g How much green should the color have [0, 255]
     * @param b How much blue should the color have [0, 255]
     * @param w How much white should the color have [0, 255]
     * @param speed How fast should the color travel the strip [0, 1]
     * @param numLed The number of LEDs the CANdle will control
     * @param mode How the pocket of LEDs will behave once it reaches the end of the strip
     * @param size How large the pocket of LEDs are [0, 7]
     * @param ledOffset Where to start the animation
     */
    void startLarsonAnimation(int animSlot, int r, int g, int b, int w, double speed, int numLed, CANdleLarsonBounceMode mode, int size, int ledOffset);

    /**
     * starts a FireAnimation that looks similarly to a flame flickering
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param brightness How bright should the animation be [0, 1]
     * @param speed How fast will the flame be processed at [0, 1]
     * @param numLed How many LEDs is the CANdle controlling
     * @param sparking The rate at which the Fire "Sparks" [0, 1]
     * @param cooling The rate at which the Fire "Cools" along the travel [0, 1]
     * @param reverseDirection True to reverse the animation direction, so instead of fire going "away" from the CANdle, it will go "toward" the CANdle.
     * @param ledOffset Where to start the animation
     */
    void startFireAnimation(int animSlot, double brightness, double speed, int numLed, double sparking, double cooling, boolean reverseDirection, int ledOffset);

    /**
     * start a ColorFlowAnimation that gradually lights the entire LED strip one LED at a time.
     * @param animSlot The animation slot to use for the animation, range is [0, getMaxSimultaneousAnimationCount()) exclusive
     * @param r How much red should the color have [0, 255]
     * @param g How much green should the color have [0, 255]
     * @param b How much blue should the color have [0, 255]
     * @param w How much white should the color have [0, 255]
     * @param speed How fast should the color travel the strip [0, 1]
     * @param numLed How many LEDs is the CANdle controlling
     * @param forward whether to flow in the forward or backward direction
     * @param ledOffset Where to start the animation
     */
    void startColorFlowAnimation(int animSlot, int r, int g, int b, int w, double speed, int numLed, boolean forward, int ledOffset);

    /**
     * stops and clears the animation occurring in the selected animSlot.
     * @param animSlot Animation slot to clear
     */
    void stopAnimation(int animSlot);
}