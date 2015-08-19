package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.Session;
import pupccb.solutionsresource.com.util.ErrorHandler;
import pupccb.solutionsresource.com.util.TouchEffect;

public class Main extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {

    public static final TouchEffect TOUCH = new TouchEffect();
    private Controller controller;
    private Validator validator;
    private SharedPreferences sharedPreferences;

    private boolean onGoing;
    @Email
    private EditText editTextUsername;
    @Password(message = "Password is required")
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_login, null);
        setScreenOrienttion(view);
        setContentView(view);
        startController();
        findViewById(view);
    }

    private void setScreenOrienttion(View view) {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            view.setBackgroundResource(R.drawable.landscape_bg);
        } else {
            view.setBackgroundResource(R.drawable.portrait_bg);
        }
    }

    private void startController() {
        controller = new Controller(new OnlineHelper());
    }

    private void findViewById(View view) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferences = BaseHelper.getSharedPreference(Main.this);
        editTextUsername = (EditText) view.findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        setTouchNClick(R.id.btnLogin);
        setTouchNClick(R.id.btnReg);

        //temporary fast login
        editTextUsername.setText("qwe@qwe.com");
        editTextPassword.setText("qweqweqwe");
    }

    public void setError(ErrorHandler.Error error, Controller.MethodTypes methodTypes) {
        onGoing = false;
        Toast.makeText(getApplicationContext(), error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    private void clearTextView() {
        editTextUsername.setText("");
        editTextPassword.setText("");
    }

    public void login(Login login) {
        onGoing = true;
        controller.login(this, login);
    }

    public void loginResult(Session session, Login login) {
        onGoing = false;

        SharedPreferences.Editor editSharedPreference = BaseHelper.getEditSharedPreference(this);
        editSharedPreference.putString("access_token", session.getAccess_token());
        editSharedPreference.putString("username", login.getUsername());
        editSharedPreference.putBoolean("logged_in", true);
        editSharedPreference.apply();

        startActivity(new Intent(Main.this, NavigationDrawer.class));
        finish();
    }

    @Override
    public void onValidationSucceeded() {
        startActivity(new Intent(this, NavigationDrawer.class));
        finish(); //temporary finish activity, handle login()

//        if (!onGoing) {
//            login(new Login(editTextUsername.getText().toString(),
//                            editTextPassword.getText().toString()));
//        }
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
