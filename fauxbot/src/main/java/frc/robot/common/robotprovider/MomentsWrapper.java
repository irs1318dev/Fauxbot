package frc.robot.common.robotprovider;

import org.opencv.imgproc.Moments;

public class MomentsWrapper implements IMoments
{
    final Moments wrappedObject;

    public MomentsWrapper(Moments wrappedObject)
    {
        this.wrappedObject = wrappedObject;
    }

    @Override
    public double get_m00()
    {
        return this.wrappedObject.get_m00();
    }

    @Override
    public double get_m10()
    {
        return this.wrappedObject.get_m10();
    }

    @Override
    public double get_m01()
    {
        return this.wrappedObject.get_m01();
    }

    @Override
    public double get_m20()
    {
        return this.wrappedObject.get_m20();
    }

    @Override
    public double get_m11()
    {
        return this.wrappedObject.get_m11();
    }

    @Override
    public double get_m02()
    {
        return this.wrappedObject.get_m02();
    }

    @Override
    public double get_m30()
    {
        return this.wrappedObject.get_m30();
    }

    @Override
    public double get_m21()
    {
        return this.wrappedObject.get_m21();
    }

    @Override
    public double get_m12()
    {
        return this.wrappedObject.get_m12();
    }

    @Override
    public double get_m03()
    {
        return this.wrappedObject.get_m03();
    }

    @Override
    public double get_mu20()
    {
        return this.wrappedObject.get_mu20();
    }

    @Override
    public double get_mu11()
    {
        return this.wrappedObject.get_mu11();
    }

    @Override
    public double get_mu02()
    {
        return this.wrappedObject.get_mu02();
    }

    @Override
    public double get_mu30()
    {
        return this.wrappedObject.get_mu30();
    }

    @Override
    public double get_mu21()
    {
        return this.wrappedObject.get_mu21();
    }

    @Override
    public double get_mu12()
    {
        return this.wrappedObject.get_mu12();
    }

    @Override
    public double get_mu03()
    {
        return this.wrappedObject.get_mu03();
    }

    @Override
    public double get_nu20()
    {
        return this.wrappedObject.get_nu20();
    }

    @Override
    public double get_nu11()
    {
        return this.wrappedObject.get_nu11();
    }

    @Override
    public double get_nu02()
    {
        return this.wrappedObject.get_nu02();
    }

    @Override
    public double get_nu30()
    {
        return this.wrappedObject.get_nu30();
    }

    @Override
    public double get_nu21()
    {
        return this.wrappedObject.get_nu21();
    }

    @Override
    public double get_nu12()
    {
        return this.wrappedObject.get_nu12();
    }

    @Override
    public double get_nu03()
    {
        return this.wrappedObject.get_nu03();
    }
}