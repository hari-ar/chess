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

public class WhiteKnight extends Knight {
    boolean isAlive = true;
    final String pieceId = "wn";

    public WhiteKnight(Context context, Point point) {
        super(context, point);
        isWhitePiece = true;
    }



    @Override
    public String getPieceId() {
        return pieceId;
    }

    @Override
    public Drawable getDrawable() {
        return context.getResources().getDrawable(R.drawable.wn);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }


}
