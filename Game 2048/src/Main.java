import java.util.Random;
import java.util.Scanner;

//Game without GUI
public class Main {

    static final int boardSize = 4;
    static final double tile2Probability = 0.9;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Model model = new Model(boardSize);
        Random random = new Random();
        Game game = new Game(model, random, tile2Probability, scanner);


        try {
            while (game.playing()) {
                game.playGame();
            }
        } catch (IllegalStateException excp) {
            System.exit(1);
        }

        scanner.close();
        System.exit(0);
    }
}