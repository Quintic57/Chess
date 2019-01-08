/**
 * Created by Destin on 8/30/16.
 */
import java.util.Arrays;
import java.util.Scanner;

public class ChessDriver
{
    public static void main(String[] args)
    {
        Chess game = new Chess();
        Scanner scanner = new Scanner(System.in);

        String currentPlayer;
        int turn = 0;

        System.out.println(game.toString());

        while(!game.checkMate()){
            if(turn % 2 == 0)
                currentPlayer = "\u001B[31m" + "RED" + "\u001B[0m";
            else
                currentPlayer = "\u001B[34m" + "BLUE" + "\u001B[0m";
            System.out.println(currentPlayer + " Player's Turn");

            String move = scanner.nextLine();
            String[] moves = move.split(",");
            String coordinates = "ABCDEFGH";
            String[] fromMark = moves[0].split("");
            String[] toMark = moves[1].split("");

            int fromRow = coordinates.indexOf(fromMark[0]);
            int fromCol = Integer.parseInt(fromMark[1]) - 1;
            int toRow = coordinates.indexOf(toMark[0]);
            int toCol = Integer.parseInt(toMark[1]) - 1;

            while(!game.validMove(fromRow,fromCol,toRow,toCol) || (currentPlayer.contains("RED") && game.getBoard()[fromRow][fromCol] > 7)
                    || (currentPlayer.contains("BLUE") && game.getBoard()[fromRow][fromCol] < 7)) {
                System.out.println("Invalid Move");

                move = scanner.nextLine();
                moves = move.split(",");

                fromMark = moves[0].split("");
                toMark = moves[1].split("");

                fromRow = coordinates.indexOf(fromMark[0]);
                fromCol = Integer.parseInt(fromMark[1]) - 1;
                toRow = coordinates.indexOf(toMark[0]);
                toCol = Integer.parseInt(toMark[1]) - 1;
            }

            for (int r = 0; r < game.getBoard().length; r++)
                for (int c = 0; c < game.getBoard()[r].length; c++)
                    game.getBackup()[r][c] = game.getBoard()[r][c];

            game.getBoard()[toRow][toCol] = game.getBoard()[fromRow][fromCol];
            game.getBoard()[fromRow][fromCol] = 0;

            System.out.println(game.toString());

            if(game.check() > 0)
                System.out.println("CHECK");

            turn++;

        }

//        Chess test = new Chess(4,"A6",13,"C8");
//        test.setPiece(8,"A7");
//
//        System.out.println(test.toString());
//        System.out.println(test.check());
//        System.out.println(test.move("C8","D6"));
//        System.out.println(test.toString());
//        System.out.println(test.check());
    }
}
