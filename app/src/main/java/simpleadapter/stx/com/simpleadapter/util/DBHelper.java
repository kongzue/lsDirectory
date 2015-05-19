package simpleadapter.stx.com.simpleadapter.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, phone VARCHAR, info TEXT,sex VARCHAR)");

        //假设拥有的用户数据库
        db.execSQL("CREATE TABLE IF NOT EXISTS user" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR)");
        db.execSQL("INSERT INTO user VALUES(null, ?, ?)", new Object[]{"chaoshen", "123456"});

        //本地保存登录的用户信息
        db.execSQL("CREATE TABLE IF NOT EXISTS localuser" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR,phone VARCHAR)");
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE IF NOT EXISTS localuser" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, password VARCHAR,phone VARCHAR)");

    }
}