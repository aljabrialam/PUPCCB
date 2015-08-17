package pupccb.solutionsresource.com.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.util.TouchEffect;

/**
 * Created by User on 8/6/2015.
 */
public class Registration extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {


    public static final TouchEffect TOUCH = new TouchEffect();
    private Controller controller;
    private Validator validator;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private View view;
    @NotEmpty
    private EditText editTextFname;
    @NotEmpty
    private EditText editTextMname;
    @NotEmpty
    private EditText editTextLname;
    @NotEmpty
    private EditText editTextContactNumber;
    @Email
    private EditText editTextEmail;
    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC, message = "Your password must contain at least 6 alphanumeric characters")
    private EditText editTextPassword;
    @ConfirmPassword
    private EditText editTextConfirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_registration, null);
        setContentView(view);
        toolBar(view);
        findViewById(view);
        startController();
    }

    private void toolBar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void startController() {
        controller = new Controller(new OnlineHelper());
    }

    private void findViewById(View view) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        setTouchNClick(R.id.btnHaveAccount);
        setTouchNClick(R.id.btnSignUp);

        editTextFname = (EditText) view.findViewById(R.id.editTextFname);
        editTextMname = (EditText) view.findViewById(R.id.editTextMname);
        editTextLname = (EditText) view.findViewById(R.id.editTextLname);
        editTextContactNumber = (EditText) view.findViewById(R.id.editTextContactNumber);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) view.findViewById(R.id.editTextConfirmPassword);

    }

    public void register(RegistrationDetails registrationDetails) {
        controller.register(this, registrationDetails);
    }

    public void registerResult(RegistrationResponse registrationResponse, RegistrationDetails registrationDetails) {
        finish();
        startActivity(new Intent(Registration.this, Main.class));
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnHaveAccount) {
            startActivity(new Intent(getApplicationContext(), Main.class));
            finish();
        } else if (view.getId() == R.id.btnSignUp) {
            validator.validate();
//            startActivity(new Intent(getApplicationContext(), Main.class));
//            finish();
        }
    }

    @Override
    public void onValidationSucceeded() {
        register(new RegistrationDetails("", "", "", "", "", ""));
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
    public void onBackPressed() {
        startActivity(new Intent(this, Main.class));
        this.finish();
        super.onBackPressed();
    }

}
