package simpleadapter.stx.com.simpleadapter.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "person.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, phone VARCHAR, info TEXT)");
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"超哥", "18875050386", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"肖逗", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"谭美女", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"荷花", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"杨贵妃", "18888888888", ""});
        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"刘帅哥", "18888888888", ""});


//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"肖逗", "18502339836", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"谭美女", "15334513069", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"荷花", "18723549123", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"杨贵妃", "18883339682", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"刘帅哥", "13310237646", ""});


//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"负责老师 李勇", "15223188957", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"负责人 马碧悦", "15223265570", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"超神陆战队组长 张超", "18875050386", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"土豪斗地主组长 尹付豪", "13996616085", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"某个组组长 谭登兆", "13310232090", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"SixGod组长 张鑫", "18623198412", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"北斗Plus组长 阿杰", "13310237974", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"风雨五组 席廷", "18996214773", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"工作人员 崔玉婷", "15223314255", ""});
//        db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?)", new Object[]{"工作人员 王森", "15223263305", ""});

    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }
}