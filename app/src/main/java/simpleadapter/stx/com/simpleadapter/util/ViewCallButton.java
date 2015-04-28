package simpleadapter.stx.com.simpleadapter.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

/**
 * Created by chao on 2015/4/23.
 */
public class ViewCallButton extends View {

    public ViewCallButton(Context context) {
        super(context);
    }

    // 以下这个方法也可以把你的图片数组传过来，以StateListDrawable来设置图片状态，来表现button的各中状态。未选
    // 中，按下，选中效果。
    public StateListDrawable setbg(Integer[] mImageIds) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = this.getResources().getDrawable(mImageIds[0]);
        Drawable selected = this.getResources().getDrawable(mImageIds[1]);
        Drawable pressed = this.getResources().getDrawable(mImageIds[2]);
        bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
        bg.addState(View.ENABLED_FOCUSED_STATE_SET, selected);
        bg.addState(View.ENABLED_STATE_SET, normal);
        bg.addState(View.FOCUSED_STATE_SET, selected);
        bg.addState(View.EMPTY_STATE_SET, normal);
        return bg;
    }
}
