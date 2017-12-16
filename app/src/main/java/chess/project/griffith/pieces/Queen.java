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
        ArrayList<Point> tempPositions = new ArrayList<>();
        int x = currentPosition.x;
        int y = currentPosition.y;
        Piece piece = new Bishop(context,currentPosition,isWhitePiece);
        allValidPositions = piece.getAllPossiblePositions(chessBoardSquares);
        piece = new Rook(context,currentPosition,isWhitePiece);
        tempPositions = piece.getAllPossiblePositions(chessBoardSquares);
        for(int i =0; i<tempPositions.size(); i++){
            allValidPositions.add(tempPositions.get(i));
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
