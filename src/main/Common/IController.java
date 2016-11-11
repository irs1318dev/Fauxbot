package main.Common;

import main.Driver.Driver;

public interface IController
{
    /**
     * calculate the various outputs to use based on the inputs and apply them to the outputs for the relevant component
     */
    public void update();

    /**
     * stop the relevant component
     */
    public void stop();

    /**
     * set the driver that the controller should use
     * @param driver to use
     */
    public void setDriver(Driver driver);
}
