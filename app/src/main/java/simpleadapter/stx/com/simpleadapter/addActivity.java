package simpleadapter.stx.com.simpleadapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class addActivity extends BaseActivity {

    private EditText edName;
    private EditText edPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edName=(EditText) findViewById(R.id.userName);
        edPhone=(EditText) findViewById(R.id.userPhone);

//        Intent intent=getIntent();
//        String message=intent.getStringExtra("result");
//
//        readNewPerson(message);

    }


    public void backOnClick(View v){
        addActivity.this.finish();
    }


    public void register(View v){
        String userName="";
        String userPhone="";
        userName=edName.getText().toString();
        userPhone=edPhone.getText().toString();
        Intent intent=new Intent();
        if(!"".equals(userName)){
            if (!"".equals(userPhone)){
                intent.putExtra("addname",userName);
                intent.putExtra("addphone",userPhone);
                setResult(2, intent);
                finish();
            }else{
                new AlertDialog
                        .Builder(this)
                        .setTitle("错误")
                        .setMessage("电话号码不能为空")
                        .setPositiveButton("确定", null)
                        .show();
            }
        }else{
            new AlertDialog
                    .Builder(this)
                    .setTitle("错误")
                    .setMessage("姓名不能为空")
                    .setPositiveButton("确定", null)
                    .show();
        }

//        Intent intent=new Intent();
//        intent.setClass(this, CaptureActivity.class);
//        startActivity(intent);

    }

    public void readNewPerson(String msg){
        try {
            String strs[] = msg.split(";");
            if("LSTXL-VCARD".equals(strs[0])) {
                edName.setText(strs[1]);
                edPhone.setText(strs[2]);
            }
        }catch (Exception e){
            Log.i("Exception","can't split message in addActivity.java @readNewPerson()");
        }
    }

    public void OnQRClick(View v){
        Intent intent=new Intent();
        intent.setClass(this,CaptureActivity.class);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==2){
            if(requestCode==1){
                String message=data.getStringExtra("result");
                readNewPerson(message);
            }
        }
    }
}
