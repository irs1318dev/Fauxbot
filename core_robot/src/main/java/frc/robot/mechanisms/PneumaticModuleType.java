package frc.robot.mechanisms;

import frc.lib.robotprovider.PneumaticsModuleType;

public class PneumaticModuleType {

    public static final PneumaticsModuleType PneumaticsControlModule = null;
    private static final PneumaticsModuleType PNEUMATICSCONTROLMODULE2 = PneumaticsControlModule;

    public static PneumaticsModuleType getPneumaticsControlModule() {
        return PNEUMATICSCONTROLMODULE2;
    }

}
