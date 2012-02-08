package com.uas.gsiam.ui;

import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.CrearUsuarioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Esta activity registra un nuevo usuario en el sistema. Lo datos necesarios
 * son el nobre, email y password. Luego desde el perfil de usuario se podra
 * agregar una foto al perfil
 * 
 * @author Martín
 * 
 */
public class CrearUsuarioActivity extends Activity implements TextWatcher{

	protected String email;
	protected String pass;
	protected String nombre;
	protected Byte[] avatar;

	protected EditText nombreTxt;
	protected EditText emailTxt;
	protected EditText passTxt;
	protected Button registrarseBtn;
	protected IntentFilter crearUsuarioFiltro;
	protected ProgressDialog progressDialog;

	protected static String TAG = "CrearUsuarioActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.crear_usuario);

		inicializarActionBar();

		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		passTxt = (EditText) findViewById(R.id.passTxt);
		registrarseBtn = (Button) findViewById(R.id.crearUsuarioBtn);
		crearUsuarioFiltro = new IntentFilter(
				Constantes.CREAR_USUARIO_FILTRO_ACTION);
		nombreTxt.addTextChangedListener(this);
		emailTxt.addTextChangedListener(this);
		passTxt.addTextChangedListener(this);
		registrarseBtn.setEnabled(false);
	}

	protected void onResume() {
		super.onResume();
		registerReceiver(receiverCrearUsuario, crearUsuarioFiltro);
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverCrearUsuario);
	}

	/**
	 * Accion que llama al servicio para crear el usuario en el sistema
	 * 
	 * @param v
	 */
	public void crearUsuario(View v) {

		nombre = nombreTxt.getText().toString().trim();
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();

		if (!Util.validaMail(email)) {
			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);

		} else {

			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setNombre(nombre);
			usuario.setEmail(email);
			usuario.setPassword(pass);

			Drawable drawable = this.getResources().getDrawable(
					R.drawable.avatardefault);

			byte[] bitmapdata = null;
			try {
				bitmapdata = Util.BitmapToArray((BitmapDrawable) drawable);
			} catch (IOException e) {
				e.printStackTrace();
			}

			usuario.setAvatar(bitmapdata);

			Intent intent = new Intent(this, CrearUsuarioServicio.class);
			intent.putExtra("usuario", usuario);
			startService(intent);

			Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

		}

	}

	/**
	 * Recibe la respuesta del servicio despues de crear el usuario en el
	 * sistema
	 */
	protected BroadcastReceiver receiverCrearUsuario = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			String respuesta = intent.getStringExtra("respuesta");
			String error = intent.getStringExtra("error");
			Util.dismissProgressDialog();

			if (error != null && !error.isEmpty()) {

				Util.showToast(context, error);

			} else {
				Util.showToast(context, respuesta);
				Intent actividadLogin = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(actividadLogin);
			}

		}
	};

	private void inicializarActionBar() {
		setTitle("GSIAM - Registrarse");
	}

	@Override
	public void afterTextChanged(Editable s) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
				
		
		if (!nombreTxt.getText().toString().equals("")
				&& !emailTxt.getText().toString().equals("")
				&& !passTxt.getText().toString().equals("")
				&& s.length() != 0) {
			registrarseBtn.setEnabled(true);
		} else {
			registrarseBtn.setEnabled(false);
		}
		
	}

}
