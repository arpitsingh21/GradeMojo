package iband.grademojo;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell on 22-11-2017.
 */

public class PrefManager {


    private static SharedPreferences pref;
    private static SharedPreferences.Editor editor;
    private static final String AUTHORIZATION_TOKEN = "csrfToken";
    private static Context _context;
    private static final String PREF_NAME = "joynin-app";
    static int PRIVATE_MODE = 0;

    public static String getPath() {
        String tmp = pref.getString("getPath", "");
        if (tmp == null)
            return null;
        else
            return tmp;
    }

    public static void setPath(String path) {
        editor.putString("getPath", path);
        editor.commit();
    }


    public PrefManager() {


    }





    public static String getAuthToken() {
        // todo it'll be default null during launch
        String tmp = pref.getString(AUTHORIZATION_TOKEN, "f79e3fa553f614133ce9ab810ebb2e327d418234");
        if (tmp == null)
            return null;
        else
            return tmp;
    }

    public static void setAuthToken(String CSRFToken) {
        editor.putString(AUTHORIZATION_TOKEN, CSRFToken);
        editor.commit();
    }
    public static void initialise(Context context) {
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public static void clear() {
        editor.clear();
        editor.commit();
    }
}
