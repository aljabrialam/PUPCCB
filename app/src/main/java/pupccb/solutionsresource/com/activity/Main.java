package pupccb.solutionsresource.com.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;

import pupccb.solutionsresource.com.R;
import pupccb.solutionsresource.com.helper.BaseHelper;
import pupccb.solutionsresource.com.helper.Controller;
import pupccb.solutionsresource.com.helper.OnlineHelper;
import pupccb.solutionsresource.com.model.Login;
import pupccb.solutionsresource.com.model.Session;
import pupccb.solutionsresource.com.util.TouchEffect;

public class Main extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {

    public static final TouchEffect TOUCH = new TouchEffect();
    private Controller controller;
    private Validator validator;
    private SharedPreferences sharedPreferences;

    private boolean onGoing;
    @Required(order = 1, message = "Client Secret is required")
    private EditText editTextClientSecret;
    @Required(order = 2, message = "Username is required")
    private EditText editTextUsername;
    @Password(order = 3)
    private EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_login, null);
        setContentView(view);
        start();
        findViewById(view);
    }

    private void start() {
        controller = new Controller(new OnlineHelper());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void findViewById(View view) {
        validator = new Validator(this);
        validator.setValidationListener(this);
        sharedPreferences = BaseHelper.getSharedPreference(Main.this);


        editTextUsername =(EditText)view.findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)view.findViewById(R.id.editTextPassword);
        setTouchNClick(R.id.btnLogin);
        setTouchNClick(R.id.btnReg);
    }


    public void setError(String errorMessage, Controller.MethodTypes methodTypes) {
        onGoing = false;
        Toast.makeText(getApplicationContext(), errorMessage.replace("401:", "").replace("showRetry:", "").replace("alertDialog:", ""), Toast.LENGTH_SHORT).show();
    }

    private void TemporatyLogin() {
        editTextUsername.setText("data-collector");
        editTextPassword.setText("data-collector");
        editTextClientSecret.setText("12345");
    }

    private void clearAlertDialog() {
        editTextUsername.setText("");
        editTextPassword.setText("");
        editTextClientSecret.setText("");
        editTextClientSecret.requestFocus();
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
        editSharedPreference.putString("client_id", login.getClient_id());
        editSharedPreference.putString("client_secret", login.getClient_secret());
        editSharedPreference.putBoolean("logged_in", true);
        editSharedPreference.apply();

        finish();
        startActivity(new Intent(Main.this, NavigationDrawer.class));
    }

    @Override
    public void onValidationSucceeded() {
        if (!onGoing) {
            login(new Login(
                    editTextUsername.getText().toString(),
                    editTextPassword.getText().toString(),
                    editTextClientSecret.getText().length() > 0 ?
                            editTextUsername.getText().toString() :
                            sharedPreferences.getString("client_id", null),
                    editTextClientSecret.getText().length() > 0 ?
                            editTextClientSecret.getText().toString() :
                            sharedPreferences.getString("client_secret", null),
                    "data_collector",
                    "password"));
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        String message = failedRule.getFailureMessage();
        if (failedView instanceof EditText) {
            failedView.requestFocus();
            ((EditText) failedView).setError(message);
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {

        if(view.getId() == R.id.btnLogin) {
            startActivity(new Intent(this, NavigationDrawer.class));
            this.finish();
        }
        else if (view.getId() == R.id.btnReg){
            startActivity(new Intent(this, Registration.class));
            this.finish();
        }
    }


    public View setClick(int var1) {
        View var2 = this.findViewById(var1);
        var2.setOnClickListener(this);
        return var2;
    }

    public View setTouchNClick(int var1) {
        View var2 = this.setClick(var1);
        var2.setOnTouchListener(TOUCH);
        return var2;
    }
}
