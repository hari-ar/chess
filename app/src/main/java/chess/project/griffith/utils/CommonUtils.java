package chess.project.griffith.utils;

import android.graphics.Point;

import java.util.ArrayList;

import chess.project.griffith.objects.ChessSquare;

/**
 * Created by aahuyarakshakaharil on 07/12/17.
 */

public class CommonUtils {

    private Point lastRankPawnPosition;

    public boolean isKingInCheck(final ChessSquare[][] testBoard, boolean isWhitePiece) {
        Point kingPosition = getKingPositionFromBoard(testBoard,isWhitePiece);
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

    public Point getKingPositionFromBoard(ChessSquare[][] testBoard, boolean isWhitePiece) {
        Point kingPosition = null;
        for(int x = 0; x<8;x++){
            for(int y=0;y<8;y++){
                if(!testBoard[x][y].isEmpty())
                {
                    if(isWhitePiece && testBoard[x][y].getPiece().getPieceId()=="wk"){
                        kingPosition = new Point(x,y);
                    }
                    else if(!isWhitePiece && testBoard[x][y].getPiece().getPieceId()=="bk"){
                        kingPosition = new Point(x,y);
                    }
                }
            }
        }
        return kingPosition;
    }

    public boolean isPawnInLastRank(final ChessSquare[][] chessBoardSquares) {
        for(int i=0;i<8;i++){
            if(!chessBoardSquares[i][0].isEmpty()){
                if(chessBoardSquares[i][0].getPiece().getPieceId().equals("wp"))
                {
                    lastRankPawnPosition = new Point(i,0);
                    return true;
                }
            }
            if(!chessBoardSquares[i][7].isEmpty()){
                if(chessBoardSquares[i][7].getPiece().getPieceId().equals("bp"))
                {
                    lastRankPawnPosition = new Point(i,7);
                    return true;
                }
            }
        }
        return false;
    }

    public Point getLastRankPawnPosition() {
        return lastRankPawnPosition;
    }


}
