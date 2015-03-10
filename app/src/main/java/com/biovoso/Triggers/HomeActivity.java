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
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class HomeActivity extends BaseActivity {

    private DatabaseHelper db;
    private List<Integer> ids;

    private Toolbar toolbar;

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
    private ObjectAnimator floatIn;
    private ObjectAnimator floatOut;
    private float fabOffset;

    private AnimatorSet openAnimSet = new AnimatorSet();;
    private AnimatorSet closeAnimSet;

    private int cardClosedHeight;
    private int cardOpenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fabOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 72, getResources().getDisplayMetrics());
        cardClosedHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 110, getResources().getDisplayMetrics());
        cardOpenHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 158, getResources().getDisplayMetrics());


        fabImageButton = (ImageButton) findViewById(R.id.fab_image_button);

        fabImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.createButton(createList(1).get(0));
                mainRecAdap.redoList(db.getButtons(0));
            }
        });

        floatIn = ObjectAnimator.ofFloat(fabImageButton, "translationX", 0, (fabOffset * -1));
        floatOut = ObjectAnimator.ofFloat(fabImageButton, "translationX", 0, fabOffset);

        db = new DatabaseHelper(getApplicationContext());
        Group group0 = new Group();
        group0.id = 0;
        group0.name = "zero";
        db.createGroup(group0);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                drawer.openDrawer(Gravity.START);
//                return true;
            case R.id.action_edit:
                //I want to make the edit button smaller and when the button is pressed to
                // change to an x and call another function onselect when changed but I don't
                // know how
                floatIn.start();
                fabImageButton.setTranslationX(-1 * fabOffset);
                populateAnimators();
                openAnimSet.start();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Button> createList(int size) {

        List<Button> result = new ArrayList<Button>();
        for (int i=1; i <= size; i++) {
            Button ci = new Button(this);
            ci.name = Integer.toString(i);
            ci.description = "udfiufbiub";
            Random random = new Random();
            ci.iconId = ids.get(random.nextInt(ids.size()));
            result.add(ci);
        }

        return result;
    }

    private void populateAnimators()
    {
        ArrayList<ValueAnimator> valueAnimators = new ArrayList<ValueAnimator>();
        for(int i = 0; i < mainRec.getChildCount(); i++)
        {
            final View view = mainRec.getChildAt(i);
            ValueAnimator valueAnimator = ValueAnimator.ofInt(cardClosedHeight, cardOpenHeight);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                    view.requestLayout();
                }
            });
            valueAnimators.add(valueAnimator);
        }
        ValueAnimator[] objectAnimators = valueAnimators.toArray(new ValueAnimator[valueAnimators.size()]);

        if (mainRec.getChildCount() > 0)
        {
            openAnimSet.playTogether(objectAnimators);
        }
    }

}
