package chess.project.griffith.objects;

import chess.project.griffith.pieces.Piece;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public class ChessSquare {

    private Piece piece;
    boolean isEmpty = true;
    boolean isHighlighted = false;


    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void setHighlighted(boolean highlighted) {
        isHighlighted = highlighted;
    }



    public boolean isEmpty() {
        return isEmpty;
    }

    public boolean isHighlighted() {
        return isHighlighted;
    }



    public Piece getPiece() {
        return piece;
    }

    public ChessSquare(Piece piece) {
        if(piece != null){
            this.piece = piece;
            isEmpty = false;
        }
    }
}
