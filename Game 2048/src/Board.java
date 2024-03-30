import java.util.Iterator;

public class Board implements Iterable<Tile> {

    private Tile[][] tiles;

    public Board(int size) {
        tiles = new Tile[size][size];
        int[][] values = new int[size][size];
        for (int c = 0; c < size; c++) {
            for (int r = 0; r < size; r++) {
                int v = values[c][r];
                Tile t = Tile.createTile(v, c, r);
                tiles[c][r] = t;
            }
        }
    }

    public int getSize() {
        return tiles.length;
    }

    public Tile getTile(int col, int row) {
        return tiles[col][row];
    }

    //Clear the board to empty
    public void clearBoard() {
        int s = tiles.length;
        for (int c = 0; c < s; c++) {
            for (int r = 0; r < s; r++) {
                Tile t = Tile.createTile(0, c, r);
                tiles[c][r] = t;
            }
        }
    }

    public void addTile(Tile t) {
        tiles[t.getCol()][t.getRow()] = t;
    }

    public Tile getNextTile(int col, int row, Tile tile) {
        int c = tile.getCol();
        int r = tile.getRow();
        if (c + col >= getSize() || r + row >= getSize() || c + col < 0 || r + row < 0) {
            return Tile.createTile(-1, c + col, r + row);
        }
        return tiles[c + col][r + row];
    }

    public void printBoard() {
        int s = getSize();
        for (int c = 0; c < s; c++) {
            for (int r = 0; r < s; r++) {
                int v = tiles[c][r].getValue();
                if (v < 10) {
                    System.out.print(v + "    ");
                } else if (v < 100) {
                    System.out.print(v + "   ");
                } else if (v < 1000) {
                    System.out.print(v + "  ");
                } else {
                System.out.print(v + " ");
                }
            }
            System.out.println();
        }
    }

    private class AllTileIterator implements Iterator<Tile>, Iterable<Tile>{
        int r, c;

        AllTileIterator() {
            r = 0;
            c = 0;
        }

        public boolean hasNext() {
            return r < getSize();
        }

        public Tile next() {
            Tile t = getTile(c, r);
            c++;
            if (c == getSize()) {
                c = 0;
                r++;
            }
            return t;
        }

        public Iterator<Tile> iterator() {
            return this;
        }
    }

    public Iterator<Tile> iterator() {
        return new AllTileIterator();
    }
}
