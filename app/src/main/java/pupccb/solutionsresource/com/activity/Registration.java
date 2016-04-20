package pupccb.solutionsresource.com.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import me.zhanghai.android.materialprogressbar.IndeterminateHorizontalProgressDrawable;
import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.model.RegistrationDetails;
import pupccb.solutionsresource.com.model.RegistrationResponse;
import pupccb.solutionsresource.com.util.Dialog;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.ToastMessage;
import pupccb.solutionsresource.com.util.TouchEffect;

/**
 * Created by User on 8/6/2015.
 */
public class Registration extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {

    public static final TouchEffect TOUCH = new TouchEffect();

    private ProgressBar progressBar;
    private Controller controller;
    private Validator validator;
    private View view;
    @NotEmpty(trim = true)
    private EditText editTextFname;
    @NotEmpty(trim = true)
    private EditText editTextLname;
    @NotEmpty(trim = true)
    private EditText editTextContactNumber;
    @Email
    private EditText editTextEmail;
    @Password(min = 6, scheme = Password.Scheme.ALPHA, message = "Your password must contain at least 6 alphanumeric characters")
    private EditText editTextPassword;
    @ConfirmPassword
    private EditText editTextConfirmPassword;
    private Button btnSignUp;
    private RadioButton radioMale, radioFemale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_registration, null);
        setContentView(view);
        toolBar();
        findViewById(view);
        startController();
    }

    private void toolBar() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void startController() {
        controller = new Controller(new OnlineHelper());
    }

    private void findViewById(View view) {

        progressBar = (ProgressBar) view.findViewById(R.id.registrationProgress);
        progressBar.setIndeterminateDrawable(new IndeterminateHorizontalProgressDrawable(this));
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.myPrimaryColor), PorterDuff.Mode.SRC_IN);

        validator = new Validator(this);
        validator.setValidationListener(this);
        setTouchNClick(R.id.btnHaveAccount);
        setTouchNClick(R.id.btnSignUp);

        editTextFname = (EditText) view.findViewById(R.id.editTextFname);
        editTextLname = (EditText) view.findViewById(R.id.editTextLname);
        editTextContactNumber = (EditText) view.findViewById(R.id.editTextContactNumber);
        editTextEmail = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) view.findViewById(R.id.editTextConfirmPassword);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        radioFemale = (RadioButton) view.findViewById(R.id.radioFemale);
        radioMale = (RadioButton) view.findViewById(R.id.radioMale);
    }

    public void setError(ErrorHandler.Error error, Controller.MethodTypes methodTypes) {
        progressBar.setVisibility(View.INVISIBLE);
        btnSignUp.setEnabled(true);
        new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.DANGER, error.getErrorMessage());
    }

    public void register(RegistrationDetails registrationDetails) {
        progressBar.setVisibility(View.VISIBLE);
        btnSignUp.setEnabled(false);
        controller.register(this, registrationDetails);
    }

    public void registerResult(RegistrationResponse registrationResponse, RegistrationDetails registrationDetails) {
        progressBar.setVisibility(View.INVISIBLE);
        if (registrationResponse.getMsg().contains("Success")) {
            dialog(registrationResponse.getMsg());
        } else {
            new BaseHelper().toastMessage(this, 2000, ToastMessage.MessageType.DANGER, registrationResponse.getMsg());
        }
        btnSignUp.setEnabled(true);
    }

    private void dialog(String message) {
        Dialog.Builder defaultBuilder = new Dialog.Builder(Registration.this);
        defaultBuilder.setSecondaryHeaderImageResource(R.mipmap.ic_launcher)
                .setPrimaryButtonDefaultColor(R.color.main_color_green)
                .setPrimaryButtonPressedColor(R.color.success)
                .setOnPrimaryButtonClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), Main.class));
                        finish();
                    }

                }, true)
                .setTitle(message)
                .setTitleTextSize(16)
                .create().show();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnHaveAccount) {
            startActivity(new Intent(getApplicationContext(), Main.class));
            finish();
        } else if (view.getId() == R.id.btnSignUp) {
            validator.validate();
        }
    }

    @Override
    public void onValidationSucceeded() {
        register(new RegistrationDetails(
                        editTextFname.getText().toString(),
                        editTextLname.getText().toString(),
                        editTextContactNumber.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextPassword.getText().toString(),
                        (radioMale.isChecked()) ? "Male" : "Female"
                )
        );
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
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
