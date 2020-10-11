package frc.robot.driver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import de.siegmar.fastcsv.writer.CsvAppender;
import de.siegmar.fastcsv.writer.CsvWriter;
import frc.robot.driver.common.PathStep;

@Singleton
public class PathManager
{
    private static final String[] NAMES =
        new String[]
        {
            "/Paths/straight_path.csv",
            "/Paths/curved_path.csv",
            "/Paths/backwards_path.csv"
        };

    private static final String LEFT_POSITION_NAME = "LeftPosition";
    private static final String RIGHT_POSITION_NAME = "RightPosition";
    private static final String LEFT_VELOCITY_NAME = "LeftVelocity";
    private static final String RIGHT_VELOCITY_NAME = "RightVelocity";
    private static final String LEFT_ACCELERATION_NAME = "LeftAcceleration";
    private static final String RIGHT_ACCELERATION_NAME = "RightAcceleration";
    private static final String HEADING_NAME = "Heading";

    private HashMap<String, List<PathStep>> map;

    /**
     * Initializes a new PathManager
     */
    @Inject
    public PathManager()
    {
        this.map = new HashMap<String, List<PathStep>>();
    }

    public void loadPaths()
    {
        for (String name : PathManager.NAMES)
        {
            List<PathStep> path = new ArrayList<PathStep>();

            try
            {
                InputStream stream = this.getClass().getResourceAsStream(name);
                InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
                CsvReader csvReader = new CsvReader();
                csvReader.setContainsHeader(true);
                CsvParser csvParser = csvReader.parse(reader);

                CsvRow row = csvParser.nextRow();
                if (row == null)
                {
                    continue;
                }

                List<String> headers = csvParser.getHeader();
                int leftPositionIndex = headers.indexOf(PathManager.LEFT_POSITION_NAME);
                int rightPositionIndex = headers.indexOf(PathManager.RIGHT_POSITION_NAME);
                int leftVelocityIndex = headers.indexOf(PathManager.LEFT_VELOCITY_NAME);
                int rightVelocityIndex = headers.indexOf(PathManager.RIGHT_VELOCITY_NAME);
                int headingIndex = headers.indexOf(PathManager.HEADING_NAME);

                do
                {
                    String leftPositionString = row.getField(leftPositionIndex);
                    String rightPositionString = row.getField(rightPositionIndex);
                    String leftVelocityString = row.getField(leftVelocityIndex);
                    String rightVelocityString = row.getField(rightVelocityIndex);
                    String headingString = row.getField(headingIndex);

                    if (leftPositionString != null && !leftPositionString.equals("") &&
                        rightPositionString != null && !rightPositionString.equals("") &&
                        leftVelocityString != null && !leftVelocityString.equals("") &&
                        rightVelocityString != null && !rightVelocityString.equals("") &&
                        headingString != null && !headingString.equals(""))
                    {
                        double leftPosition = Double.parseDouble(leftPositionString);
                        double rightPosition = Double.parseDouble(rightPositionString);
                        double leftVelocity = Double.parseDouble(leftVelocityString);
                        double rightVelocity = Double.parseDouble(rightVelocityString);
                        double heading = Double.parseDouble(headingString);

                        path.add(new PathStep(leftPosition, rightPosition, leftVelocity, rightVelocity, heading));
                    }
                }
                while ((row = csvParser.nextRow()) != null);

                this.map.put(name, path);
            }
            catch (IOException ex)
            {
                System.out.println("couldn't load/parse CSV! " + name);
                System.out.println(ex.toString());
            }
        }
    }

    public List<PathStep> getPath(String name)
    {
        return map.getOrDefault(name, null);
    }

    public void addPath(String name, List<PathStep> path)
    {
        map.put(name, path);
    }

    public static void writePathToFile(String filePath, List<PathStep> pathSteps)
    {
        File file = new File(filePath);
        if (file.exists())
        {
            file.delete();
        }

        CsvWriter csvWriter = new CsvWriter();
        try (CsvAppender csvAppender = csvWriter.append(file, StandardCharsets.UTF_8))
        {
            csvAppender.appendLine(
                PathManager.LEFT_POSITION_NAME,
                PathManager.RIGHT_POSITION_NAME,
                PathManager.LEFT_VELOCITY_NAME,
                PathManager.RIGHT_VELOCITY_NAME,
                PathManager.LEFT_ACCELERATION_NAME,
                PathManager.RIGHT_ACCELERATION_NAME,
                PathManager.HEADING_NAME);

            for (PathStep step : pathSteps)
            {
                csvAppender.appendLine(
                    "" + step.getLeftPosition(),
                    "" + step.getRightPosition(),
                    "" + step.getLeftVelocity(),
                    "" + step.getRightVelocity(),
                    "" + step.getLeftAcceleration(),
                    "" + step.getRightAcceleration(),
                    "" + step.getHeading());
            }
        }
        catch (IOException e)
        {
        }
    }
}
