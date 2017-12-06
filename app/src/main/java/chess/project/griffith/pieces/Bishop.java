package chess.project.griffith.pieces;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;

import chess.project.griffith.objects.ChessSquare;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public abstract class Bishop extends Piece {
    boolean isAlive = true;
    Context context;
    Point currentPosition = null;

    public Bishop(Context context, Point point) {
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
    public boolean isAlive() {
        return isAlive;
    }
}
