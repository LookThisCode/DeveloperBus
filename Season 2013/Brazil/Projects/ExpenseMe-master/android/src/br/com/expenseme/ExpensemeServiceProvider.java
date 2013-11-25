package br.com.expenseme;

import java.io.IOException;

import android.accounts.AccountsException;
import android.app.Activity;
import br.com.expenseme.authenticator.ApiKeyProvider;
import br.com.expenseme.core.ExpensemeService;
import br.com.expenseme.core.UserAgentProvider;

/**
 * Provider for a {@link br.com.expenseme.core.ExpensemeService} instance
 */
public class ExpensemeServiceProvider {

    ApiKeyProvider keyProvider;
    UserAgentProvider userAgentProvider;

    public ExpensemeServiceProvider() {
        keyProvider = new ApiKeyProvider();
        userAgentProvider = new UserAgentProvider();
    }

    /**
     * Get service for configured key provider
     * <p>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    public ExpensemeService getService(Activity activity) throws IOException, AccountsException {
        return new ExpensemeService(keyProvider.getAuthKey(activity), userAgentProvider);
    }
}
