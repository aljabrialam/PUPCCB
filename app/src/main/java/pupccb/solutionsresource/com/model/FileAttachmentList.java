package pupccb.solutionsresource.com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/9/2015.
 */
public class FileAttachmentList {
    private String total_count, total_pages, current_page, limit;
    private List<FileAttachment> data;

    public FileAttachmentList() {

    }

    public FileAttachmentList(String total_count, String total_pages, String current_page, String limit, List<FileAttachment> data) {
        this.total_count = total_count;
        this.total_pages = total_pages;
        this.current_page = current_page;
        this.limit = limit;
        this.data = data;
    }

    public String getTotal_count() {
        return total_count != null ? total_count : "";
    }

    public String getTotal_pages() {
        return total_pages != null ? total_pages : "";
    }

    public String getCurrent_page() {
        return current_page != null ? current_page : "";
    }

    public String getLimit() {
        return limit != null ? limit : "";
    }

    public List<FileAttachment> getData() {
        return data != null ? data : new ArrayList<FileAttachment>();
    }
}
