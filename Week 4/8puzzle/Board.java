public class Board {
    private int board[][];

    public Board(int[][] tiles) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    public String toString() {
        String ans = Integer.toString(this.dimension());

        for (int i = 0; i < this.dimension(); i++) {
            ans += "\n";
            for (int j = 0; j < this.dimension(); j++) {
                ans += Integer.toString(board[i][j]) + " ";
            }
        }

        return ans;
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int numWrong = 0;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != this.dimension() * i + (j + 1)) {
                    numWrong += 1;
                }
            }
        }

        return numWrong;
    }

    public int manhattan() {
        
    }

    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != board.length * i + (j + 1)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean equals(Object y) {

    }

    public Iterable<Board> neighbors() {

    }

    public Board twin() {

    }

    public static void main(String[] args) {

    }
}
