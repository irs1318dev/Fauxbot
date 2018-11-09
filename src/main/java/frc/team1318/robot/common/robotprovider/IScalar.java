package frc.team1318.robot.common.robotprovider;

public interface IScalar
{
    double[] getValues();
    void set(double[] vals);
    IScalar clone();
    IScalar mul(IScalar it, double scale);
    IScalar mul(IScalar it);
    IScalar conj();
    boolean isReal();
}