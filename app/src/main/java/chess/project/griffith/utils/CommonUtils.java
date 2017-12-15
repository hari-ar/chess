package chess.project.griffith.utils;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashSet;

import chess.project.griffith.objects.ChessSquare;

/**
 * Created by aahuyarakshakaharil on 07/12/17.
 */

public class CommonUtils {

    private Point lastRankPawnPosition;
    private HashSet<String> piecesNeededForForcedMate = new HashSet<>();

    public CommonUtils(HashSet<String> piecesNeededForForcedMate) {
        this.piecesNeededForForcedMate = piecesNeededForForcedMate;
    }

    public CommonUtils(){
        //Do nothing overloaded constructor
    }

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


    public boolean checkForEnoughMaterials(ChessSquare[][] chessBoardSquares) {

            boolean whiteLightBishop = false;
            boolean whiteDarkBishop = false;
            boolean blackLightBishop = false;
            boolean blackDarkBishop = false;
            boolean whiteKnight = false;
            boolean blackKnight = false;
            int whiteKnightCount =0;
            int blackKnightCount =0;

            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(!chessBoardSquares[i][j].isEmpty()){
                        String id = chessBoardSquares[i][j].getPiece().getPieceId();
                        if(piecesNeededForForcedMate.contains(id)){
                            return false;
                        }
                        if("wn".equals(id)){
                            whiteKnight = true;
                            whiteKnightCount++;
                        }
                        else if("bn".equals(id)){
                            blackKnight = true;
                            blackKnightCount++;
                        }
                        else if("wb".equals(id)){
                            if((i+j)%2==0){
                                whiteDarkBishop = true;
                            }
                            else
                                whiteLightBishop = true;
                        }
                        else if("bb".equals(id)){
                            if((i+j)%2==0){
                                blackDarkBishop= true;
                            }
                            else
                                blackLightBishop= true;
                        }
                    }
                }
            }
            if(whiteKnightCount > 1 || blackKnightCount > 1)//Two Knights
            {
                return false;
            }
            if((whiteDarkBishop && whiteKnight) || (whiteLightBishop && whiteKnight)) //OneBishop and a Knight
            {
                return false;
            }
            if((blackDarkBishop &&  blackKnight)|| (blackLightBishop && blackKnight))//OneBishop and a Knight
            {
                return false;
            }
            if((whiteDarkBishop && whiteLightBishop) || (blackDarkBishop && blackLightBishop)) //Two Opposite Colored Bishops
            {
                return false;
            }

            return true;
        }

    public ChessSquare[][] copyDataIntoBackupArray(ChessSquare[][] chessBoardSquares) {
        ChessSquare[][] chessBoardLastMoveState = new ChessSquare[8][8];
        for(int i = 0; i<8; i++){
            for(int j =0;j<8;j++){
                chessBoardLastMoveState[i][j] =  chessBoardSquares[i][j];
            }
        }
        return chessBoardLastMoveState;
    }

}
