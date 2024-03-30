public class Tile {

    private int value;
    private int col;
    private int row;

    private Tile(int value, int col, int row) {
        this.value = value;
        this.row = row;
        this.col = col;
    }

    public int getValue() {
        return this.value;
    }

    public int getCol() {
        return this.col;
    }

    public int getRow() {
        return this.row;
    }

    public static Tile createTile(int value, int col, int row) {
        return new Tile(value, col, row);
    }

    //Move to (col, row) and merge with the empty tile there, return a new empty tile at original place
    public Tile moveTo(int col, int row, Tile t) {
        assert t.value == 0;
        t.value = this.value;
        this.value = 0;
        return t;
    }

    //Move to (col, row) and merge with the tile there, return a new empty tile at original place
    public Tile mergeWith(int col, int row, Tile t) {
        assert t.value == this.value;
        t.value *= 2;
        this.value = 0;
        return t;
    }
}
