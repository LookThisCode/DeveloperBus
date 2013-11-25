package br.com.expenseme;

import android.content.Context;
import br.com.expenseme.authenticator.LogoutService;

import com.squareup.otto.Bus;

/**
 * Dagger module for setting up provides statements. Register all of your entry
 * points below.
 */

public class ExpensemeModule {

    Bus provideOttoBus() {
        return new Bus();
    }

    LogoutService provideLogoutService(final Context context) {
        return new LogoutService(context);
    }

}
