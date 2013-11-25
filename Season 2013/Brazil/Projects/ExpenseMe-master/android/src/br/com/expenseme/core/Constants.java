package br.com.expenseme.core;

/**
 * Bootstrap constants
 */
public class Constants {

    public static class Auth {
        private Auth() {}

        /**
         * Account type id
         */
        public static final String BOOTSTRAP_ACCOUNT_TYPE = "br.com.expenseme";

        /**
         * Account name
         */
        public static final String BOOTSTRAP_ACCOUNT_NAME = "ExpenseMe";

        /**
         * Provider id
         */
        public static final String BOOTSTRAP_PROVIDER_AUTHORITY = "br.com.expenseme.sync";

        /**
         * Auth token type
         */
        public static final String AUTHTOKEN_TYPE = BOOTSTRAP_ACCOUNT_TYPE;
    }

    /**
     * All HTTP is done through a REST style API built for demonstration purposes on Parse.com
     * Thanks to the nice people at Parse for creating such a nice system for us to use for bootstrap!
     */
    public static class Http {
        private Http() {}

        /**
         * Base URL for all requests
         */
        public static final String URL_BASE = "https://expense-me.appspot.com/_ah/api";

        /**
         * Authentication URL
         */
        public static final String URL_AUTH = URL_BASE + "/user/v1/user/auth";

        /**
         * List Users URL
         */
        public static final String URL_USERS = URL_BASE + "/1/users";

        /**
         * Expense URL
         */
        public static final String URL_EXPENSES = URL_BASE + "/despesa/v1/despesa";

        public static final String CONTENT_TYPE_JSON = "application/json";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String SESSION_TOKEN = "sessionToken";
    }

    public static class Extra {
        private Extra() {}

        public static final String NEWS_ITEM = "news_item";

        public static final String USER = "user";
    }

    public static class Intent {
        private Intent() {}

        /**
         * Action prefix for all intents created
         */
        public static final String INTENT_PREFIX = "br.com.expenseme.";
    }

    public static class Notification {
        private Notification() {}

        public static final int TIMER_NOTIFICATION_ID = 1000; // Why 1000? Why not? :)
    }
}
