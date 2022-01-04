package starData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class StarFileController {
    private final int NUM_OF_LEVELS = 9;
    private final String[] fileLines = new String[NUM_OF_LEVELS];
    private Scanner fileScanner;

    public void updateStars(int level, int[] newStars) {
        readFileLines();
        String newLine = newStars[0] + " " + newStars[1] + " " + newStars[2];
        fileLines[level - 1] = newLine;

        writeFileLines();
    }

    // read data from fileLines to stars
    public void getAllStars(int[][] stars) {
        readFileLines();  // update fileLines
        final int NUM_STARS = 3;
        for (int i = 0; i < stars.length; ++i) {
            int[] s = new int[NUM_STARS];
            String[] strStars = fileLines[i].split(" ");  // split: "1 0 1" to {1, 0, 1}
            for (int j = 0; j < strStars.length; ++j) {
                s[j] = Integer.parseInt(strStars[j]); // String -> int
            }
            stars[i] = s;
        }
    }

    public void resetAllStars() {
        Arrays.fill(fileLines, "0 0 0");
        writeFileLines();
    }

    private void writeFileLines() {
        try {
            String path = System.getProperty("user.dir");
            FileWriter file = new FileWriter(path + "/src/starData/data.txt", false);
            for (String line: fileLines) {
                file.write(line + "\n");
            }
            file.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read data from data.txt and save it to fileLines
    private void readFileLines() {
        String path = System.getProperty("user.dir");
        File file = new File(path + "/src/starData/data.txt");
        try {
            fileScanner = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (fileScanner.hasNextLine()) {
            fileLines[i] = fileScanner.nextLine();
            ++i;
        }
        fileScanner.close();
    }
}
