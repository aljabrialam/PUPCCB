package pupccb.solutionsresource.com.util;

import android.util.Log;

import com.google.gson.Gson;
import com.octo.android.robospice.exception.NetworkException;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;

import pupccb.solutionsresource.com.model.APIErrors;
import pupccb.solutionsresource.com.model.Errors;
import retrofit.RetrofitError;

/**
 * Created by Richard on 13/08/2015.
 */
public class ErrorHandler {
    //TAGS
    protected static final String TAG = "Error Handler";
    protected static final String RETROFIT_TAG = "Retrofit Error";
    protected static final String EXCEPTION_TAG = "Exception";
    //OAUTH
    protected static final String ERROR = "Error";
    protected static final String ERROR_DESC = "Error Description";
    //API
    protected static final String CODE = "Code";
    protected static final String MESSAGE = "Message";
    protected static final String DESC = "Description";
    protected static final String ERRORS = "Errors";
    //OTHERS
    protected static final String STATUS = "Status";
    protected static final String ERROR_MSG = "Error Message";

    public Error onRequestFailure(SpiceException spiceException) {
        Log.e(TAG, spiceException.getMessage());

        Error error;
        if (spiceException.getCause() instanceof RetrofitError) {
            RetrofitError retrofitError = (RetrofitError) spiceException.getCause();
            Log.e(RETROFIT_TAG, retrofitError.toString());
            try {
                error = getOauthAndApiError(retrofitError);
            } catch (Exception exception) {
                Log.e(EXCEPTION_TAG, exception.toString());
                error = getRetrofitErrorMessage(retrofitError);
            }
        } else if (spiceException instanceof NoNetworkException) {
            error = new Error(ErrorType.RETRY, "No network connection!");
        } else if (spiceException instanceof NetworkException) {
            error = new Error(ErrorType.RETRY, "Display server unreachable!");
        } else {
            error = new Error(ErrorType.TOAST, spiceException.getMessage());
        }
        return error;
    }

    private Error getOauthAndApiError(RetrofitError retrofitError) {
        Error error;
        Errors errors = (Errors) retrofitError.getBodyAs(Errors.class);

        Log.e(ERROR, errors.getError());
        Log.e(ERROR_DESC, errors.getError_description());
        Log.e(CODE, errors.getCode());
        Log.e(MESSAGE, errors.getMessage());
        Log.e(DESC, errors.getDescription());
        Log.e(ERRORS, new Gson().toJson(errors.getErrors()));
        Log.e(STATUS, errors.getStatus());
        Log.e(ERROR_MSG, errors.getError_message());

        if (errors.getError_description().length() > 0) {
            if (errors.getError().equals("access_denied")) {
                error = new Error(ErrorType.LOGOUT, errors.getError_description());
            } else {
                error = new Error(ErrorType.TOAST, errors.getError_description());
            }
        } else if (errors.getErrors().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (APIErrors item : errors.getErrors()) {
                stringBuilder.append(stringBuilder.length() > 0 ? "\n" : "").append(item.getMessage());
            }
            error = new Error(ErrorType.TOAST, stringBuilder.toString());
        } else if (errors.getError_message().length() > 0) {
            if (errors.getStatus().equals("401")) {
                error = new Error(ErrorType.LOGOUT, errors.getError_message());
            } else if (errors.getStatus().equals("400")) {
                error = new Error(ErrorType.TOAST, errors.getError_message());
            }else {
                error = new Error(ErrorType.TOAST, errors.getError_message());
            }
        } else {
            error = getRetrofitErrorMessage(retrofitError);
        }
        return error;
    }

    private Error getRetrofitErrorMessage(RetrofitError retrofitError) {
        Error error;
        try {
            String[] message = retrofitError.getMessage().split(":");
            int messageLength = message.length;
            if (retrofitError.toString().contains("SocketTimeoutException") || retrofitError.toString().contains("ConnectionTimeoutException") || retrofitError.toString().contains("UnknownHostException")) {
                error = new Error(ErrorType.RETRY, "There seems to be a problem with your internet connection.");
            } else if (retrofitError.toString().contains("No address associated with hostname")) {
                error = new Error(ErrorType.RETRY, "No address associated with hostname");
            } else if (retrofitError.toString().contains("Path parameter \"id\" value must not be null")) {
                error = new Error(ErrorType.RETRY, "Path parameter \"id\" value must not be null");
            } else if (retrofitError.toString().contains("No authentication challenges found")) {
                error = new Error(ErrorType.LOGOUT, "No authentication challenges found");
            } else if (retrofitError.toString().contains("The email has already been taken.")) {
                error = new Error(ErrorType.TOAST, "The email has already been taken.");
            } else {
                error = new Error(ErrorType.TOAST, "Uncatched Error!\n" + message[--messageLength]);
            }
        } catch (Exception exception) {
            Log.e(EXCEPTION_TAG, exception.toString());
            error = new Error(ErrorType.TOAST, "Whooops, looks like something went wrong.");
        }
        return error;
    }

    public enum ErrorType {
        LOGOUT, RETRY, TOAST, DIALOG
    }

    public class Error {
        private ErrorType errorType;
        private String errorMessage;

        public Error(ErrorType errorType, String errorMessage) {
            setErrorType(errorType);
            setErrorMessage(errorMessage);
        }

        public ErrorType getErrorType() {
            return errorType;
        }

        public void setErrorType(ErrorType errorType) {
            this.errorType = errorType;
        }

        public String getErrorMessage() {
            return errorMessage != null ? errorMessage.trim() : "";
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage != null ? errorMessage.trim() : "";
        }
    }
}
