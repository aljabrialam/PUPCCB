package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.model.AgencyList;
import pupccb.solutionsresource.com.model.UserInfoResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by User on 9/14/2015.
 */
public interface AgenciesService {

    @GET("/api/v1/agencies")
    AgencyList agencies();
}
