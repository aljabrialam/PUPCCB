package pupccb.solutionsresource.com.model;

import android.content.Intent;
import android.content.pm.ResolveInfo;

/**
 * Created by User on 8/19/2015.
 */
public class Attachment {

    private ResolveInfo resolveInfo;
    private Intent intent;

    public Attachment(ResolveInfo resolveInfo, Intent intent) {
        setResolveInfo(resolveInfo);
        setIntent(intent);
    }

    public ResolveInfo getResolveInfo() {
        return resolveInfo;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setResolveInfo(ResolveInfo resolveInfo) {
        this.resolveInfo = resolveInfo;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }
}
