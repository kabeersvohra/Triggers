package com.biovoso.Triggers;

import android.os.Bundle;

/**
 * Created by mmanghnani on 21/03/15.
 */
public class EditActivity extends BaseActivity
{
    @Override
    protected int getLayoutResource()
    {
        return R.layout.activity_edit;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.animation_exit, R.anim.animation_enter);
    }


}
