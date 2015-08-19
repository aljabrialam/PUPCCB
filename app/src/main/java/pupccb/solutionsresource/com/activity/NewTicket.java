package pupccb.solutionsresource.com.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.AgencyAdapter;
import pupccb.solutionsresource.com.adapter.CardAdapter;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.fragment.AgencyDialogFragment;
import pupccb.solutionsresource.com.fragment.AttachmentDialogFragment;
import pupccb.solutionsresource.com.fragment.BrowseFileDialogFragment;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OfflineHelper;
import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;
import pupccb.solutionsresource.com.model.Agency;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.util.Dialog;
import pupccb.solutionsresource.com.util.RequestCodes;
import pupccb.solutionsresource.com.util.ToastMessage;
import pupccb.solutionsresource.com.util.TouchEffect;

/**
 * Created by User on 8/6/2015.
 */
public class NewTicket extends AppCompatActivity implements OfflineCommunicator, AttachmentDialogFragment.Communicator,
        View.OnClickListener,
        Validator.ValidationListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        AgencyDialogFragment.Communicator,
        AgencyAdapter.Communicator {

    public static final TouchEffect TOUCH = new TouchEffect();
    private StringBuilder stringBuilder;
    private Toolbar toolbar;
    private View view;
    private Validator validator;
    private SharedPreferences sharedPreferences;
    private FileAttachment fileAttachment;
    private Controller controller;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private CardAdapter cardAdapter;
    private List<Agency> agencyList;

    @NotEmpty
    private EditText editTextComplainee, editTextIncidentDetails;
    @NotEmpty
    private TextView editTextDate, editTextTime, textViewAddAttachment, editTextSubject;
    @NotEmpty(message = "Agency field is required")
    private TextView editTextAgency;
    private OfflineCommunicator offlineCommunicator;

    public static void launch(AppCompatActivity activity) {
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity);
        Intent intent = new Intent(activity, NewTicket.class);
        ActivityCompat.startActivity(activity, intent, options.toBundle());

    }

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_ticket_with_attachment, null);
        setContentView(view);
        toolBar();
        findViewById(view);
        startController();
    }
    private void startController() {
        controller = new Controller(new OfflineHelper());
    }


    private void toolBar() {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void findViewById(View view) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferences = BaseHelper.getSharedPreference(NewTicket.this);

        editTextDate = (TextView) view.findViewById(R.id.editTextDate);
        editTextTime = (TextView) view.findViewById(R.id.editTextTime);
        editTextAgency = (TextView) view.findViewById(R.id.editTextAgency);
        editTextComplainee = (EditText) view.findViewById(R.id.editTextComplainee);
        editTextSubject = (EditText) view.findViewById(R.id.editTextSubject);
        editTextIncidentDetails = (EditText) view.findViewById(R.id.editTextIncidentDetails);
        textViewAddAttachment = (TextView) view.findViewById(R.id.textViewAddAttachment);
        setTouchNClick(R.id.textViewAddAttachment);

        editTextDate.setText(BaseHelper.getDateNow());
        editTextTime.setText(BaseHelper.getTimeNow());

        setTouchNClick(R.id.editTextDate);
        setTouchNClick(R.id.editTextTime);
        setTouchNClick(R.id.editTextAgency);
        setAttachmentCard(view);
        initializeAgencyList();

    }

    public void setAttachmentCard(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAttachment);
        cardAdapter = new CardAdapter(this, null);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cardAdapter);
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
            case R.id.cancel_action:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showBrowseFileDialog() {
        BrowseFileDialogFragment dialogFragment = new BrowseFileDialogFragment(sharedPreferences.getBoolean("logged_in", false));
        dialogFragment.show(getSupportFragmentManager(), "browseFileDialogFragment");
    }

    @Override
    public void showBrowseFileDialogResult(FileAttachment fileAttachment) {
        this.fileAttachment = fileAttachment;
        int ticket_id = 0, user_id = 0;
        controller.postFileAttachment(this, ++ticket_id + "", ++user_id +"", fileAttachment);
    }

    public void postFileAttachmentResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.SUCCESS, "Add Attachment Success!");
        //consultationTabPagerAdapter.setConsultationDocumentsTabData(fileAttachmentAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == RequestCodes.FILE_SELECT) {
            BrowseFileDialogFragment fragment = (BrowseFileDialogFragment) getSupportFragmentManager().findFragmentByTag("browseFileDialogFragment");
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewAddAttachment:
                showBrowseFileDialog();
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
                getAgency(agencyList);
                break;
        }
    }

    public void initializeAgencyList() {
        agencyList = new ArrayList<Agency>();
        agencyList.add(new Agency("1", "CSC"));
        agencyList.add(new Agency("2", "NCC"));
        agencyList.add(new Agency("3", "BIR"));
        agencyList.add(new Agency("4", "PHILHEALTH"));
        agencyList.add(new Agency("5", "DOH"));
        agencyList.add(new Agency("5", "DTI"));
    }

    public void getAgency(List<Agency> list) {
        showAgencyListDialog("Select Agency",
                new AgencyAdapter(this, R.layout.two_line_list_item,
                        null,
                        (ArrayList<Agency>) list,
                        AgencyAdapter.MethodTypes.READ));
    }

    public void showAgencyListDialog(String title, AgencyAdapter adapter) {
        DialogFragment dialogFragment = new AgencyDialogFragment(adapter, 0);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "agencyListDialog");
    }

    @Override
    public void dialogSelectedAgency(Agency agency, int requestCode) {
        editTextAgency.setText(agency.getAgencyname());
    }

    @Override
    public void adapterSelectedAgency(Agency agency) {
        dialogAgencyDetail(agency.getAgencyname());
    }

    private void dialogAgencyDetail(String message) {

        Dialog.Builder uvaBuilder = new Dialog.Builder(NewTicket.this);
        uvaBuilder.setSecondaryHeaderImageResource(R.mipmap.ic_launcher)
                .setTitle(message)
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }

                }, true)
                .setContentView(R.layout.dialog_content)
                .create().show();
    }

    private void dialogPreviewTicket(String message) {

        Dialog.Builder uvaBuilder = new Dialog.Builder(NewTicket.this);
        uvaBuilder.setSecondaryHeaderImageResource(R.mipmap.ic_launcher)
                .setTitle(message)
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }

                }, true)
                .setOnSecondaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                    }

                }, true)
                .setContentView(R.layout.dialog_preview_ticket)
                .create().show();
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
        dialogPreviewTicket("Ticket Number");
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
