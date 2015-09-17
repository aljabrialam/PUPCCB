package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.Announcement;
import pupccb.solutionsresource.com.model.AnnouncementList;
import pupccb.solutionsresource.com.model.CommentList;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by User on 9/14/2015.
 */
public interface AnnouncementListService {

    @GET("/api/v1/announcements")
    AnnouncementList getAnnouncement();

}
