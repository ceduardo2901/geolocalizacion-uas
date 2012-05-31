package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.R;
import com.facebook.android.Facebook.DialogListener;
import com.uas.gsiam.servicios.EnviarInvitacionServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SessionStore;
import com.uas.gsiam.utils.Util;

/**
 * Esta activity permite enviar una solicitud de invitacion a la red por medio
 * del email.
 * 
 * @author Martin
 * 
 */
public class InvitarAmigosActivity extends Activity implements TextWatcher{

	protected static String TAG = "InvitarAmigosActivity";

	protected IntentFilter enviarInvitacionesFiltro;
	protected EditText emailTxt;
	protected String email;
	protected String APP_ID;
	protected static Facebook facebook;
	protected Button invitarBtn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.invitar_amigos_tab);
		this.emailTxt = (EditText) findViewById(R.id.emailTxt);
		this.invitarBtn = (Button) findViewById(R.id.invitarBtn);
		emailTxt.addTextChangedListener(this);
		invitarBtn.setEnabled(false);
		enviarInvitacionesFiltro = new IntentFilter(Constantes.ENVIAR_INVITACIONES_FILTRO_ACTION);
	}

	protected void onResume() {
		super.onResume();
		registerReceiver(receiverEnviarInvitaciones, enviarInvitacionesFiltro);
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverEnviarInvitaciones);
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!emailTxt.getText().toString().equals("")) {
			invitarBtn.setEnabled(true);
		} else {
			invitarBtn.setEnabled(false);
		}
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}
	
	/**
	 * Accion que llama al servicio que envia una solicitud de invitacion por
	 * email
	 * 
	 * @param v
	 */
	public void enviarInvitacion(View v) {

		email = emailTxt.getText().toString().trim();

		if (!Util.validaMail(email)) {

			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);

		} else {

			Intent intent = new Intent(this, EnviarInvitacionServicio.class);
			intent.putExtra("direccion", email);
			startService(intent);

			Util.showProgressDialog(this,
					Constantes.MSG_ESPERA_ENVIANDO_INVITACION);
		}
	}

	/**
	 * Permite invitar amigos por la red facebook
	 * 
	 * @param v
	 */
	public void enviarInvitacionFacebook(View v) {

		APP_ID = getString(R.string.facebook_app_id);
		facebook = new Facebook(APP_ID);

		SessionStore.restore(facebook, getApplicationContext(), APP_ID);

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					getResources().getStringArray(R.array.permissions),
					new FaceBookDialog());
		} else {
			obtenerAmigos();
		}

	}

	public void obtenerAmigos() {
		Intent intent = new Intent(this, ListaAmigosFacebook.class);
		startActivity(intent);
	}

	/**
	 * Recibe la respuesta a la invitacion via email
	 */
	protected BroadcastReceiver receiverEnviarInvitaciones = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			String error = intent.getStringExtra("error");

			Util.dismissProgressDialog();

			if (error != null && error.length() > 0) {

				Util.showToast(context, error);

			} else {

				Util.showToast(context, Constantes.MSG_INVITACIONES_OK);

			}
		}
	};

	public class FaceBookDialog implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
			SessionStore.save(facebook, getApplicationContext(), APP_ID);
			obtenerAmigos();
		}

		@Override
		public void onFacebookError(FacebookError e) {
			Log.e(TAG, "Error al autenticase en facebook");
		}

		@Override
		public void onError(DialogError e) {
			Log.e(TAG, "Error al autenticase en facebook");
		}

		@Override
		public void onCancel() {
		}

	}

}