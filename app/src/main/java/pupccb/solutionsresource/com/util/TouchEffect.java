package pupccb.solutionsresource.com.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class TouchEffect implements OnTouchListener {
    public boolean onTouch(View var1, MotionEvent var2) {
        if (var2.getAction() == 0) {
            var1.setAlpha(0.6F);
        } else if (var2.getAction() == 1 || var2.getAction() == 3) {
            var1.setAlpha(1.0F);
        }
        return false;
    }
}
