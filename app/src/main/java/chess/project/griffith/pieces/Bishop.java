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

public class Bishop extends Piece {
    boolean isAlive = true;
    Context context;


    public Bishop(Context context, Point point, boolean isWhitePiece) {
        this.context = context;
        currentPosition = point;
        super.isWhitePiece = isWhitePiece;
        if(isWhitePiece)
            pieceId = "wb";
        else
            pieceId = "bb";
    }

    @Override
    public ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares) {
        return super.filterInvalidPositions(chessBoardSquares,getAllPossiblePositions(chessBoardSquares),this);
    }

    @Override
    public ArrayList<Point> getAllPossiblePositions(ChessSquare[][] chessBoardSquares) {
        ArrayList<Point> allValidPositions = new ArrayList<>();
        int x = currentPosition.x;
        int y = currentPosition.y;
        for(int i = 1 ; i < 8 ; i++){ //Top left
            int testX = x-i;
            int testY = y-i;
            if( testX>=0 && testY >=0){
                if(chessBoardSquares[testX][testY].isEmpty()){
                    allValidPositions.add(new Point(testX,testY));
                }
                else{
                    if(isWhitePiece && !chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    else if (!isWhitePiece && chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    break;
                }
            }
        }

        for(int i = 1 ; i < 8 ; i++){ //Top Right
            int testX = x+i;
            int testY = y-i;
            if( testX<=7 && testY >=0){
                if(chessBoardSquares[testX][testY].isEmpty()){
                    allValidPositions.add(new Point(testX,testY));
                }
                else{
                    if(isWhitePiece && !chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    else if (!isWhitePiece && chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    break;
                }
            }
        }

        for(int i = 1 ; i < 8 ; i++){ //Bottom Right
            int testX = x+i;
            int testY = y+i;
            if( testX<=7 && testY <=7){
                if(chessBoardSquares[testX][testY].isEmpty()){
                    allValidPositions.add(new Point(testX,testY));
                }
                else{
                    if(isWhitePiece && !chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    else if (!isWhitePiece && chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    break;
                }
            }
        }

        for(int i = 1 ; i < 8 ; i++){ //Bottom Left
            int testX = x-i;
            int testY = y+i;
            if( testX>=0 && testY <=7){
                if(chessBoardSquares[testX][testY].isEmpty()){
                    allValidPositions.add(new Point(testX,testY));
                }
                else{
                    if(isWhitePiece && !chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    else if (!isWhitePiece && chessBoardSquares[testX][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,testY));
                    }
                    break;
                }
            }
        }
        return allValidPositions;
    }


    @Override
    public Drawable getDrawable() {
        if(isWhitePiece)
        {
            return context.getResources().getDrawable(R.drawable.wb);
        }
        else{
            return context.getResources().getDrawable(R.drawable.bb);
        }
    }
}
