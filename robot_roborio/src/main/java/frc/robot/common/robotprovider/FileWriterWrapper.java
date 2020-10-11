package frc.robot.common.robotprovider;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterWrapper implements IFileWriter
{
    private FileWriter wrappedObject;

    public FileWriterWrapper(String fileName) throws IOException
    {
        this.wrappedObject = new FileWriter(fileName);
    }

    public void append(String string) throws IOException
    {
        this.wrappedObject.append(string);
    }

    public void flush() throws IOException
    {
        this.wrappedObject.flush();
    }
}
