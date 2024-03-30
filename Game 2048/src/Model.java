public class Model{

    private Board board;

    private int score;

    private int[] mergeTimes = {0, 0, 0, 0};

    private int maxScore;

    private boolean gameOver;

    public static final int MAX_PIECE = 2048;

    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    public Tile getTile(int col, int row) {
        return board.getTile(col, row);
    }

    public int getSize() {
        return board.getSize();
    }

    public int getScore() {
        return score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void clearModel() {
        score = 0;
        gameOver = false;
        board.clearBoard();
    }

    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver(board);
    }

    public static boolean emptySpaceExists(Board board) {
        int s = board.getSize();
        for (int c = 0; c < s; c++) {
            for (int r = 0; r < s; r++) {
                if (board.getTile(c, r).getValue() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean maxTileExists(Board board) {
        int s = board.getSize();
        for (int c = 0; c < s; c++) {
            for (int r = 0; r < s; r++) {
                if (board.getTile(c, r).getValue() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean adjacentSameTilesExist(Board board) {
        int s = board.getSize();
        for (int c = 0; c < s; c++) {
            for (int r = 0; r < s; r++) {
                Tile t = board.getTile(c, r);
                if (t.getValue() != 0) {
                    if (c + 1 < s && t.getValue() == board.getNextTile(1, 0, t).getValue()) {
                        return true;
                    }
                    if (r + 1 < s && t.getValue() == board.getNextTile(0, 1, t).getValue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean oneMoveExists(Board board) {
        if (adjacentSameTilesExist(board) && emptySpaceExists(board)) {
            return true;
        }
        return false;
    }

    public boolean checkGameOver(Board board) {
        return maxTileExists(board) || !oneMoveExists(board);
    }

    public boolean isGameOver() {
        checkGameOver(board);
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    private boolean moveHelper(int col, int row, Tile tile) {
        if (tile.getValue() == 0) {
            return false;
        }
        //If can move, move until meet a not empty tile
        int m = 0;
        int j = 0;
        if (col == 0) {
            m = tile.getCol();
        } else if (row == 0) {
            m = tile.getRow();
        }
        if (board.getNextTile(col, row, tile).getValue() == 0) {
            do {
                tile = tile.moveTo(tile.getCol() + col, tile.getRow() + row, board.getNextTile(col, row, tile));
            } while (board.getNextTile(col, row, tile).getValue() == 0);
            moveMergeHelper(col, row, tile, m);
            return true;
        }
        j = moveMergeHelper(col, row, tile, m);
        if (j == 1) {
            return true;
        }
        return false;
    }

    private int moveMergeHelper(int col, int row, Tile tile, int m) {
        if (tile.getValue() == board.getNextTile(col, row, tile).getValue() && mergeTimes[m] == 0) {
            score += tile.getValue();
            tile.mergeWith(tile.getCol() + col, tile.getRow() + row, board.getNextTile(col, row, tile));
            mergeTimes[m] += 1;
            return 1;
        }
        return 0;
    }

    private boolean moveUpHelper(Tile tile) {
        if (tile.getCol() == 0) {
            return false;
        }
        return moveHelper(-1, 0, tile);
    }

    public boolean moveUp() {
        return moveDirection(0, 0, -1, 0);
    }

    private boolean moveDownHelper(Tile tile) {
        int s = board.getSize();
        if (tile.getCol() == s - 1) {
            return false;
        }
        return moveHelper(+1, 0, tile);
    }

    public boolean moveDown() {
        int s = board.getSize();
        return moveDirection(s - 1, 0, +1, 0);
    }

    public boolean moveRight() {
        return moveDirection(0, 0, 0, +1);
    }

    private boolean moveRightHelper(Tile tile) {
        int s = board.getSize();
        if (tile.getRow() == s - 1) {
            return false;
        }
        return moveHelper(0, +1, tile);
    }

    public boolean moveLeft() {
        int s = board.getSize();
        return moveDirection(s - 1, 0, 0, -1);
    }

    private boolean moveLeftHelper(Tile tile) {
        if (tile.getRow() == 0) {
            return false;
        }
        return moveHelper(0, -1, tile);
    }

    private boolean moveDirection(int first, int second, int col, int row) {
        boolean changed;
        changed = false;
        int n = 0;

        int s = board.getSize();

        if (first == s - 1 && row == -1) {
            for (int r = 0; r < s; r++) {
                for (int c = 0; c < s; c++) {
                    Tile t = board.getTile(c, r);
                    changed = moveLeftHelper(t);
                    if (changed) {
                        n++;
                    }
                }
            }
        }

        if (first == 0 && row == +1) {
            for (int r = s - 1; r >= 0; r--) {
                for (int c = 0; c < s; c++) {
                    Tile t = board.getTile(c, r);
                    changed = moveRightHelper(t);
                    if (changed) {
                        n++;
                    }
                }
            }
        }

        if (first == s - 1 && col == +1) {
            for (int c = s - 1; c >= 0; c--) {
                for (int r = 0; r < s; r++) {
                    Tile t = board.getTile(c, r);
                    changed = moveDownHelper(t);
                    if (changed) {
                        n++;
                    }
                }
            }
        }

        if (first == 0 && col == -1) {
            for (int c = 0; c < s; c++) {
                for (int r = 0; r < s; r++) {
                    Tile t = board.getTile(c, r);
                    changed = moveUpHelper(t);
                    if (changed) {
                        n++;
                    }
                }
            }
        }

        for (int i = 0; i < mergeTimes.length; i++) {
            mergeTimes[i] = 0;
        }

        if (n > 0) {
            changed = true;
        }
        checkGameOver(board);
        return changed;
    }

    public void print() {
        board.printBoard();
        System.out.println("Score: " + score);
    }
}
