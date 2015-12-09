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

    private static final String TABLE_SKILLMAP = "jpengskll";

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SKILLMAP);
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
}
