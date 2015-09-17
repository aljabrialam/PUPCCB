package pupccb.solutionsresource.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import me.zhanghai.android.materialprogressbar.IndeterminateProgressDrawable;
import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.Session;
import pupccb.solutionsresource.com.model.UserInfo;
import pupccb.solutionsresource.com.model.UserInfoResponse;
import pupccb.solutionsresource.com.util.CreateAlertDialog;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.ToastMessage;
import pupccb.solutionsresource.com.util.TouchEffect;

public class Main extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {

    public static final TouchEffect TOUCH = new TouchEffect();
    private Controller controller;
    private Validator validator;
    private SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private ToastMessage toastMessage;
    private BaseHelper baseHelper;

    private boolean onGoing;
    @Email(message = "Email address is required")
    private EditText editTextUsername;
    @Password(message = "Password is required")
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(BaseHelper.getSharedPreference(), Activity.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("logged_in", false)) {
            overridePendingTransition(0, 0);
            startNavigationDrawer();
        } else {
            View view = getLayoutInflater().inflate(R.layout.activity_login, null);
            setScreenOrientation(view);
            setContentView(view);
            startController();
            findViewById(view);
           // temporatyLogin();
        }

    }

    public void startNavigationDrawer() {
        finish();
        startActivity(new Intent(Main.this, NavigationDrawer.class));
    }

    private void setScreenOrientation(View view) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource(R.drawable.landscape_bg);
        } else {
            view.setBackgroundResource(R.drawable.portrait_bg);
        }
    }

    private void startController() {
        controller = new Controller(new OnlineHelper());

        if (Main.this.getIntent().getBooleanExtra("sessionTimeout", false)) {
            new CreateAlertDialog(Main.this, "Session", "Your Session Has Timed Out", true);
        }
    }

    private void findViewById(View view) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferences = BaseHelper.getSharedPreference(Main.this);
        baseHelper = new BaseHelper();

        progressBar = (ProgressBar) view.findViewById(R.id.progressBarLogin);
        progressBar.setIndeterminateDrawable(new IndeterminateProgressDrawable(this));
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.myPrimaryColor), PorterDuff.Mode.SRC_IN);
        editTextUsername = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        setTouchNClick(R.id.btnLogin);
        setTouchNClick(R.id.btnReg);
    }

    public void setError(ErrorHandler.Error error, Controller.MethodTypes methodTypes) {
        onGoing = false;
        progressBar.setVisibility(View.INVISIBLE);
        baseHelper.toastMessage(this, 5000, ToastMessage.MessageType.DANGER, error.getErrorMessage());
    }

    private void temporatyLogin() {
        editTextUsername.setText("juan@gmail.com");
        editTextPassword.setText("secret");
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void login(Login login) {
        onGoing = true;
        progressBar.setVisibility(View.VISIBLE);
        controller.login(this, login);
    }

    public void loginResult(Session session, Login login) {
        onGoing = false;
        progressBar.setVisibility(View.INVISIBLE);
        SharedPreferences.Editor editSharedPreference = BaseHelper.getEditSharedPreference(this);
        editSharedPreference.putString("access_token", session.getAccess_token());
        editSharedPreference.putString("refresh_token", session.getRefresh_token());
        editSharedPreference.putString("username", login.getUsername());
        editSharedPreference.putBoolean("logged_in", true);
        editSharedPreference.apply();

        userInfo(new UserInfo(session.getAccess_token()));
        startActivity(new Intent(Main.this, NavigationDrawer.class));
        finish();


    }

    public void userInfo(UserInfo userInfo) {
        controller.userInfo(this, userInfo);
    }

    public void userInfoResult(UserInfoResponse userInfoResponse, UserInfo userInfo) {

        SharedPreferences.Editor editSharedPreference = BaseHelper.getEditSharedPreference(this);
        editSharedPreference.putString("user_id", userInfoResponse.getId());
        editSharedPreference.apply();

    }

    @Override
    public void onValidationSucceeded() {
        if (!onGoing) {
            login(new Login(editTextUsername.getText().toString(),
                    editTextPassword.getText().toString()));
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
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            validator.validate();
        } else if (view.getId() == R.id.btnReg) {
            startActivity(new Intent(this, Registration.class));
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
}
