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

public class Queen extends Piece {
    Context context;

    public Queen(Context context, Point point, boolean isWhitePiece) {
        this.context = context;
        this.currentPosition = point;
        super.isWhitePiece = isWhitePiece;
        if(isWhitePiece)
            setPieceId("wq");
        else
            setPieceId("bq");
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
        for(int i = 1 ; i < 8 ; i++){ // Move left on same column
            int testX = x-i;
            if(testX>=0){
                if(chessBoardSquares[testX][y].isEmpty()){
                    allValidPositions.add(new Point(testX,y));
                }
                else{
                    if(isWhitePiece && !chessBoardSquares[testX][y].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,y));
                    }
                    else if (!isWhitePiece && chessBoardSquares[testX][y].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,y));
                    }
                    break;
                }
            }
        }
        for(int i = 1 ; i < 8 ; i++){ // Move right on same column
            int testX = x+i;
            if(testX<=7){
                if(chessBoardSquares[testX][y].isEmpty()){
                    allValidPositions.add(new Point(testX,y));
                }
                else{
                    if(isWhitePiece && !chessBoardSquares[testX][y].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,y));
                    }
                    else if (!isWhitePiece && chessBoardSquares[testX][y].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(testX,y));
                    }
                    break;
                }
            }
        }

        for(int i = 1 ; i < 8 ; i++){ // Move up on same row
            int testY = y-i;
            if(testY>=0){
                if(chessBoardSquares[x][testY].isEmpty()){
                    allValidPositions.add(new Point(x,testY));
                }
                else{
                    if(isWhitePiece && !chessBoardSquares[x][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(x,testY));
                    }
                    else if (!isWhitePiece && chessBoardSquares[x][testY].getPiece().isWhitePiece)
                    {
                        allValidPositions.add(new Point(x,testY));
                    }
                    break;
                }
            }
        }

        for(int i = 1 ; i < 8 ; i++) { // Move bottom on same row
            int testY = y + i;
            if (testY <= 7) {
                if (chessBoardSquares[x][testY].isEmpty()) {
                    allValidPositions.add(new Point(x, testY));
                } else {
                    if (isWhitePiece && !chessBoardSquares[x][testY].getPiece().isWhitePiece) {
                        allValidPositions.add(new Point(x, testY));
                    } else if (!isWhitePiece && chessBoardSquares[x][testY].getPiece().isWhitePiece) {
                        allValidPositions.add(new Point(x, testY));
                    }
                    break;
                }
            }
        }
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
    public Drawable getDrawable(){
        if(isWhitePiece)
        {
            return context.getResources().getDrawable(R.drawable.wq);
        }

        else{
            return context.getResources().getDrawable(R.drawable.bq);
        }
    };

}
