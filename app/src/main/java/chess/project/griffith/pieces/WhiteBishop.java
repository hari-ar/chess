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

public class WhiteBishop extends Bishop {
    boolean isAlive = true;
    final String pieceId = "wb";


    public WhiteBishop(Context context, Point point) {
        super(context,point);
        isWhitePiece= true;
    }

    @Override
    public ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares) {
        return null;
    }


    @Override
    public String getPieceId() {
        return pieceId;
    }

    @Override
    public Drawable getDrawable() {
        return context.getResources().getDrawable(R.drawable.wb);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
