package com.biovoso.Triggers;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Button {
    protected int id;
    protected String name;
    protected String description;
    protected int iconId = 0;
    protected int groupId;
    protected Context context;

    public Button(Context context)
    {
        this.context = context;
    }

    public Drawable getIcon()
    {
        if(this.iconId != 0)
            return context.getResources().getDrawable(iconId);
        else
            return null;
    }
}
