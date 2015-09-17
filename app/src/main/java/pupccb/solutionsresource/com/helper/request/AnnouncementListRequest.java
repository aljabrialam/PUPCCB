package pupccb.solutionsresource.com.helper.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import pupccb.solutionsresource.com.helper.service.AnnouncementListService;
import pupccb.solutionsresource.com.model.AnnouncementList;

/**
 * Created by User on 9/14/2015.
 */
public class AnnouncementListRequest extends RetrofitSpiceRequest<AnnouncementList, AnnouncementListService> {

    public AnnouncementListRequest() {
        super(AnnouncementList.class, AnnouncementListService.class);
    }

    @Override
    public AnnouncementList loadDataFromNetwork() throws Exception {
        return getService().getAnnouncement();
    }

    public String createCacheKey() {
        return "AnnouncementListRequest";
    }

}
