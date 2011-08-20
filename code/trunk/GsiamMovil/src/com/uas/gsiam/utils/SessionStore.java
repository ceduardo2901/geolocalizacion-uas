package com.uas.gsiam.utils;

import com.facebook.android.Facebook;
import com.uas.gsiam.negocio.dto.UsuarioDTO;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionStore {
    
    private static final String TOKEN = "access_token";
    private static final String EXPIRES = "expires_in";
    private static final String KEY = "gsiam-session";
    
    public static boolean save(UsuarioDTO usuario, Context context) {
        Editor editor =
            context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.putString(TOKEN, usuario.getEmail());
        editor.putString(EXPIRES, usuario.getPassword());
        return editor.commit();
    }

    public static boolean restore(UsuarioDTO usuario, Context context) {
        SharedPreferences savedSession =
            context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        usuario.setEmail(savedSession.getString(TOKEN, null));
        usuario.setPassword(savedSession.getString(EXPIRES, null));
        return true;
    }

    public static void clear(Context context) {
        Editor editor = 
            context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
    
}
