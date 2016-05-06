package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.Agencies;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by Josh on 4/27/2016.
 */
public interface AgencyService {

    @Multipart
    @POST("/api/v1/new-agency")
    Agencies createAgency(
            @Part("name") String name,
            @Part("location") String location
    );
}
