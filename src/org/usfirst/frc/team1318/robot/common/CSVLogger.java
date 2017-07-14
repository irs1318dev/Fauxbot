package org.usfirst.frc.team1318.robot.common;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVLogger extends StringLogger implements IDashboardLogger
{
    private final FileWriter fileWriter;
    private final ArrayList<String> schema;

    private String[] values;

    /**
     * Initializes a new instance of the CSVLogger class.
     * @param fileName to write to
     * @param schema to use for writing
     * @throws IOException 
     */
    public CSVLogger(String fileName, String[] schema) throws IOException
    {
        this.fileWriter = new FileWriter(fileName);
        this.schema = new ArrayList<String>();
        for (String schemaEntry : schema)
        {
            this.schema.add(schemaEntry);
        }

        this.values = new String[this.schema.size()];

        this.fileWriter.append(String.join(",", this.schema));
        this.fileWriter.append("\r\n"); // file will be read in windows
    }

    /**
     * Write a string to the smart dashboard
     * @param component to log for
     * @param key to write to
     * @param value to write
     */
    @Override
    public void logString(String component, String key, String value)
    {
        String logKey = String.format("%s.%s", component, key);
        int index = this.schema.indexOf(logKey);
        if (index >= 0)
        {
            this.values[index] = value;
        }
    }

    /**
     * Flush the output stream, if appropriate..
     */
    @Override
    public void flush()
    {
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
            this.fileWriter.flush();
        }
        catch (IOException e)
        {
            // best-effort...
        }

        this.values = new String[this.schema.size()];
    }
}
