package chess.project.griffith.pieces;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import chess.project.griffith.chess.R;
import chess.project.griffith.objects.ChessSquare;
import chess.project.griffith.views.ChessBoardCustomView;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public class King extends Piece {
    boolean isAlive = true;
    Context context;


    public King(Context context, Point point, boolean isWhitePiece) {
        this.context = context;
        currentPosition = point;
        super.isWhitePiece = isWhitePiece;
        if(isWhitePiece)
            setPieceId("wk");
        else
            setPieceId("bk");
    }

    @Override
    public ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares) {
        ArrayList<Point> returnedData =  filterInvalidPositions(chessBoardSquares,getAllPossiblePositions(chessBoardSquares),this);
        if(isWhitePiece && ChessBoardCustomView.isWhiteKingSideCastlingEligible){
            for(int i=0;i<returnedData.size();i++){
                if(returnedData.get(i).x == 5 && returnedData.get(i).y == 7)
                {
                    if(chessBoardSquares[6][7].isEmpty())
                    returnedData.add(new Point(6,7));

                }
            }

        }
        if(isWhitePiece && ChessBoardCustomView.isWhiteQueengSideCastlingEligible){
            for(int i=0;i<returnedData.size();i++){
                if(returnedData.get(i).x == 3 && returnedData.get(i).y == 7)
                {
                    if(chessBoardSquares[2][7].isEmpty())
                    returnedData.add(new Point(2,7));
                }
            }

        }
        if(!isWhitePiece && ChessBoardCustomView.isBlackKingSideCastlingEligible){
            for(int i=0;i<returnedData.size();i++){
                if(returnedData.get(i).x == 5 && returnedData.get(i).y == 0)
                {
                    if(chessBoardSquares[6][0].isEmpty())
                    returnedData.add(new Point(6,0));

                }
            }

        }
        if(!isWhitePiece && ChessBoardCustomView.isBlackQueengSideCastlingEligible){
            for(int i=0;i<returnedData.size();i++){
                if(returnedData.get(i).x == 3 && returnedData.get(i).y == 0)
                {
                    if(chessBoardSquares[2][0].isEmpty())
                    returnedData.add(new Point(2,0));
                }
            }

        }
             return filterInvalidPositions(chessBoardSquares,returnedData,this);
    }

    @Override
    public ArrayList<Point> getAllPossiblePositions(ChessSquare[][] chessBoardSquares) {
        ArrayList<Point> allValidPositions = new ArrayList<>();
        int x = currentPosition.x;
        int y = currentPosition.y;


        for (int rowIndex = -1; rowIndex < 2; rowIndex++) //Horizontal Moves
        {
            for (int columnIndex = -1; columnIndex < 2; columnIndex++) //Vertical Moves
            {
                int testX = x+rowIndex;
                int testY = y+columnIndex;
                if (!(testX < 0 || testX >= 8 || testY < 0 || testY >= 8)) // Checking corner case for IndexOutOfBounds
                {
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
                    }
                }
            }
        }



        return allValidPositions;
    }


    @Override
     public Drawable getDrawable(){if(isWhitePiece)
    {
        return context.getResources().getDrawable(R.drawable.wk);
    }

    else{
        return context.getResources().getDrawable(R.drawable.bk);
    }}


}
