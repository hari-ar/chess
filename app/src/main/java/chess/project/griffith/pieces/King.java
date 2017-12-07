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

public class King extends Piece {
    boolean isAlive = true;
    Context context;


    public King(Context context, Point point, boolean isWhitePiece) {
        this.context = context;
        currentPosition = point;
        super.isWhitePiece = isWhitePiece;
    }

    @Override
    public ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares) {
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
    public Point getCurrentPosition() {
        return currentPosition;
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
