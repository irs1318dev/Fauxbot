package frc.lib.robotprovider;

import java.io.IOException;

/**
 * Represents a File on the local filesystem
 */
public interface IFile
{
    /**
     * Open a file with the provided filename
     * @param fileName to open
     */
    public void open(String fileName);

    /**
     * Check whether the file exists
     * @return true if the file already exists, otherwise false
     */
    public boolean exists();

    /**
     * Checks the amount of free space available for the file
     * @return
     */
    public long getFreeSpace();

    /**
     * Create the directory where the file is located
     */
    public void mkdir();

    /**
     * Attempt to open a writer to write data into the file
     * @return file writer
     * @throws IOException if there's an issue opening a writer
     */
    public IFileWriter openWriter() throws IOException;
}