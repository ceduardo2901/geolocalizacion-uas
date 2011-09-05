package com.uas.gsiam.ui;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.CrearSitioServicio;
import com.uas.gsiam.servicios.CrearUsuarioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CrearSitioActivity extends Activity{

	private static final String TAG = "CrearSitioActivity";
	private EditText nombreSitioTxt;
	private EditText direccionSitioTxt;
	private EditText telefonoSitioTxt;
	private Button crearSitioBtn;
	private SitioDTO sitioDto;
	private Location loc;
	protected IntentFilter crearSitioFiltro;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_sitio);
		
		nombreSitioTxt = (EditText) findViewById(R.id.txtNombreId);
		direccionSitioTxt = (EditText) findViewById(R.id.txtDireccionId);
		telefonoSitioTxt = (EditText) findViewById(R.id.txtTelefonoId);
		crearSitioBtn = (Button) findViewById(R.id.btnAgregarSitioId);
		sitioDto = new SitioDTO();
		crearSitioFiltro = new IntentFilter(Constantes.CREAR_SITIO_FILTRO_ACTION);
	}
	
	protected void onResume(){
		super.onResume();
		Bundle bundle = getIntent().getExtras();
		registerReceiver(receiverCrearSitio, crearSitioFiltro);
		loc = bundle.getParcelable("ubicacion");
	}
	
	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiverCrearSitio);
	}
	
	public void crearSitio(View v) {
		sitioDto.setDireccion(direccionSitioTxt.getText().toString());
		sitioDto.setNombre(nombreSitioTxt.getText().toString());
		sitioDto.setLat(loc.getLatitude());
		sitioDto.setLon(loc.getLongitude());
		
		Bundle bundle = new Bundle();
		bundle.putSerializable("sitio", sitioDto);
		
		Intent intent = new Intent(this,CrearSitioServicio.class);
		intent.putExtras(bundle);
		startService(intent);
		
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
	}
	
	
	protected BroadcastReceiver receiverCrearSitio = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(TAG, "onReceive");
	    	Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");
		
			Util.dismissProgressDialog();
			
	    	if (respuesta.equals(Constantes.RETURN_OK)){
	    		
	    		Util.showToast(context, Constantes.MSG_USUARIO_CREADO_OK);
				Intent actividadPrincipal = new Intent(getApplicationContext(), SitiosActivity.class);
				startActivity(actividadPrincipal);
				
			}
			else{
				Util.showToast(context, respuesta);
			}
	    	
			
	    }
	  };
}
