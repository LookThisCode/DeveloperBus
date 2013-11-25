package br.com.expenseme.authenticator;

import static android.accounts.AccountManager.KEY_AUTHTOKEN;

import java.io.IOException;

import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AccountsException;
import android.app.Activity;
import android.os.Bundle;
import br.com.expenseme.core.Constants;

/**
 * Bridge class that obtains a API key for the currently configured account
 */
public class ApiKeyProvider {

    AccountManager accountManager;

    /**
     * This call blocks, so shouldn't be called on the UI thread
     *
     * @return API key to be used for authorization with a {@link br.com.expenseme.core.ExpensemeService} instance
     * @throws AccountsException
     * @throws IOException
     */
    public String getAuthKey(Activity activity) throws AccountsException, IOException {
        accountManager = AccountManager.get(activity);
        AccountManagerFuture<Bundle> accountManagerFuture = accountManager.getAuthTokenByFeatures(Constants.Auth.BOOTSTRAP_ACCOUNT_TYPE,
            Constants.Auth.AUTHTOKEN_TYPE, new String[0], activity, null, null, null, null);

        return accountManagerFuture.getResult().getString(KEY_AUTHTOKEN);
    }
}
