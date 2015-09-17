package pupccb.solutionsresource.com.helper.service;

import android.content.SharedPreferences;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.util.RetrofitHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by User on 7/16/2015.
 */
public class RetrofitSpiceService extends BaseRetrofitSpiceService {

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                SharedPreferences sharedPreferences = BaseHelper.getSharedPreference(getApplicationContext());
                String access_token = sharedPreferences.getString("access_token", null);
                if (access_token != null) {
                    request.addHeader("Authorization", access_token);
                }
            }
        };

        return super.createRestAdapterBuilder()
                .setRequestInterceptor(requestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new RetrofitHttpClient());
    }
}
