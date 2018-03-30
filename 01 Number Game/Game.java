import java.util.InputMismatchException;
import java.util.Scanner;

// Number-Picking Game
// Ethan Johnston

// Algorithms - CS 4050 - Fall 2017
// Assignment #04
// Dr. Gerald Shultz

public class Game {

    private static int size;
    private static int playerScore = 0, botScore = 0;
    private static int[] values;
    private static int[][] path;
    private static int[][] direction;

    private static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        printHeader();
        getInput();
        playGame();
    }

    private static void printHeader() {
        System.out.println("");
        System.out.println("=====================");
        System.out.println("     Number Game     ");
        System.out.println("=====================");
        System.out.println("");
    }

    private static void getInput() {
        String filename;
        boolean isValid;

        do {
            try {
                System.out.println("");
                System.out.print("How many numbers are in this game?: ");
                size = console.nextInt();
                console.nextLine(); // consume newline fron input

                System.out.print("Provide these numbers in order: ");
                String inputString = console.nextLine();
                String[] valueStrings = inputString.split("\\s");

                if (valueStrings.length != size) {
                    isValid = false;

                    System.out.println("");
                    System.out.println("Number of values does not match previous input...");
                }
                else {
                    values = new int[size];

                    for (int i = 0; i < size; i++) {
                        values[i] = Integer.parseInt(valueStrings[i]);
                    }

                    System.out.println("");
                    isValid = true;
                }
            }
            catch (InputMismatchException | NumberFormatException ex) {
                isValid = false;

                System.out.println("");
                System.out.println("Invalid input. Please provide only integers...");

                console.nextLine(); // consume newline fron input
            }
        }
        while (!isValid);
    }

    private static void playGame() {
        do {
            buildTable();
            printTable();

            getPlayerMove();

            if (size <= 0) {
                break;
            }

            buildTable();
            printTable();

            getBotMove();

            if (size <= 0) {
                break;
            }
        }
        while(true);

        System.out.println("Your final score is: " + playerScore);
        System.out.println("My final score is: " + botScore);
        System.out.println("");

        if (playerScore > botScore) {
            System.out.println("Congratulations! You win!");
        }
        else if (botScore > playerScore) {
            System.out.println("I win this round. Better luck next time!");
        }
        else {
            System.out.println("It's a tie!");
        }
    }

    private static void buildTable() {
        path = new int[size][size];
        direction = new int[size][size];

        // initialize table
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                path[i][j] = 0;
                direction[i][j] = 0;
            }
        }

        // set base cases
        for (int i = 0; i < size; i++) {
            path[i][i] = values[i];
        }

        // set up first iteration with directions
        for (int i = 0; i < size - 1; i++) {
            if(path[i][i] < path[i+1][i+1]) {
                path[i][i+1] = path[i+1][i+1];
                direction[i][i+1] = -1;
            }
            else {
                path[i][i+1] = path[i][i];
                direction[i][i+1] = 1;
            }
        }

        // do rest of iterations

        // process the diagonals of all but the first two columns
        for (int i = 0; i < size - 2; i++) {
            // process each element in the diagonal
            for (int j = 0; j < size - 2 - i; j++) {
                if (path[j][(i+1)+j] < path[j+1][(i+2)+j] ) { // take last
                    direction[j][(i+2)+j] = -1;

                    if (direction[j][(i+1)+j] == -1) { // follow "take last"
                        path[j][(i+2)+j] = path[j][i+j] + values[(i+2)+j];
                    }
                    else { // follow "take first"
                        path[j][(i+2)+j] = path[j+1][(i+1)+j] + values[(i+2)+j];
                    }
                }
                else { // take first
                    direction[j][(i+2)+j] = 1;

                    if (direction[j+1][(i+2)+j] == -1) { // follow "take last"
                        path[j][(i+2)+j] = path[j+1][(i+1)+j] + values[(i+2)+j];
                    }
                    else { // follow "take first"
                        path[j][(i+2)+j] = path[j+2][(i+2)+j] + values[j];
                    }
                }
            }
        }
    }

    private static void printTable() {
        System.out.print("     ");
        for (int i = 0; i < size; i++) {
            System.out.print("   " + values[i] + "  ");
        }
        System.out.println("");
        System.out.println("=========================================");

        for (int i = 0; i < size; i++) {
            System.out.print(" " + values[i] + " | ");
            for (int j = 0; j < size; j++) {
                System.out.printf("%3d", path[i][j]);

                if (direction[i][j] == 0) {
                    System.out.print(",- ");
                }
                else if (direction[i][j] == 1) {
                    System.out.print(",F ");
                }
                else {
                    System.out.print(",L ");
                }
            }
            System.out.println("");
        }
    }

    private static void getPlayerMove() {
        do {
            System.out.println("");

            System.out.print("Do you select the first element or the last? (f/l): ");
            String choice = console.nextLine();

            System.out.println("");

            if (choice.equalsIgnoreCase("f")) {
                playerScore += values[0];
                removeElement("f");
                break;
            }
            else if (choice.equalsIgnoreCase("l")) {
                playerScore += values[values.length - 1];
                removeElement("l");
                break;
            }
            else {
                System.out.println("Invalid input. First or last element? (f/l): ");
            }
        }
        while(true);
    }

    private static void getBotMove() {
        int choice = direction[0][direction[0].length - 1];

        System.out.println("");

        if (choice > 0) {
            botScore += values[0];
            System.out.println("I have selected the first element...");
            removeElement("f");
        }
        else if (choice < 0) {
            botScore += values[values.length - 1];
            System.out.println("I have selected the last element...");
            removeElement("l");
        }
        else {
            botScore += values[0];
            System.out.println("I have selected the only element...");
            removeElement("f");
        }

        System.out.println("");
    }

    private static void removeElement(String location) {
        int[] buffer = new int[values.length - 1];
        size--;

        if (location.equals("f")) { // remove first
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = values[i+1];
            }
        }
        else { // remove last
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = values[i];
            }
        }

        values = buffer;
    }
}
