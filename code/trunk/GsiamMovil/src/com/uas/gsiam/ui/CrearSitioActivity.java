package com.uas.gsiam.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.CrearSitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class CrearSitioActivity extends Activity{

	private static final String TAG = "CrearSitioActivity";
	private EditText nombreSitioTxt;
	private EditText direccionSitioTxt;
	private EditText telefonoSitioTxt;
	private EditText categoriaSitioTxt;
	private Button crearSitioBtn;
	private SitioDTO sitioDto;
	private Location loc;
	private Integer index;
	protected IntentFilter crearSitioFiltro;
	
	//private ArrayList<CategoriaIconoMenu> listCategorias = new ArrayList<CategoriaIconoMenu>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agregar_sitio);
		
		nombreSitioTxt = (EditText) findViewById(R.id.txtNombreId);
		direccionSitioTxt = (EditText) findViewById(R.id.txtDireccionId);
		telefonoSitioTxt = (EditText) findViewById(R.id.txtTelefonoId);
		crearSitioBtn = (Button) findViewById(R.id.btnAgregarSitioId);
		categoriaSitioTxt = (EditText) findViewById(R.id.txtCategoriaId);
		sitioDto = new SitioDTO();
		crearSitioFiltro = new IntentFilter(Constantes.CREAR_SITIO_FILTRO_ACTION);
		Resources res = getResources();
		
		
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
		sitioDto.setTelefono(telefonoSitioTxt.getText().toString());
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setDescripcion(categoriaSitioTxt.getText().toString());
		categoria.setIdCategoria(index);
		sitioDto.setLat(loc.getLatitude());
		sitioDto.setLon(loc.getLongitude());
		sitioDto.setCategoria(categoria);
		Bundle bundle = new Bundle();
		bundle.putSerializable("sitio", sitioDto);
		
		Intent intent = new Intent(this,CrearSitioServicio.class);
		intent.putExtras(bundle);
		startService(intent);
		
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
	}
	
	
/*	public void mostarCategoria(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	//	final String[] categorias = getResources().getStringArray(R.array.listNames);
		builder.setItems(categorias, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialoginterface, int i) {
				index = i;
				categoriaSitioTxt.setText(categorias[i]);
				
			}
		});
		builder.show();
	}
	*/
	protected BroadcastReceiver receiverCrearSitio = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(TAG, "onReceive");
	    	Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");
		
			Util.dismissProgressDialog();
			
	    	if (respuesta != null){
	    		
	    		Util.showToast(context, respuesta);
//				Intent actividadPrincipal = new Intent(getApplicationContext(), SitiosActivity.class);
//				startActivity(actividadPrincipal);
				
			}
//			else{
//				Util.showToast(context, respuesta);
//			}
	    	
			
	    }
	  };
}
