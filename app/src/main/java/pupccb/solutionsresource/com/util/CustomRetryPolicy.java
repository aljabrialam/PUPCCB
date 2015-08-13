package pupccb.solutionsresource.com.util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.retry.RetryPolicy;

import retrofit.RetrofitError;

/**
 * Created by Richard on 28/04/2015.
 */
public class CustomRetryPolicy implements RetryPolicy {

    /** The default number of retry attempts. */
    public static final int DEFAULT_RETRY_COUNT = 3;

    /** The default delay before retry a request (in ms). */
    public static final long DEFAULT_DELAY_BEFORE_RETRY = 2500;

    /** The default backoff multiplier. */
    public static final float DEFAULT_BACKOFF_MULT = 1f;

    /** The number of retry attempts. */
    private int retryCount = DEFAULT_RETRY_COUNT;

    /**
     * The delay to wait before next retry attempt. Will be multiplied by
     * {@link #backOffMultiplier} between every retry attempt.
     */
    private long delayBeforeRetry = DEFAULT_DELAY_BEFORE_RETRY;

    /**
     * The backoff multiplier. Will be multiplied by {@link #delayBeforeRetry}
     * between every retry attempt.
     */
    private float backOffMultiplier = DEFAULT_BACKOFF_MULT;

    public CustomRetryPolicy(int retryCount, long delayBeforeRetry, float backOffMultiplier) {
        this.retryCount = retryCount;
        this.delayBeforeRetry = delayBeforeRetry;
        this.backOffMultiplier = backOffMultiplier;
    }

    public CustomRetryPolicy() {
        this(DEFAULT_RETRY_COUNT, DEFAULT_DELAY_BEFORE_RETRY, DEFAULT_BACKOFF_MULT);
    }

    @Override
    public int getRetryCount() {
        return retryCount;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void retry(SpiceException exception) {
        if (exception.getCause() instanceof RetrofitError) {
            Log.e("Retry Policy Error - Retrofit", exception.getMessage());
            retryCount = 0;
        } else {
            retryCount--;
        }
        delayBeforeRetry = (long) (delayBeforeRetry * backOffMultiplier);
    }

    @Override
    public long getDelayBeforeRetry() {
        return delayBeforeRetry;
    }
}
