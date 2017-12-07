package chess.project.griffith.pieces;

import android.graphics.Point;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import chess.project.griffith.objects.ChessSquare;

/**
 * Created by aahuyarakshakaharil on 03/12/17.
 */

public abstract class Piece {



    public abstract ArrayList<Point> getAllValidPositions(ChessSquare[][] chessBoardSquares);
    public abstract ArrayList<Point> getAllPossiblePositions(ChessSquare[][] chessBoardSquares);
    public abstract  Drawable getDrawable();
    boolean isWhitePiece;
    String pieceId;
    Point currentPosition;


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
                        if(!isKingInCheck(testBoard, selectedPiece.isWhitePiece)){
                            verifiedValidPoints.add(testPosition);
                        }

            }
        //}
        return verifiedValidPoints;
    }

    private boolean isKingInCheck(final ChessSquare[][] testBoard, boolean isWhitePiece) {
        Point kingPosition = getKingPositionFromBoard(testBoard);
        for(int x = 0; x<8;x++){
            for(int y=0;y<8;y++){
                if(!testBoard[x][y].isEmpty()){
                    if(isWhitePiece && !testBoard[x][y].getPiece().isWhitePiece()){
                        ArrayList<Point> allPoints = testBoard[x][y].getPiece().getAllPossiblePositions(testBoard);
                        for(int i=0;i<allPoints.size();i++){
                            if(allPoints.get(i).x == kingPosition.x && allPoints.get(i).y == kingPosition.y){
                                return true;
                            }
                        }
                    }
                    else if (!isWhitePiece && testBoard[x][y].getPiece().isWhitePiece()){
                        ArrayList<Point> allPoints = testBoard[x][y].getPiece().getAllPossiblePositions(testBoard);
                        for(int i=0;i<allPoints.size();i++){
                            if(allPoints.get(i).x == kingPosition.x && allPoints.get(i).y == kingPosition.y){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private Point getKingPositionFromBoard(ChessSquare[][] testBoard) {
        Point kingPosition = null;
        for(int x = 0; x<8;x++){
            for(int y=0;y<8;y++){
                if(!testBoard[x][y].isEmpty())
                {
                    if(isWhitePiece && testBoard[x][y].getPiece().pieceId=="wk"){
                        kingPosition = new Point(x,y);
                    }
                    else if(!isWhitePiece && testBoard[x][y].getPiece().pieceId=="bk"){
                        kingPosition = new Point(x,y);
                    }
                }
            }
        }
        return kingPosition;
    }

    public boolean isWhitePiece() {
        return isWhitePiece;
    }

}
