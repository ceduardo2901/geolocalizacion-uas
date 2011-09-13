package com.uas.gsiam.utils;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionStore {
    
    private static final String TOKEN = "email";
    //private static final String KEY = "gsiam-session";
    
    public static boolean save(String email, Context context) {
        Editor editor =
            context.getSharedPreferences(Constantes.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, email);
        return editor.commit();
    }

    public static String restore(Context context) {
        SharedPreferences savedSession =
            context.getSharedPreferences(Constantes.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
        return (savedSession.getString(TOKEN, null));
        
    }

    public static void clear(Context context) {
        Editor editor = 
            context.getSharedPreferences(Constantes.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
    
}
