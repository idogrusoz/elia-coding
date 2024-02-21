package com.coding.elia.application.context;

public class UserEmailContext {
    private static final ThreadLocal<String> USER_EMAIL_CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setUserContext(String email) {
        USER_EMAIL_CONTEXT_HOLDER.set(email);
    }

    public static String getUserContext() {
        return USER_EMAIL_CONTEXT_HOLDER.get();
    }

    public static void clear() {
        USER_EMAIL_CONTEXT_HOLDER.remove();
    }

}
