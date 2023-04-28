package frc.robot.driver;

import org.junit.jupiter.api.Test;

import frc.lib.driver.ButtonMapVerifier;

public class ButtonMapTests
{
    @Test
    public void verifyButtonMap()
    {
        ButtonMapVerifier.Verify(new ButtonMap(), true, false);
    }
}