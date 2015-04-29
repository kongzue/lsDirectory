package simpleadapter.stx.com.simpleadapter.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simpleadapter.stx.com.simpleadapter.LoginActivity;
import simpleadapter.stx.com.simpleadapter.MainActivity;
import simpleadapter.stx.com.simpleadapter.R;

import static android.widget.Toast.*;

/**
 * Created by chao on 2015/4/25.
 */
public class MyAdapter extends SimpleAdapter {
    private int mResource;
    private Context context;
    private List<? extends Map<String, ?>> mData;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mInflater = LayoutInflater.from(context);
        this.mResource = resource;
        this.mData = data;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.layout_item, null);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.btn_callItem);
            final TextView textView=(TextView)convertView.findViewById(R.id.textView3);

            Integer[] mButtonState = { R.drawable.btn_list_call,
                    R.drawable.btn_list_call, R.drawable.btn_list_call_down };
            ImageView mButton = (ImageView) convertView.findViewById(R.id.btn_callItem);
            ListCallButton myButton = new ListCallButton(context);
            mButton.setBackgroundDrawable(myButton.setbg(mButtonState));

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + textView.getText().toString()));
                    context.startActivity(intent);
                }
            });

            final ImageView imageTitle = (ImageView) convertView.findViewById(R.id.ImageTitle);
            imageTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }
            });

//
//            imageView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Toast.makeText(context,"²¦´òµç»°",LENGTH_SHORT).show();
//                    return false;
//                }
//            });
        }
        return super.getView(position, convertView, parent);
    }


}