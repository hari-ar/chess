package chess.project.griffith.views;

import android.content.Context;
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

import chess.project.griffith.objects.Board;
import chess.project.griffith.chess.R;
import chess.project.griffith.objects.ChessSquare;
import chess.project.griffith.pieces.Piece;


/**
 * Created by aahuyarakshakaharil on 02/12/17.
 */

public class ChessBoardCustomView extends View {

    private Paint white, black, yellow;
    private float multiplier;
    private Canvas canvas;
    private RectF square;
    private Rect bounds, highlightBounds;
    private float borderOffset = 3f;
    private float highlightOffset = 5f;
    private Board board;
    int rowDown =-1, columnDown =-1, rowUp =-1,columnUp =-1;
    static boolean isWhiteTurn = true;
    boolean isHighlightedMode = false;
    Point selectedPiecePosition = null;
    long touchDownTime = 0;
    long touchUpTime = 0;

    public ChessSquare[][] getChessBoardSquares() {
        return chessBoardSquares;
    }

    public void setChessBoardSquares(ChessSquare[][] chessBoardSquares) {
        this.chessBoardSquares = chessBoardSquares;
    }

    ChessSquare[][] chessBoardSquares;

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

    //Common method to be called by the constructor..!!
    //Gives consitent behaviour irrespective of how the view is initialized.
    private void init() {
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
            touchDownTime = System.currentTimeMillis();
            rowDown = (int) (x / multiplier);columnDown = (int) (y / multiplier);
            System.out.println("Touch down x is "+rowDown+" and y is "+columnDown);
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
            return true;
        }
        if(event.getActionMasked() == MotionEvent.ACTION_UP) {//Get touch up co-ordinates
            float x = event.getX();
            float y = event.getY();

            //Convert user touch co-ordinates to out cell map
            rowUp = (int) (x / multiplier);
            columnUp = (int) (y / multiplier);
            System.out.println("Touch up x is "+rowUp+" and y is "+columnUp);
            //Used to move piece in swipes and tocuh up events
            if(chessBoardSquares[rowUp][columnUp].isHighlighted()){
                moveSelectedPieceToTouchUp();
            }
        }

        invalidate();
        return super.onTouchEvent(event);
    }

    private void checkForValidPieceAndUnHighlight() {
        if(chessBoardSquares[rowDown][columnDown].getPiece()!=null)
        {
            if(!isTouchDownOnOpponentPiece() && chessBoardSquares[rowDown][columnDown].isHighlighted()) //Avoid unhighlighting if clicked on other pieces
            {
                unhighlightSquares();
                isHighlightedMode = false;
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
        if(selectedPiecePosition.x !=rowUp || selectedPiecePosition.y !=columnUp)
        {
        Piece piece = chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].getPiece();
        piece.setCurrentPosition(new Point(rowUp,columnUp));
        chessBoardSquares[selectedPiecePosition.x][selectedPiecePosition.y].setPiece(null);
        chessBoardSquares[rowUp][columnUp].setPiece(piece);
        unhighlightChangeModeAndTurn();
        }
    }

    private void unhighlightChangeModeAndTurn() {
        for (int x = 0; x<8 ; x++){ //Loop for pawns
            for(int y = 0; y<8 ; y++) {
                chessBoardSquares[x][y].setHighlighted(false);
                isHighlightedMode=false;
            }
        }
        isWhiteTurn=!isWhiteTurn;
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
        invalidate();
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


}
