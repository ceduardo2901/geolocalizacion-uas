package com.uas.gsiam.utils;

import com.facebook.android.Facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Esta clase es la encargada de guardar la session de facebook en el contexto
 * de la aplicación
 * 
 * @author Antonio
 * 
 */
public class SessionStore {

	private static final String TOKEN = "access_token";
	private static final String EXPIRES = "expires_in";

	/**
	 * 
	 * Guarda una session de facebook en el contexto de la aplicación
	 * 
	 * @param session
	 *            Session de facebook a guardar
	 * @param context
	 *            Contexto de la aplicación
	 * @param KEY
	 *            Clave de facebook
	 * @return Retorna true si se guardo la session correctamente, false en caso
	 *         contrario
	 */
	public static boolean save(Facebook session, Context context,
			final String KEY) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
				.edit();
		editor.putString(TOKEN, session.getAccessToken());
		editor.putLong(EXPIRES, session.getAccessExpires());
		return editor.commit();
	}

	/**
	 * 
	 * Recupera una session de facebook guardada en el contexto con una clave
	 * dada
	 * 
	 * @param session
	 *            Session de facebook
	 * @param context
	 *            Contexto de la aplicación
	 * @param KEY
	 *            Clave de facebook
	 * @return Retorna true si encontro la session, false en caso contrario
	 */
	public static boolean restore(Facebook session, Context context,
			final String KEY) {
		SharedPreferences savedSession = context.getSharedPreferences(KEY,
				Context.MODE_PRIVATE);
		session.setAccessToken(savedSession.getString(TOKEN, null));
		session.setAccessExpires(savedSession.getLong(EXPIRES, 0));
		return session.isSessionValid();
	}

	/**
	 * Borra la session de facebook guardada en el contexto con la clave
	 * ingresada
	 * 
	 * @param context
	 *            Contexto de la aplicación
	 * @param KEY
	 *            Clave de facebook
	 */
	public static void clear(Context context, final String KEY) {
		Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
				.edit();
		editor.clear();
		editor.commit();
	}

}
