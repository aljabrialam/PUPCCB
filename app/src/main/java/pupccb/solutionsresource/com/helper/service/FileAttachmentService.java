package pupccb.solutionsresource.com.helper.service;

import pupccb.solutionsresource.com.model.FileAttachmentList;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by User on 9/9/2015.
 */
public interface FileAttachmentService {

    @GET("/api/v1/ticket/{id}/attachment")
    FileAttachmentList getAttachment(@Path("id") String id,
                                     @Query("limit") String limit,
                                     @Query("page") String page);

    @Multipart
    @POST("/api/v1/ticket/{id}/attachment")
    FileAttachmentList postAttachment(@Path("id") String id,
                                      @Part("file")TypedFile file);
}
