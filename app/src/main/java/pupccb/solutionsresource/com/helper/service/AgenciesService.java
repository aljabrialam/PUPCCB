package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.AgencyList;
import retrofit.http.GET;

/**
 * Created by User on 9/14/2015.
 */
public interface AgenciesService {
    @GET("/api/v1/agencies")
    AgencyList agencies();
}
