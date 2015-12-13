/**
 * Created by Darrell on 12/7/2015.
 * Code covers the sqLite database for the list of skills
 */
package brodude.mhxtranslate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "mhxSkillMap.db";

    private static final String TABLE_SKILLMAP = "jpengskill";
    private static final String TABLE_ITEMMAP = "jpengitem";
    private static final String TABLE_PSKILL = "jpengpskill";
    private static final String TABLE_HART = "jpenghart";
    private static final String TABLE_KSKILL = "jpengkskill";

    private static final String COL_ID = "id";
    private static final String COL_JP = "jpskill";
    private static final String COL_ENG = "engskill";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLMAP);
        String CREATE_SKILLS_TABLE = "CREATE TABLE " + TABLE_SKILLMAP + "("
                + COL_ID + " INTEGER PRIMARY KEY, " +  COL_JP + " TEXT NOT NULL UNIQUE,"
                + COL_ENG + " TEXT NOT NULL UNIQUE" + ")";
        db.execSQL(CREATE_SKILLS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMMAP);
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEMMAP + "("
                + COL_ID + " INTEGER PRIMARY KEY, " +  COL_JP + " TEXT NOT NULL UNIQUE,"
                + COL_ENG + " TEXT NOT NULL UNIQUE" + ")";
        db.execSQL(CREATE_ITEM_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PSKILL);
        String CREATE_PSKILL_TABLE = "CREATE TABLE " + TABLE_PSKILL + "("
                + COL_ID + " INTEGER PRIMARY KEY, " +  COL_JP + " TEXT NOT NULL UNIQUE,"
                + COL_ENG + " TEXT NOT NULL UNIQUE," + "level INTEGER, cost INTEGER, desc TEXT" + ")";
        db.execSQL(CREATE_PSKILL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HART);
        String CREATE_HART_TABLE = "CREATE TABLE " + TABLE_HART + "("
                + COL_ID + " INTEGER PRIMARY KEY, " +  COL_JP + " TEXT NOT NULL UNIQUE,"
                + COL_ENG + " TEXT NOT NULL UNIQUE," + "weapon TEXT NOT NULL, desc TEXT, unlock TEXT" + ")";
        db.execSQL(CREATE_HART_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KSKILL);
        String CREATE_KSKILL_TABLE = "CREATE TABLE " + TABLE_KSKILL + "("
                + COL_ID + " INTEGER PRIMARY KEY, " +  COL_JP + " TEXT NOT NULL UNIQUE,"
                + COL_ENG + " TEXT NOT NULL UNIQUE," + "mealno INTEGER, daily INTEGER, wine INTEGER, descjp TEXT, desc TEXT" + ")";
        db.execSQL(CREATE_KSKILL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLMAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMMAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PSKILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KSKILL);
        onCreate(db);
    }

    public void addSkill(String engText, String jpText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_JP, jpText);
        values.put(COL_ENG, engText);
        db.insert(TABLE_SKILLMAP, null, values);
        db.close();
    }

    public HashMap<String, String> getSkillsByLength(String length){
        HashMap<String, String> skillList = new HashMap<String, String>();

        String lengthQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_SKILLMAP + " WHERE LENGTH(" + COL_JP + ") = " + length + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(lengthQuery, null);

        if(cursor.moveToFirst()) {
            do {
                skillList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return skillList;
    }

    public HashMap<String, String> getSkillsByLike(String like){
        HashMap<String, String> skillList = new HashMap<String, String>();

        String lengthQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_SKILLMAP + " WHERE " + COL_JP + " LIKE + '" + like + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(lengthQuery, null);

        if(cursor.moveToFirst()) {
            do {
                skillList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return skillList;
    }

    public void addItem(String engText, String jpText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_JP, jpText);
        values.put(COL_ENG, engText);
        db.insert(TABLE_ITEMMAP, null, values);
        db.close();
    }

    public HashMap<String, String> getAllItems(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_ITEMMAP + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public HashMap<String, String> getItemsByLike(String like){
        HashMap<String, String> skillList = new HashMap<String, String>();

        String lengthQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_ITEMMAP + " WHERE " + COL_JP + " LIKE + '" + like + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(lengthQuery, null);

        if(cursor.moveToFirst()) {
            do {
                skillList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return skillList;
    }

    public void addPSkill(String engText, String jpText, String level, String cost, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_JP, jpText);
        values.put(COL_ENG, engText);
        values.put("level", Integer.valueOf(level));
        values.put("cost", Integer.valueOf(cost));
        values.put("desc", desc);
        db.insert(TABLE_PSKILL, null, values);
        db.close();
    }

    public HashMap<String, String> getAllPSkill(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_PSKILL + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public HashMap<String, String> getPSkillByLike(String like){
        HashMap<String, String> skillList = new HashMap<String, String>();

        String lengthQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_PSKILL + " WHERE " + COL_JP + " LIKE + '" + like + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(lengthQuery, null);

        if(cursor.moveToFirst()) {
            do {
                skillList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return skillList;
    }
    
    public void addHArt(String engText, String jpText, String weapon, String desc, String unlock) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_JP, jpText);
        values.put(COL_ENG, engText);
        values.put("weapon", weapon);
        values.put("desc", desc);
        values.put("unlock", unlock);
        db.insert(TABLE_HART, null, values);
        db.close();
    }

    public HashMap<String, String> getAllHArt(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_HART + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public HashMap<String, String> getHArtByLike(String like){
        HashMap<String, String> skillList = new HashMap<String, String>();

        String lengthQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_HART + " WHERE " + COL_JP + " LIKE + '" + like + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(lengthQuery, null);

        if(cursor.moveToFirst()) {
            do {
                skillList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return skillList;
    }

    public void addKSkill(String engText, String jpText, String mealno, String daily, String wine, String descjp, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_JP, jpText);
        values.put(COL_ENG, engText);
        values.put("mealno", Integer.valueOf(mealno));
        values.put("daily", Integer.valueOf(daily));
        values.put("wine", Integer.valueOf(wine));
        values.put("descjp", descjp);
        values.put("desc", desc);
        db.insert(TABLE_KSKILL, null, values);
        db.close();
    }

    public HashMap<String, String> getAllKSkill(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_KSKILL + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public HashMap<String, String> getKSkillByLike(String like){
        HashMap<String, String> skillList = new HashMap<String, String>();

        String lengthQuery = "SELECT " + COL_JP + ", " + COL_ENG + " FROM "
                + TABLE_KSKILL + " WHERE " + COL_JP + " LIKE + '" + like + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(lengthQuery, null);

        if(cursor.moveToFirst()) {
            do {
                skillList.put(cursor.getString(0), cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return skillList;
    }
    
}
