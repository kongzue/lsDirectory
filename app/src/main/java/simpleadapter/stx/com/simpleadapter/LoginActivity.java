package simpleadapter.stx.com.simpleadapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import simpleadapter.stx.com.simpleadapter.util.DBManager;
import simpleadapter.stx.com.simpleadapter.util.MyButton;


public class LoginActivity extends BaseActivity {

    private DBManager mgr;
    private EditText etUsername;
    private EditText etPassword;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mgr = new DBManager(this);
        etUsername = (EditText) findViewById(R.id.TxtUserName);
        etPassword = (EditText) findViewById(R.id.TxtPassword);
        etUsername.setText("chaoshen");

        password = mgr.queryDB("user", "password", "where name='" + etUsername.getText().toString() + "'");
        etPassword.setText(password);


        Integer[] mButtonState = {R.drawable.login_button,
                R.drawable.btn_login, R.drawable.btn_login_down};
        Button mButton = (Button) findViewById(R.id.btnLogin);
        MyButton myButton = new MyButton(this);
        mButton.setBackgroundDrawable(myButton.setbg(mButtonState));

    }

    public void login(View v) {
        etUsername = (EditText) findViewById(R.id.TxtUserName);
        password = mgr.queryDB("user", "password", "where name='" + etUsername.getText().toString() + "'");
        if (etPassword.getText().toString().equalsIgnoreCase(password)) {
            showMessageByToast("»¶Ó­Äú," + etUsername.getText().toString());
            mgr.insertDB("localuser", "null,'" + etUsername.getText().toString() + "','" + password + "',0");
            mgr.InitializeChaoShenDB();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            showMessageByToast("µÇÂ¼Ê§°Ü£¬Çë¼ì²éÄúµÄÕÊºÅºÍÃÜÂë");
        }
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
}
