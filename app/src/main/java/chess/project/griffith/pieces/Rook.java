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

public class Rook extends Piece {
    boolean isAlive = true;
    final String pieceId = "wr";
    Context context;

    public Rook(Context context, Point point, boolean isWhitePiece) {
        this.context = context;
        currentPosition = point;
        super.isWhitePiece = isWhitePiece;
    }

    @Override
    public ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares) {
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

        for(int i = 1 ; i < 8 ; i++){ // Move bottom on same row
            int testY = y+i;
            if(testY<=7){
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
        return allValidPositions;
    }

    @Override
    public Drawable getDrawable() {
        if(isWhitePiece)
        {
            return context.getResources().getDrawable(R.drawable.wr);
        }

        else{
            return context.getResources().getDrawable(R.drawable.br);
        }

    }

    @Override
    public Point getCurrentPosition() {
        return currentPosition;
    }


}
