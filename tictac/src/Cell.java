public class Cell {

    int content;
    int row, col;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();  // clear content
    }

    public void clear() {
        content = Player.EMPTY;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public void paint() {
        switch (content) {
            case Player.CROSS:  System.out.print(" X "); break;
            case Player.NOUGHT: System.out.print(" O "); break;
            case Player.EMPTY:  System.out.print("   "); break;
        }
    }
}