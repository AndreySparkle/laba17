package com.example.laba17.presentation.profile;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Purpose: Draws a deterministic local QR-style loyalty code without external libraries.
 * Created: 2026-06-15.
 * Author: Codex.
 * Main methods: setValue changes encoded data; onDraw renders the code grid.
 */
public class QrCodeView extends View {

    private static final int GRID_SIZE = 25;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String value = "";

    public QrCodeView(Context context) {
        this(context, null);
    }

    public QrCodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
    }

    public void setValue(String value) {
        this.value = value == null ? "" : value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float cell = Math.min(getWidth(), getHeight()) / (float) GRID_SIZE;
        paint.setColor(Color.BLACK);
        int seed = value.hashCode();
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                boolean finder = isFinderCell(row, column);
                int bit = Integer.rotateLeft(seed, (row * GRID_SIZE + column) % 31);
                if (finder || (bit & 1) == 1) {
                    canvas.drawRect(
                            column * cell,
                            row * cell,
                            (column + 1) * cell,
                            (row + 1) * cell,
                            paint
                    );
                }
            }
        }
    }

    private boolean isFinderCell(int row, int column) {
        return isFinderAt(row, column, 1, 1)
                || isFinderAt(row, column, 1, GRID_SIZE - 8)
                || isFinderAt(row, column, GRID_SIZE - 8, 1);
    }

    private boolean isFinderAt(int row, int column, int top, int left) {
        int localRow = row - top;
        int localColumn = column - left;
        if (localRow < 0 || localRow > 6 || localColumn < 0 || localColumn > 6) {
            return false;
        }
        return localRow == 0 || localRow == 6 || localColumn == 0 || localColumn == 6
                || localRow >= 2 && localRow <= 4 && localColumn >= 2 && localColumn <= 4;
    }
}
