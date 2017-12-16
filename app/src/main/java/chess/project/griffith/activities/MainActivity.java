package chess.project.griffith.activities;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import chess.project.griffith.objects.ChessSquare;
import chess.project.griffith.chess.R;
import chess.project.griffith.pieces.Bishop;
import chess.project.griffith.pieces.BlackPawn;
import chess.project.griffith.pieces.King;
import chess.project.griffith.pieces.Knight;
import chess.project.griffith.pieces.Queen;
import chess.project.griffith.pieces.Rook;
import chess.project.griffith.pieces.WhitePawn;
import chess.project.griffith.views.ChessBoardCustomView;

public class MainActivity extends AppCompatActivity {

   ChessSquare[][] chessBoardSquares = new ChessSquare[8][8];
   ChessBoardCustomView chessBoardCustomView;
   public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBoard();
        chessBoardCustomView.setMainActivity(this);
    }

    private void initBoard() {

        chessBoardCustomView = findViewById(R.id.chessBoardView);
        textView = findViewById(R.id.display);
        //Pawn
        for (int x = 0; x<8 ; x++){ //Loop for pawns
            for(int y = 0; y<8 ; y++) {
                chessBoardSquares[x][y] = new ChessSquare(null);
                chessBoardSquares[x][6] = new ChessSquare(new WhitePawn(getBaseContext(), new Point(x, 6)));
                chessBoardSquares[x][1] = new ChessSquare(new BlackPawn(getBaseContext(), new Point(x, 1)));
            }
        }
        //Bishops
        chessBoardSquares[2][0] = new ChessSquare(new Bishop(getBaseContext(), new Point(2, 0),false));
        chessBoardSquares[5][0] = new ChessSquare(new Bishop(getBaseContext(), new Point(5,0),false));
        chessBoardSquares[2][7] = new ChessSquare(new Bishop(getBaseContext(),new Point(2,7),true));
        chessBoardSquares[5][7] = new ChessSquare(new Bishop(getBaseContext(),new Point(5,7),true));
        //Knights
        chessBoardSquares[1][0] = new ChessSquare(new Knight(getBaseContext(),new Point(1,0),false));
        chessBoardSquares[6][0] = new ChessSquare(new Knight(getBaseContext(),new Point(6,0),false));
        chessBoardSquares[1][7] = new ChessSquare(new Knight(getBaseContext(),new Point(1,7),true));
        chessBoardSquares[6][7] = new ChessSquare(new Knight(getBaseContext(),new Point(6,7),true));
        //Rooks
        chessBoardSquares[0][0] = new ChessSquare(new Rook(getBaseContext(),new Point(0,0),false));
        chessBoardSquares[7][0] = new ChessSquare(new Rook(getBaseContext(),new Point(7,0),false));
        chessBoardSquares[0][7] = new ChessSquare(new Rook(getBaseContext(),new Point(0,7),true));
        chessBoardSquares[7][7] = new ChessSquare(new Rook(getBaseContext(),new Point(7,7),true));

        //King and Queen
        chessBoardSquares[3][0] = new ChessSquare(new Queen(getBaseContext(),new Point(3,0),false));
        chessBoardSquares[4][0] = new ChessSquare(new King(getBaseContext(), new Point(4, 0), false));
        chessBoardSquares[3][7] = new ChessSquare(new Queen(getBaseContext(),new Point(3,7),true));
        chessBoardSquares[4][7] = new ChessSquare(new King(getBaseContext(),new Point(4,7),true));
        chessBoardCustomView.setChessBoardSquares(chessBoardSquares);
    }

    //Method called when reset button is clicked.
    public void resetGame(View view) {
        chessBoardSquares = new ChessSquare[8][8];
        initBoard();
        chessBoardCustomView.init();
    }

}
