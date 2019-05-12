package frc.robot.driver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.siegmar.fastcsv.reader.CsvParser;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;

import frc.robot.driver.common.PathStep;

@Singleton
public class PathManager
{
    private static final String[] NAMES =
        new String[]
        {
            "/Paths/Circle 40 inch radius.csv",
            "/Paths/S path.csv",
            "/Paths/Straight 4 feet.csv",
            "/Paths/Turn left 4 feet.csv",
            "/Paths/Turn right 4 feet.csv",
        };

    private static final String LEFT_POSITION_NAME = "LeftPosition";
    private static final String RIGHT_POSITION_NAME = "RightPosition";
    private static final String LEFT_VELOCITY_NAME = "LeftVelocity";
    private static final String RIGHT_VELOCITY_NAME = "RightVelocity";
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

                CsvRow row;
                while ((row = csvParser.nextRow()) != null)
                {
                    String leftPosition = row.getField(PathManager.LEFT_POSITION_NAME);
                    String rightPosition = row.getField(PathManager.RIGHT_POSITION_NAME);
                    String leftVelocity = row.getField(PathManager.LEFT_VELOCITY_NAME);
                    String rightVelocity = row.getField(PathManager.RIGHT_VELOCITY_NAME);
                    String heading = row.getField(PathManager.HEADING_NAME);

                    if (leftPosition != null && !leftPosition.equals("") &&
                        rightPosition != null && !rightPosition.equals("") &&
                        leftVelocity != null && !leftVelocity.equals("") &&
                        rightVelocity != null && !rightVelocity.equals("") &&
                        heading != null && !heading.equals(""))
                    {
                        double lpos = Double.parseDouble(leftPosition);
                        double rpos = Double.parseDouble(rightPosition);
                        double lvel = Double.parseDouble(leftVelocity);
                        double rvel = Double.parseDouble(rightVelocity);
                        double head = Double.parseDouble(heading);

                        path.add(new PathStep(lpos, rpos, lvel, rvel, head));
                    }
                }

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
}
