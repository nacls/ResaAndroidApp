package ir.ceit.resa.service.storage;

import android.content.Context;
import android.content.SharedPreferences;

import ir.ceit.resa.model.ERole;

public class ResaSharedPreferences {

    private static final String PREF_KEY = "ResaPreferences";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_TOKEN = "authorization-key";
    private static final String PREF_ROLE = "user-main-role";
    private static final String PREF_USER_FIRST_NAME = "user-first-name";
    private static final String PREF_USER_LAST_NAME = "user-last-name";
    private static final String PREF_USER_EMAIL = "user-email";
    private static final String PREF_USER_FACULTY = "user-faculty";


    static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
    }

    private static void setValueToKey(Context context, String value, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setUserName(Context context, String username) {
        setValueToKey(context, username, PREF_USERNAME);
    }

    public static void setToken(Context context, String token) {
        setValueToKey(context, token, PREF_TOKEN);
    }

    public static void setRole(Context context, ERole role) {
        setValueToKey(context, String.valueOf(role), PREF_ROLE);
    }

    public static void setFirstName(Context context, String firstname) {
        setValueToKey(context, firstname, PREF_USER_FIRST_NAME);
    }

    public static void setLastName(Context context, String lastname) {
        setValueToKey(context, lastname, PREF_USER_LAST_NAME);
    }

    public static void setEmail(Context context, String email) {
        setValueToKey(context, email, PREF_USER_EMAIL);
    }

    public static void setFaculty(Context context, String faculty) {
        setValueToKey(context, faculty, PREF_USER_FACULTY);
    }

    public static String getUserName(Context context) {
        return getSharedPreferences(context).getString(PREF_USERNAME, null);
    }

    public static String getToken(Context context) {
        return getSharedPreferences(context).getString(PREF_TOKEN, null);
    }

    public static ERole getRole(Context context) {
        return ERole.valueOf(getSharedPreferences(context).getString(PREF_ROLE, null));
    }

    public static String getUserFirstName(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_FIRST_NAME, null);
    }

    public static String getUserLastName(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_LAST_NAME, null);
    }

    public static String getUserEmail(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_EMAIL, null);
    }

    public static String getUserFaculty(Context context) {
        return getSharedPreferences(context).getString(PREF_USER_FACULTY, null);
    }

    public static void clearPreferences(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}
