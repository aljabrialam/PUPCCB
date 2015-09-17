package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.AgenciesService;
import pupccb.solutionsresource.com.helper.service.UserInfoService;
import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.model.AgencyList;
import pupccb.solutionsresource.com.model.UserInfo;
import pupccb.solutionsresource.com.model.UserInfoResponse;

/**
 * Created by User on 9/14/2015.
 */
public class AgenciesRequest extends RetrofitSpiceRequest<AgencyList, AgenciesService> {

    public AgenciesRequest( ) {
        super(AgencyList.class, AgenciesService.class);
    }

    @Override
    public AgencyList loadDataFromNetwork() throws Exception {
        return getService().agencies();
    }

    public String createCacheKey() {
        return "AgenciesRequest";
    }

}
