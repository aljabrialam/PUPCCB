package pupccb.solutionsresource.com.helper.service;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import pupccb.solutionsresource.com.helper.BaseHelper;


/**
 * Created by User on 7/16/2015.
 */
public class BaseRetrofitSpiceService extends RetrofitGsonSpiceService {

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(SessionService.class);
    }

    @Override
    protected String getServerUrl() {
        return BaseHelper.getBaseUrl();
    }
}
