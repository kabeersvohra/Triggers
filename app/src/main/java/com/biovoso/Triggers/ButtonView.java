package com.biovoso.Triggers;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;

/**
 * Created by mmanghnani on 11/03/15.
 */
public class ButtonView extends CardView
{
    int CLOSED_HEIGHT;
    int OPEN_HEIGHT;

    public ButtonView(Context context)
    {
        super(context);

        CLOSED_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, context.getResources().getDisplayMetrics());
        OPEN_HEIGHT =  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 158, context.getResources().getDisplayMetrics());
    }

    public ButtonView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ButtonView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public void setHeight(int height)
    {
        getLayoutParams().height = height;
        requestLayout();
    }
}
