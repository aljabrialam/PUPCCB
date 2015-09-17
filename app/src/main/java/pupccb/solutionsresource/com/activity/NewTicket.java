package pupccb.solutionsresource.com.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.AgencyAdapter;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
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

/**
 * Created by User on 8/6/2015.
 */
public class NewTicket extends AppCompatActivity implements OfflineCommunicator, AttachmentDialogFragment.Communicator,
        View.OnClickListener, Validator.ValidationListener, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener, AgencyDialogFragment.Communicator, AgencyAdapter.Communicator,
        FileAttachmentAdapter.Communicator, CreateYesNoDialog.Communicator {

    public static final TouchEffect TOUCH = new TouchEffect();

    private StringBuilder stringBuilder;
    private Toolbar toolbar;
    private View view, connectionProblemView, preview;
    private Validator validator;
    private FileAttachment fileAttachment;
    private Controller controller, onlineController;
    private RecyclerView recyclerView;
    private FileAttachmentAdapter fileAttachmentAdapter;
    private List<Agencies> agencyList;
    private ArrayList<FileAttachment> fileAttachments = new ArrayList<>();
    private ProgressDialog progressDialog;
    private boolean isProgressBarVisible, isProblemViewShown;
    private Controller.MethodTypes methodTypes;
    private TextView textViewErrorMessage;
    private SharedPreferences sharedPreferences;
    private int position;
    private Agencies agencies;
    @NotEmpty
    private EditText editTextIncidentDetails;
    private EditText editTextComplainee;
    @NotEmpty
    private TextView editTextDate, editTextTime, editTextSubject;
    @NotEmpty(message = "Agency field is required")
    private TextView editTextAgency;

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
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        editTextDate = (TextView) view.findViewById(R.id.editTextDate);
        editTextTime = (TextView) view.findViewById(R.id.editTextTime);
        editTextAgency = (TextView) view.findViewById(R.id.editTextAgency);
        editTextComplainee = (EditText) view.findViewById(R.id.editTextComplainee);
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextIncidentDetails = (EditText) view.findViewById(R.id.editTextIncidentDetails);
        setTouchNClick(R.id.textViewAddAttachment);

        editTextDate.setText(BaseHelper.getDateNow());
        editTextTime.setText(BaseHelper.getTimeNow());

        setTouchNClick(R.id.editTextDate);
        setTouchNClick(R.id.editTextTime);
        setTouchNClick(R.id.editTextAgency);

        setAttachmentCard(view);
    }

    public void setAttachmentCard(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAttachment);
        fileAttachmentAdapter = new FileAttachmentAdapter(this, fileAttachments);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(fileAttachmentAdapter);
    }


    public void setError(ErrorHandler.Error error, Controller.MethodTypes methodTypes) {
        this.methodTypes = methodTypes;
        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.DANGER, error.getErrorMessage());
    }

    public void retryMethod(Controller.MethodTypes methodTypes) {
        switch (methodTypes) {
            case POST_FILE_ATTACHMENT: {
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
        if(fileAttachmentAdapter.getItemCount() > 0 || !editTextIncidentDetails.getText().toString().matches("") ){
            new CreateYesNoDialog(this, null, "New Ticket", "Are you sure you want to discard this ticket?",
                    false, false, getResources().getString(R.string.action_ok), getResources().getString(R.string.cancel),
                    RequestCodes.CANCEL_TICKET_CREATION);
        }else{
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
        showProgressDialog("Attaching file...");
        addItem(fileAttachment);
        //controller.postFileAttachment(this, ++ticket_id + "", sharedPreferences.getString("user_id", null), fileAttachment);
    }


    private void addItem(FileAttachment fileAttachment) {
        int i = 0;
        this.fileAttachmentAdapter.addItem(++i, fileAttachment);
        dismissProgressObjects(false);
        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.SUCCESS, "Add Attachment Success!");
    }

    public void postFileAttachmentResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        dismissProgressObjects(value);
        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.SUCCESS, "Add Attachment Success!");
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
        //dismissProgressObjects(value);
    }

    @Override
    public void deleteFile(FileAttachment fileAttachment) {
        controller.deleteFile(this, fileAttachment);
    }

    public void deleteFileResult() {
        new BaseHelper().toastMessage(this, 8000, ToastMessage.MessageType.SUCCESS, "Attachment has been removed");
    }

    public void createTicket(Ticket ticket) {
        showProgressDialog("Processing ticket...");
        onlineController.createTicket(this, ticket);
    }

    public void createTicketResult(TicketResponse ticketResponse, Ticket ticket) {
        progressDialog.dismiss();
        new BaseHelper().toastMessage(this, 8000, ToastMessage.MessageType.SUCCESS, ticketResponse.getMsg());
        startActivity(new Intent(this, NavigationDrawer.class));
        //finish();
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
                    RequestCodes.DELETE_ATTACHMMENT);
        }
    }

    @Override
    public void yesNoDialogPositiveResponse(int requestCode) {
        if (RequestCodes.DELETE_ATTACHMMENT == requestCode) {
            fileAttachmentAdapter.removeItem(position);
        }else if( RequestCodes.CANCEL_TICKET_CREATION == requestCode){
            super.onBackPressed();
        }
    }

    private void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.setProgress(0);
        progressDialog.setMax(50);
        progressDialog.show();
    }

    private void showProgressBarIndeterminate() {
        if (!isProgressBarVisible) {
            NewTicket.this.setProgressBarIndeterminateVisibility(true);
            isProgressBarVisible = true;
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
                if(fileAttachmentAdapter.getItemCount() < 2){
                    showBrowseFileDialog();
                }else{
                    new BaseHelper().toastMessage(this, 3000,ToastMessage.MessageType.WARNING, "The attachments size exceeds the allowable limit");
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
        showProgressDialog("Loading list...");
        onlineController.agencies(this);
    }

    public void agenciesResult(boolean value, List<Agencies> list) {
        if (list != null && list.size() > 0) {
            showAgencyListDialog("Select Agency",
                    new AgencyAdapter(this, R.layout.two_line_list_item,
                            null,
                            (ArrayList<Agencies>) list,
                            AgencyAdapter.MethodTypes.READ));
        }
        progressDialog.dismiss();
    }

    public void showAgencyListDialog(String title, AgencyAdapter adapter) {
        DialogFragment dialogFragment = new AgencyDialogFragment(adapter, 0);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "agencyListDialog");
    }

    @Override
    public void dialogSelectedAgency(Agencies agency, int requestCode) {
        this.agencies = agency;
        editTextAgency.setText(agency.getName());
    }

    @Override
    public void adapterSelectedAgency(Agencies agencies) {
        dialogAgencyDetail(agencies);
    }

    private void dialogAgencyDetail(Agencies agencies) {

        View viewAgency = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_content, null);

        Dialog.Builder uvaBuilder = new Dialog.Builder(NewTicket.this);
        uvaBuilder.setSecondaryHeaderImageResource(R.mipmap.ic_launcher)
                .setTitle(agencies.getName())
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }

                }, true)
                .setContentView(viewAgency)
                .create().show();
        ((TextView) viewAgency.findViewById(R.id.editTextLongName)).setText(agencies.getDescription());
    }

    private void dialogPreviewTicket(String message) {
        View myContent = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_preview_ticket, null);

        Dialog.Builder uvaBuilder = new Dialog.Builder(NewTicket.this);
        uvaBuilder.setSecondaryHeaderImageResource(R.mipmap.ic_launcher)
                .setTitle(message)
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateInString = editTextDate.getText().toString();
                        try {
                            Date date = formatter.parse(dateInString);
                            System.out.println(date);
                            System.out.println(formatter.format(date));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        createTicket(new Ticket(
                                        sharedPreferences.getString("user_id", null),
                                        dateInString + " " + editTextTime.getText().toString(),
                                        agencies.getId(),
                                        editTextComplainee.getText().toString(),
                                        editTextSubject.getText().toString(),
                                        editTextIncidentDetails.getText().toString()
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
        stringBuilder = new StringBuilder();
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
        dialogPreviewTicket("Ticket Confirmation");
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
}
