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



    public abstract ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares);
    public abstract ArrayList<Point> getAllPossiblePositions(ChessSquare[][] chessBoardSquares);
    public abstract  Drawable getDrawable();
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


    public void setCurrentPosition(Point currentPosition) {
        this.currentPosition = currentPosition;

    }

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





    public boolean isWhitePiece() {
        return isWhitePiece;
    }

}
