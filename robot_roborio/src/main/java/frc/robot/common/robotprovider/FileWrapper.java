package frc.robot.common.robotprovider;

import java.io.File;
import java.io.IOException;

import com.google.inject.Inject;

public class FileWrapper implements IFile
{
    private File wrappedObject;

    @Inject
    public FileWrapper()
    {
    }

    public void open(String fileName)
    {
        this.wrappedObject = new File(fileName);
    }

    public boolean exists()
    {
        return this.wrappedObject.exists();
    }

    public long getFreeSpace()
    {
        return this.wrappedObject.getFreeSpace();
    }

    public void mkdir()
    {
        this.wrappedObject.mkdir();
    }

    public IFileWriter openWriter() throws IOException
    {
        return new FileWriterWrapper(this.wrappedObject.getAbsolutePath());
    }
}
