package pupccb.solutionsresource.com.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import net.steamcrafted.loadtoast.LoadToast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.AgencyAdapter;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.fragment.AddAgencyDialogFragment;
import pupccb.solutionsresource.com.fragment.AgencyDialogFragment;
import pupccb.solutionsresource.com.fragment.AttachmentDialogFragment;
import pupccb.solutionsresource.com.fragment.BrowseFileDialogFragment;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OfflineHelper;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;
import pupccb.solutionsresource.com.model.Agencies;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.model.Ticket;
import pupccb.solutionsresource.com.model.TicketResponse;
import pupccb.solutionsresource.com.util.CreateYesNoDialog;
import pupccb.solutionsresource.com.util.Dialog;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.RequestCodes;
import pupccb.solutionsresource.com.util.ToastMessage;
import pupccb.solutionsresource.com.util.TouchEffect;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by User on 8/6/2015.
 */
public class NewTicket extends AppCompatActivity implements OfflineCommunicator, AttachmentDialogFragment.Communicator,
        View.OnClickListener, Validator.ValidationListener, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, AgencyDialogFragment.Communicator, AgencyAdapter.Communicator,
        FileAttachmentAdapter.Communicator, CreateYesNoDialog.Communicator, AddAgencyDialogFragment.Communicator {

    public static final TouchEffect TOUCH = new TouchEffect();

    private View view, connectionProblemView, preview;
    private Validator validator;
    private FileAttachment fileAttachment;
    private Controller controller, onlineController;
    private RecyclerView recyclerView;
    private FileAttachmentAdapter fileAttachmentAdapter;
    private List<Agencies> agencyList;
    private List<FileAttachment> fileAttachments = new ArrayList<>();
    private ProgressDialog progressDialog;
    private boolean isProgressBarVisible, isProblemViewShown;
    private Controller.MethodTypes methodTypes;
    private TextView textViewErrorMessage, textViewCounter;
    private SharedPreferences sharedPreferences;
    private int position;
    private Agencies agencies;
    private File file;
    private Boolean online, connected;
    @NotEmpty(trim = true)
    private EditText editTextIncidentDetails;
    private EditText editTextComplainee;
    @NotEmpty(trim = true)
    private TextView editTextDate, editTextTime, editTextSubject;
    @NotEmpty(trim = true, message = "Agency field is required")
    private TextView editTextAgency;
    private RelativeLayout rlAttachment;
    private Activity activity;
    private LoadToast loadToast;
    private boolean isGoing;
    private ToastMessage toastMessage;
    private TextView textViewAddAttachment, textViewLocation;
    private CheckBox checkBoxAnonymous;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_ticket_with_attachment, null);
        connectionProblemView = getLayoutInflater().inflate(R.layout.activity_connection_problem, null);
        setContentView(view);
        toolBar();
        findViewById(view);
        findViewByIdConnectionLayout(connectionProblemView);
        startController();
    }

    private void startController() {
        controller = new Controller(new OfflineHelper());
        onlineController = new Controller(new OnlineHelper());
    }

    private void toolBar() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findViewByIdConnectionLayout(View view) {
        textViewErrorMessage = (TextView) view.findViewById(R.id.textViewErrorMessage);
        TextView textViewRetry = (TextView) view.findViewById(R.id.textViewRetry);
        textViewRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(view);
                retryMethod(methodTypes);
            }
        });
    }

    private void findViewById(View view) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferences = BaseHelper.getSharedPreference(NewTicket.this);

        loadToast = new LoadToast(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        toastMessage = new ToastMessage();

        rlAttachment = (RelativeLayout) view.findViewById(R.id.rlAttachment);
        activity = this;

        editTextDate = (TextView) view.findViewById(R.id.editTextDate);
        editTextTime = (TextView) view.findViewById(R.id.editTextTime);
        editTextAgency = (TextView) view.findViewById(R.id.editTextAgency);
        editTextComplainee = (EditText) view.findViewById(R.id.editTextComplainee);
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextIncidentDetails = (EditText) view.findViewById(R.id.editTextIncidentDetails);

        textViewCounter = (TextView) view.findViewById(R.id.textViewCounter);
        textViewAddAttachment = (TextView) view.findViewById(R.id.textViewAddAttachment);
        checkBoxAnonymous = (CheckBox) view.findViewById(R.id.checkBoxAnonymous);
        textViewLocation = (TextView) view.findViewById(R.id.textViewLocation);

        setTouchNClick(R.id.textViewAddAttachment);

        editTextDate.setText(BaseHelper.getDateNow());
        editTextTime.setText(BaseHelper.getTimeNow());

        setTouchNClick(R.id.editTextDate);
        setTouchNClick(R.id.editTextTime);
        setTouchNClick(R.id.editTextAgency);

        setAttachmentCard(view);
        presentShowcaseSequence();
    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500);
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, RequestCodes.SHOWCASE_ID);
        sequence.setConfig(config);

        sequence.addSequenceItem(presentShowcaseView(editTextDate, "By default current date is selected, if  you wish to change it for the exact date of the incident tap the field then select from the date picker."));
        sequence.addSequenceItem(presentShowcaseView(editTextTime, "By default current time is selected, if  you wish to change it for the exact time of the incident tap the field then select from the time picker."));
        sequence.addSequenceItem(presentShowcaseView(editTextAgency, "Tap this field to select the name of an agency from the dialog list."));
        sequence.addSequenceItem(presentShowcaseView(editTextComplainee, "Enter the name the person you want to give a feedback, comment or complain (Optional)"));
        sequence.addSequenceItem(presentShowcaseView(editTextSubject, "Enter the subject, this will serve as a title of the ticket."));
        sequence.addSequenceItem(presentShowcaseView(editTextIncidentDetails, "Enter the details of you comment, feedback or complain here."));
        sequence.addSequenceItem(presentShowcaseView(textViewAddAttachment, "Tap this to add an image attachment from your gallery or capture via camera then attach the image."));
        sequence.addSequenceItem(presentShowcaseView(checkBoxAnonymous, "Tap this to change your anonymity. If checked, your personal details will be hidden"));
        sequence.start();
    }

    private MaterialShowcaseView presentShowcaseView(View view, String message) {
        return new MaterialShowcaseView.Builder(this)
                .setTarget(view)
                .setContentText(message)
                .withRectangleShape()
                .setDismissText("GOT IT")
                .setDismissOnTouch(false)
                .build();
    }

    public void setAttachmentCard(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAttachment);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        fileAttachmentAdapter = new FileAttachmentAdapter(this, fileAttachments);
        recyclerView.setAdapter(fileAttachmentAdapter);
    }

    private void loadToast(String message) {
        loadToast.setProgressColor(R.color.selected_selected_blue);
        loadToast.setProgressColor(R.color.half_black);
        loadToast.setTranslationY(200);
        loadToast.setText(message);
        loadToast.show();
    }

    public void setError(ErrorHandler.Error error, Controller.MethodTypes methodTypes) {
        this.methodTypes = methodTypes;
        toast(2000, error.getErrorMessage(), ToastMessage.MessageType.DANGER);
        loadToast.error();
        isGoing = false;
    }

    public void retryMethod(Controller.MethodTypes methodTypes) {
        switch (methodTypes) {
            case POST_FILE_ATTACHMENT: {
                break;
            }
            case CREATE_AGENCY: {
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_ticket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.send_ticket:
                validator.validate();
                break;
            case R.id.action_cancel:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (fileAttachmentAdapter.getItemCount() > 0 || !editTextIncidentDetails.getText().toString().matches("")) {
            new CreateYesNoDialog(this, null, "New Ticket", "Are you sure you want to discard this ticket?",
                    false, false, getResources().getString(R.string.action_ok), getResources().getString(R.string.cancel),
                    RequestCodes.CANCEL_TICKET_CREATION);
        } else if (isGoing) {

        } else {
            super.onBackPressed();
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
        fileAttachments = new ArrayList<>(fileAttachments);
        loadToast("Attaching file...");
        addItem(fileAttachments);
    }

    private void addItem(List<FileAttachment> fileAttachments) {
        int i = 0;
        this.fileAttachmentAdapter.addItem(++i, fileAttachment);
        loadToast.success();
        checkAttachment();
        toast(2000, "Add Attachment Success!", ToastMessage.MessageType.SUCCESS);
    }

    public void checkAttachment() {
        if (this.fileAttachmentAdapter.getItemCount() > 0) {
            rlAttachment.setVisibility(View.VISIBLE);
        } else {
            rlAttachment.setVisibility(View.GONE);
        }
    }

    public void postFileAttachmentResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        toast(200, "Add Attachment Success!", ToastMessage.MessageType.SUCCESS);
    }

    //<-- FILE ATTACHMENT -->
    public void getFileAttachmentList() {
        //showProgressBarIndeterminate();
        controller.getFileAttachmentList(this, "", "");
    }

    public void getFileAttachmentListResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        this.fileAttachmentAdapter = fileAttachmentAdapter;
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(fileAttachmentAdapter);
    }

    @Override
    public void deleteFile(FileAttachment fileAttachment) {
        controller.deleteFile(this, fileAttachment);
    }

    public void deleteFileResult() {
        toast(3000, "Attachment has been removed", ToastMessage.MessageType.SUCCESS);
    }

    public void createTicket(Ticket ticket) {
        loadToast("Sending ticket...");
        onlineController.createTicket(this, ticket);
    }

    public void createTicketResult(TicketResponse ticketResponse, Ticket ticket) {
        toast(2000, ticketResponse.getMsg(), ToastMessage.MessageType.SUCCESS);
        loadToast.success();
        //startActivity(new Intent(this, NavigationDrawer.class));
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void toast(int duration, String message, ToastMessage.MessageType messageType) {
        toastMessage.init(this)
                .timer(duration)
                .gravity(Gravity.BOTTOM)
                .message(message)
                .messageType(messageType)
                .build()
                .show();
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
    public void yesNoDialogPositiveResponse(int requestCode) {
        if (RequestCodes.DELETE_ATTACHMENT == requestCode) {
            fileAttachmentAdapter.removeItem(position);
            checkAttachment();
        } else if (RequestCodes.CANCEL_TICKET_CREATION == requestCode) {
            super.onBackPressed();
        }
    }

    private void dismissProgressObjects(Boolean value) {
        if (!value) {
            if (isProgressBarVisible) {
                NewTicket.this.setProgressBarIndeterminateVisibility(false);
                isProgressBarVisible = false;
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (isProblemViewShown) {
                setContentView(view);
                isProblemViewShown = false;
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewAddAttachment:
                if (fileAttachmentAdapter.getItemCount() < 1) {
                    showBrowseFileDialog();
                } else {
                    toast(3000, "The attachments size exceeds the allowable limit", ToastMessage.MessageType.WARNING);
                }
                break;
            case R.id.editTextDate:
                datePicker();
                editTextDate.setClickable(false);
                break;
            case R.id.editTextTime:
                timePicker();
                editTextTime.setClickable(false);
                break;
            case R.id.editTextAgency:
                agencies();
                break;
        }
    }

    public void agencies() {
        isGoing = true;
        loadToast("Loading...");
        onlineController.agencies(this);
    }

    public void agenciesResult(boolean value, List<Agencies> list) {
        if (list != null && list.size() > 0) {
            showAgencyListDialog("Select Agency",
                    new AgencyAdapter(this, R.layout.two_line_list_item,
                            null,
                            (ArrayList<Agencies>) list,
                            AgencyAdapter.MethodTypes.READ));

            loadToast.success();
        } else {
            showAgencyListDialog("Select Agency",
                    new AgencyAdapter(this, R.layout.two_line_list_item,
                            null,
                            (ArrayList<Agencies>) list,
                            AgencyAdapter.MethodTypes.READ));
            loadToast.error();
        }
        isGoing = false;
    }

    public void showAgencyListDialog(String title, AgencyAdapter adapter) {
        DialogFragment dialogFragment = new AgencyDialogFragment(adapter, 0);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "agencyListDialog");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (progressDialog != null)
            progressDialog.dismiss();
        toastMessage.hide();
    }

    @Override
    public void dialogSelectedAgency(Agencies agency, int requestCode) {
        this.agencies = agency;
        editTextAgency.setText(agency.getName());
        textViewLocation.setText(agency.getLocation());
    }

    @Override
    public void adapterSelectedAgency(Agencies agencies) {
        dialogAgencyDetail(agencies);
    }

    private void dialogAgencyDetail(Agencies agencies) {
        View viewAgency = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_content, null);
        TextView textViewAgency = ((TextView) viewAgency.findViewById(R.id.editTextAgencyDetails));
        Dialog.Builder uvaBuilder = new Dialog.Builder(NewTicket.this);
        uvaBuilder.setSecondaryHeaderImageResource(R.mipmap.ic_launcher)
                .setTitle("Agency")
                .setPrimaryButtonDefaultColor(R.color.main_color_green)
                .setPrimaryButtonPressedColor(R.color.success)
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }, true)
                .setContentView(viewAgency)
                .create().show();
        textViewAgency.setVisibility(View.VISIBLE);
        textViewAgency.setGravity(Gravity.CENTER);
        textViewAgency.setText(agencies.getName() + "\n" + agencies.getLocation() + "\n" + agencies.getRegion() + "\n" + agencies.getProvince());
    }

    private void dialogPreviewTicket(String message) {
        View myContent = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_preview_ticket, null);

        Dialog.Builder uvaBuilder = new Dialog.Builder(NewTicket.this);
        uvaBuilder.setSecondaryHeaderImageResource(R.mipmap.ic_launcher)
                .setTitle(message)
                .setPrimaryButtonDefaultColor(R.color.main_color_green)
                .setPrimaryButtonPressedColor(R.color.success)
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        createTicket(new Ticket(
                                        sharedPreferences.getString("user_id", null),
                                        new BaseHelper().formatDate(editTextDate.getText().toString()) + " " + (editTextTime.getText().toString()).replace("AM", "").replace("PM", ""),
                                        agencies.getId(),
                                        editTextComplainee.getText().toString(),
                                        editTextSubject.getText().toString(),
                                        editTextIncidentDetails.getText().toString(),
                                        fileAttachmentAdapter.getItemCount() > 0 ? fileAttachmentAdapter.getItems().get(0).getFile() : null,
                                        checkBoxAnonymous.isChecked()
                                )
                        );
                    }

                }, true)
                .setOnSecondaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }

                }, true)

                .setContentView(myContent)
                .create().show();
        ((TextView) myContent.findViewById(R.id.editTextDate)).setText(editTextDate.getText());
        ((TextView) myContent.findViewById(R.id.editTextTime)).setText(editTextTime.getText());
        ((TextView) myContent.findViewById(R.id.editTextAgency)).setText(editTextAgency.getText());
        ((EditText) myContent.findViewById(R.id.editTextComplainee)).setText(editTextComplainee.getText());
        ((TextView) myContent.findViewById(R.id.editTextSubject)).setText(editTextSubject.getText());
        ((EditText) myContent.findViewById(R.id.editTextIncidentDetails)).setText(editTextIncidentDetails.getText());
        ((CheckBox) myContent.findViewById(R.id.checkBoxAnonymous)).setChecked(checkBoxAnonymous.isChecked());
    }

    public View setClick(int btn) {
        View view = this.findViewById(btn);
        view.setOnClickListener(this);
        return view;
    }

    public View setTouchNClick(int btn) {
        View view = this.setClick(btn);
        view.setOnTouchListener(TOUCH);
        return view;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        editTextTime.setClickable(true);
        String timeSet;
        if (hourOfDay > 12) {
            hourOfDay -= 12;
            timeSet = "PM";
        } else if (hourOfDay == 0) {
            hourOfDay += 12;
            timeSet = "AM";
        } else if (hourOfDay == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes;
        if (minute < 10)
            minutes = "0" + minute;
        else
            minutes = String.valueOf(minute);

        // Append in a StringBuilder
        String time = new StringBuilder().append(hourOfDay).append(':').append(minutes).append(" ").append(timeSet).toString();
        // String time = stringBuilder.append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(':').append(minute < 10 ? "0" + minute : minute).append(":00").toString();
        editTextTime.setText(time);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        editTextDate.setClickable(true);
        String date = (++monthOfYear) + "/" + dayOfMonth + "/" + year;
        editTextDate.setText(date);
    }

    private void timePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                NewTicket.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.setThemeDark(false);
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
                editTextTime.setClickable(true);
            }
        });
        timePickerDialog.show(getFragmentManager(), "Timepickerdialog");
    }

    private void datePicker() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                NewTicket.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMaxDate(now);
        datePickerDialog.setThemeDark(false);
        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
                editTextDate.setClickable(true);
            }
        });
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onValidationSucceeded() {
        if (BaseHelper.isOnline(this)) {
            dialogPreviewTicket("Ticket Confirmation");
        } else {
            toast(3000, "No network connection!", ToastMessage.MessageType.DANGER);
        }
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
                toast(2000, message, ToastMessage.MessageType.DANGER);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toastMessage.hide();
    }

    @Override
    protected void onPause() {
        super.onPause();
        toastMessage.hide();
    }

    @Override
    public void createdAgency(Agencies agency) {
        //textViewLocation.setText(agency.getLocation());
        //editTextAgency.setText(agency.getName());
        createAgency(agency);
    }

    @Override
    public void dismissAgencyList() {
        agencies();
    }

    public void createAgency(Agencies agency) {
        isGoing = false;
        loadToast("Creating Agency...");
        onlineController.createAgency(this, agency);
    }

    public void createAgencyResult(boolean value, Agencies agency) {
        toast(2000, "SUCCESS", ToastMessage.MessageType.SUCCESS);
        loadToast.success();
        this.agencies = agency;
        Toast.makeText(this, agencies.getId().toString(), Toast.LENGTH_LONG).show();
    }
}
