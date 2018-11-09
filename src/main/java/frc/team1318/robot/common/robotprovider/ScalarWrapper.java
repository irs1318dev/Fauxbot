package frc.team1318.robot.common.robotprovider;

import org.opencv.core.Scalar;

public class ScalarWrapper implements IScalar
{
    final Scalar wrappedObject;

    public ScalarWrapper(Scalar wrappedObject)
    {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public double[] getValues()
    {
        return this.wrappedObject.val;
    }

    @Override
    public void set(double[] vals)
    {
        this.wrappedObject.set(vals);
    }

    @Override
    public IScalar clone()
    {
        return new ScalarWrapper(this.wrappedObject.clone());
    }

    @Override
    public IScalar mul(IScalar it, double scale)
    {
        return new ScalarWrapper(this.wrappedObject.mul(OpenCVProvider.unwrap(it), scale));
    }

    @Override
    public IScalar mul(IScalar it)
    {
        return new ScalarWrapper(this.wrappedObject.mul(OpenCVProvider.unwrap(it)));
    }

    @Override
    public IScalar conj()
    {
        return new ScalarWrapper(this.wrappedObject.conj());
    }

    @Override
    public boolean isReal()
    {
        return this.wrappedObject.isReal();
    }
}