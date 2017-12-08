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

public class Knight extends Piece {
    boolean isAlive = true;
    Context context;

    public Knight(Context context, Point point, boolean isWhitePiece) {
        this.context = context;
        currentPosition = point;
        super.isWhitePiece = isWhitePiece;
        if(isWhitePiece)
            setPieceId("wn");
        else
            setPieceId("bn");
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
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(Math.abs(x-i)==2 && Math.abs(y-j)==1){
                    if(chessBoardSquares[i][j].isEmpty())
                        allValidPositions.add(new Point(i,j));
                    else{
                        if(!isWhitePiece && chessBoardSquares[i][j].getPiece().isWhitePiece) //Adds highlight of white pieces for black knight
                        {
                            allValidPositions.add(new Point(i,j));
                        }
                        if(isWhitePiece && !chessBoardSquares[i][j].getPiece().isWhitePiece) //Adds highlight of white pieces for black knight
                        {
                            allValidPositions.add(new Point(i,j));
                        }
                    }
                }
                if(Math.abs(x-i)==1 && Math.abs(y-j)==2){
                    if(chessBoardSquares[i][j].isEmpty())
                        allValidPositions.add(new Point(i,j));
                    else{
                        if(!isWhitePiece && chessBoardSquares[i][j].getPiece().isWhitePiece) //Adds highlight of white pieces for black knight
                        {
                            allValidPositions.add(new Point(i,j));
                        }
                        if(isWhitePiece && !chessBoardSquares[i][j].getPiece().isWhitePiece) //Adds highlight of white pieces for black knight
                        {
                            allValidPositions.add(new Point(i,j));
                        }
                    }
                }
            }

        }
        return allValidPositions;
    }


    @Override
     public Drawable getDrawable(){
        if(isWhitePiece)
        {
            return context.getResources().getDrawable(R.drawable.wn);
        }

        else{
            return context.getResources().getDrawable(R.drawable.bn);
        }
    }



}
