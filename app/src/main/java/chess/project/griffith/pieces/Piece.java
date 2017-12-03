package chess.project.griffith.pieces;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public interface Piece {
    public boolean isValidMove(Point toPoint);
    public Point getcurrentPosition();
    public String getPieceId();
    public Drawable getDrawable();
    public boolean isAlive();
}
