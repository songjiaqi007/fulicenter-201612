package cn.ucai.fulicenter.model.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by liuning on 2017/3/21.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String FULICENTER_USER_TABLE_CREATE =
            "CREATE TABLE " + UserDao.USER_TABLE_NAME + " ("
                    + UserDao.USER_COLUMN_NAME + " TEXT PRIMARY KEY,"
                    + UserDao.USER_COLUMN_NICK + " TEXT ,"
                    + UserDao.USER_COLUMN_AVATAR_ID + " INTEGER,"
                    + UserDao.USER_COLUMN_AVATAR_PATH + " TEXT,"
                    + UserDao.USER_COLUMN_AVATAR_SUFFIX + " TEXT,"
                    + UserDao.USER_COLUMN_AVATAR_TYPE + " TEXT, "
                    + UserDao.USER_COLUMN_AVATAR_LASTUPDATE_TIME + " TEXT);";


    private static DBOpenHelper instance;

    public static DBOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBOpenHelper(context);
        }
        return instance;
    }

    public DBOpenHelper(Context context) {
        super(context, getDatabaseNames(), null, DATABASE_VERSION);
    }


    private static String getDatabaseNames() {
        return "cn.ucai.fulicenter.db";
    }

    private String mDatabaseNames;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FULICENTER_USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void closeDB() {
        if (instance != null) {
            SQLiteDatabase db = instance.getWritableDatabase();
            db.close();
            instance = null;
        }
    }

}
