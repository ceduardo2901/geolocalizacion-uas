package com.uas.gsiam.ui;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.CrearSitioServicio;
import com.uas.gsiam.servicios.CrearUsuarioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CrearSitioActivity extends Activity{

	private static final String TAG = "AgregarSitioActivity";
	private EditText nombreSitioTxt;
	private EditText direccionSitioTxt;
	private EditText telefonoSitioTxt;
	private Button crearSitioBtn;
	private SitioDTO sitioDto;
	private Location loc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_sitio);
		
		nombreSitioTxt = (EditText) findViewById(R.id.txtNombreId);
		direccionSitioTxt = (EditText) findViewById(R.id.txtDireccionId);
		telefonoSitioTxt = (EditText) findViewById(R.id.txtTelefonoId);
		crearSitioBtn = (Button) findViewById(R.id.btnAgregarSitioId);
		sitioDto = new SitioDTO();
	}
	
	public void onResume(){
		super.onResume();
		Bundle bundle = getIntent().getExtras();
		
		loc = bundle.getParcelable("ubicacion");
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
	
	
}
