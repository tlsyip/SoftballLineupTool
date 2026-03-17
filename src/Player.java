public class Player {
    String name;
    String position;
    String gender;
    boolean pitcher;

    Player (String name, String gender, Boolean pitcher) {
        this.name = name;
        this.gender = gender;
        this.pitcher = pitcher;
    }

    private String pitcherStatus() {
        if (pitcher) {
            return " (P)";
        }
        else {
            return "";
        }
    }

    @Override
    public String toString() {
        return name + " (" + gender + ")" + pitcherStatus();
    }
}
