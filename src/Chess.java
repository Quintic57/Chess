/**
 * Created by Destin on 8/24/16.
 */
public class Chess
{
    private int[][] board;
    private int[][] backup;

    private static final int RED_PAWN = 1;
    private static final int RED_ROOKE = 2;
    private static final int RED_KNIGHT = 3;
    private static final int RED_BISHOP = 4;
    private static final int RED_QUEEN = 5;
    private static final int RED_KING = 6;

    private static final int BLUE_PAWN = 8;
    private static final int BLUE_ROOKE = 9;
    private static final int BLUE_KNIGHT = 10;
    private static final int BLUE_BISHOP = 11;
    private static final int BLUE_QUEEN = 12;
    private static final int BLUE_KING = 13;

    public Chess()
    {
        board = new int[8][8];
        backup = new int[8][8];

        for(int c = 0; c < board[1].length; c++) {
            board[1][c] = BLUE_PAWN;
            board[6][c] = RED_PAWN;
        }

        board[0][0] = BLUE_ROOKE;
        board[0][7] = BLUE_ROOKE;
        board[0][1] = BLUE_KNIGHT;
        board[0][6] = BLUE_KNIGHT;
        board[0][2] = BLUE_BISHOP;
        board[0][5] = BLUE_BISHOP;
        board[0][3] = BLUE_QUEEN;
        board[0][4] = BLUE_KING;

        board[7][0] = RED_ROOKE;
        board[7][7] = RED_ROOKE;
        board[7][1] = RED_KNIGHT;
        board[7][6] = RED_KNIGHT;
        board[7][2] = RED_BISHOP;
        board[7][5] = RED_BISHOP;
        board[7][3] = RED_QUEEN;
        board[7][4] = RED_KING;

    }

    public Chess(int pieceValue1, String coordinates1, int pieceValue2, String coordinates2)
    {
        board = new int[8][8];
        backup = new int[8][8];

        String letters = "ABCDEFGH";

        String[] mark1 = coordinates1.split("");
        String[] mark2 = coordinates2.split("");

        int pieceRow1 = letters.indexOf(mark1[0]);
        int pieceCol1 = Integer.parseInt(mark1[1]) - 1;
        int pieceRow2 = letters.indexOf(mark2[0]);
        int pieceCol2 = Integer.parseInt(mark2[1]) - 1;

        board[pieceRow1][pieceCol1] = pieceValue1;
        board[pieceRow2][pieceCol2] = pieceValue2;

    }

    public void setPiece(int piece, String coordinates)
    {
        String letters = "ABCDEFGH";

        String[] mark = coordinates.split("");

        int pieceRow = letters.indexOf(mark[0]);
        int pieceCol = Integer.parseInt(mark[1]) - 1;

        board[pieceRow][pieceCol] = piece;
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getBackup() {
        return backup;
    }

    public boolean validMove(int fromRow, int fromCol, int toRow, int toCol)
    {
        if(board[fromRow][fromCol] == RED_PAWN || board[fromRow][fromCol] == BLUE_PAWN)
            if(validPawnMove(fromRow,fromCol,toRow,toCol))
                return kingCheck(fromRow, fromCol, toRow, toCol);

        if(board[fromRow][fromCol] == RED_ROOKE || board[fromRow][fromCol] == BLUE_ROOKE)
            if(validVerticalMove(fromRow,fromCol,toRow,toCol))
                return kingCheck(fromRow, fromCol, toRow, toCol);

        if(board[fromRow][fromCol] == RED_KNIGHT || board[fromRow][fromCol] == BLUE_KNIGHT)
            if(validKnightMove(fromRow,fromCol,toRow,toCol))
                return kingCheck(fromRow, fromCol, toRow, toCol);

        if(board[fromRow][fromCol] == RED_BISHOP || board[fromRow][fromCol] == BLUE_BISHOP)
            if(validDiagonalMove(fromRow,fromCol,toRow,toCol))
                return kingCheck(fromRow, fromCol, toRow, toCol);

        if(board[fromRow][fromCol] == RED_QUEEN || board[fromRow][fromCol] == BLUE_QUEEN)
            if(validDiagonalMove(fromRow, fromCol, toRow, toCol) || validVerticalMove(fromRow,fromCol,toRow,toCol))
                return kingCheck(fromRow, fromCol, toRow, toCol);

        if(board[fromRow][fromCol] == RED_KING || board[fromRow][fromCol] == BLUE_KING)
            if(validKingMove(fromRow,fromCol,toRow,toCol))
                return kingCheck(fromRow, fromCol, toRow, toCol);

        return false;

    }

    private boolean validVerticalMove(int fromRow, int fromCol, int toRow, int toCol)
    {
        if(fromCol != toCol && fromRow != toRow)
            return false;

        if(friendlyFire(fromRow, fromCol, toRow, toCol))
            return false;

        if(toCol > fromCol){
            for (int c = fromCol + 1; c < toCol; c++)
                if (board[fromRow][c] > 0)
                    return false;
        }
        else if(toCol < fromCol){
            for (int c = toCol + 1; c < fromCol; c++)
                if (board[fromRow][c] > 0)
                    return false;
        }
        else if(toRow > fromRow){
            for(int r = fromRow + 1; r < toRow; r++)
                if(board[r][fromCol] > 0)
                    return false;
        }
        else if(toRow < fromRow){
            for(int r = toRow + 1; r < fromRow; r++)
                if(board[r][fromCol] > 0)
                    return false;
        }

        return true;
    }

    private boolean validDiagonalMove(int fromRow, int fromCol, int toRow, int toCol)
    {
        if(Math.abs(fromRow - toRow) != Math.abs(fromCol - toCol))
            return false;

        if(friendlyFire(fromRow,fromCol,toRow,toCol))
            return false;

        if(toRow > fromRow){
            if(toCol > fromCol) {
                for (int r = fromRow + 1, c = fromCol + 1; r < toRow; r++, c++)
                    if (board[r][c] > 0)
                        return false;
            }
            else if(toCol < fromCol) {
                for (int r = fromRow + 1, c = fromCol - 1; r < toRow; r++, c--)
                    if (board[r][c] > 0)
                        return false;
            }
        }

        else if(toRow < fromRow){
            if(toCol > fromCol) {
                for (int r = fromRow - 1, c = fromCol + 1; r > toRow; r--, c++)
                    if (board[r][c] > 0)
                        return false;
            }

            else if(toCol < fromCol) {
                for (int r = fromRow - 1, c = fromCol - 1; r > toRow; r--, c--)
                    if (board[r][c] > 0)
                        return false;
            }
        }

        return true;
    }

    private boolean validPawnMove(int fromRow, int fromCol, int toRow, int toCol)
    {
        if(Math.abs(fromRow - toRow) <= 1 && Math.abs(fromCol - toCol) <= 1) {
            if (board[fromRow][fromCol] < 7 && !friendlyFire(fromRow, fromCol, toRow, toCol)) {
                if ((fromRow > toRow) && (fromCol == toCol) && (board[toRow][toCol] == 0))
                    return true;
                else if ((fromRow > toRow) && (fromCol != toCol) && (board[toRow][toCol] > 0))
                    return true;
            } else if (board[fromRow][fromCol] > 7 && !friendlyFire(fromRow, fromCol, toRow, toCol)) {
                if ((fromRow < toRow) && (fromCol == toCol) && (board[toRow][toCol] == 0))
                    return true;
                else if ((fromRow < toRow) && (fromCol != toCol) && (board[toRow][toCol] > 0))
                    return true;
            }
        }

        else if(Math.abs(fromRow - toRow) <= 2 && fromCol - toCol == 0 && board[toRow][toCol] == 0) {
            if (board[fromRow][fromCol] == RED_PAWN && fromRow == 6)
                return true;
            else if (board[fromRow][fromCol] == BLUE_PAWN && fromRow == 1)
                return true;
        }

        return false;
    }

    private boolean validKnightMove(int fromRow, int fromCol, int toRow, int toCol)
    {
        if(!friendlyFire(fromRow, fromCol, toRow, toCol)) {
            if (Math.abs(fromRow - toRow) == 1 && Math.abs(fromCol - toCol) == 2)
                return true;

            else if (Math.abs(fromRow - toRow) == 2 && Math.abs(fromCol - toCol) == 1)
                return true;
        }

        return false;
    }

    private boolean validKingMove(int fromRow, int fromCol, int toRow, int toCol)
    {
        if(Math.abs(fromRow - toRow) <= 1 && Math.abs(fromCol - toCol) <= 1)
            if(!friendlyFire(fromRow, fromCol, toRow, toCol))
                return true;

        return false;
    }

    private boolean kingCheck(int fromRow, int fromCol, int toRow, int toCol)
    {
        int[][] copy = new int[8][8];

        for(int r = 0; r < board.length; r++)
            for(int c = 0; c < board[r].length; c++)
                copy[r][c] = board[r][c];

        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = 0;

        if((check() == RED_KING && board[toRow][toCol] < 7) || (check() == BLUE_KING && board[toRow][toCol] > 7)) {
            for (int r = 0; r < board.length; r++)
                for (int c = 0; c < board[r].length; c++)
                    board[r][c] = copy[r][c];

            return false;
        }
        else{
            for (int r = 0; r < board.length; r++)
                for (int c = 0; c < board[r].length; c++)
                    board[r][c] = copy[r][c];

            return true;
        }
    }

    public void revertMove()
    {
        for(int r = 0; r < board.length; r++)
            for(int c = 0; c < board[r].length; c++)
                board[r][c] = backup[r][c];
    }

    public int check()
    {
        int redKingRow = 0;
        int redKingCol = 0;
        int blueKingRow = 0;
        int blueKingCol = 0;

        for(int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == RED_KING) {
                    redKingRow = r;
                    redKingCol = c;
                }

                else if (board[r][c] == BLUE_KING) {
                    blueKingRow = r;
                    blueKingCol = c;
                }
            }
        }

        if(diagonalCheck(redKingRow, redKingCol, RED_KING) || verticalCheck(redKingRow, redKingCol, RED_KING) || horseCheck(redKingRow, redKingCol, RED_KING))
            return RED_KING;
        else if(diagonalCheck(blueKingRow, blueKingCol, BLUE_KING) || verticalCheck(blueKingRow, blueKingCol, BLUE_KING) || horseCheck(blueKingRow, blueKingCol, BLUE_KING))
            return BLUE_KING;

        return 0;
    }

    private boolean verticalCheck(int row, int col, int king)
    {
        int rooke = BLUE_ROOKE;
        int queen = BLUE_QUEEN;

        if(king == BLUE_KING){
            rooke = RED_ROOKE;
            queen = RED_QUEEN;
        }

        //N
        for(int r = row - 1; r >= 0; r--)
            if(board[r][col] > 0) {
                if (board[r][col] == rooke || board[r][col] == queen)
                    return true;

                break;
            }

        //S
        for(int r = row + 1; r < board.length; r++)
            if(board[r][col] > 0) {
                if (board[r][col] == rooke || board[r][col] == queen)
                    return true;

                break;
            }

        //W
        for(int c = col - 1; c >= 0; c--)
            if(board[row][c] > 0) {
                if (board[row][c] == rooke || board[row][c] == queen)
                    return true;

                break;
            }


        //E
        for(int c = col + 1; c < board.length; c++)
            if(board[row][c] > 0) {
                if (board[row][c] == rooke || board[row][c] == queen)
                    return true;

                break;
            }

        return false;
    }

    private boolean diagonalCheck(int row, int col, int king)
    {
        int pawn = BLUE_PAWN;
        int bishop = BLUE_BISHOP;
        int queen = BLUE_QUEEN;

        if(king == BLUE_KING){
            pawn = RED_PAWN;
            bishop = RED_BISHOP;
            queen = RED_QUEEN;
        }

        //NW
        for(int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--,c--){
            if(board[r][c] > 0) {
                if (board[r][c] == bishop || board[r][c] == queen)
                    return true;
                else if (board[r][c] == pawn && Math.abs(row - r) == 1 && Math.abs(col - c) == 1 && king == RED_KING)
                    return true;

                break;
            }
        }

        //NE
        for(int r = row - 1, c = col + 1; r >= 0 && c < board.length; r--,c++){
            if(board[r][c] > 0) {
                if (board[r][c] == bishop || board[r][c] == queen)
                    return true;
                else if (board[r][c] == pawn && Math.abs(row - r) == 1 && Math.abs(col - c) == 1 && king == RED_KING)
                    return true;

                break;
            }
        }

        //SW
        for(int r = row + 1,c = col - 1; r < board.length && c >= 0; r++,c--)
        {
            if(board[r][c] > 0) {
                if (board[r][c] == bishop || board[r][c] == queen)
                    return true;
                else if (board[r][c] == pawn && Math.abs(row - r) == 1 && Math.abs(col - c) == 1 && king == BLUE_KING)
                    return true;

                break;
            }
        }

        //SE
        for(int r = row + 1,c = col + 1; r < board.length && c < board.length; r++, c++)
        {
            if(board[r][c] > 0) {
                if (board[r][c] == bishop || board[r][c] == queen)
                    return true;
                else if (board[r][c] == pawn && Math.abs(row - r) == 1 && Math.abs(col - c) == 1 && king == BLUE_KING)
                    return true;

                break;
            }
        }

        return false;
    }

    private boolean horseCheck(int row, int col, int king)
    {
        int knight = BLUE_KNIGHT;

        if(king == BLUE_KING)
            knight = RED_KNIGHT;

        if((row-2 >= 0 && col-1 >= 0 && board[row-2][col-1] == knight) || (row-1 >= 0 && col-2 >= 0 && board[row-1][col-2] == knight))
            return true;

        else if((row-2 >= 0 && col+1 < board.length && board[row-2][col+1] == knight) || (row-1 >= 0 && col+2 < board.length && board[row-1][col+2] == knight))
            return true;

        else if((row+2 < board.length && col-1 >= 0 && board[row+2][col-1] == knight) || (row+1 < board.length && col-2 >= 0 && board[row+1][col-2] == knight))
            return true;

        else if((row+2 < board.length && col+1 < board.length && board[row+2][col+1] == knight) || (row+1 < board.length && col+2 < board.length && board[row+1][col+2] == knight))
            return true;

        return false;
    }

    public boolean checkMate()
    {


        return false;
    }

    private boolean friendlyFire(int fromRow, int fromCol, int toRow, int toCol)
    {
        if((board[fromRow][fromCol] < 7 && board[toRow][toCol] > 7) ||
                (board[fromRow][fromCol] > 7 && board[toRow][toCol] < 7) ||
                    (board[toRow][toCol] == 0))
            return false;

        return true;
    }

    public String toString()
    {
        String output = ("    1   2   3   4   5   6   7   8   " + "\n");
        String coordinates = "ABCDEFGH";

        for(int r = 0; r < board.length; r++) {
            output = output + ("   - - - - - - - - - - - - - - - -" + "\n");
            output = output + coordinates.charAt(r) + " ";

            for (int piece : board[r]) {
                if(piece == 0)
                    output = output + "|" + "   ";
                else
                    output = output + "|" + " " + pieceToString(piece) + " ";
            }

            output = output + "|" + "\n";
        }
        output = output + "   - - - - - - - - - - - - - - - -";

        return output;

    }

    private String pieceToString(int piece)
    {
        String Reset = "\u001B[0m";
        String RedText = "\u001B[31m";
        String BlueText = "\u001B[34m";

        String[] pieces = {"P", "R", "N", "B", "Q", "K"};
        String output = pieces[(piece%7) - 1];

        if(piece < 7)
            return RedText + output + Reset;
        return BlueText + output + Reset;
    }
}
