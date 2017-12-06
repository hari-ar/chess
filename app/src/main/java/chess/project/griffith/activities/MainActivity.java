package chess.project.griffith.activities;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import chess.project.griffith.objects.ChessSquare;
import chess.project.griffith.chess.R;
import chess.project.griffith.pieces.BlackBishop;
import chess.project.griffith.pieces.BlackKing;
import chess.project.griffith.pieces.BlackKnight;
import chess.project.griffith.pieces.BlackPawn;
import chess.project.griffith.pieces.BlackQueen;
import chess.project.griffith.pieces.BlackRook;
import chess.project.griffith.pieces.WhiteBishop;
import chess.project.griffith.pieces.WhiteKing;
import chess.project.griffith.pieces.WhiteKnight;
import chess.project.griffith.pieces.WhitePawn;
import chess.project.griffith.pieces.WhiteQueen;
import chess.project.griffith.pieces.WhiteRook;
import chess.project.griffith.views.ChessBoardCustomView;

public class MainActivity extends AppCompatActivity {

   ChessSquare[][] chessBoardSquares = new ChessSquare[8][8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBoard();
    }

    private void initBoard() {

        ChessBoardCustomView chessBoardCustomView = findViewById(R.id.chessBoardView);
        //Pawn
        for (int x = 0; x<8 ; x++){ //Loop for pawns
            for(int y = 0; y<8 ; y++) {
                chessBoardSquares[x][y] = new ChessSquare(null);
                chessBoardSquares[x][6] = new ChessSquare(new WhitePawn(getBaseContext(), new Point(x, 6)));
                chessBoardSquares[x][1] = new ChessSquare(new BlackPawn(getBaseContext(), new Point(x, 1)));
            }
        }
        //Bishops
        chessBoardSquares[2][0] = new ChessSquare(new BlackBishop(getBaseContext(),new Point(2,0)));
        chessBoardSquares[5][0] = new ChessSquare(new BlackBishop(getBaseContext(),new Point(5,0)));
        chessBoardSquares[2][7] = new ChessSquare(new WhiteBishop(getBaseContext(),new Point(2,7)));
        chessBoardSquares[5][7] = new ChessSquare(new WhiteBishop(getBaseContext(),new Point(5,7)));
        //Knights
        chessBoardSquares[1][0] = new ChessSquare(new BlackKnight(getBaseContext(),new Point(1,0)));
        chessBoardSquares[6][0] = new ChessSquare(new BlackKnight(getBaseContext(),new Point(6,0)));
        chessBoardSquares[1][7] = new ChessSquare(new WhiteKnight(getBaseContext(),new Point(1,7)));
        chessBoardSquares[6][7] = new ChessSquare(new WhiteKnight(getBaseContext(),new Point(6,7)));
        //Rooks
        chessBoardSquares[0][0] = new ChessSquare(new BlackRook(getBaseContext(),new Point(0,0)));
        chessBoardSquares[7][0] = new ChessSquare(new BlackRook(getBaseContext(),new Point(7,0)));
        chessBoardSquares[0][7] = new ChessSquare(new WhiteRook(getBaseContext(),new Point(0,7)));
        chessBoardSquares[7][7] = new ChessSquare(new WhiteRook(getBaseContext(),new Point(7,7)));

        //King and Queen
        chessBoardSquares[3][0] = new ChessSquare(new BlackQueen(getBaseContext(),new Point(3,0)));
        chessBoardSquares[4][0] = new ChessSquare(new BlackKing(getBaseContext(),new Point(4,0)));
        chessBoardSquares[3][7] = new ChessSquare(new WhiteQueen(getBaseContext(),new Point(3,7)));
        chessBoardSquares[4][7] = new ChessSquare(new WhiteKing(getBaseContext(),new Point(4,7)));
        chessBoardCustomView.setChessBoardSquares(chessBoardSquares);
    }

    public void resetGame(View view) {
    }
}
