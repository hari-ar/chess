package chess.project.griffith.views;

import android.annotation.SuppressLint;
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
    static public boolean enPassantEligible = false;


    private MainActivity mainActivity;


    public static boolean isWhiteKingSideCastlingEligible = true;
    public static boolean isWhiteQueengSideCastlingEligible = true;
    public static boolean isBlackQueengSideCastlingEligible = true;
    public static boolean isBlackKingSideCastlingEligible = true;



    private boolean isNotEnoughMaterialsToCheckmate = false;

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

    public ChessSquare[][] getChessBoardSquares() {
        return chessBoardSquares;
    }

    public void setChessBoardSquares(ChessSquare[][] chessBoardSquares) {
        this.chessBoardSquares = chessBoardSquares;
    }

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

        HashSet<String> piecesNeededForForcedMate = new HashSet<>();
        piecesNeededForForcedMate.add("wp");
        piecesNeededForForcedMate.add("bp");
        piecesNeededForForcedMate.add("bq");
        piecesNeededForForcedMate.add("wq");
        piecesNeededForForcedMate.add("br");
        piecesNeededForForcedMate.add("wr");
        isWhiteKingSideCastlingEligible = true;
        isWhiteQueengSideCastlingEligible = true;
        isBlackQueengSideCastlingEligible = true;
        isBlackKingSideCastlingEligible = true;
        rowDown =-1;
        columnDown =-1;
        rowUp =-1;
        columnUp =-1;
        isHighlightedMode = false;
        selectedPiecePosition = null;
        isGameOver = false;
        borderOffset = 3f;
        highlightOffset = 5f;
        isStaleMate = false;
        commonUtils = new CommonUtils(piecesNeededForForcedMate);
        isWhiteTurn = true;
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
            int left = (int) (borderOffset -multiplier/2);
            int top = (int) (borderOffset - multiplier/2);
            int right = (int) (multiplier/2 - borderOffset);
            int bottom = (int) (multiplier/2- borderOffset);
            bounds = new Rect(left, top, right , bottom);

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

                        canvas.drawRect(highlightBounds, yellow); //Render Rectangles
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


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) { //Get touch down co-ordinates
            float x = event.getX(); float y = event.getY();

            rowDown = (int) (x / multiplier);columnDown = (int) (y / multiplier);
            //System.out.println("Touch down x is "+rowDown+" and y is "+columnDown);
            if(!isGameOver){
                if(isHighlightedMode) //Reset the suggestions..!!
                {
                    checkForValidPieceAndUnHighlight();
                }
                else{
                    if(chessBoardSquares[rowDown][columnDown].getPiece()!=null){
                        if(isValidPieceForCurrentTurn())
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
            float x = event.getX();
            float y = event.getY();

            //Convert user touch co-ordinates to out cell map
            rowUp = (int) (x / multiplier);
            columnUp = (int) (y / multiplier);
            //System.out.println("Touch up x is "+rowUp+" and y is "+columnUp);
            //Used to move piece in swipes and tocuh up events
            if(!isGameOver && chessBoardSquares[rowUp][columnUp].isHighlighted()){
                moveSelectedPieceToTouchUp();
            }

        }

        //Checking for piece promotion
        checkForPawnPromotion();



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

    private void checkForGameOver() {

            if(commonUtils.isKingInCheck(chessBoardSquares,isWhiteTurn)){
                isGameOver = checkForNoValidMove();
                return;
            }
                isStaleMate = checkForNoValidMove();
        isNotEnoughMaterialsToCheckmate = commonUtils.checkForEnoughMaterials(chessBoardSquares);
    }



    private boolean checkForNoValidMove() {



        if(isWhiteTurn){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(!chessBoardSquares[i][j].isEmpty() && chessBoardSquares[i][j].getPiece().isWhitePiece()){
                    ArrayList<Point> validMovesList= chessBoardSquares[i][j].getPiece().getAllValidPositions(chessBoardSquares);
                    if(validMovesList.size()>0)
                        return false;
                }
            }
        }
        }
            else{
                for(int i=0;i<8;i++){
                    for(int j=0;j<8;j++){
                        if(!chessBoardSquares[i][j].isEmpty() && !chessBoardSquares[i][j].getPiece().isWhitePiece()){
                            ArrayList<Point> validMovesList= chessBoardSquares[i][j].getPiece().getAllValidPositions(chessBoardSquares);
                            if(validMovesList.size()>0)
                                return false;
                        }
                    }
                }
            }

        return true;
    }

    private void checkForValidPieceAndUnHighlight() {
        if(chessBoardSquares[rowDown][columnDown].getPiece()!=null)
        {
            if(!isTouchDownOnOpponentPiece() && chessBoardSquares[rowDown][columnDown].isHighlighted()) //Avoid unhighlighting if clicked on other pieces
            {
                unhighlightSquares();
                isHighlightedMode = false;
            }
            else{
            if(!isTouchDownOnOpponentPiece() )
                Toast.makeText(getContext(),"Please click on valid square to make move or click on highlighted piece to unselect the piece..!!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isTouchDownOnOpponentPiece() {
        if(chessBoardSquares[rowDown][columnDown].getPiece().isWhitePiece() && isWhiteTurn){
            return false;
        }
        else if (!chessBoardSquares[rowDown][columnDown].getPiece().isWhitePiece() && !isWhiteTurn){
            return false;
        }
        return true;
    }

    private boolean isValidPieceForCurrentTurn(){
        if(chessBoardSquares[rowDown][columnDown].getPiece()!=null){
            if(chessBoardSquares[rowDown][columnDown].getPiece().isWhitePiece() && isWhiteTurn)
            {
                return true;
            }
            else if ((!chessBoardSquares[rowDown][columnDown].getPiece().isWhitePiece()) && !isWhiteTurn)
            {
                return true;
            }
        }
        return false;
    }

    private void moveSelectedPieceToTouchUp() {
        //chessBoardLastMoveState = commonUtils.copyDataIntoBackupArray(chessBoardSquares);; //Keeping track of last state for undo..
        if(selectedPiecePosition.x !=rowUp || selectedPiecePosition.y !=columnUp) //Needed to check if user clicked on same position again
        {
        Piece piece = chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece();
        piece.setCurrentPosition(new Point(rowUp,columnUp));
        if("wk".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()) || "bk".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()))
        {
            checkIfCastlingAndMoveRook(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId());
            revokeCastlingRight(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().isWhitePiece());
        }


        if("wr".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()) || "br".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId()) )
        {
            revokeCastlingRightOneSide(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId());
        }

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

            //Check for capturing
            if(columnUp == 2 && "bp".equals(chessBoardSquares[rowUp][columnUp+1].getPiece().getPieceId())){
                chessBoardSquares[rowUp][columnUp+1].setPiece(null);
            }

        }

        if("bp".equals(chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece().getPieceId())){
            if(columnUp == 3 && selectedPiecePosition.y ==1) //Check for two steps.
            {
                //Check for black pawn in touchUp + 1 or - 1

                if(rowUp!=7 && !chessBoardSquares[rowUp+1][3].isEmpty() && "wp".equals(chessBoardSquares[rowUp+1][3].getPiece().getPieceId())){
                    WhitePawn wp = (WhitePawn) chessBoardSquares[rowUp+1][3].getPiece();
                    wp.setEnPassantEligible(true, new Point(rowUp,2));
                }
                if(rowUp!=0 && !chessBoardSquares[rowUp-1][3].isEmpty() && "wp".equals(chessBoardSquares[rowUp-1][3].getPiece().getPieceId())){
                    WhitePawn wp = (WhitePawn)  chessBoardSquares[rowUp-1][3].getPiece();
                    wp.setEnPassantEligible(true, new Point(rowUp,2));
                }
            }
            //Check for capturing
            if(columnUp == 5 && "wp".equals(chessBoardSquares[rowUp][columnUp-1].getPiece().getPieceId())){
                chessBoardSquares[rowUp][columnUp-1].setPiece(null);
            }
        }

            commonUtils.resetEnpassent(isWhiteTurn,chessBoardSquares);
        chessBoardSquares[rowUp][columnUp].setPiece(piece);


        chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].setPiece(null);
        unhighlightChangeModeAndTurn();
        }

    }

    private void checkIfCastlingAndMoveRook(String pieceId) {
        if("wk".equals(pieceId)){
            if(selectedPiecePosition.x == 4 && selectedPiecePosition.y ==7 ){
                if(rowUp == 6 && columnUp == 7){//King side castling
                    Piece piece = chessBoardSquares[7][7].getPiece();
                    piece.setCurrentPosition(new Point(5,7));
                    chessBoardSquares[5][7].setPiece(piece);
                    chessBoardSquares[7][7].setPiece(null);
                }
                else if(rowUp == 2 && columnUp == 7){
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
                else if(rowUp == 2 && columnUp == 0){
                    Piece piece = chessBoardSquares[0][0].getPiece();
                    piece.setCurrentPosition(new Point(3,0));
                    chessBoardSquares[3][0].setPiece(piece);
                    chessBoardSquares[0][0].setPiece(null);
                }
            }
        }
    }

    private void revokeCastlingRightOneSide(String pieceId) {
        if("wr".equals(pieceId)){
            if(selectedPiecePosition.x == 7 && selectedPiecePosition.y ==7)
            {
                isWhiteKingSideCastlingEligible = false;
            }
            else if(selectedPiecePosition.x == 0 && selectedPiecePosition.y ==7)
            {
                isWhiteQueengSideCastlingEligible = false;
            }
        }
        else if ("br".equals(pieceId)){
            if(selectedPiecePosition.x == 7 && selectedPiecePosition.y ==0)
            {
                isBlackKingSideCastlingEligible = false;
            }
            else if(selectedPiecePosition.x == 0 && selectedPiecePosition.y ==0)
            {
                isBlackQueengSideCastlingEligible = false;
            }
        }
    }

    private void revokeCastlingRight(boolean isWhiteKing) {
        if(isWhiteKing){
            isWhiteKingSideCastlingEligible = false;
            isWhiteQueengSideCastlingEligible = false;
        }
        else{
            isBlackKingSideCastlingEligible = false;
            isBlackQueengSideCastlingEligible = false;
        }
    }

    private void unhighlightChangeModeAndTurn() {
        for (int x = 0; x<8 ; x++){ //Loop to unhighlight every piece
            for(int y = 0; y<8 ; y++) {
                chessBoardSquares[x][y].setHighlighted(false);
                isHighlightedMode=false;
            }
        }
        setIsWhiteTurn(!isWhiteTurn);
    }

    private void setHighlightForMoves(){
        selectedPiecePosition = new Point(rowDown,columnDown);
        Piece currentPiece = chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece();
        ArrayList<Point> allPossibleMoves = currentPiece.getAllValidPositions(chessBoardSquares);
        for(int i=0;i<allPossibleMoves.size();i++){
            Point point = allPossibleMoves.get(i);
            chessBoardSquares[point.x][point.y].setHighlighted(true);
            isHighlightedMode = true;
        }
        chessBoardSquares[rowDown][columnDown].setHighlighted(true);
        isHighlightedMode = true;
    }


    private void unhighlightSquares() {
        for (int x = 0; x<8 ; x++){ //Loop for pawns
            for(int y = 0; y<8 ; y++) {
                chessBoardSquares[x][y].setHighlighted(false);
                //isWhiteTurn=!isWhiteTurn;
                isHighlightedMode=false;
            }
        }
    }


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


}
