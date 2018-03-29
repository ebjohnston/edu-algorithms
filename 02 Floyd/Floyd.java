import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Floyd-Warshall Algorithm
// Ethan Johnston

// Algorithms - CS 4050 - Fall 2017
// Assignment #05
// Dr. Gerald Shultz

public class Floyd {

    private static Scanner contents;

    private static int size;
    private static int[][] weight;
    private static int[][] vertex;

    public static void main(String[] args) {
        printHeader();
        getFile();
        verifyContents();
        iterateAlgorithm();
    }

    private static void printHeader() {
        System.out.println("");
        System.out.println("=====================");
        System.out.println("  Floyd's Algorithm  ");
        System.out.println("=====================");
        System.out.println("");
    }

    private static void getFile() {
        Scanner console = new Scanner(System.in);
        String filename;
        boolean isValid;

        System.out.print("Please input the filename for the data to be processed: ");
        filename = console.nextLine();

        do {
            try {
                contents = new Scanner(new File(filename));

                // only executes if contents doesn't throw FNF
                isValid = true;
            }
            catch (FileNotFoundException e) {
                isValid = false;

                System.out.println("");
                System.out.print("File not found. Please input a valid filename: ");
                filename = console.nextLine();
            }
        }
        while (!isValid);
    }

    // checks file for validity and stores 2-D int array "weight" if valid
    // if invalid, restarts program from getFile()
    private static void verifyContents() {
        try {
            String buffer;

            buffer = contents.nextLine();
            size = Integer.parseInt(buffer);

            weight = new int[size][size];
            vertex = new int[size][size];

            for (int i = 0; i < size; i++) {

                buffer = contents.nextLine();
                String[] tokens = buffer.split("\\s");

                for (int j = 0; j < size; j++) {
                    weight[i][j] = Integer.parseInt(tokens[j]);
                    vertex[i][j] = 0;
                }
            }
        }
        catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
            System.out.println("");
            System.out.println("Invalid file contents. Restarting...");
            System.out.println("");

            getFile();
            verifyContents();
        }
    }

    private static void iterateAlgorithm() {

        printGrid("0");

        // i - iteration of the algorithm
        // r - row of the grid
        // c - column of the grid

        for (int i = 0; i < size; i++) {

            for (int r = 0; r < size; r++) {
                for (int c = 0; c < size; c++) {

                    if (r == i || c == i) {
                        continue;
                    }

                    if (weight[r][i] < 0 || weight[i][c] < 0) {
                        continue;
                    }

                    if (weight[r][c] > weight[r][i] + weight[i][c] || weight[r][c] < 0) {
                        weight[r][c] = weight[r][i] + weight[i][c];
                        vertex[r][c] = i + 1;
                    }
                }
            }

            printGrid(Integer.toString(i+1));
        }
    }

    private static void printGrid(String name) {
        System.out.println("");
        System.out.println("------------------------------------");
        System.out.println("                D(" + name + ")");
        System.out.println("------------------------------------");
        System.out.println("");

        for (int i = 0; i < size; i++) {
            System.out.println("====================================");

            // print weight row
            for (int j = 0; j < size; j++) {
                if (weight[i][j] >= 0) {
                    System.out.printf("| %-4d ", weight[i][j]);
                }
                else {
                    System.out.print("| -    ");
                }
            }
            System.out.println("|");

            // print vertex row
            for (int j = 0; j < size; j++) {
                System.out.printf("|  %3d ", vertex[i][j]);
            }
            System.out.println("|");
        }

        System.out.println("====================================");
    }
}
