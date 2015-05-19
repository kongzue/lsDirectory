package simpleadapter.stx.com.simpleadapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import simpleadapter.stx.com.simpleadapter.util.DBManager;
import simpleadapter.stx.com.simpleadapter.util.MyButton;
import simpleadapter.stx.com.simpleadapter.util.MyMD5;


public class LoginActivity extends BaseActivity {

    private DBManager mgr;
    private EditText etUsername;
    private EditText etPassword;
    private String password;
    private ProgressDialog pd;
    private ProgressDialog pdDownloadUserInfo;
    private String strUsername = "";
    private String userKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mgr = new DBManager(this);
        etUsername = (EditText) findViewById(R.id.TxtUserName);
        etPassword = (EditText) findViewById(R.id.TxtPassword);
        //etUsername.setText("chaoshen");

        //password = mgr.queryDB("user", "password", "where name='" + etUsername.getText().toString() + "'");
        //etPassword.setText(password);
        etUsername.setText("");
        etPassword.setText("");

        Integer[] mButtonState = {R.drawable.login_button,
                R.drawable.btn_login, R.drawable.btn_login_down};
        Button mButton = (Button) findViewById(R.id.btnLogin);
        MyButton myButton = new MyButton(this);
        mButton.setBackgroundDrawable(myButton.setbg(mButtonState));
        //DownloadUserDB("");
    }

    public void login(View v) {
//        etUsername = (EditText) findViewById(R.id.TxtUserName);
//        password = mgr.queryDB("user", "password", "where name='" + etUsername.getText().toString() + "'");
//        if (etPassword.getText().toString().equalsIgnoreCase(password)) {
//            showMessageByToast("欢迎您," + etUsername.getText().toString());
//            mgr.insertDB("localuser", "null,'" + etUsername.getText().toString() + "','" + password + "',0");
//            mgr.InitializeChaoShenDB();
//            Intent intent = new Intent();
//            intent.setClass(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            showMessageByToast("登录失败，请检查您的帐号和密码");
//        }
        strUsername = ((EditText) findViewById(R.id.TxtUserName)).getText().toString();

        pd = new ProgressDialog(this);
        pd.setCancelable(true);
        pd = pd.show(this, "", strUsername + "正在登录...");
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String result = LoginNow("zxn");
//						activity_main_btn1.setText("请求结果为："+result);
                return result;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                String resultStr = (String) result;
                String strs[] = resultStr.split(";");
                if (strs[0].equals("true")) {
                    userKey = strs[1];
                    pdDownloadUserInfo = new ProgressDialog(LoginActivity.this);
                    pdDownloadUserInfo.setCancelable(true);
                    pdDownloadUserInfo = pdDownloadUserInfo.show(LoginActivity.this, "", "正在同步数据...");
                    downloadUserInfo(userKey);

                } else {
                    showMessageByToast("登录失败！请检查您输入的用户名和密码");
                }

                pd.cancel();
                //可以更新UI
            }
        }.execute();
    }

    private String LoginNow(String msg) {
        String url = "http://kongzue.sinaapp.com/MyzchhServerLogin";
        String result = "";
        try {
            //创建连接
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            //设置参数，仿html表单提交
            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            BasicNameValuePair param = new BasicNameValuePair("username", ((EditText) findViewById(R.id.TxtUserName)).getText().toString());
            BasicNameValuePair param2 = new BasicNameValuePair("password", ((EditText) findViewById(R.id.TxtPassword)).getText().toString());
            paramList.add(param);
            paramList.add(param2);

            post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
            //发送HttpPost请求，并返回HttpResponse对象
            HttpResponse httpResponse = httpClient.execute(post);
            // 判断请求响应状态码，状态码为200表示服务端成功响应了客户端的请求
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //获取返回结果
                result = EntityUtils.toString(httpResponse.getEntity());
                //showMessageByToast(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //showMessageByToast("错误!");
        }

        return result;
    }

    public void regist(View v) {
        String url = "http://lstxl.sinaapp.com/regist"; // web address
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void whenProblem(View v) {
        String url = "http://lstxl.sinaapp.com/"; // web address
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void showMessageByToast(String Msg) {
        Toast.makeText(LoginActivity.this, Msg, Toast.LENGTH_SHORT).show();
    }


    public void backOnClick(View v) {
        LoginActivity.this.finish();
    }

    public void downloadUserInfo(String Key) {
//        String message="hahahahahah";
//        BufferedWriter bufferedWriter=null;
//        Writer writer=null;
//        try {
//            writer=new FileWriter(oldfileName);
//            bufferedWriter=new BufferedWriter(writer);
//            bufferedWriter.write("大家好，哈哈哈，排排坐，吃果果");
//        } catch (Exception e) {
//        }finally{
//            try {
//                if(bufferedWriter!=null)
//                    bufferedWriter.close();
//                if(writer!=null)
//                    writer.close();
//            } catch (Exception e2) {
//
//            }
//        }
        final String myUserKey = Key;
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String dwUrl = "http://kongzue.sinaapp.com/LSTXL/UserTitlePic?username=" + strUsername + "&key=" + myUserKey;
                String fileName = "titlepic_" + strUsername + ".png";
                Log.i("DownloadImg", "http://kongzue.sinaapp.com/LSTXL/UserTitlePic?username=" + strUsername + "&key=" + myUserKey);
                OutputStream os = null;
                InputStream is = null;
                try {
                    URL url = new URL(dwUrl);
                    //os = new FileOutputStream(filePath);
                    os = openFileOutput(fileName, Context.MODE_WORLD_READABLE);
                    is = url.openStream();
                    byte[] buff = new byte[1024];
                    while (true) {
                        int readed = is.read(buff);
                        if (readed == -1) {
                            break;
                        }
                        byte[] temp = new byte[readed];
                        System.arraycopy(buff, 0, temp, 0, readed);
                        os.write(temp);
                    }
                    is.close();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                pdDownloadUserInfo.cancel();
                showMessageByToast("欢迎您，" + strUsername);
                mgr.insertDB("localuser", "null,'" + etUsername.getText().toString() + "','" + password + "',0");

                downloadUserTable(strUsername);


            }
        }.execute();
    }

    public void downloadUserTable(final String USERNAME){
        new AsyncTask<String, Void, Object>() {
            @Override
            protected Object doInBackground(String... params) {
                String url = "http://kongzue.sinaapp.com/LSTXL/getUserTable";
                String result = "";
                try {
                    //创建连接
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(url);
                    //设置参数，仿html表单提交
                    List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                    BasicNameValuePair param = new BasicNameValuePair("username", USERNAME);
                    BasicNameValuePair param2 = new BasicNameValuePair("key", MyMD5.MD5(USERNAME + new SimpleDateFormat("yyyyMMdd").format(new Date())));
                    paramList.add(param);
                    paramList.add(param2);

                    post.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
                    //发送HttpPost请求，并返回HttpResponse对象
                    HttpResponse httpResponse = httpClient.execute(post);
                    // 判断请求响应状态码，状态码为200表示服务端成功响应了客户端的请求
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //获取返回结果
                        result = EntityUtils.toString(httpResponse.getEntity());
                        //showMessageByToast(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //showMessageByToast("错误!");
                }
                return result;
            }

            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                mgr.InitializeUserTable((String)result);
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                //showMessageByToast("" + result);
            }
        }.execute();
    }
}
