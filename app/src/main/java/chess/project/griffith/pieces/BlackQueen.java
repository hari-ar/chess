package chess.project.griffith.pieces;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import chess.project.griffith.chess.R;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public class BlackQueen extends Queen {
    boolean isAlive = true;
    final String pieceId = "bq";


    public BlackQueen(Context context, Point point) {
        super(context,point);
        isWhitePiece = false;
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
        return context.getResources().getDrawable(R.drawable.bq);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
