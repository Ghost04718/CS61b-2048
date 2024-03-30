import java.util.Random;
import java.util.Scanner;

public class Game {

    private boolean playing;

    private Model model;

    private Random random;

    private Double proOf2;

    private Scanner scanner;

    public Game(Model m, Random r, double p, Scanner s) {
        model = m;
        random = r;
        proOf2 = p;
        scanner = s;
        playing = true;
    }

    boolean playing() {
        return playing;
    }

    private Tile getValidNewTile() {
        while (true) {
            Tile t = getNewTile(model.getSize());
            if (model.getTile(t.getCol(), t.getRow()).getValue() == 0) {
                return t;
            }
        }
    }

    private Tile getNewTile(int size) {
        int c = random.nextInt(size), r = random.nextInt(size);
        int v = random.nextDouble() <= proOf2 ? 2 : 4;
        return Tile.createTile(v, c, r);
    }

    void playGame() {
        model.clearModel();
        model.addTile(getValidNewTile());

        while (playing) {
            if (!model.isGameOver()) {
                model.addTile(getValidNewTile());
            }
            model.print();

            boolean moved;
            moved = false;
            while (!moved) {
                String cmnd = getKey();
                switch (cmnd) {
                    case "Quit":
                        playing = false;
                        System.exit(0);
                        return;
                    case "New Game":
                        return;
                    case "Up":
                        if (!model.isGameOver() && model.moveUp()) {
                            moved = true;
                        }
                        break;
                    case "Down":
                        if (!model.isGameOver() && model.moveDown()) {
                            moved = true;
                        }
                        break;
                    case "Left":
                        if (!model.isGameOver() && model.moveLeft()) {
                            moved = true;
                        }
                        break;
                    case "Right":
                        if (!model.isGameOver() && model.moveRight()) {
                            moved = true;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        model.print();
    }

    public String getKey() {
        String command = scanner.nextLine();
        switch (command) {
            case "↑" : case "w" :
                command = "Up";
                break;
            case "→" : case "d" :
                command = "Right";
                break;
            case "↓" : case "s" :
                command = "Down";
                break;
            case "←" : case "a" :
                command = "Left";
                break;
            default :
                break;
        }
        return command;
    }

    public Tile getTile(int col, int row) {
        return model.getTile(col, row);
    }
}
