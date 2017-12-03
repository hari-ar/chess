package chess.project.griffith.objects;

import chess.project.griffith.pieces.Piece;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public class ChessSquare {
    private Piece piece;
    boolean isEmpty = true;

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
