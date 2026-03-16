public class Player {
    String name;
    String position;

    Player (String name) {
        this.name = name;
    }

        Player (String name, String position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return name;
    }
}
