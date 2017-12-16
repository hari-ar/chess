package chess.project.griffith.pieces;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import chess.project.griffith.objects.ChessSquare;
import chess.project.griffith.utils.CommonUtils;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public abstract class Piece {



    //Method to check legal moves where piece can move to.!!
    public abstract ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares);

    // Method to get the piece movements., But without check for self check. This is need to verify if opponent king can be checked.
    // If above method is used., then it will miss a corner case where a piece in defence line of sight of same king can not check the opponent king,
    // but that should not be the case in chess. Even though a piece is defending its own king and can not move., it can affect check for opponent king.
    public abstract ArrayList<Point> getAllPossiblePositions(ChessSquare[][] chessBoardSquares);

    //Return drawable image.
    public abstract  Drawable getDrawable();

    //Flag to set piece color.
    boolean isWhitePiece;
    private String pieceId;

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    Point currentPosition;
    CommonUtils commonUtils = new CommonUtils();



    //Filter invalid positions from all the possible moves.
    public ArrayList<Point> filterInvalidPositions(final ChessSquare[][] chessBoardSquares, ArrayList<Point> allPoints, final Piece selectedPiece){
        ChessSquare[][] testBoard = new ChessSquare[8][8];
        ArrayList<Point> verifiedValidPoints = new ArrayList();
        for(int i =0 ; i<allPoints.size() ; i++){
            for(int x=0; x<8;x++){
                for(int y=0;y<8;y++){
                    testBoard[x][y] = new ChessSquare(null);
                    testBoard[x][y].setPiece(chessBoardSquares[x][y].getPiece());
                }
            }
            Point testPosition = allPoints.get(i);
            //if(!(currentPosition.x==testPosition.x && currentPosition.y==testPosition.y)){
                for(int x=0; x<8;x++){
                    for(int y=0;y<8;y++){
                        if((x==testPosition.x && y==testPosition.y) )
                        {
                            testBoard[x][y] = new ChessSquare(selectedPiece);
                        }
                        else if ((x==currentPosition.x && y==currentPosition.y)){
                            testBoard[x][y] = new ChessSquare(null);
                        }
                    }
                }
                        if(!commonUtils.isKingInCheck(testBoard, selectedPiece.isWhitePiece)){
                            verifiedValidPoints.add(testPosition);
                        }

            }
        //}
        return verifiedValidPoints;
    }



    //Getters and Setter.

    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;

    }

    public boolean isWhitePiece() {
        return isWhitePiece;
    }


}
