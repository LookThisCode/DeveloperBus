
package br.com.expenseme.authenticator;

import static android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Authenticator service that returns a subclass of AbstractAccountAuthenticator in onBind()
 */
public class AccountAuthenticatorService extends Service {

    private static ExpensemeAccountAuthenticator AUTHENTICATOR = null;

    @Override
    public IBinder onBind(Intent intent) {
        return intent.getAction().equals(ACTION_AUTHENTICATOR_INTENT) ? getAuthenticator().getIBinder() : null;
    }

    private ExpensemeAccountAuthenticator getAuthenticator() {
        if (AUTHENTICATOR == null)
            AUTHENTICATOR = new ExpensemeAccountAuthenticator(this);
        return AUTHENTICATOR;
    }
}