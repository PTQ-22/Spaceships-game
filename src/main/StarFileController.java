package main;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class StarFileController {
    private final int NUM_OF_LEVELS = 9;
    private final String[] fileLines = new String[NUM_OF_LEVELS];

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
            FileWriter file = new FileWriter(path + "/data.txt", false);
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
        File file = new File(path + "/data.txt");
        try {
            Scanner fileScanner = new Scanner(file);
            int i = 0;
            while (fileScanner.hasNextLine()) {
                fileLines[i] = fileScanner.nextLine();
                ++i;
            }
            fileScanner.close();
        }
        catch (FileNotFoundException e) {
            try {
                file.createNewFile();
                resetAllStars();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
