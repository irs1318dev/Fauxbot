package frc.lib.robotprovider;

/**
 * The neutral behavior of the motor
 * 
 * Brake: motor controller provides some resistance, typically 2 poles are shorted together (may turn motor into a generator, a la regenerative braking)
 * Coast: motor spins freely with only friction slowing it down
 */
public enum MotorNeutralMode
{
    Brake,
    Coast;
}
