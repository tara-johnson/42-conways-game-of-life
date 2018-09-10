package com.example.conwaysgameoflife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.example.conwaysgameoflife.models.Cell;

public class GameOfLife extends SurfaceView implements Runnable {

    public static final int DEFAULT_SIZE = 25;
    public static final int DEFAULT_ALIVE_COLOR = Color.WHITE;
    public static final int DEFAULT_DEAD_COLOR = Color.BLACK;
    private Thread thread;
    private boolean isRunning = false;
    private int columnWidth = 1;
    private int rowHeight = 1;
    private int neighborColumns = 1;
    private int neighborRows = 1;
    private World world;
    private Rect rect = new Rect();
    private Paint paint = new Paint();

    public GameOfLife(Context context) {
        super(context);
        initWorld();
    }

    public GameOfLife(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initWorld();
    }

    @Override
    public void run() {
        while (isRunning) {
            if(!getHolder().getSurface().isValid())
                continue;

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
            }

            Canvas canvas = getHolder().lockCanvas();
            world.nextGeneration();
            drawCells(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning = false;

        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }

            break;
        }
    }

    private void initWorld() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        neighborColumns = point.x / DEFAULT_SIZE;
        neighborRows = point.y / DEFAULT_SIZE;

        columnWidth = point.x / neighborColumns;
        rowHeight = point.y / neighborRows;

        world = new World(neighborColumns, neighborRows);
    }

    private void drawCells(Canvas canvas) {
        for (int row = 0; row < neighborColumns; row++) {
            for (int col = 0; col < neighborRows; col++) {
                Cell cell = world.get(row, col);
                rect.set((cell.x * columnWidth) - 1, (cell.y * rowHeight) - 1,
                        (cell.x * columnWidth + columnWidth) - 1,
                        (cell.y * rowHeight + rowHeight) - 1);
                paint.setColor(cell.alive ? DEFAULT_ALIVE_COLOR : DEFAULT_DEAD_COLOR);
                canvas.drawRect(rect, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int row = (int) (event.getX() / columnWidth);
            int col = (int) (event.getY() / rowHeight);

            Cell cell = world.get(row, col);
            cell.invert();

            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
