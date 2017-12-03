package chess.project.griffith.chess;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by aahuyarakshakaharil on 02/12/17.
 */

public class ChessBoardCustomView extends View {

    private Paint white, black;
    private float multiplier;
    private Canvas canvas;
    private RectF square;
    private Rect bounds;
    private float borderOffset = 3f;

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
        //Assigning color values.
        black.setColor(getResources().getColor(R.color.black));
        white.setColor(getResources().getColor(R.color.white));
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
                        canvas.drawRect(square, black); //Render Rectangles
                        Drawable d = getResources().getDrawable(R.drawable.wq);
                        d.setBounds(bounds);
                        d.draw(canvas);
                        //canvas.drawBitmap();
                    }
                    else{ //To draw text
                        canvas.drawRect(square, white); //Render Rectangles//drawTextInsideTheCell(row, col); //Print the data
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
            setMeasuredDimension((int) height, (int) height); // Responsible for rendering square mines.
        } else {
            setMeasuredDimension((int) width, (int) width); // Responsible for rendering square mines.
        }

    }
}
