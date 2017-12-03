package chess.project.griffith.pieces;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import chess.project.griffith.chess.R;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public class BlackRook implements Piece {
    boolean isAlive = true;
    final String pieceId = "br";
    Context context;
    Point currentPosition = null;

    public BlackRook(Context context, Point point) {
        this.context = context;
        this.currentPosition = point;
    }

    @Override
    public boolean isValidMove(Point toPoint) {
        return false;
    }

    @Override
    public Point getcurrentPosition() {
        return currentPosition;
    }

    @Override
    public String getPieceId() {
        return pieceId;
    }

    @Override
    public Drawable getDrawable() {
        return context.getResources().getDrawable(R.drawable.br);
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
