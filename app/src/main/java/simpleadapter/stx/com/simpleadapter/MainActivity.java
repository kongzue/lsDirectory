package simpleadapter.stx.com.simpleadapter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import simpleadapter.stx.com.simpleadapter.util.DBManager;
import simpleadapter.stx.com.simpleadapter.util.MyAdapter;
import simpleadapter.stx.com.simpleadapter.util.MyButton;
import simpleadapter.stx.com.simpleadapter.util.Person;


public class MainActivity extends BaseActivity {

    private ListView lv;
    private List<Map<String,Object>> datas;
    private MyAdapter sa;
    private EditText et;
    private EditText pt;
    private long exitTime;
    private Button addBtn;
    private DBManager mgr;
    private ImageView imgSmallTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mTintManager.setStatusBarDarkMode(true, this);


        mgr = new DBManager(this);

        lv=(ListView)findViewById(R.id.listView);
        addBtn=(Button)findViewById(R.id.newButton);
        imgSmallTitle=(ImageView)findViewById(R.id.ImageTitle2);

        addBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMessageByToast("点击添加新联系人");
                return false;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,addActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //et=(EditText)findViewById(R.id.name);
        //pt=(EditText)findViewById(R.id.phone);


        Integer[] mButtonState = { R.drawable.add_button,
                R.drawable.btn_add, R.drawable.btn_add_on };
        Button mButton = (Button) findViewById(R.id.newButton);
        MyButton myButton = new MyButton(this);
        mButton.setBackgroundDrawable(myButton.setbg(mButtonState));


        registerForContextMenu(lv);

        datas=new ArrayList<Map<String,Object>>();
//
//        //一个人
//        Map<String,String> data1= new HashMap<String,String>();
//        data1.put("name","超哥");
//        //data1.put("age","21");
//        //data1.put("gender","male");
//        data1.put("phone", "18875050386");
//
//        //第二个人
//        Map<String,String> data2= new HashMap<String,String>();
//        data2.put("name","肖海斌");
//        //data2.put("age","22");
//        //data2.put("gender","male");
//        data2.put("phone","18502339836");
//
//        //第三个人
//        Map<String,String> data3= new HashMap<String,String>();
//        data3.put("name", "谭玉霞");
//        //data3.put("age","30");
//        //data3.put("gender","male");
//        data3.put("phone","15334513069");
//
//        Map<String,String> data4= new HashMap<String,String>();
//        data4.put("name", "何华");
//        data4.put("phone","18723549123");
//
//        Map<String,String> data5= new HashMap<String,String>();
//        data5.put("name", "杨云柳");
//        data5.put("phone","18883339682");
//
//        Map<String,String> data6= new HashMap<String,String>();
//        data6.put("name", "刘帅");
//        data6.put("phone","13310237646");
//
//
//        datas.add(data1);
//        datas.add(data2);
//        datas.add(data3);
//        datas.add(data4);
//        datas.add(data5);
//        datas.add(data6);

//        sa=new SimpleAdapter(this,datas,R.layout.layout_item,new String[]{"name","age","gender","phone"},
//                new int[]{R.id.textView2,R.id.textView3,R.id.textView4,R.id.textView5});
//        sa=new SimpleAdapter(this,datas,R.layout.layout_item,new String[]{"name","phone"},
//                new int[]{R.id.textView2,R.id.textView3});
        refreshList();

        lv.setAdapter(sa);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView personName = (TextView) view.findViewById(R.id.textView2);
                TextView phoneNumber = (TextView) view.findViewById(R.id.textView3);
//                ImageView callBtn = (ImageView) view.findViewById(R.id.btn_callItem);

//                callBtn.setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        showMessageByToast("SDSD");
//                    }
//                });
//                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phonenum.getText().toString()));
//                startActivity(intent);
                //Toast.makeText(MainActivity.this,name.getText().toString(),Toast.LENGTH_SHORT).show();
                String resID = ((TextView) getViewByPosition(position, lv).findViewById(R.id.resID))
                        .getText().toString();
                Intent intent = new Intent();
                intent.putExtra("viewname", personName.getText().toString());
                intent.putExtra("viewphone", phoneNumber.getText().toString());
                intent.putExtra("resID", resID);
                intent.setClass(MainActivity.this, viewActivity.class);

                startActivity(intent);
            }
        });
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View c=lv.getChildAt(0);
                if (c != null){
                    if(c.getHeight()>=200){
                        if(c.getTop()<-370 ){
                            imgSmallTitle.setVisibility(View.VISIBLE);
                        }else{
                            imgSmallTitle.setVisibility(View.GONE);
                        }
                    }
                }
                if (c != null)
                    Log.i("scroll:",""+c.getTop()+";"+c.getHeight());
            }
        });
    }

    public int getScrollY() {               //获取
        View c = lv.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition =1;//lv.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }

    public void callPhoneNumber(String phoneNum){
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        startActivity(intent);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1, 0 ,menu.NONE,"查看");
        menu.add(1, 1 ,menu.NONE,"添加");
        menu.add(1, 2 ,menu.NONE,"删除");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acm=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        String phoneNumber=((TextView)getViewByPosition(acm.position, lv).findViewById(R.id.textView3)).getText().toString();
        String personName=((TextView)getViewByPosition(acm.position, lv).findViewById(R.id.textView2)).getText().toString();

        switch(item.getItemId()) {
            case 0 :
                //do 查看

                //showMessageByToast(((TextView)getViewByPosition(acm.position, lv).findViewById(R.id.textView3)).getText().toString());
                String resID=((TextView) getViewByPosition(acm.position, lv).findViewById(R.id.resID))
                        .getText().toString();
                Intent intent=new Intent();
                intent.putExtra("viewname",personName);
                intent.putExtra("viewphone",phoneNumber);
                intent.putExtra("resID",resID);
                intent.setClass(this,viewActivity.class);

                startActivity(intent);
                break;
            case 1:
                // do 添加
                Intent intent2=new Intent();
                intent2.setClass(this,addActivity.class);
                startActivityForResult(intent2, 1);
                break;
            case 2:
                // do 删除
                Log.i("itemclick", "1");

                Person person = new Person(personName, phoneNumber, "");
                mgr.delete(person);
                datas.remove(acm.position);
                sa.notifyDataSetChanged();
                showMessageInTaskBar("超神陆战队·通讯录","联系人"+personName+"已删除","成功删除联系人！");
                //refreshList();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

//    public void addPerson(View v){
//        String name=et.getText().toString();
//        String phone=pt.getText().toString();
//        Map<String,String> data= new HashMap<String,String>();
//        data.put("name",name);
//        data.put("phone",phone);
//
//        datas.add(data);
//        sa.notifyDataSetChanged();
//
//        et.setText("");
//        pt.setText("");
//        Toast.makeText(MainActivity.this,"联系人<"+name+">已经添加成功！",Toast.LENGTH_SHORT).show();
//    }

    public void showMessageByToast(String Msg){
        Toast.makeText(MainActivity.this,Msg,Toast.LENGTH_SHORT).show();
    }

//    public void showMessage(View v){
//        String name="";//et.getText().toString();
////        String phone =pt.getText().toString();
////        Map<String,String> data= new HashMap<String,String>();
////        data.put("name",name);
////        data.put("phone", phone);
////
////        datas.add(data);
////        sa.notifyDataSetChanged();
////
////        et.setText("");
////        pt.setText("");
//        Intent intent=new Intent();
//        intent.setClass(this,addActivity.class);
//        startActivityForResult(intent, 1);
//
//    }

    public void showMessageInTaskBar(String Title,String message,String Tip){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        Intent resultIntent=new Intent(this, MainActivity.class);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT );

        builder.setContentTitle(Title);
        builder.setContentText(message);
        builder.setTicker(Tip);
        builder.setSmallIcon(R.drawable.btn_add);
        builder.setAutoCancel(true);    //设置点击后自动删除
        //builder.setOngoing(true);       //设置不可删除

        builder.setContentIntent(resultPendingIntent);
        NotificationManager me=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        me.notify(1, builder.build());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //Log.i("Main", "requestCode:"+requestCode+"resultCode:"+resultCode);

        if(resultCode==2){
            if(requestCode==1){
                String name=data.getStringExtra("addname");
                String phone =data.getStringExtra("addphone");
                Map<String,Object> addData= new HashMap<String,Object>();
                addData.put("name", name);
                addData.put("phone", phone);
                addData.put("icon", R.drawable.user_ico);
                datas.add(addData);
                sa.notifyDataSetChanged();
                showMessageInTaskBar("超神陆战队·通讯录", "联系人" + name + "已添加！", "添加成功！");

                //数据库
                ArrayList<Person> persons = new ArrayList<Person>();
                persons.add(new Person(name, phone, ""));
                mgr.add(persons);
                //Toast.makeText(MainActivity.this, data.getStringExtra("addname")+";"+data.getStringExtra("addphone"),Toast.LENGTH_SHORT).show();
            }
        }
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }









    //以下数据库
//    public void addDB(View view) {
//        ArrayList<Person> persons = new ArrayList<Person>();
//
//        Person person1 = new Person("超哥", "18875050386", "");
//        Person person2 = new Person("肖海斌", "18502339836", "");
//        Person person3 = new Person("谭玉霞", "15334513069", "");
//        Person person4 = new Person("何华", "18723549123", "");
//        Person person5 = new Person("杨云柳", "18883339682", "");
//        Person person6 = new Person("刘帅", "13310237646", "");
//
//        persons.add(person1);
//        persons.add(person2);
//        persons.add(person3);
//        persons.add(person4);
//        persons.add(person5);
//        persons.add(person6);
//
//        mgr.add(persons);
//    }

    public void update(View view) {
        Person person = new Person();
        person.name = "Jane";
        person.phone = "";
        mgr.updatePhone(person);
    }

    public void delete(View view) {
        Person person = new Person();
        person.phone = "";
        mgr.delete(person);
    }

    public void refreshList() {
        List<Person> persons = mgr.query();
        Object[] obj=persons.toArray();                                                                       //for (int i=0;i<obj.length;i++) {
        HashMap<String, Object> map_title = new HashMap<String, Object>();
        map_title.put("name", "");
        map_title.put("phone", "");
        map_title.put("icon", 0);
        map_title.put("resID", 0);
        map_title.put("titleBKG", R.drawable.title_cs);
        datas.add(map_title);
        for (Person person : persons) {
                                                                                                                            //for (int i=0;i<obj.length;i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", person.name);
            map.put("phone", person.phone);
            int resID=0;
            switch (person.phone){
                case "18875050386":
                    resID = R.drawable.p_chao;
                    break;
                case "18502339836":
                    resID = R.drawable.p_dou;
                    break;
                case "15334513069":
                    resID = R.drawable.p_tan;
                    break;
                case "18723549123":
                    resID = R.drawable.p_hua;
                    break;
                case "18883339682":
                    resID = R.drawable.p_yun;
                    break;
                case "13310237646":
                    resID = R.drawable.p_shuai;
                    break;
                default:
                    resID = R.drawable.user_ico;
            }

            map.put("icon", resID);
            map.put("resID", resID);
            map.put("titleBKG", R.drawable.img_alpha);
            datas.add(map);
        }
        sa=new MyAdapter(this,datas,R.layout.layout_item,new String[]{"name","phone","icon","resID","titleBKG"},
                new int[]{R.id.textView2,R.id.textView3,R.id.image_people,R.id.resID,R.id.ImageTitle});
        lv.setAdapter(sa);

//        for (int i=0;i<obj.length;i++) {
//            Log.i(">>>", ((ImageView) getViewByPosition(i, lv)
//                    .findViewById(R.id.image_people)).toString());
//
////            iv.setImageResource(R.drawable.btn_qr);
//            //((ImageView) getViewByPosition(i, lv)
//                    //.findViewById(R.id.image_people)).setImageResource(R.drawable.btn_add);
//        }
    }

    public void queryTheCursor(View view) {
        Cursor c = mgr.queryTheCursor();
        startManagingCursor(c); //托付给activity根据自己的生命周期去管理Cursor的生命周期
        CursorWrapper cursorWrapper = new CursorWrapper(c) {
            @Override
            public String getString(int columnIndex) {
                //将简介前加上年龄
                if (getColumnName(columnIndex).equals("info")) {
                    String phone = getString(getColumnIndex("phone"));
                    return phone;//age + " years old, " + super.getString(columnIndex);
                }
                return super.getString(columnIndex);
            }
        };
        //确保查询结果中有"_id"列
        sa=new MyAdapter(this,datas,R.layout.layout_item,new String[]{"name","phone"},
                new int[]{R.id.textView2,R.id.textView3});
        lv.setAdapter(sa);
    }





}
