package frc.lib.robotprovider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import frc.robot.LoggingKey;

/**
 * Logs data into a CSV (comma-separated values) file that is saved to the provided location.
 */
public class CSVLogger extends StringLogger
{
    private final IFileWriter fileWriter;
    private final ArrayList<String> schema;

    private String[] values;

    /**
     * Initializes a new instance of the CSVLogger class, using the shouldLog LoggingKeys to determine the schema
     * @param fileWriter to write into
     * @throws IOException 
     */
    public CSVLogger(IFileWriter fileWriter) throws IOException
    {
        this.fileWriter = fileWriter;
        LoggingKey[] keys = LoggingKey.values();
        this.schema = new ArrayList<String>();
        for (LoggingKey key : keys)
        {
            if (key.shouldLogToCsv)
            {
                this.schema.add(key.value);
            }
        }

        this.values = new String[this.schema.size()];
        this.writeHeader();
    }

    /**
     * Initializes a new instance of the CSVLogger class.
     * @param fileWriter to write into
     * @param schema to use for writing
     * @throws IOException 
     */
    public CSVLogger(IFileWriter fileWriter, String... schema) throws IOException
    {
        this.fileWriter = fileWriter;
        this.schema = new ArrayList<String>();
        for (String schemaEntry : schema)
        {
            this.schema.add(schemaEntry);
        }

        this.values = new String[this.schema.size()];
        this.writeHeader();
    }

    private void writeHeader() throws IOException
    {
        this.fileWriter.append(String.join(",", this.schema));
        this.fileWriter.append("\r\n");
        this.fileWriter.flush();
    }

    /**
     * Write a string to the log
     * @param key to write to
     * @param value to write
     */
    @Override
    public void internalLogString(LoggingKey key, String value)
    {
        int index = this.schema.indexOf(key.value);
        if (index >= 0)
        {
            // check if string needs to be quoted
            if (value.contains("\"") || value.contains("\r") || value.contains("\n"))
            {
                value = "\"" + value.replace("\"", "\"\"") + "\"";
            }

            this.values[index] = value;
        }
    }

    /**
     * Update the log, if appropriate..
     */
    @Override
    public void update()
    {
        super.update();

        try
        {
            for (int i = 0; i < this.schema.size(); i++)
            {
                if (i > 0)
                {
                    this.fileWriter.append(",");
                }

                String value = this.values[i];
                if (value != null)
                {
                    this.fileWriter.append(value);
                }
            }

            this.fileWriter.append("\r\n");
        }
        catch (IOException e)
        {
            // best-effort...
        }

        // clear the array
        Arrays.fill(this.values, null);
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
        try
        {
            this.fileWriter.flush();
        }
        catch (IOException e)
        {
            // best-effort...
        }
    }
}
