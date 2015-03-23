/*
 * Copyright (C) 2014 Antonio Leiva Gordillo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.biovoso.Triggers;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeActivity extends BaseActivity {

    private List<Integer> ids;

    private RecyclerView navRec;
    private RecyclerView.Adapter navRecAdap;
    private RecyclerView.LayoutManager navRecLM;

    private RecyclerView mainRec;
    private ButtonAdapter mainRecAdap;
    private LinearLayoutManager mainRecLM;

    private String TITLES[] = {"Home","Events","Mail","Shop","Travel"};
    private DrawerLayout drawer;
    private ActionBarDrawerToggle drawerToggle;
    private ImageButton fabImageButton;
    private boolean editMode = false;
    private boolean startMode = true;

    private AnimatorSet openAnimSet = new AnimatorSet();
    private AnimatorSet closeAnimSet = new AnimatorSet();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d("test", "start onCreate");
        super.onCreate(savedInstanceState);

        final Intent intent = new Intent(this, EditActivity.class);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabImageButton = (ImageButton) findViewById(R.id.fab_image_button);

        fabImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                db.createButton(createList(1).get(0));
//                mainRecAdap.redoList(db.getButtons(0));
                startActivity(intent);
            }
        });


        // FIX THIS
        Group group0 = new Group();
        group0.id = 0;
        group0.name = "zero";
        db.createGroup(group0);

        navRec = (RecyclerView) findViewById(R.id.RecyclerView);
        navRec.setHasFixedSize(true);
        navRecAdap = new navAdapter(TITLES, this);
        navRec.setAdapter(navRecAdap);
        navRecLM = new LinearLayoutManager(this);
        navRec.setLayoutManager(navRecLM);

        mainRec = (RecyclerView) findViewById(R.id.cardList);
        mainRec.setHasFixedSize(true);
        mainRecLM = new LinearLayoutManager(this);
        mainRecLM.setOrientation(LinearLayoutManager.VERTICAL);
        mainRec.setLayoutManager(mainRecLM);
        mainRec.setItemAnimator(new DefaultItemAnimator());
        mainRecAdap = new ButtonAdapter(db.getButtons(0));
        mainRec.setAdapter(mainRecAdap);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);
        drawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // if in edit mode to go back to normal mode
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        };
        drawer.setDrawerListener(drawerToggle); // drawer Listener set to the drawer toggle
        drawerToggle.syncState();               // Finally we set the drawer toggle sync State

        loadIcons();
        Log.d("test", "end onCreate");

    }

    @Override
    protected void onResume()
    {
        Log.d("test", "start onResume");
        super.onResume();
        Log.d("test", "end onResume");

    }

    @Override
    protected void onPostResume()
    {
        Log.d("test", "start onPostResume");
        super.onPostResume();
        Log.d("test", "end onPostResume");

    }

    private void loadIcons()
    {
        this.ids = new ArrayList<Integer>();
        Field[] drawables = R.drawable.class.getFields();
        for (Field f : drawables) {
            try
            {
                String fName = f.getName();
                if(fName.startsWith("icon"))
                {
                    ids.add(getResources().getIdentifier(f.getName(), "drawable", getPackageName()));
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    @Override protected int getLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("test", "start onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        Log.d("test", "end onCreateOptionsMenu");
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d("test", "start onPrepareOptionsMenu");
        super.onPrepareOptionsMenu(menu);
        Log.d("test", "end onPrepareOptionsMenu");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_mainedit:
                if (!editMode)
                {
                    if(startMode)
                    {
                        createAnimatorSets();
                        startMode = false;
                    }
                    openAnimSet.start();
                    editMode = true;
                }
                else
                {
                    closeAnimSet.start();
                    editMode = false;
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Button> createList(int size) {

        List<Button> result = new ArrayList<Button>();
        for (int i=1; i <= size; i++)
        {
            Button ci = new Button(this);
            ci.name = Integer.toString(i);
            ci.description = "udfiufbiub";
            Random random = new Random();
            ci.iconId = ids.get(random.nextInt(ids.size()));
            result.add(ci);
        }

        return result;
    }

    private void createAnimatorSets()
    {
        final ActionMenuItemView editButton = (ActionMenuItemView) toolbar.getChildAt(1).findViewById(R.id.action_edit);
        final ActionMenuItemView closeButton = (ActionMenuItemView) toolbar.getChildAt(1).findViewById(R.id.action_close);
        final ActionMenuItemView mainButton = (ActionMenuItemView) toolbar.getChildAt(1).findViewById(R.id.action_mainedit);

        float fabOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, -86, getResources().getDisplayMetrics());
        int cardClosedHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
        int cardOpenHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 158, getResources().getDisplayMetrics());

        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);

        if(editButton != null && closeButton != null)
        {
            closeButton.setIcon(getResources().getDrawable(R.drawable.exitediting));
            closeButton.setAlpha(0f);
            closeButton.setTranslationX(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 96, getResources().getDisplayMetrics()));
            //mainButton.setAlpha(0f);
            mainButton.setIcon(getResources().getDrawable(R.drawable.mainbutton));
            editButton.setIcon(getResources().getDrawable(R.drawable.editbutton));
            editButton.setTranslationX(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()));


            //Initial press of edit button

            // Edit button going out
            ObjectAnimator editOut_translateY = ObjectAnimator.ofFloat(editButton, "translationY", 0, -0.3f * actionBarHeight);
            editOut_translateY.setDuration(150);
            ObjectAnimator editOut_alpha = ObjectAnimator.ofFloat(editButton, "alpha", 1f, 0f);
            editOut_alpha.setDuration(100);
            editOut_alpha.setStartDelay(50);

            // Close button going in
            ObjectAnimator closeIn_translateY = ObjectAnimator.ofFloat(closeButton, "translationY", 0.3f * actionBarHeight, 0);
            closeIn_translateY.setDuration(150);
            ObjectAnimator closeIn_alpha = ObjectAnimator.ofFloat(closeButton, "alpha", 0f, 1f);
            closeIn_alpha.setDuration(100);
            closeIn_alpha.setStartDelay(50);

            // FAB going in
            ObjectAnimator floatIn = ObjectAnimator.ofFloat(fabImageButton, "translationY", 0, fabOffset);

            // Card height growing
            ValueAnimator cardIn_height = ValueAnimator.ofInt(cardClosedHeight, cardOpenHeight);
            cardIn_height.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    mainRecAdap.setHeight((Integer) animation.getAnimatedValue());
                }
            });


            //Press of close edit button


            // Edit button going in
            ObjectAnimator editIn_translateY = ObjectAnimator.ofFloat(editButton, "translationY", -0.3f * actionBarHeight, 0);
            editIn_translateY.setDuration(150);
            ObjectAnimator editIn_alpha = ObjectAnimator.ofFloat(editButton, "alpha", 0f, 1f);
            editIn_alpha.setDuration(100);
            editIn_alpha.setStartDelay(50);

            // Close button going out
            ObjectAnimator closeOut_translateY = ObjectAnimator.ofFloat(closeButton, "translationY", 0,  0.3f * actionBarHeight);
            closeOut_translateY.setDuration(150);
            ObjectAnimator closeOut_alpha = ObjectAnimator.ofFloat(closeButton, "alpha", 1f, 0f);
            closeOut_alpha.setDuration(100);
            closeOut_alpha.setStartDelay(50);

            // FAB going out
            ObjectAnimator floatOut = ObjectAnimator.ofFloat(fabImageButton, "translationY", fabOffset, 0);


            // Card height shrinking
            ValueAnimator cardOut_height = ValueAnimator.ofInt(cardOpenHeight, cardClosedHeight);
            cardOut_height.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    mainRecAdap.setHeight((Integer) animation.getAnimatedValue());
                }
            });

            openAnimSet.playTogether(editOut_translateY, editOut_alpha, closeIn_translateY, closeIn_alpha, floatIn, cardIn_height);
            closeAnimSet.playTogether(editIn_translateY, editIn_alpha, closeOut_translateY, closeOut_alpha, floatOut, cardOut_height);

        }

    }

}
