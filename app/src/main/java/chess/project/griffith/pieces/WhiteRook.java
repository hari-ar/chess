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

public class WhiteRook extends Rook {
    boolean isAlive = true;


    public WhiteRook(Context context, Point point) {
        super(context, point);
        isWhitePiece = true;
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
        return context.getResources().getDrawable(R.drawable.wr);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
