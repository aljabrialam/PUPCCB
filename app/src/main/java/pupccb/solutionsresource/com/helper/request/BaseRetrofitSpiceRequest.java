package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.util.CustomRetryPolicy;


/**
 * Created by User on 7/16/2015.
 */
public abstract class BaseRetrofitSpiceRequest<T,R> extends RetrofitSpiceRequest<T,R> {

    public BaseRetrofitSpiceRequest(Class<T> clazz, Class<R> retrofitedInterfaceClass) {
        super(clazz, retrofitedInterfaceClass);
        setRetryPolicy(new CustomRetryPolicy());
    }
}
