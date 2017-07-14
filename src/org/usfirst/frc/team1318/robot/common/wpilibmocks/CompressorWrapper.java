package org.usfirst.frc.team1318.robot.common.wpilibmocks;

import edu.wpi.first.wpilibj.Compressor;

public class CompressorWrapper implements ICompressor
{
    private final Compressor wrappedObject;

    public CompressorWrapper()
    {
        this.wrappedObject = new Compressor();
    }

    public CompressorWrapper(int module)
    {
        this.wrappedObject = new Compressor(module);
    }

    public void start()
    {
        this.wrappedObject.start();
    }

    public void stop()
    {
        this.wrappedObject.stop();
    }
}
