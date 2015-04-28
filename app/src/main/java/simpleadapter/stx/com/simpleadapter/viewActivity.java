package simpleadapter.stx.com.simpleadapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

import simpleadapter.stx.com.simpleadapter.util.ViewCallButton;


public class viewActivity extends BaseActivity {

    private ImageView imgOutput;
    private ImageView imgProple;
    private TextView txtPhoneNum;
    private Bitmap barcodeBitmap;
    private static final String TAG = "MainActivity";
    private static final int QR_WIDTH = 200;
    private static final int QR_HEIGHT = 200;
    private String RQcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent=getIntent();
        String Name=intent.getStringExtra("viewname");
        String phoneNum=intent.getStringExtra("viewphone");
        int resId=Integer.parseInt(intent.getStringExtra("resID"));
        TextView txtName=(TextView)findViewById(R.id.view_name);
        txtName.setText(Name);
        txtPhoneNum=(TextView)findViewById(R.id.view_phoneNum);
        txtPhoneNum.setText(phoneNum);
        RQcode="LSTXL-VCARD;"+txtName.getText().toString()+";"+txtPhoneNum.getText().toString()+"";
        imgOutput = (ImageView)findViewById(R.id.img_main_output);
        imgProple = (ImageView)findViewById(R.id.imgPeopleIcon);
        //Toast.makeText(this,resId+"",Toast.LENGTH_SHORT).show();
        imgProple.setImageResource(resId);
        createImage();


        Integer[] mButtonState = {R.drawable.view_call_button,
                R.drawable.btn_view_call, R.drawable.btn_view_call_down };
        Button mButton = (Button) findViewById(R.id.btn_callPhone);
        ViewCallButton myButton = new ViewCallButton(this);
        mButton.setBackgroundDrawable(myButton.setbg(mButtonState));
    }

    // 生成QR图
    private void createImage() {
        try {
            // 需要引入core包
            QRCodeWriter writer = new QRCodeWriter();

            String text = RQcode;

            Log.i(TAG, "生成的文本：" + text);
            if (text == null || "".equals(text) || text.length() < 1) {
                return;
            }

            // 把输入的文本转为二维码
            BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
                    QR_WIDTH, QR_HEIGHT);

            System.out.println("w:" + martix.getWidth() + "h:"
                    + martix.getHeight());

            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(text,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }

                }
            }

            barcodeBitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
                    Bitmap.Config.ARGB_8888);

            barcodeBitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            imgOutput.setImageBitmap(barcodeBitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void backOnClick(View v){
        viewActivity.this.finish();
    }

    public void btnCall(View v){
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txtPhoneNum.getText().toString()));
        startActivity(intent);
    }
}
