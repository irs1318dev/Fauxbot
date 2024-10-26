package frc.lib.robotprovider;

import java.io.IOException;

/**
 * Represents a writer of data into a file on the local filesystem
 */
public interface IFileWriter
{
    /**
     * Append data into the file
     * @param string to append
     * @throws IOException if there's some issue writing data
     */
    public void append(String string) throws IOException;

    /**
     * Attempt to flush the data to disk (out of buffer)
     * @throws IOException if there's some issue writing data
     */
    public void flush() throws IOException;
}