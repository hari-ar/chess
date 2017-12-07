package chess.project.griffith.pieces;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import chess.project.griffith.objects.ChessSquare;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public abstract class Piece {

    public abstract ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares);
    public abstract  Drawable getDrawable();
    boolean isWhitePiece;
    Point currentPosition;


    public Point getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;

    }

    public ArrayList<Point> filterInvalidPositions(final ChessSquare[][] chessBoardSquares, ArrayList<Point> allPoints, final Piece selectedPiece){
        ChessSquare[][] testBoard = new ChessSquare[8][8];
        for(int i =0 ; i<allPoints.size() ; i++){
         Point testPosition = allPoints.get(0);
         for(int j=0; j<8;j++){
             for(int k=0;k<8;k++){
                 if((j==testPosition.x && k==testPosition.y) )
                 {
                     testBoard[j][k] = new ChessSquare(selectedPiece);
                 }
                 else if ((j==currentPosition.x && k==currentPosition.y)){
                     testBoard[j][k] = new ChessSquare(null);
                 }
                 else{
                     testBoard[j][k] = chessBoardSquares[j][k];
                 }
                 if(isKinginCheck(testBoard)){

                 }
             }
         }
     }
     return null;
    }

    private boolean isKinginCheck(ChessSquare[][] testBoard) {
        return false;
    }






    public boolean isWhitePiece() {
        return isWhitePiece;
    }

}
