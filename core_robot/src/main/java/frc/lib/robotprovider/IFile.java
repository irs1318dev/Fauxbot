package frc.lib.robotprovider;

import java.io.IOException;

public interface IFile
{
    public void open(String fileName);
    public boolean exists();
    public long getFreeSpace();
    public void mkdir();
    public IFileWriter openWriter() throws IOException;
}