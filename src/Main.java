import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Player> battingLineup = new ArrayList<>();
    private static int currentBatterIndex = 0;
    private static int inning = 1;
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        loadBattingLineup();
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
            System.out.println("Batting lineup loaded: " + battingLineup.size() + " players.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}