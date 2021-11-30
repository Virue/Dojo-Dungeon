package com.example.dojodungeons;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperQuest extends SQLiteOpenHelper{

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "quest_db";/*formerly notes_db*/

    public static final String TABLE_NAME = "quests";

    public static final String COLUMN_QUESTNUM = "QuestNum";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_STAMINAVALUE = "StaminaValue";
    public static final String COLUMN_PROGRESS = "progress";
    public static final String COLUMN_CURRENT = "current";
    public static final String COLUMN_GOAL = "goal";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_QUESTNUM + "INT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_STAMINAVALUE + " INT,"
                    + COLUMN_PROGRESS + "INT,"
                    + COLUMN_CURRENT + "INT,"
                    + COLUMN_GOAL + "INT"
                    + ")";

    public DatabaseHelperQuest(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create quest table
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public Quest getQuest(long questNum) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_QUESTNUM, COLUMN_DESCRIPTION, COLUMN_STAMINAVALUE, COLUMN_PROGRESS, COLUMN_CURRENT, COLUMN_GOAL},
                COLUMN_QUESTNUM + "=?",
                new String[]{String.valueOf(questNum)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        @SuppressLint("Range") Quest quest = new Quest(
                cursor.getInt(cursor.getColumnIndex(COLUMN_QUESTNUM)),
                cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_STAMINAVALUE)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_PROGRESS)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_GOAL)));

        // close the db connection
        cursor.close();

        return quest;
    }

    public String getCurrentDescription(long current) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_QUESTNUM, COLUMN_DESCRIPTION, COLUMN_STAMINAVALUE, COLUMN_PROGRESS, COLUMN_CURRENT, COLUMN_GOAL},
                COLUMN_CURRENT + "=?",
                new String[]{String.valueOf(current)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        @SuppressLint("Range") String currentDescription = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));


        // close the db connection
        cursor.close();

        return currentDescription;
    }

    @SuppressLint("Range")
    public List<Quest> getAllQuests() {
        List<Quest> quests = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " +
                COLUMN_QUESTNUM + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Quest quest = new Quest();
                quest.setquestNum(cursor.getInt(cursor.getColumnIndex(COLUMN_QUESTNUM)));
                quest.setdescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                quest.setstaminaValue(cursor.getInt(cursor.getColumnIndex(COLUMN_STAMINAVALUE)));
                quest.setprogress(cursor.getInt(cursor.getColumnIndex(COLUMN_PROGRESS)));
                quest.setprogress(cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT)));
                quest.setprogress(cursor.getInt(cursor.getColumnIndex(COLUMN_GOAL)));
                quests.add(quest);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return quests;
    }

    public int getQuestsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int updateQuestProgress(Quest quest) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PROGRESS, quest.getprogress());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_QUESTNUM + " = ?",
                new String[]{String.valueOf(quest.getquestNum())});
    }


    public void deleteQuest(Quest quest) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_QUESTNUM + " = ?",
                new String[]{String.valueOf(quest.getquestNum())});
        db.close();
    }
}
