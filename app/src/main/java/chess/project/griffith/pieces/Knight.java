package chess.project.griffith.pieces;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import chess.project.griffith.objects.ChessSquare;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public abstract class Knight extends Piece {
    boolean isAlive = true;
    final String pieceId = "bn";
    Context context;
    Point currentPosition = null;

    public Knight(Context context, Point point) {
        this.context = context;
        this.currentPosition = point;
    }

    @Override
    public ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares) {
        return null;
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
    abstract public Drawable getDrawable();


    @Override
    public boolean isAlive() {
        return isAlive;
    }
}
