package br.com.expenseme;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.FROYO;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import br.com.expenseme.core.User;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * EasyExpense application
 */
public class ExpensemeApplication extends Application {

    private static ExpensemeApplication instance;

    /**
     * Create main application
     */
    public ExpensemeApplication() {

        // Disable http.keepAlive on Froyo and below
        if (SDK_INT <= FROYO)
            HttpRequest.keepAlive(false);
    }

    /**
     * Create main application
     *
     * @param context
     */
    public ExpensemeApplication(final Context context) {
        this();
        attachBaseContext(context);

    }

    public User getCurrentUser() {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    /**
     * Create main application
     *
     * @param instrumentation
     */
    public ExpensemeApplication(final Instrumentation instrumentation) {
        this();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static ExpensemeApplication getInstance() {
        return instance;
    }
}
