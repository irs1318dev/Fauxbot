package frc.lib.robotprovider;

import java.io.IOException;

import com.google.inject.Inject;

public class FauxbotFile implements IFile
{
    @Inject
    public FauxbotFile()
    {
    }

    public void open(String fileName)
    {
    }

    public boolean exists()
    {
        return false;
    }

    public long getFreeSpace()
    {
        return 0L;
    }

    public void mkdir()
    {
    }

    public IFileWriter openWriter() throws IOException
    {
        return new FauxbotFileWriter();
    }
}
