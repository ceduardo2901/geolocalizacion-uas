package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;

import java.io.IOException;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.CrearUsuarioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class CrearUsuarioActivity extends GDActivity {

	protected String email;
	protected String pass;
	protected String nombre;
	protected Byte [] avatar;

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
		
		setActionBarContentView(R.layout.crear_usuario);
		
		inicializarActionBar();
		
		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		passTxt = (EditText) findViewById(R.id.passTxt);
		registrarseBtn = (Button) findViewById(R.id.crearUsuarioBtn);  
		crearUsuarioFiltro = new IntentFilter(Constantes.CREAR_USUARIO_FILTRO_ACTION);
	}
	
	
	
	protected void onResume() {
		 super.onResume();
		 registerReceiver(receiverCrearUsuario, crearUsuarioFiltro);
	 }
	
	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiverCrearUsuario);
	}
	 
	public void crearUsuario(View v) {


		nombre = nombreTxt.getText().toString().trim();
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();
	
		if (!Util.validaMail(email)) {
			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);
					
		} 
		else {
			
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setNombre(nombre);
			usuario.setEmail(email);
			usuario.setPassword(pass);
			
			Drawable drawable= this.getResources().getDrawable(R.drawable.avatardefault);
		
			byte[] bitmapdata = null;
			try {
				bitmapdata = Util.BitmapToArray((BitmapDrawable) drawable);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
			usuario.setAvatar(bitmapdata);
			
			Bundle bundle = new Bundle();
			bundle.putSerializable("usuario", usuario);
			
			Intent intent = new Intent(this,CrearUsuarioServicio.class);
			intent.putExtras(bundle);
			startService(intent);
			
			Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

		}

	}

	
	
	
	protected BroadcastReceiver receiverCrearUsuario = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(TAG, "onReceive");
	    	Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");
		
			Util.dismissProgressDialog();
			
	    	if (respuesta.equals(Constantes.RETURN_OK)){
	    		
	    		Util.showToast(context, Constantes.MSG_USUARIO_CREADO_OK);
				Intent actividadPrincipal = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(actividadPrincipal);
				
			}
			else{
				Util.showToast(context, respuesta);
			}
	    	
			
	    }
	  };
	  
	  
	  private void inicializarActionBar() {
			addActionBarItem(Type.Trashcan, 0);
	/*		addActionBarItem(Type.Search, BUSCAR);
			addActionBarItem(Type.Refresh, ACTUALIZAR);
*/
			setTitle("GSIAM - Registrarse");
		}
	  
	  

}
