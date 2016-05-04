package pupccb.solutionsresource.com.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 9/21/2015.
 */
public class CommentList {

    private List<Comment> data;

    public List<Comment> getData() {
        return data != null ? data : new ArrayList<Comment>();
    }

    public void setData(List<Comment> data) {
        this.data = data != null ? data : new ArrayList<Comment>();
    }
}
