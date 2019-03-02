import java.util.Scanner;
/**
 * Tic-Tac-Toe: Two-player console, non-graphics, non-OO version.
 * All variables/methods are declared as static (belong to the class)
 *  in the non-OO version.
 */
public class Main {

    // The game board and the game status
    public static int ROWS = 4, COLS = 4; // number of rows and columns
    public static Cell[][] board;
    //  containing (EMPTY, CROSS, NOUGHT)
    public static int currentState;  // the current state of the game
    // (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
    public static int currentPlayer; // the current player (CROSS or NOUGHT)
    public static int currntRow, currentCol; // current seed's row and column

    public static Scanner in = new Scanner(System.in); // the input Scanner

    /** The entry main method (the program starts here) */
    public static void main(String[] args) {

        // Initialize the game-board and current status
        initGame();
        // Play the game once
        do {
            playerMove(currentPlayer); // update currentRow and currentCol
            updateGame(currentPlayer, currntRow, currentCol); // update currentState
            printBoard();
            // Print message if game-over
            if (currentState == Status.CROSS_WON) {
                System.out.println("'X' won! Bye!");
            } else if (currentState == Status.NOUGHT_WON) {
                System.out.println("'O' won! Bye!");
            } else if (currentState == Status.DRAW) {
                System.out.println("It's a Draw! Bye!");
            }
            // Switch player
            currentPlayer = (currentPlayer == Player.CROSS) ? Player.NOUGHT : Player.CROSS;
        } while (currentState == Status.PLAYING); // repeat if not game-over
    }

    /** Initialize the game-board contents and the current states */
    public static void initGame() {

        System.out.println("Enter the dimension of the game : ");
        int dimension = in.nextInt();
        ROWS = dimension;
        COLS = dimension;
        board = new Cell[ROWS][COLS]; // game board in 2D array

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col]  = new Cell(row,col);
                //board[row][col] = Player.EMPTY;  // all cells empty
            }
        }
        currentState = Status.PLAYING; // ready to play
        currentPlayer = Player.CROSS;  // cross plays first
    }

    /** Player with the "theSeed" makes one move, with input validation.
     Update global variables "currentRow" and "currentCol". */
    public static void playerMove(int theSeed) {
        boolean validInput = false;  // for input validation
        do {
            if (theSeed == Player.CROSS) {
                System.out.print("Player 'X', enter your move (row[1-" + ROWS + "] column[1-" + ROWS + "]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-" + ROWS + "] column[1-" + ROWS + "]): ");
            }
            int row = in.nextInt() - 1;  // array index starts at 0 instead of 1
            int col = in.nextInt() - 1;
            if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col].content == Player.EMPTY) {
                currntRow = row;
                currentCol = col;
                board[currntRow][currentCol].content = theSeed;  // update game-board content
                validInput = true;  // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);  // repeat until input is valid
    }

    /** Update the "currentState" after the player with "theSeed" has placed on
     (currentRow, currentCol). */
    public static void updateGame(int theSeed, int currentRow, int currentCol) {
        if (hasWon(theSeed)) {  // check if winning move
            currentState = (theSeed == Player.CROSS) ? Status.CROSS_WON : Status.NOUGHT_WON;
        } else if (isDraw()) {  // check for draw
            currentState = Status.DRAW;
        }
        // Otherwise, no change to currentState (still PLAYING).
    }

    /** Return true if it is a draw (no more empty cell) */
    // TODO: Shall declare draw if no player can "possibly" win
    public static boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col].content == Player.EMPTY) {
                    return false;  // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no empty cell, it's a draw
    }

    /** Return true if the player with "theSeed" has won after placing at
     (currentRow, currentCol) */
    public static boolean hasWon(int theSeed) {
        return (checkLines(theSeed) || checkColumns(theSeed) || checkDiags(theSeed));
    }

    /** Print the game board */
    public static void printBoard() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col].paint(); // print each of the cells
                if (col != COLS - 1) {
                    System.out.print("|");   // print vertical partition
                }
            }
            System.out.println();
            if (row != ROWS - 1) {
                for(int x = 1 ;x<ROWS;x++) {
                    System.out.print("-----"); // print horizontal partition
                }
                System.out.println("");
            }
        }
        System.out.println();
    }

    static boolean checkLines(int theSeed) {
        boolean goodLine = false;
        for (int line = 0; line < ROWS && !goodLine; line++) {
            goodLine = true;
            for (int i = 0; i < ROWS; i++) {
                if (board[line][i].content != theSeed) {
                    goodLine = false;
                    break;
                }
            }
        }
        return goodLine;
    }

    static boolean checkColumns(int theSeed) {
        boolean goodCol = false;
        for (int col = 0; col < ROWS && !goodCol; col++) {
            goodCol = true;
            for (int i = 0; i < ROWS; i++) {
                if (board[i][col].content != theSeed) {
                    goodCol = false;
                    break;
                }
            }
        }
        return goodCol;
    }

    static boolean checkDiags(int theSeed) {
        boolean goodDiag = true;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][i].content != theSeed) {
                goodDiag = false;
                break;
            }
        }
        if (goodDiag) return true;

        goodDiag = true;
        for (int i = 0, j = ROWS - 1; i < ROWS; i++, j--) {
            goodDiag = true;
            if (board[j][i].content != theSeed) {
                goodDiag = false;
                break;
            }
        }
        return goodDiag;
    }

}