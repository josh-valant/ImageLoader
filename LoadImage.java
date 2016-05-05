/*
Testing Acceptance Criteria
- when given an invalid file path a FileNotFoundException should be thrown.
- when given a file with valid data, it should return an image with the right number of rows and columns, and the correct name and data numbers.
- when given a file with rows or columns not parseable as ints, a NumberFormatException should be thrown.
- when given a file with a data number not parseable as a short, a NumberFormatException should be thrown.


Developer Notes

Design explanation:
There could be some value in reading in invalid data and letting the consumer examine that, 
so the code doesn't throw an exception if it can still store the numbers from the file.
If the numbers aren't parseable, an exception is thrown, because I didn't want to store that string anywhere outside of the int or short fields.
Used a short[] because bytes have a max value of 127.
Assuming all Images should be treated as different, so not overriding hashCode or equals.

Please make sure to upload your project to GitHub.
*/

import java.io.*;
import java.util.*;

class Image
{
    // making fields private
    private final int rows;
    private final int columns;
    private final String name;
    private final short[][] data;

    // adding constructor to for easier construction
    public Image(final String name, final int rows, final int cols, final short[][] data) {
        // Assuming we don't want to validate that the rows and columns match the actual data, nor anything else about the data, 
        // since there's not much we can do in that case and maybe someone will actually want to use loadImageFromFile for malformed data.
        this.name = name;
        this.rows = rows;
        this.columns = cols;
        this.data = data;
    }

    // accessor methods for easier mocking
    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public String getName() { return name; }
    public short[][] getData() { return data; }

    @Override
    public String toString() {
        return String.format("Image['%s', %s rows x %s columns]", name, rows, columns);
    }

    // Assuming all Images should be treated as different, so not overriding hashCode or equals

    static public Image loadImageFromFile(final String filename) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String name = br.readLine();
            String[] rowsCols = br.readLine().split(" ");

            List<short[]> stringRows = new ArrayList<short[]>();

            String line = null;
            while ((line = br.readLine()) != null) {

                String[] stringNums = line.trim().split(", ");
                short[] nums = new short[stringNums.length];
                for (int i = 0; i < stringNums.length; i++) {
                    nums[i] = Short.parseShort(stringNums[i]);
                }

                stringRows.add(nums);
            }
            short[][] data = new short[stringRows.get(0).length][stringRows.size()];
            for (int i = 0; i < stringRows.size(); i++) {
                data[i] = stringRows.get(i);
            }
            return new Image(name, Integer.parseInt(rowsCols[0]), Integer.parseInt(rowsCols[1]), data);
        }
    }
}
