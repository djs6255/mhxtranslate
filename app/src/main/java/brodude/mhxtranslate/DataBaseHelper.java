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
    private static final String TABLE_KQUEST = "jpengkquest";
    private static final String TABLE_MONWEAK = "jpengmonweak";
    private static final String TABLE_PART = "jpengpart";

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
                + COL_ENG + " TEXT NOT NULL UNIQUE," + "type TEXT, level INTEGER, cost INTEGER, desc TEXT" + ")";
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KQUEST);
        String CREATE_KQUEST_TABLE = "CREATE TABLE " + TABLE_KQUEST + "("
                + COL_ID + " INTEGER PRIMARY KEY, " +  COL_JP + " TEXT NOT NULL,"
                + COL_ENG + " TEXT NOT NULL," + "location TEXT NOT NULL, rank INTEGER, type TEXT, note TEXT, t1 TEXT, qty INTEGER," +
                " t2 TEXT, t3 TEXT, t4 TEXT, jpt1 TEXT, jpt2 TEXT, jpt3 TEXT, jpt4 TEXT, UNIQUE (location, rank, " + COL_JP + "))";
        db.execSQL(CREATE_KQUEST_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONWEAK);
        String CREATE_MONWEAK_TABLE = "CREATE TABLE " + TABLE_MONWEAK + "(" + COL_ID + " INTEGER PRIMARY KEY, name TEXT, jpname TEXT, state TEXT, cut TEXT, blunt TEXT," +
                " shot TEXT, fire TEXT, water TEXT, thunder TEXT, ice TEXT, dragon TEXT, poison TEXT, paralysis TEXT, sleep TEXT," +
                " sTrap TEXT, pTrap TEXT, flash TEXT, sound TEXT, meat TEXT, roar TEXT, wind TEXT, tremor TEXT, note TEXT," +
                " UNIQUE (name, state))";
        db.execSQL(CREATE_MONWEAK_TABLE);
        String CREATE_PART_TABLE = "CREATE TABLE " + TABLE_PART + "(" + COL_ID + " INTEGER PRIMARY KEY, engart TEXT NOT NULL UNIQUE," +
                " jpart TEXT NOT NULL UNIQUE, forte TEXT, cost INTEGER, unlock TEXT, teach TEXT, desc TEXT)";
        db.execSQL(CREATE_PART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLMAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMMAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PSKILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HART);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KSKILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KQUEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONWEAK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PART);
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

    public void addPSkill(String engText, String jpText, String level, String cost, String desc, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_JP, jpText);
        values.put(COL_ENG, engText);
        values.put("level", Integer.valueOf(level));
        values.put("cost", Integer.valueOf(cost));
        values.put("desc", desc);
        values.put("type", type);
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

    public HashMap<String, String> getAllPSkillList(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + ", level, cost, desc, type FROM "
                + TABLE_PSKILL + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put("    " + cursor.getString(0)+ " - " + cursor.getString(1) + "\n        Level Unlocked - " + cursor.getString(2) + " Cost - " + cursor.getString(3) + "\n        "
                        + cursor.getString(4),cursor.getString(5) + " Skills");
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

    public HashMap<String, String> getAllHArtList(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + ", weapon, desc FROM "
                + TABLE_HART + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put("    " + cursor.getString(0)+ " - " + cursor.getString(1) + "\n        " + cursor.getString(3), cursor.getString(2));
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

    public HashMap<String, String> getAllKSkillList(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + ", desc FROM "
                + TABLE_KSKILL + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put("    Effect: " + cursor.getString(2), cursor.getString(0)+ " - " + cursor.getString(1));
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

    public void addKQuest(String location, String rank, String type, String engText, String note, String t1, String tqty, String t2, String t3, String t4,
                           String jpText, String jpt1, String jpt2, String jpt3, String jpt4) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_JP, jpText);
        values.put(COL_ENG, engText);
        values.put("location", location);
        values.put("rank", Integer.valueOf(rank));
        values.put("type", type);
        values.put("note", note);
        values.put("t1", t1);
        values.put("qty", tqty);
        values.put("t2", t2);
        values.put("t3", t3);
        values.put("t4", t4);
        values.put("jpt1", jpt1);
        values.put("jpt2", jpt2);
        values.put("jpt3", jpt3);
        values.put("jpt4", jpt4);
        db.insert(TABLE_KQUEST, null, values);
        db.close();
    }

    public HashMap<String, String> getAllKQuest(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT " + COL_JP + ", " + COL_ENG + ", location, rank, type FROM "
                + TABLE_KQUEST + " ORDER BY location DESC, rank ASC;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put("        " + cursor.getString(0) + " - " + cursor.getString(4) + " - " + cursor.getString(1), cursor.getString(2) + " - " + cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public void addMonWeak(String name, String jpname, String state, String cut, String blunt, String shot, String fire, String water,
                           String thunder, String ice, String dragon, String poison, String paralysis, String sleep, String sTrap,
                           String pTrap, String flash, String sound, String meat, String roar, String wind, String tremor, String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("jpname", jpname);
        values.put("state", state);
        values.put("cut", cut);
        values.put("blunt", blunt);
        values.put("shot", shot);
        values.put("fire", fire);
        values.put("water", water);
        values.put("thunder", thunder);
        values.put("ice", ice);
        values.put("dragon", dragon);
        values.put("poison", poison);
        values.put("paralysis", paralysis);
        values.put("sleep", sleep);
        values.put("sTrap", sTrap);
        values.put("pTrap", pTrap);
        values.put("flash", flash);
        values.put("sound", sound);
        values.put("meat", meat);
        values.put("roar", roar);
        values.put("wind", wind);
        values.put("tremor", tremor);
        values.put("note", note);
        db.insert(TABLE_MONWEAK, null, values);
        db.close();
    }

    public HashMap<String, String> getAllMonWeak(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT name, jpname, state, fire, water, thunder, ice, dragon, poison, paralysis, sleep, sTrap, pTrap, flash, " +
                "sound, meat, roar, wind, tremor, note FROM "
                + TABLE_MONWEAK + " ORDER BY name ASC;";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                String output = "";
                if (!cursor.getString(2).equals("-")){
                    output = "    " + cursor.getString(2) + "\n";
                }
                output += "        Fire - " + cursor.getString(3) + ", Water - " + cursor.getString(4)
                        + ", Thunder - " + cursor.getString(5) + ", Ice - " + cursor.getString(6) + ", Dragon - "
                        + cursor.getString(7) + "\n        Poison - " + cursor.getString(8) + ", Paralysis - " + cursor.getString(9)
                        + ", Sleep - " + cursor.getString(10) + "\n        Shock Trap - " + cursor.getString(11) + ", Pitfall Trap - "
                        + cursor.getString(12) + "\n        Flash Bomb - " + cursor.getString(13) + ", Sonic Bomb - " + cursor.getString(14)
                        + ", Meat - " + cursor.getString(15) + "\n        Roar - ";
                if (cursor.getString(16).equals("-") || cursor.getString(16).equals("")) {
                    output += "N/A, Wind - ";
                }
                else {
                    output += cursor.getString(16) + ", Wind - ";
                }
                if (cursor.getString(17).equals("-") || cursor.getString(17).equals("")) {
                    output += "N/A, Tremor - ";
                }
                else {
                    output += cursor.getString(17) + ", Tremor - ";
                }
                if (cursor.getString(18).equals("-") || cursor.getString(18).equals("")) {
                    output += "N/A";
                }
                else {
                    output += cursor.getString(18);
                }
                itemList.put(output, cursor.getString(0) + " - " + cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return itemList;
    }

    public void addPArt(String engart, String jpart, String forte, String cost, String unlock, String teach, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("jpart", jpart);
        values.put("engart", engart);
        values.put("forte", forte);
        values.put("cost", Integer.valueOf(cost));
        values.put("unlock", unlock);
        values.put("teach", teach);
        values.put("desc", desc);
        db.insert(TABLE_PART, null, values);
        db.close();
    }

    public HashMap<String, String> getAllPArt(){
        HashMap<String, String> itemList = new HashMap<String, String>();

        String itemQuery = "SELECT engart, jpart, forte, cost, unlock, teach, desc FROM "
                + TABLE_PART + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(itemQuery, null);

        if(cursor.moveToFirst()) {
            do {
                itemList.put("        Cost - " + cursor.getString(3) + " Teach - " + cursor.getString(5) + " Forte - " + cursor.getString(2) + "\n        Unlock - "
                        + cursor.getString(4) + "\n        " + cursor.getString(6), cursor.getString(1) + " - " + cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return itemList;
    }
}
