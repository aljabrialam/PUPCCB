package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.AgencyService;
import pupccb.solutionsresource.com.model.Agencies;

/**
 * Created by Josh on 4/27/2016.
 */
public class CreateAgencyRequest extends RetrofitSpiceRequest<Agencies, AgencyService> {
    private Agencies agency;

    public CreateAgencyRequest(Agencies agency) {
        super(Agencies.class, AgencyService.class);
        this.agency = agency;
    }

    @Override
    public Agencies loadDataFromNetwork() throws Exception {
        return getService().createAgency(
                agency.getName(),
                agency.getLocation()
        );
    }

    public String createCacheKey() {
        return "AgencyRequest" + agency.getName() + agency.getLocation();
    }
}
