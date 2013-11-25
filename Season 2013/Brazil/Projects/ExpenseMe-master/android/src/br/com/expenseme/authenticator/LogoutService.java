package br.com.expenseme.authenticator;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import br.com.expenseme.core.Constants;
import br.com.expenseme.util.Ln;
import br.com.expenseme.util.SafeAsyncTask;


/**
 * Class used for logging a user out.
 */
public class LogoutService {

    protected Context context;

    public LogoutService(Context context) {
        this.context = context;
    }

    public void logout(final Runnable onSuccess) {

        new LogoutTask(context, onSuccess).execute();
    }

    private static class LogoutTask extends SafeAsyncTask<Boolean> {

        private final Context context;
        private Runnable onSuccess;

        protected LogoutTask(Context context, Runnable onSuccess) {
            this.context = context;
            this.onSuccess = onSuccess;
        }

        @Override
        public Boolean call() throws Exception {

            final Account[] accounts = AccountManager.get(context).getAccountsByType(Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE);
            if (accounts.length > 0) {
                AccountManagerFuture<Boolean> removeAccountFuture = AccountManager.get(context).removeAccount
                        (accounts[0], null, null);
                return removeAccountFuture.getResult();
            }

            return false;
        }

        @Override
        protected void onSuccess(Boolean accountWasRemoved) throws Exception {
            super.onSuccess(accountWasRemoved);

            Ln.d("Logout succeeded: %s", accountWasRemoved);
            onSuccess.run();
        }

        @Override
        protected void onException(Exception e) throws RuntimeException {
            super.onException(e);
            Ln.e(e.getCause(), "Logout failed.");
        }
    }
}
