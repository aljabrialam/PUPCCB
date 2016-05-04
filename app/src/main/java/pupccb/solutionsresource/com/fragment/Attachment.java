package pupccb.solutionsresource.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.model.FileAttachment;

/**
 * Created by User on 8/26/2015.
 */
public class Attachment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private FileAttachmentAdapter fileAttachmentAdapter;
    private List<FileAttachment> fileAttachments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_file_attachment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAttachment);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeAdapter();
        findViewById(view);
    }

    private void initializeAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //fileAttachmentAdapter = new FileAttachmentAdapter(getActivity(), fileAttachments, this);
        recyclerView.setAdapter(fileAttachmentAdapter);

    }
    private void findViewById(View view) {

    }
}