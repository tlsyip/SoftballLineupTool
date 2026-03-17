import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Player> battingLineup = new ArrayList<>();
    private static ArrayList<String> fieldingLineup = new ArrayList<>();
    private static int currentBatterIndex = 0;
    private static int inning = 1;
    private static boolean batting;
    private static Scanner input = new Scanner(System.in);


    public static void main(String[] args) {
        //load lineups
        loadBattingLineup();
        loadFieldingLineup();

        // initial lineup
        System.out.println("Enter side: Home/Away"); //determine which lineup to print first
        String side = (input.nextLine());

        if (side.equalsIgnoreCase("Home")) { //home team = fielding first
            batting = false;
            printFieldingLineup();
        }
        else if (side.equalsIgnoreCase("Away")) { //away team = batting first
            batting = true;
            printBattingOrder();
        }
        else {
            System.out.println("Invalid input. Quitting program."); //invalid input
            System.exit(0);
        }

        System.out.println("Press B for next batter, I for next half inning, Q to quit.");

        String action = input.nextLine().trim().toUpperCase();
            if (action.equals("Q")) {
                System.out.println("Exiting...");
            } else if (action.equals("I")) {
                nextHalfInning();
            } else if (action.equals("B")) {
                // nextBatter();
            } else {
                System.out.println("Invalid input. Use B, I, or Q.");
            }
    }

    private static void loadBattingLineup () {
       try {
            Scanner fileScanner = new Scanner(new File("BattingLineup.txt"));
            while (fileScanner.hasNextLine()) {
                String name = fileScanner.nextLine().trim();
                if (!name.isEmpty()) {
                    battingLineup.add(new Player(name));
                }
            }
            fileScanner.close();
            System.out.println("Batting lineup loaded: " + battingLineup.size() + " players found.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void loadFieldingLineup() {
        try {
            Scanner fileScanner = new Scanner(new File("FieldingLineup.txt"));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    fieldingLineup.add(line);
                }
            }
            fileScanner.close();
            System.out.println("Fielding lineup loaded: " + fieldingLineup.size() + " innings found.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printBattingOrder() {
        System.out.println("\nCurrent Batting Order:");
        for (int i = 0; i < currentBatterIndex+3; i++) {
            String marker = "<- Next up";
            if (i==currentBatterIndex) {
                System.out.println((i + 1) + ". " + battingLineup.get(i) + marker);
            }
            else {
                System.out.println((i + 1) + ". " + battingLineup.get(i));
            }
        }
    }

    private static void printFieldingLineup() {
        System.out.println("\nFielding Lineup for Inning " + inning + ":\n");
        if (inning - 1 < fieldingLineup.size()) {
            String line = fieldingLineup.get(inning - 1);
            String[] positions = line.split(",");
            for (String pos : positions) {
                System.out.println(pos.trim());
            }
        } else {
            System.out.println("No fielding lineup for this inning.");
        }
    }

    private static void nextHalfInning() {
        if (batting) {
            batting = false;
            printFieldingLineup();
        }
        else {
            printBattingOrder();
        }
    }


}