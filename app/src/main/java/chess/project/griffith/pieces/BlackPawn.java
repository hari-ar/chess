package chess.project.griffith.pieces;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import chess.project.griffith.chess.R;
import chess.project.griffith.objects.ChessSquare;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public class BlackPawn extends Piece {
    boolean isAlive = true;
    final String pieceId = "bp";
    Context context;
    Point currentPosition = null;

    public BlackPawn(Context context, Point point) {
        this.context = context;
        this.currentPosition = point;
        isWhitePiece = false;
    }

    @Override
    public ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares) {
        isWhiteTurn = false;
        System.out.println("Black Pawn");
        ArrayList<Point> listOfValidPoints = new ArrayList<>();
        if(currentPosition.x == 0 ) //Left most file a-pawn
        {
            if( currentPosition.y == 1){
                if(chessBoardSquares[0][2].getPiece()==null)
                    listOfValidPoints.add(new Point(0,2));//Progressing Ahead
                if(chessBoardSquares[0][3].getPiece()==null)
                    listOfValidPoints.add(new Point(0,3)); // Two Steps ahead
                if(chessBoardSquares[1][2].getPiece()!=null && chessBoardSquares[1][2].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(1,2)); // Crossing Opponent
            }
            else if(currentPosition.y>1 && currentPosition.y<7){ //All Cases except final rank for white
                if(chessBoardSquares[0][currentPosition.y+1].getPiece()==null)
                    listOfValidPoints.add(new Point(0,currentPosition.y+1));
                if(chessBoardSquares[1][currentPosition.y+1].getPiece()!=null && chessBoardSquares[1][currentPosition.y+1].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(1,currentPosition.y+1));
            }
        }
        else if(currentPosition.x ==7){ //Right Most Pawn
            if( currentPosition.y == 1){
                if(chessBoardSquares[7][2].getPiece()==null)
                    listOfValidPoints.add(new Point(7,2));//Progressing Ahead
                if(chessBoardSquares[7][3].getPiece()==null)
                    listOfValidPoints.add(new Point(7,3)); // Two Steps ahead
                if(chessBoardSquares[6][3].getPiece()!=null && chessBoardSquares[6][3].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(6,3)); // Crossing Opponent
            }
            else if(currentPosition.y>1 && currentPosition.y<7){ //All Cases except final rank for white
                if(chessBoardSquares[7][currentPosition.y+1].getPiece()==null)
                    listOfValidPoints.add(new Point(7,currentPosition.y+1));
                if(chessBoardSquares[6][currentPosition.y+1].getPiece()!=null && chessBoardSquares[6][currentPosition.y+1].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(6,currentPosition.y+1));
            }
        }

        else {
            if( currentPosition.y == 1){ //Two Steps
                if(chessBoardSquares[currentPosition.x][2].getPiece()==null)
                    listOfValidPoints.add(new Point(currentPosition.x,2));//Progressing Ahead
                if(chessBoardSquares[currentPosition.x][3].getPiece()==null)
                    listOfValidPoints.add(new Point(currentPosition.x,3)); // Two Steps ahead
                if(chessBoardSquares[currentPosition.x+1][2].getPiece()!=null && chessBoardSquares[currentPosition.x+1][2].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(currentPosition.x+1,2));
                if(chessBoardSquares[currentPosition.x-1][2].getPiece()!=null && chessBoardSquares[currentPosition.x-1][2].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(currentPosition.x-1,2));
            }
            else if(currentPosition.y>1 && currentPosition.y<6){ //All Cases except final rank for black
                if(chessBoardSquares[currentPosition.x][currentPosition.y+1].getPiece()==null)
                    listOfValidPoints.add(new Point(currentPosition.x,currentPosition.y+1));
                if(chessBoardSquares[currentPosition.x-1][currentPosition.y+1].getPiece()!=null && chessBoardSquares[currentPosition.x-1][currentPosition.y+1].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(currentPosition.x-1,currentPosition.y+1));
                if(chessBoardSquares[currentPosition.x+1][currentPosition.y+1].getPiece()!=null && chessBoardSquares[currentPosition.x+1][currentPosition.y+1].getPiece().isWhitePiece())
                    listOfValidPoints.add(new Point(currentPosition.x+1,currentPosition.y+1));
            }
        }
        return listOfValidPoints;

    }

    @Override
    public Point getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public String getPieceId() {
        return pieceId;
    }

    @Override
    public Drawable getDrawable() {
        return context.getResources().getDrawable(R.drawable.bp);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
