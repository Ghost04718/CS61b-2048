import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Random;

//--module-path "D:\JavaJDK\javafx-sdk-21.0.2\lib" --add-modules=javafx.base,javafx.controls,javafx.graphics

public class GUI extends Application{

    static final int boardSize = 4;
    static final double proOf2 = 0.9;

    private int score;

    private Model model;
    private Random random;

    private GridPane gridPane;
    private MenuBar menuBar;
    private Menu menu;
    private MenuItem quit;
    private MenuItem newGame;
    private Label scoreLabel;
    private Scene scene;
    private Stage stage;

    private boolean isPlaying;


    public void start(Stage primaryStage) {
        stage = primaryStage;
        newGame();
    }

    private void newGame() {
        model = new Model(boardSize);
        random = new Random();
        score = 0;

        isPlaying = true;
        model.clearModel();
        model.addTile(getValidNewTile());

        setStage();

        if (!model.isGameOver()) {
            model.addTile(getValidNewTile());
        }
        updateBoard();

        VBox vbox = new VBox(menuBar, createScoreLabel(), gridPane);
        VBox.setVgrow(gridPane, Priority.ALWAYS);

        scene = new Scene(vbox, 255, 300); // Set scene size as needed
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.W) {
                model.moveUp();
                updateBoard();
            } else if (event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.S) {
                model.moveDown();
                updateBoard();
            } else if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.A) {
                model.moveLeft();
                updateBoard();
            } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.D) {
                model.moveRight();
                updateBoard();
            } else {
                return;
            }

            score = model.getScore();
            if (!model.isGameOver()) {
                model.addTile(getValidNewTile());
            } else {
                endGame();
            }
            updateBoard();
            updateScoreLabel();
        });

        stage.setTitle("2048 Game");
        stage.setScene(scene);
        stage.show();
    }

    private void setStage() {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        menuBar = new MenuBar();
        menu = new Menu("Menu");
        quit = new MenuItem("Quit");
        newGame = new MenuItem("NEW GAME");
        quit.setOnAction(actionEvent -> System.exit(0));
        newGame.setOnAction(actionEvent -> newGame());
        menu.getItems().addAll(quit, newGame);
        menuBar.getMenus().add(menu);

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Rectangle cell = new Rectangle(50, 50);
                cell.setFill(Color.LIGHTGRAY);
                gridPane.add(cell, j, i);
            }
        }
    }

    private Label createScoreLabel() {
        scoreLabel = new Label("Score: " + score);
        return scoreLabel;
    }

    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + score);
    }

    private void endGame() {
        showAlert("Game Over", "Your score is: " + score);

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("New Game");
        confirmDialog.setHeaderText("Start a New Game?");
        confirmDialog.setContentText("Do you want to start a new game?");
        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                newGame();
            } else {
                System.exit(0);
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    private void updateBoard() {
        gridPane.getChildren().clear();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Rectangle cell = new Rectangle(50, 50);
                int v = model.getTile(i, j).getValue();
                cell.setFill(getColorForValue(v));
                Label label = new Label(v == 0 ? "" : " " + v);
                label.setStyle("-fx-font-size: 20px;");
                gridPane.add(cell, j, i);
                gridPane.add(label, j, i);
            }
        }
    }

    private Color getColorForValue(int value) {
        switch (value) {
            case 0: return Color.LIGHTGRAY;
            case 2: return Color.LIGHTBLUE;
            case 4: return Color.LIGHTGREEN;
            case 8: return Color.LIGHTPINK;
            case 16: return Color.LIGHTCYAN;
            case 32: return Color.LIGHTCORAL;
            case 64: return Color.LIGHTSEAGREEN;
            case 128: return Color.LIGHTSALMON;
            case 256: return Color.LIGHTSKYBLUE;
            case 512: return Color.LIGHTYELLOW;
            case 1024: return Color.LIGHTGOLDENRODYELLOW;
            default: return Color.LIGHTGRAY;
        }
    }
}
