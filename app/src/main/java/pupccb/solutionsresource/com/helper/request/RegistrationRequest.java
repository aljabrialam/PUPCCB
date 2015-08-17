package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.RegistrationService;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;

/**
 * Created by User on 7/16/2015.
 */
public class RegistrationRequest extends RetrofitSpiceRequest<RegistrationResponse, RegistrationService> {

    private RegistrationDetails register;

    public RegistrationRequest(RegistrationDetails register) {
        super(RegistrationResponse.class, RegistrationService.class);
        this.register = register;
    }

    @Override
    public RegistrationResponse loadDataFromNetwork() throws Exception {
        return getService().register(
                register.getFirstname(),
                register.getMiddlename(),
                register.getLasrname(),
                register.getContactnumber(),
                register.getEmailaddress(),
                register.getPassword()
        );
    }

    public String createCacheKey() {
        return "SessionRequest" + register.getEmailaddress();
    }
}
