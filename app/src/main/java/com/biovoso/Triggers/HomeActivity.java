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

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity {

    private DrawerLayout drawer;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarIcon(R.drawable.ic_ab_drawer);

        db = new DatabaseHelper(getApplicationContext());

        loadIcons();

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        ButtonAdapter ca = new ButtonAdapter(createList(30));
        recList.setAdapter(ca);
    }

    private void loadIcons()
    {
        List<Integer> ids = new ArrayList<Integer>();
        Field[] drawables = R.drawable.class.getFields();
        for (Field f : drawables) {
            try
            {
                String fName = f.getName();
                if(fName.substring(0,4) == "icon")
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
            case android.R.id.home:
                drawer.openDrawer(Gravity.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Button> createList(int size) {

        List<Button> result = new ArrayList<Button>();
        for (int i=1; i <= size; i++) {
            Button ci = new Button(this);
            ci.name = Integer.toString(i);

            result.add(ci);

        }

        return result;
    }

}
