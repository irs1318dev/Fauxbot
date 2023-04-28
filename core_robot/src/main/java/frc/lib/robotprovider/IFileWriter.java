package frc.lib.robotprovider;

import java.io.IOException;

public interface IFileWriter
{
    public void append(String string) throws IOException;
    public void flush() throws IOException;
}