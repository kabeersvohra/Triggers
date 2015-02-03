package com.biovoso.Triggers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Contacts;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kabeer Vohra on 29/01/2015.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "triggers";
    private static final String TABLE_BUTTONS = "buttons";
    private static final String TABLE_GROUPS = "groups";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IID = "iid";
    private static final String KEY_DESC = "desc";
    private static final String KEY_GID = "gid";
    private Context context;

    private static final String CREATE_TABLE_BUTTONS = "CREATE TABLE "
            + TABLE_BUTTONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT," + KEY_DESC + " TEXT,"
            + KEY_IID + " INTEGER," + KEY_GID + " INTEGER" + ")";

    private static final String CREATE_TABLE_GROUPS = "CREATE TABLE "
            + TABLE_GROUPS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT" + ")";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_BUTTONS);
        db.execSQL(CREATE_TABLE_GROUPS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUTTONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);

        onCreate(db);
    }

    public long createButton(Button button) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, button.name);
        values.put(KEY_DESC, button.description);
        values.put(KEY_GID, button.groupId);
        values.put(KEY_IID, button.iconId);

        long button_id = db.insert(TABLE_BUTTONS, null, values);

        return button_id;
    }

    public long createGroup(Group group)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.name);

        long group_id = db.insert(TABLE_BUTTONS, null, values);

        return group_id;
    }

    public List<Button> getButtons(int gid)
    {
        List<Button> buttons = new ArrayList<Button>();
        String selectQuery = "SELECT * FROM " + TABLE_BUTTONS + " WHERE " + KEY_GID + " = " + gid;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Button btn = new Button(context);
                btn.id = (c.getInt((c.getColumnIndex(KEY_ID))));
                btn.name = ((c.getString(c.getColumnIndex(KEY_NAME))));
                btn.description = (c.getString(c.getColumnIndex(KEY_DESC)));
                btn.iconId = (c.getInt(c.getColumnIndex(KEY_IID)));
                btn.groupId = (c.getInt(c.getColumnIndex(KEY_GID)));
                buttons.add(btn);
            } while (c.moveToNext());
        }

        return buttons;
    }

    public List<Group> getGroups()
    {
        List<Group> groups = new ArrayList<Group>();
        String selectQuery = "SELECT * FROM " + TABLE_GROUPS;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Group grp = new Group();
                grp.id = (c.getInt((c.getColumnIndex(KEY_ID))));
                grp.name = ((c.getString(c.getColumnIndex(KEY_NAME))));
                grp.buttons = getButtons(grp.id);
                groups.add(grp);
            } while (c.moveToNext());
        }

        return groups;
    }

    public int updateButton(Button button)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, button.name);
        values.put(KEY_DESC, button.description);
        values.put(KEY_IID, button.iconId);
        values.put(KEY_GID, button.groupId);

        return db.update(TABLE_BUTTONS, values, KEY_ID + " = " + button.id, null);
    }

    public int updateGroup(Group group)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, group.name);

        return db.update(TABLE_GROUPS, values, KEY_ID + " = " + group.id, null);
    }

    public void deleteButton(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BUTTONS, KEY_ID + " = " + id, null);
    }

    public void deleteGroup(Group group)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BUTTONS, KEY_GID + " = " + group.id, null);
        db.delete(TABLE_GROUPS, KEY_ID + " = " + group.id, null);
    }

    public void closeDB()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
