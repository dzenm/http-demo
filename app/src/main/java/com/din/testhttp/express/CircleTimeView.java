package com.din.testhttp.express;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class CircleTimeView extends View{
    private Rect mBounds = new Rect();

    public CircleTimeView(Context context) {
        super(context);
    }
    public CircleTimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CircleTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(new Color().parseColor("#4CAF50"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);

        paint.setAntiAlias(true);
        paint.setColor(new Color().parseColor("#f2f2f2"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 4, paint);

    }

}
