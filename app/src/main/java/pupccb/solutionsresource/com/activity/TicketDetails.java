package pupccb.solutionsresource.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.ArrayList;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.CardAdapter;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.fragment.AttachmentDialogFragment;
import pupccb.solutionsresource.com.fragment.BrowseFileDialogFragment;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OfflineHelper;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;
import pupccb.solutionsresource.com.model.Comment;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.model.TicketInfo;
import pupccb.solutionsresource.com.model.TicketResponse;
import pupccb.solutionsresource.com.util.CreateMaterialDialog;
import pupccb.solutionsresource.com.util.CreateYesNoDialog;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.PrettyTime;
import pupccb.solutionsresource.com.util.RequestCodes;
import pupccb.solutionsresource.com.util.ToastMessage;

/**
 * Created by User on 8/17/2015.
 */
public class TicketDetails extends AppCompatActivity implements Validator.ValidationListener,
        CreateYesNoDialog.Communicator, View.OnClickListener, CreateMaterialDialog.Communicator,
        OfflineCommunicator, AttachmentDialogFragment.Communicator, FileAttachmentAdapter.Communicator {

    Transformation transformation = new Transformation() {

        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth = 400;

            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                // Same bitmap is returned if sizes are the same
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "transformation" + " desiredWidth";
        }
    };

    private Menu menu;
    private Validator validator;
    private MenuItem menuItem;
    private View view;
    private RecyclerView recyclerView;
    private Controller onlineController, offlineController;
    private SharedPreferences sharedPreferences;
    private TicketInfo ticketInfo;
    private TextView textViewSubject, textViewBy, textViewIncidentDetails, textViewPersonToAddress,
            textViewAttachment, textViewCommentAttachmentImage, textViewAddAttachment, emptyList,
            textViewCounter;
    private LinearLayout commentsList;
    private List<Comment> comments = new ArrayList<>();
    private Activity activity;
    private Bundle bundle;
    private LoadToast loadToast;
    private FloatingActionButton floatingActionButton;
    private ProgressBar progressBar;
    private FileAttachment fileAttachment;
    private List<FileAttachment> fileAttachments = new ArrayList<>();
    private LinearLayout llAttachment;
    private Holder holder;
    private FileAttachmentAdapter fileAttachmentAdapter;
    private int position;
    private EditText editTextComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.ticket_details_layout, null);
        setContentView(view);
        startController();
        toolBar();
        getExtras();
        findViewById(view);
        setupHeader(ticketInfo);
        commentList();
    }

    private void startController() {
        offlineController = new Controller(new OfflineHelper());
        onlineController = new Controller(new OnlineHelper());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab) {
            newCommentDialog();
        } else if (view.getId() == R.id.attachmentImage) {
            previewAttachmentDialog(ticketInfo.getAttachment());
        } else if (view.getId() == R.id.empty_text) {
            commentsList.invalidate();
            commentList();
        }
    }

    public void getExtras() {
        bundle = getIntent().getExtras();
        ticketInfo = (TicketInfo) bundle.getSerializable("ticketInfoList");
        activity = this;
        setTitle(ticketInfo.getTicket_id());
    }

    private void toolBar() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void findViewById(View view) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferences = BaseHelper.getSharedPreference(this);

        loadToast = new LoadToast(this);

        commentsList = (LinearLayout) view.findViewById(R.id.comments_list);
        emptyList = (TextView) view.findViewById(R.id.empty_text);
        emptyList.setOnClickListener(this);
        textViewSubject = (TextView) view.findViewById(R.id.subject);
        textViewPersonToAddress = (TextView) view.findViewById(R.id.textViewPersonToAddress);
        textViewIncidentDetails = (TextView) view.findViewById(R.id.textViewIncidentDetails);
        textViewBy = (TextView) view.findViewById(R.id.by);
        textViewAttachment = (TextView) view.findViewById(R.id.attachmentImage);
        textViewAttachment.setOnClickListener(this);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

//        List<Comment> tempComment = new ArrayList<>();
//        tempComment.add(new Comment(
//                ticketInfo.getId(),
//                "CCB operate 5 day working week applies, Mondays - Fridays 8:00am to 5:00pm.The " +
//                "CCB Hotline number is 1-6565 and SMS Hotline Number (0908)881-6565. " +
//                " This number is accessible thru PLDT, SMART, and DIGITEL Landlines nationwide."
//                , sharedPreferences.getString("user_id", null)
//                , getCommenterRole("0")
//                , ticketInfo.getUpdated_at()
//                , ticketInfo.getTicket_id()
//                , null));
//        setupComments(tempComment);

        hideAttribute();
    }

    public void hideAttribute() {
        if (ticketInfo.getStatus().matches("4") || ticketInfo.getStatus().matches("5")) {
            floatingActionButton.setVisibility(View.GONE);
        }
        if (ticketInfo.getAttachment().matches("")) {
            textViewAttachment.setVisibility(View.GONE);
        }
    }

    private void setupHeader(TicketInfo ticketInfo) {
        textViewSubject.setText(ticketInfo.getSubject());
        textViewPersonToAddress.setText(ticketInfo.getComplainee() + " | " + ticketInfo.getAgency());
        textViewIncidentDetails.setText(ticketInfo.getIncident_details());
        textViewBy.setText(getString(R.string.by_text, ticketInfo.getAssignee() + " | ", new BaseHelper().formatDateTime(ticketInfo.getUpdated_at())));
    }

    private void loadToast(String message) {
        loadToast.setProgressColor(R.color.selected_selected_blue);
        loadToast.setProgressColor(R.color.half_black);
        loadToast.setTranslationY(200);
        loadToast.setText(message);
        loadToast.show();
    }

    public void setError(ErrorHandler.Error error, Controller.MethodTypes methodTypes) {
        new BaseHelper().toastMessage(this, 5000, ToastMessage.MessageType.DANGER, error.getErrorMessage());
        loadToast.error();
        if (Controller.MethodTypes.COMMENT_LIST == methodTypes) {
            emptyList.setVisibility(View.VISIBLE);
        }
    }

    private void newCommentDialog() {
        new CreateMaterialDialog(this, "New Comment", "Cancel", "Send"
                , new ViewHolder(R.layout.dialog_new_comment)
                , Gravity.BOTTOM
                , false
                , false
                , RequestCodes.ADD_COMMENT
                , ""
        );

    }

    private void previewAttachmentDialog(String filePath) {
        new CreateMaterialDialog(this, "Preview Attachment", "Close", "Send"
                , new ViewHolder(R.layout.preview_attachment)
                , Gravity.CENTER
                , false
                , true
                , RequestCodes.PREVIEW_ATTACHMENT
                , filePath
        );
    }

    @Override
    public void MaterialDialogResponse(int requestCode, Holder holder, String filePath) {
        this.holder = holder;
        if (RequestCodes.ADD_COMMENT == requestCode) {
            setAttachmentCardAdapter(holder);
            if (BaseHelper.isOnline(activity)) {
                if (!editTextComment.getText().toString().matches("")) {
                    commentTicket(editTextComment.getText().toString());
                } else {
                    new BaseHelper().toastMessage(activity, 3000, ToastMessage.MessageType.DANGER, "Comment field is required!");
                }
            } else {
                new BaseHelper().toastMessage(activity, 3000, ToastMessage.MessageType.DANGER, "No network connection!");
            }
        } else if (RequestCodes.PREVIEW_ATTACHMENT == requestCode) {
            ImageView imageViewAttachment = (ImageView) holder.getInflatedView().findViewById(R.id.attachmentImage);
            loadImage(imageViewAttachment, filePath);
        } else if (RequestCodes.ADD_ATTACHMENT == requestCode) {
            setAttachmentCardAdapter(holder);
            if (fileAttachmentAdapter.getItemCount() < 1) {
                showBrowseFileDialog();
            } else {
                new BaseHelper().toastMessage(this, 3000, ToastMessage.MessageType.WARNING, "The attachments size exceeds the allowable limit");
            }
        } else if (RequestCodes.TEXT_COUNTER == requestCode) {
            setAttachmentCardAdapter(holder);
        }
    }

    public void setAttachmentCardAdapter(Holder holder) {
        editTextComment = (EditText) holder.getInflatedView().findViewById(R.id.editTextComment);
        textViewCounter = (TextView) holder.getInflatedView().findViewById(R.id.textViewCounter);
        editTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                textViewCounter.setText(charSequence.length() + "/255");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        llAttachment = (LinearLayout) holder.getInflatedView().findViewById(R.id.llAttachment);
        recyclerView = (RecyclerView) holder.getInflatedView().findViewById(R.id.recyclerViewAttachment);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        if (recyclerView.getAdapter() == null) {
            fileAttachmentAdapter = new FileAttachmentAdapter(activity);
            recyclerView.setAdapter(fileAttachmentAdapter);
        }
    }

    @Override
    public void adapterSelectedTicket(View view, int position) {
        FileAttachment selectedItem = fileAttachmentAdapter.getItems().get(position);
        this.position = position;
        if (view.getId() == R.id.imageViewDelete) {
            new CreateYesNoDialog(this, null, "Remove Attachment", "Are you sure you want to remove this attachment?",
                    false, false, getResources().getString(R.string.remove), getResources().getString(R.string.cancel),
                    RequestCodes.DELETE_ATTACHMENT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCodes.FILE_SELECT) {
            BrowseFileDialogFragment fragment = (BrowseFileDialogFragment) getSupportFragmentManager().findFragmentByTag("browseFileDialogFragment");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showBrowseFileDialog() {
        BrowseFileDialogFragment dialogFragment = new BrowseFileDialogFragment(sharedPreferences.getBoolean("logged_in", false));
        dialogFragment.show(getSupportFragmentManager(), "browseFileDialogFragment");
    }

    @Override
    public void showBrowseFileDialogResult(FileAttachment fileAttachment) {
        this.fileAttachment = fileAttachment;
        loadToast("Attaching file...");
        addAttachment(fileAttachment);
    }

    private void addAttachment(FileAttachment fileAttachment) {
        fileAttachments = new ArrayList<>(fileAttachments);
        loadToast("Attaching file...");
        addItem(fileAttachments);
    }

    private void addItem(List<FileAttachment> fileAttachments) {
        int i = 0;
        this.fileAttachmentAdapter.addItem(++i, fileAttachment);
        loadToast.success();
        checkAttachment();
        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.SUCCESS, "Add Attachment Success!");
    }

    public void checkAttachment() {
        if (this.fileAttachmentAdapter.getItemCount() > 0) {
            llAttachment.setVisibility(View.VISIBLE);
        } else {
            llAttachment.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteFile(FileAttachment fileAttachment) {
        offlineController.deleteFile(this, fileAttachment);
    }

    private void loadImage(ImageView view, String filePath) {
        Picasso.with(activity)
                .load(BaseHelper.getBaseUrl() + "/" + filePath)
                .transform(transformation)
                .error(R.drawable.ic_cloud_warning)
                .into(view, new ImageLoadedCallback(progressBar));
    }

    @Override
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.DANGER, message);
            }
        }
    }

    private void setupComments(List<Comment> comments) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < comments.size(); i++) {
            View view = createCommentView(i);
            commentsList.addView(view, params);
        }
    }

    private View createCommentView(final int id) {
        final View view = LayoutInflater.from(this).inflate(R.layout.comment_item, null);
        Comment item = comments.get(id);
        if (item != null) {
            setupCommentView(item, view);
        }
        return view;
    }

    private void setupCommentView(final Comment item, View view) {
        textViewBy = (TextView) view.findViewById(R.id.by);
        textViewCommentAttachmentImage = (TextView) view.findViewById(R.id.commentAttachmentImage);
        textViewCommentAttachmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previewAttachmentDialog(item.getAttachment());
            }
        });
        ((TextView) view.findViewById(R.id.title)).setText(item.getComment());
        if (item.getAttachment().matches("")) {
            textViewCommentAttachmentImage.setVisibility(View.GONE);
        }
        if (item.getCommenter_role().equals("3")) {
            ((CardView) view.findViewById(R.id.card_view)).setCardBackgroundColor(getResources().getColor(R.color.success));
            textViewBy.setText(getString(R.string.by_text, getCommenterRole(item.getCommenter_role()) + " | ", checkDateTime(item.getCreated_at())));
        } else {
            ((CardView) view.findViewById(R.id.card_view)).setCardBackgroundColor(getResources().getColor(R.color.normal));
            textViewBy.setText(getString(R.string.by_text, getCommenterRole(item.getCommenter_role()) + " | ", checkDateTime(item.getCreated_at())));
        }
    }

    public String checkDateTime(String dateTime) {
        String formattedDateTime;
        String dt = PrettyTime.toDuration(System.currentTimeMillis() - (new BaseHelper().convertDateTimeToMillis(dateTime)));
        if (dt.contains("day") || dt.contains("hour") || dt.contains("month")) {
            formattedDateTime = new BaseHelper().formatDateTime(dateTime);
        } else {
            formattedDateTime = dt;
        }
        return formattedDateTime;
    }

    public String getCommenterRole(String commenter_role) {
        String commenter;
        if (commenter_role.equalsIgnoreCase("0")) {
            commenter = "CCB Administrator";
        } else if (commenter_role.equalsIgnoreCase("1")) {
            commenter = "CCB Supervisor";
        } else if (commenter_role.equalsIgnoreCase("2")) {
            commenter = "CCB Agent";
        } else {
            commenter = "Me";
        }
        return commenter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cancel_ticket, menu);
        this.menu = menu;
        if (ticketInfo.getStatus().matches("4") || ticketInfo.getStatus().matches("5")) {
            menuItem = menu.findItem(R.id.action_cancel_ticket);
            menuItem.setVisible(false);
        }
        return true;
    }

    public void hideMenuItem() {
        menuItem = menu.findItem(R.id.action_cancel_ticket);
        menuItem.setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_cancel_ticket:
                new CreateYesNoDialog(this, null, ticketInfo.getTicket_id(), "Once you cancel this ticket, it cannot be undone.",
                        false, false, getResources().getString(R.string.action_proceed), getResources().getString(R.string.cancel),
                        RequestCodes.CANCEL_TICKET);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void yesNoDialogPositiveResponse(int requestCode) {
        if (RequestCodes.CANCEL_TICKET == requestCode) {
            cancelTicket();
        } else if (RequestCodes.DELETE_ATTACHMENT == requestCode) {
            fileAttachmentAdapter.removeItem(position);
            checkAttachment();
        }
    }

    public void cancelTicket() {
        loadToast("Cancelling ticket..");
        onlineController.cancelTicket(this, ticketInfo.getId(), sharedPreferences.getString("user_id", null));
    }

    public void cancelTicketResult(boolean value, TicketResponse ticketResponse) {
        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.SUCCESS, ticketResponse.getMsg());
        loadToast.success();
        hideMenuItem();
    }

    public void commentList() {
        if(BaseHelper.isOnline(this)){
            loadToast("");
            onlineController.commentList(this, ticketInfo.getId());
        } else {
            new BaseHelper().toastMessage(this, 3000, ToastMessage.MessageType.DANGER, "There seems to be a problem with your internet connection.");
            emptyList.setVisibility(View.VISIBLE);
            loadToast.error();
        }
    }

    public void commentListResult(boolean value, List<Comment> comments) {
        this.comments = comments;
        commentsList.invalidate();
//        List<Comment> tempComment = new ArrayList<>();
//        tempComment.add(new Comment(
//                ticketInfo.getId(),
//                "Which operate 5 day working week applies, Mondays - Fridays 8:00am to 5:00pm"
//                , ));
        setupComments(comments);
        emptyList.setVisibility(View.GONE);
        loadToast.success();
    }

    public void commentTicket(String ticket_comment) {
        loadToast("Sending Comment...");
        onlineController.commentTicket(this
                , ticketInfo.getId()
                , sharedPreferences.getString("user_id", null)
                , ticket_comment
                , fileAttachmentAdapter.getItemCount() > 0 ? fileAttachmentAdapter.getItems().get(0).getFile() : null);
    }

    public void commentTicketResult(boolean value, TicketResponse ticketResponse) {
//        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.SUCCESS, ticketResponse.getMsg());
        finish();
        startActivity(getIntent());
        loadToast.success();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public void onSuccess() {
            if (this.progressBar != null) {
                this.progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void onError() {
            if (this.progressBar != null) {
                this.progressBar.setVisibility(View.GONE);
            }
        }
    }
}
