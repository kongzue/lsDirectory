package simpleadapter.stx.com.simpleadapter.util;

/**
 * Created by chao on 2015/4/20.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import simpleadapter.stx.com.simpleadapter.MainActivity;

public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     * @param persons
     */
    public boolean addPerson(List<Person> persons, Context context) {
        boolean flag = false;
        db.beginTransaction();  //开始事务
        try {
            for (Person person : persons) {
                Cursor c = db.rawQuery("SELECT * FROM person where phone=" + person.phone, null);
                String findPhone = "";
                while (c.moveToNext()) {
                    findPhone = c.getString(c.getColumnIndex("phone"));
                }
                if (findPhone.equals("")) {
                    db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?, ?)", new Object[]{person.name, person.phone, person.info, person.sex});
                    flag = true;
                } else {
                    Toast.makeText(context, "添加联系人失败，该联系人已存在！", Toast.LENGTH_SHORT).show();
                }
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
        return flag;
    }

    /**
     * update person's age
     * @param person
     */
    public void updatePhone(Person person) {
        ContentValues cv = new ContentValues();
        cv.put("phone", person.phone);
        db.update("person", cv, "name = ?", new String[]{person.name});
    }

    /**
     * delete old person
     * @param person
     */
    public void delete(Person person) {
        db.delete("person", "phone = ?", new String[]{String.valueOf(person.phone)});
    }

    /**
     * query all persons, return list
     * @return List<Person>
     */
    public List<Person> query() {
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            Person person = new Person();
            person._id = c.getInt(c.getColumnIndex("_id"));
            person.name = c.getString(c.getColumnIndex("name"));
            person.phone = c.getString(c.getColumnIndex("phone"));
            person.info = c.getString(c.getColumnIndex("info"));
            person.sex = c.getString(c.getColumnIndex("sex"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * query all persons, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        return c;
    }

    public String queryDB(String strDatabase, String findColumn, String strWhere) {
        Cursor c = db.rawQuery("SELECT * FROM " + strDatabase + " " + strWhere, null);
        String resultStr = "";
        try {
            while (c.moveToNext()) {
                resultStr = c.getString(c.getColumnIndex(findColumn));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c.close();
        }

        return resultStr;
    }

    public void insertDB(String strDatabase, String values) {

        db.beginTransaction();  //开始事务
        db.execSQL("INSERT INTO " + strDatabase + " VALUES(" + values + ")");
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

    public void InitializeUserTable(String result) {
        db.beginTransaction();  //开始事务

        String strsResultStr[] = result.split(";");
        for (int i = 0; i < strsResultStr.length - 1; i++) {
            String userProperty[] = strsResultStr[i].split("§");

            Cursor c = db.rawQuery("SELECT * FROM person where phone=" + userProperty[1], null);
            String findPhone = "";
            while (c.moveToNext()) {
                findPhone = c.getString(c.getColumnIndex("phone"));
            }
            if (findPhone.equals("")) {
                db.execSQL("INSERT INTO person VALUES(null, ?, ?, ?, ?)", new Object[]{userProperty[0], userProperty[1], "", userProperty[2]});
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public String getAllUserTable() {
        Cursor c = db.rawQuery("SELECT * FROM person", null);
        String resultStr = "";
        while (c.moveToNext()) {
            resultStr = resultStr + c.getString(c.getColumnIndex("name")) + "§";
            resultStr = resultStr + c.getString(c.getColumnIndex("phone")) + "§";
            resultStr = resultStr + c.getString(c.getColumnIndex("sex")) + ";";
        }
        c.close();
        return resultStr;
    }
}