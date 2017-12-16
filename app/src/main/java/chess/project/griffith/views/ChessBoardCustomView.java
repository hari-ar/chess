package chess.project.griffith.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

import chess.project.griffith.activities.MainActivity;
import chess.project.griffith.chess.R;
import chess.project.griffith.objects.ChessSquare;
import chess.project.griffith.pieces.Bishop;
import chess.project.griffith.pieces.BlackPawn;
import chess.project.griffith.pieces.Knight;
import chess.project.griffith.pieces.Piece;
import chess.project.griffith.pieces.Queen;
import chess.project.griffith.pieces.Rook;
import chess.project.griffith.pieces.WhitePawn;
import chess.project.griffith.utils.CommonUtils;


/**
 * Created by aahuyarakshakaharil on 02/12/17.
 */

public class ChessBoardCustomView extends View {



    static boolean isWhiteTurn = true;
    int rowDown =-1, columnDown =-1, rowUp =-1,columnUp =-1;
    boolean isHighlightedMode = false;
    Point selectedPiecePosition = null;
    boolean isGameOver = false;
    CommonUtils commonUtils ;
    ChessSquare[][] chessBoardSquares;
    private Paint white, black, yellow;
    private float multiplier;
    private Canvas canvas;
    private RectF square;
    private Rect bounds, highlightBounds;
    private float borderOffset;
    private float highlightOffset ;
    private boolean isStaleMate = false;

    private MainActivity mainActivity;


    public static boolean isWhiteKingSideCastlingEligible = true;
    public static boolean isWhiteQueenSideCastlingEligible = true;
    public static boolean isBlackQueenSideCastlingEligible = true;
    public static boolean isBlackKingSideCastlingEligible = true;



    private boolean isNotEnoughMaterialsToCheckmate = false;

    //Constructor Block Begins
    public ChessBoardCustomView(Context context) {
        super(context);
        init();
    }

    public ChessBoardCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChessBoardCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //Constructors Block Ends

    //Common method to be called by the constructor..!!
    //Gives consitent behaviour irrespective of how the view is initialized.
    public void init() {


        black = new Paint();
        white = new Paint();
        yellow = new Paint();
        //Assigning color values.
        black.setColor(getResources().getColor(R.color.black));
        white.setColor(getResources().getColor(R.color.white));
        yellow.setColor(getResources().getColor(R.color.yellow));
        //Setting Anti Aliasing Flags..!!
        white.setAntiAlias(true);
        black.setAntiAlias(true);

        //Minimum of one Piece of any of following ensure game continues.
        HashSet<String> piecesNeededForForcedMate = new HashSet<>();
        piecesNeededForForcedMate.add("wp");
        piecesNeededForForcedMate.add("bp");
        piecesNeededForForcedMate.add("bq");
        piecesNeededForForcedMate.add("wq");
        piecesNeededForForcedMate.add("br");
        piecesNeededForForcedMate.add("wr");

        //Reset Castling Flags
        isWhiteKingSideCastlingEligible = true;
        isWhiteQueenSideCastlingEligible = true;
        isBlackQueenSideCastlingEligible = true;
        isBlackKingSideCastlingEligible = true;

        //Reset Game Flags
        isHighlightedMode = false;
        isStaleMate = false;
        isGameOver = false;
        isWhiteTurn = true;

        //Default positions.
        rowDown =-1;
        columnDown =-1;
        rowUp =-1;
        columnUp =-1;


        //Reset other values to default.
        selectedPiecePosition = null;
        borderOffset = 3f;
        highlightOffset = 5f;
        commonUtils = new CommonUtils(piecesNeededForForcedMate);
        //Calling invalidate again., to be sure to refresh on reset.
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas); //Calling super..!!
        this.canvas = canvas;

        float width = getMeasuredWidth(); //Get Width
        float height = getMeasuredHeight(); //Get Height
        if (height < width) //Checking least values of height and weight
        {
            multiplier = height / 8;//Setting with least of the values
        } else {
            multiplier = width / 8; // Set Multiplier to adjust to make it perfect square
        }
        System.out.println("Multiplier is "+multiplier);

        //Initialize cell square.
        if(square==null) //Avoids unnecessary initialization
        {
            square = new RectF( -multiplier/2,  -multiplier/2,multiplier/2,multiplier/2);
        }
        if(bounds==null) //Avoids unnecessary initialization
        {
            //Calculating bounds for rectangle which holds the color.
            int left = (int) (borderOffset -multiplier/2);
            int top = (int) (borderOffset - multiplier/2);
            int right = (int) (multiplier/2 - borderOffset);
            int bottom = (int) (multiplier/2- borderOffset);
            bounds = new Rect(left, top, right , bottom);
            //Adjusting for offset
            left = (int) (highlightOffset -multiplier/2);
            top = (int) (highlightOffset - multiplier/2);
            right = (int) (multiplier/2 - highlightOffset);
            bottom = (int) (multiplier/2- highlightOffset);
            highlightBounds = new Rect(left, top, right , bottom);
        }
        drawGameBoard(); // Draw Background

    }

    private void drawGameBoard() {
        for (int row = 0; row < 8; row++) { //Iterate through rows
            for (int col = 0; col < 8; col++) { // Iterate Columns
                canvas.save();

                //Can move this to cell object as well.. to be cleaner.
                canvas.translate((2*row+1)*multiplier/2,(2*col+1)*multiplier/2); //Finding centers of squares to move the origin.

                    if((row+col)%2==0) //Draw black rectangle for covered cells.
                    {
                        canvas.drawRect(square, white); //Render Rectangles
                    }
                    else{ //To draw text
                        canvas.drawRect(square, black); //Render Rectangles//drawTextInsideTheCell(row, col); //Print the data
                    }
                    if(chessBoardSquares[row][col].isHighlighted()){
                        canvas.drawRect(highlightBounds, yellow); //Render Rectangles to display hi
                    }

                    if(chessBoardSquares[row][col].getPiece()!= null){
                        Drawable drawable = chessBoardSquares[row][col].getPiece().getDrawable();
                        drawable.setBounds(bounds);
                        drawable.draw(canvas);
                    }

                canvas.restore();
            }
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Setting square dimension.. Requirement and followed logic as per pdf
        float width = getMeasuredWidth();
        float height = getMeasuredHeight();
        if (height < width) {
            setMeasuredDimension((int) height, (int) height); // Responsible for rendering square board.
        } else {
            setMeasuredDimension((int) width, (int) width); // Responsible for rendering square board.
        }

    }


    //Method to handle touch events on custom view.
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) { //Get touch down co-ordinates
            float x = event.getX(); float y = event.getY();

            rowDown = (int) (x / multiplier);columnDown = (int) (y / multiplier);
            //System.out.println("Touch down x is "+rowDown+" and y is "+columnDown);
            if(!isGameOver){
                if(isHighlightedMode) //Reset the suggestions..!!
                {
                    if(!chessBoardSquares[rowDown][columnDown].isHighlighted())
                    {
                        Toast.makeText(getContext(),"Please click on selected piece to unhighlight",Toast.LENGTH_SHORT).show();
                    }
                    checkForValidPieceAndUnHighlight();
                }
                else{
                    if(chessBoardSquares[rowDown][columnDown].getPiece()!=null){
                        //Check if user selected on valid piece
                        if(!isTouchDownOnOpponentPiece())
                            setHighlightForMoves();
                        else
                        {
                            Toast.makeText(getContext(),"Please select your piece",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        if(event.getActionMasked() == MotionEvent.ACTION_UP) {//Get touch up co-ordinates
            //Get touch points.
            float x = event.getX();
            float y = event.getY();

            //Convert user touch co-ordinates to out cell map
            rowUp = (int) (x / multiplier);
            columnUp = (int) (y / multiplier);

            // If user click on or remove finger from highlighted piece., move the piece to position.
            if(!isGameOver && chessBoardSquares[rowUp][columnUp].isHighlighted()){
                moveSelectedPieceToTouchUp();
            }

        }

        //Checking for piece promotion
        checkForPawnPromotion();


        //Checking if game is over.
        checkForGameOver();

        if(!isGameOver && commonUtils.isKingInCheck(chessBoardSquares,isWhiteTurn)){
            Toast.makeText(getContext(),"Check..!!!!",Toast.LENGTH_SHORT).show();
        }

        else if(isGameOver){
            if(isWhiteTurn)
                Toast.makeText(getContext(),"Game Over..!! Black Wins",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(),"Game Over..!! White Wins",Toast.LENGTH_SHORT).show();
        }

        else if(isStaleMate){
            Toast.makeText(getContext(),"Well Played mate..!! but its Stalemate..!! Reset game to try again",Toast.LENGTH_SHORT).show();
        }
        else if(isNotEnoughMaterialsToCheckmate){
            Toast.makeText(getContext(),"Its a Draw..!! Not enough materials to continue",Toast.LENGTH_SHORT).show();
        }


        invalidate();
        return super.onTouchEvent(event);
    }

    //Method that handles Pawn promotion
    private void checkForPawnPromotion() {
        if(commonUtils.isPawnInLastRank(chessBoardSquares)){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Congrats...!! Your pawn is promoted. Select a piece..!!");
            final Point lastRankPawnPosition = commonUtils.getLastRankPawnPosition();

            builder.setItems(R.array.choices, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int index) {
                            if(chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].getPiece().isWhitePiece()){
                                if(index == 0)
                                {
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Queen(getContext(),lastRankPawnPosition,true));
                                }
                                else if(index == 1)
                                {
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Rook(getContext(),lastRankPawnPosition,true));
                                }
                                else if(index == 2)
                                {
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Bishop(getContext(),lastRankPawnPosition,true));
                                }
                                else if (index == 3){
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Knight(getContext(),lastRankPawnPosition,true));
                                }
                                if(commonUtils.isKingInCheck(chessBoardSquares,isWhiteTurn)){ //Added to avoid bug which doesn't alert check on pawn promotion
                                    Toast.makeText(getContext(),"Check..!!!!",Toast.LENGTH_SHORT).show();
                                }
                                invalidate();
                            }
                            else{
                                if(index == 0)
                                {
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Queen(getContext(),lastRankPawnPosition,false));
                                }
                                else if(index == 1)
                                {
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Rook(getContext(),lastRankPawnPosition,false));
                                }
                                else if(index == 2)
                                {
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Bishop(getContext(),lastRankPawnPosition,false));
                                }
                                else if (index == 3){
                                    chessBoardSquares[lastRankPawnPosition.x][lastRankPawnPosition.y].setPiece(new Knight(getContext(),lastRankPawnPosition,false));
                                }
                                if(commonUtils.isKingInCheck(chessBoardSquares,isWhiteTurn)){ //Added to avoid bug which doesn't alert check on pawn promotion
                                    Toast.makeText(getContext(),"Check..!!!!",Toast.LENGTH_SHORT).show();
                                }
                                invalidate();
                            }
                        }
                    }
            );
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    //Method to check for checkmate..!!
    private void checkForGameOver() {

            if(commonUtils.isKingInCheck(chessBoardSquares,isWhiteTurn)){
                isGameOver = commonUtils.checkForNoValidMove(chessBoardSquares,isWhiteTurn);
                return;
            }
                isStaleMate = commonUtils.checkForNoValidMove(chessBoardSquares,isWhiteTurn);
        isNotEnoughMaterialsToCheckmate = commonUtils.checkForEnoughMaterials(chessBoardSquares);
    }

    //Check if the user clicked on valid and highlighted position
    private void checkForValidPieceAndUnHighlight() {
        if(!chessBoardSquares[rowDown][columnDown].isEmpty())
        {
            if(!isTouchDownOnOpponentPiece() && chessBoardSquares[rowDown][columnDown].isHighlighted()) //Avoid unhighlighting if clicked on other pieces
            {
                unhighlightSquares();
                isHighlightedMode = false;
            }
        }
    }

    //See if user clicked on opponent piece
    private boolean isTouchDownOnOpponentPiece() {
        if(chessBoardSquares[rowDown][columnDown].getPiece().isWhitePiece() && isWhiteTurn){
            return false;
        }
        else if (!chessBoardSquares[rowDown][columnDown].getPiece().isWhitePiece() && !isWhiteTurn){
            return false;
        }
        return true;
    }

    //The method responsible for moving the piece to user selected square.
    private void moveSelectedPieceToTouchUp() {

        if(selectedPiecePosition.x !=rowUp || selectedPiecePosition.y !=columnUp) //Needed to check if user clicked on same position again
        {
        Piece piece = chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece();
        piece.setCurrentPosition(new Point(rowUp,columnUp));

        //Special case for king. Check if king castled or moved. If castled, we've to move rook as well.
        if("wk".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()) || "bk".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()))
        {
            checkIfCastlingAndMoveRook(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId());
            revokeCastlingRight(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().isWhitePiece());
        }

        //Special case for rook. Castling right for king on that side will be revoked.
        if("wr".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()) || "br".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()) )
        {
            revokeCastlingRightOneSide(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId());
        }

        //Checking for enpassant.
        if("wp".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId())){

            //Check to enable enpassant
            if(columnUp == 4 && selectedPiecePosition.y ==6) //Check for two steps.
            {
                //Check for black pawn in touchUp + 1 or - 1
                if(rowUp!=7 && !chessBoardSquares[rowUp+1][4].isEmpty() && "bp".equals(chessBoardSquares[rowUp+1][4].getPiece().getPieceId())){ //Check for corner pieces
                    BlackPawn bp = (BlackPawn) chessBoardSquares[rowUp+1][4].getPiece();
                    bp.setEnPassantEligible(true,new Point(rowUp,5));
                }
                if(rowUp!=0 && !chessBoardSquares[rowUp-1][4].isEmpty() && "bp".equals(chessBoardSquares[rowUp-1][4].getPiece().getPieceId())){
                    BlackPawn bp = (BlackPawn) chessBoardSquares[rowUp-1][4].getPiece();
                    bp.setEnPassantEligible(true,new Point(rowUp,5));
                }
            }

            //Check for capturing the enpassant eligible piece.
            if(columnUp == 2 && chessBoardSquares[rowUp][columnUp].isEmpty() && !chessBoardSquares[rowUp][columnUp+1].isEmpty() && "bp".equals(chessBoardSquares[rowUp][columnUp+1].getPiece().getPieceId())){
                chessBoardSquares[rowUp][columnUp+1].setPiece(null);
            }

        }

            //Checking for enpassant.
        if("bp".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId())){
            if(columnUp == 3 && selectedPiecePosition.y ==1) //Check for two steps.
            {
                //Check for white pawn in touchUp + 1 or - 1
                if(rowUp!=7 && !chessBoardSquares[rowUp+1][3].isEmpty() && "wp".equals(chessBoardSquares[rowUp+1][3].getPiece().getPieceId())){
                    WhitePawn wp = (WhitePawn) chessBoardSquares[rowUp+1][3].getPiece();
                    wp.setEnPassantEligible(true, new Point(rowUp,2));
                }
                if(rowUp!=0 && !chessBoardSquares[rowUp-1][3].isEmpty() && "wp".equals(chessBoardSquares[rowUp-1][3].getPiece().getPieceId())){
                    WhitePawn wp = (WhitePawn)  chessBoardSquares[rowUp-1][3].getPiece();
                    wp.setEnPassantEligible(true, new Point(rowUp,2));
                }
            }
            //Check for capturing the enpassant eligible piece.
            if(columnUp == 5 && chessBoardSquares[rowUp][columnUp].isEmpty() && !chessBoardSquares[rowUp][columnUp-1].isEmpty() &&"wp".equals(chessBoardSquares[rowUp][columnUp-1].getPiece().getPieceId())){
                chessBoardSquares[rowUp][columnUp-1].setPiece(null);
            }
        }

        //This is done to revoke enpassant right after movement.
        commonUtils.resetEnpassant(isWhiteTurn,chessBoardSquares);
        //Set the selected piece to the square
        chessBoardSquares[rowUp][columnUp].setPiece(piece);
        //Remove from old position.
        chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].setPiece(null);
        //Unhighlight all the squares and change the user turn.
        unhighlightChangeModeAndTurn();
        }

    }

    //The method used to move the rook to square beside the king.
    private void checkIfCastlingAndMoveRook(String pieceId) {
        if("wk".equals(pieceId)){
            if(selectedPiecePosition.x == 4 && selectedPiecePosition.y ==7 ){
                if(rowUp == 6 && columnUp == 7){//King side castling
                    Piece piece = chessBoardSquares[7][7].getPiece();
                    piece.setCurrentPosition(new Point(5,7));
                    chessBoardSquares[5][7].setPiece(piece);
                    chessBoardSquares[7][7].setPiece(null);
                }
                else if(rowUp == 2 && columnUp == 7){ // Queen Side Castling
                    Piece piece = chessBoardSquares[0][7].getPiece();
                    piece.setCurrentPosition(new Point(3,7));
                    chessBoardSquares[3][7].setPiece(piece);
                    chessBoardSquares[0][7].setPiece(null);
                }
            }
        }
        else{
            if(selectedPiecePosition.x == 4 && selectedPiecePosition.y ==0 ){
                if(rowUp == 6 && columnUp == 0){//King side castling
                    Piece piece = chessBoardSquares[7][0].getPiece();
                    piece.setCurrentPosition(new Point(5,0));
                    chessBoardSquares[5][0].setPiece(piece);
                    chessBoardSquares[7][0].setPiece(null);
                }
                else if(rowUp == 2 && columnUp == 0){ // Queen Side Castling
                    Piece piece = chessBoardSquares[0][0].getPiece();
                    piece.setCurrentPosition(new Point(3,0));
                    chessBoardSquares[3][0].setPiece(piece);
                    chessBoardSquares[0][0].setPiece(null);
                }
            }
        }
    }

    //Will be called on rook movement.
    private void revokeCastlingRightOneSide(String pieceId) {
        if("wr".equals(pieceId)){
            if(selectedPiecePosition.x == 7 && selectedPiecePosition.y ==7)
            {
                isWhiteKingSideCastlingEligible = false;
            }
            else if(selectedPiecePosition.x == 0 && selectedPiecePosition.y ==7)
            {
                isWhiteQueenSideCastlingEligible = false;
            }
        }
        else if ("br".equals(pieceId)){
            if(selectedPiecePosition.x == 7 && selectedPiecePosition.y ==0)
            {
                isBlackKingSideCastlingEligible = false;
            }
            else if(selectedPiecePosition.x == 0 && selectedPiecePosition.y ==0)
            {
                isBlackQueenSideCastlingEligible = false;
            }
        }
    }

    //Will be called on king movement.
    private void revokeCastlingRight(boolean isWhiteKing) {
        if(isWhiteKing){
            isWhiteKingSideCastlingEligible = false;
            isWhiteQueenSideCastlingEligible = false;
        }
        else{
            isBlackKingSideCastlingEligible = false;
            isBlackQueenSideCastlingEligible = false;
        }
    }

    //Used to unhighlight all squares as well as change the turn.
    private void unhighlightChangeModeAndTurn() {
        for (int x = 0; x<8 ; x++){ //Loop to unhighlight every piece
            for(int y = 0; y<8 ; y++) {
                chessBoardSquares[x][y].setHighlighted(false);
                isHighlightedMode=false;
            }
        }
        setIsWhiteTurn(!isWhiteTurn);
    }

    //On touch down of a piece., Set the highlights on square to
    // indicate to user the possible moves.
    private void setHighlightForMoves(){
        selectedPiecePosition = new Point(rowDown,columnDown);
        //Calling the super class method.
        Piece currentPiece = chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece();
        //Java Magic works here., and gets the valid moves
        ArrayList<Point> allPossibleMoves = currentPiece.getAllValidPositions(chessBoardSquares);
        for(int i=0;i<allPossibleMoves.size();i++){
            Point point = allPossibleMoves.get(i);
            chessBoardSquares[point.x][point.y].setHighlighted(true);
            isHighlightedMode = true;
        }
        chessBoardSquares[rowDown][columnDown].setHighlighted(true);
        isHighlightedMode = true;
    }

    //Method used to clear all the highlights set earlier
    private void unhighlightSquares() {
        for (int x = 0; x<8 ; x++){ //Loop for pawns
            for(int y = 0; y<8 ; y++) {
                chessBoardSquares[x][y].setHighlighted(false);
                //isWhiteTurn=!isWhiteTurn;
                isHighlightedMode=false;
            }
        }
    }


    //Getters And Setters
    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setIsWhiteTurn(boolean isWhiteTurn) {
        ChessBoardCustomView.isWhiteTurn = isWhiteTurn;
        if(isWhiteTurn){
            mainActivity.textView.setText("White To Play");
        }
        else
        {
            mainActivity.textView.setText("Black To Play");
        }
    }

    public ChessSquare[][] getChessBoardSquares() {
        return chessBoardSquares;
    }

    public void setChessBoardSquares(ChessSquare[][] chessBoardSquares) {
        this.chessBoardSquares = chessBoardSquares;
    }


}
