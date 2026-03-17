import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Player> battingLineup = new ArrayList<>();
    private static ArrayList<String> fieldingLineup = new ArrayList<>();
    private static int currentBatterIndex = 0;
    private static int inning = 1;
    private static int numOuts = 0;
    private static int homePts = 0;
    private static int awayPts = 0;
    private static int inningScore = 0;
    private static boolean batting;
    private static boolean topInning = true;
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

        while (true) {
            System.out.println("\nHome -  " + homePts + " | Away - " + awayPts + "\nInning: " + inning + " | Number of Outs: " + numOuts + "\n");
            System.out.println("Press B for next batter, O to add an out, H if the home team scores, A if the away team scores and Q to quit.");
            String action = input.nextLine().trim().toUpperCase();
            if (action.equals("Q")) {
                System.out.println("Exiting...");
                System.exit(0);
            } else if (action.equals("B") && batting) {
                nextBatter();
            } else if (action.equals("O")) {
                numOuts++;
                if (numOuts==3) {
                    System.out.println("3 outs - switch sides");
                    nextHalfInning();
                }
            } else if (action.equals("A")) {
                awayPts++;
                inningScore++;
                if (inningScore==5) {
                    System.out.println("Mercy rule - switch sides");
                    nextHalfInning();
                    inningScore = 0;
                }
            } else if (action.equals("H")) {
                homePts++;
                inningScore++; 
                if (inningScore==5) {
                    System.out.println("Mercy rule - switch sides");
                    nextHalfInning();
                    inningScore = 0;
                }
            }
            else {
                System.out.println("Invalid input");
            }
        }
    }

    private static void loadBattingLineup () {
       try {
            Scanner fileScanner = new Scanner(new File("BattingLineup.txt"));
            while (fileScanner.hasNextLine()) {
                String [] info = fileScanner.nextLine().split(",");
                if (info.length>=3) {
                    boolean pitcher;
                    String name = info[0].trim();
                    String gender = info[1].trim();
                    if (info[2].trim().equals("true")) {
                        pitcher = true;
                    }
                    else {
                        pitcher = false;
                    }

                    battingLineup.add(new Player(name, gender, pitcher));
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
        for (int i = currentBatterIndex; i < currentBatterIndex+4; i++) {
            String battingNow = "<- Batting now";
            String onDeck = "<- On deck";
            if (i==currentBatterIndex) {
                System.out.println((i + 1) + ". " + battingLineup.get(i) + battingNow);
            }
            else if (i==currentBatterIndex + 1 ){
                System.out.println((i + 1) + ". " + battingLineup.get(i) + onDeck);
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
            System.out.println("No innings remain. Game over.");
            System.exit(0);
        }
    }

    private static void nextBatter() {
        currentBatterIndex++;
        printBattingOrder();
    }

    private static void nextHalfInning() {
        if (topInning) {
            topInning = false;
        }
        else {
            topInning = true;
            inning++;
        }

        if (batting) {
            batting = false;
            printFieldingLineup();
            numOuts = 0;
        }
        else if (!batting){
            batting = true;
            printBattingOrder();
            numOuts = 0;
        }
    }


}