package pupccb.solutionsresource.com.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.adapter.FileAttachmentAdapter;
import pupccb.solutionsresource.com.fragment.AttachmentDialogFragment;
import pupccb.solutionsresource.com.fragment.BrowseFileDialogFragment;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;
import pupccb.solutionsresource.com.model.FileAttachment;
import pupccb.solutionsresource.com.util.RequestCodes;
import pupccb.solutionsresource.com.util.TouchEffect;

/**
 * Created by User on 8/6/2015.
 */
public class NewTicket extends AppCompatActivity implements OfflineCommunicator, AttachmentDialogFragment.Communicator,
        View.OnClickListener,
        Validator.ValidationListener,
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    public static final TouchEffect TOUCH = new TouchEffect();
    private StringBuilder stringBuilder;
    private Boolean processClick = true;
    private Toolbar toolbar;
    private View view;
    private Validator validator;
    private SharedPreferences sharedPreferences;
    private FileAttachment fileAttachment;
    private Controller controller;
    @NotEmpty
    private EditText  editTextComplainee, editTextIncidentDetails;
    @NotEmpty
    private TextView editTextDate, editTextTime,editTextAgency, textViewAddAttachment;
    private OfflineCommunicator offlineCommunicator;
    private CardView cardViewAttachment;

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
        editTextIncidentDetails = (EditText) view.findViewById(R.id.editTextIncidentDetails);
        textViewAddAttachment = (TextView) view.findViewById(R.id.textViewAddAttachment);
        setTouchNClick(R.id.textViewAddAttachment);

        editTextDate.setText(BaseHelper.getDateNow());
        editTextTime.setText(BaseHelper.getTimeNow());
        setTouchNClick(R.id.editTextDate);
        setTouchNClick(R.id.editTextTime);
        setTouchNClick(R.id.editTextAgency);

        cardViewAttachment = (CardView) view.findViewById(R.id.card_attachment);
        cardViewAttachment.setVisibility(View.GONE);

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
            case R.id.send_ticket:
                validator.validate();
            case R.id.cancel_action:

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        editTextTime.setClickable(true);
        editTextDate.setClickable(true);
        editTextAgency.setClickable(true);
    }

    @Override
    public void showBrowseFileDialog() {
        BrowseFileDialogFragment dialogFragment = new BrowseFileDialogFragment(sharedPreferences.getBoolean("logged_in", false));
        dialogFragment.show(getSupportFragmentManager(), "browseFileDialogFragment");
    }

    @Override
    public void showBrowseFileDialogResult(FileAttachment fileAttachment) {
        this.fileAttachment = fileAttachment;
        controller.postFileAttachment(this, "", "", fileAttachment);
    }

    public void postFileAttachmentResult(Boolean value, FileAttachmentAdapter fileAttachmentAdapter) {
        Toast.makeText(NewTicket.this, "Add Attachment Success!", Toast.LENGTH_SHORT).show();
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
                break;
        }
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
        String time = stringBuilder.append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(':').append(minute < 10 ? "0" + minute : minute).append(":00").toString();
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
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
