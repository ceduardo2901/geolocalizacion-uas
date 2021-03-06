package com.uas.gsiam.ui;

import greendroid.app.GDTabActivity;
import greendroid.widget.LoaderActionBarItem;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.uas.gsiam.adapter.AmigoAdapter;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.GetAmigosServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Esta activity muestra la lista de amigos dentro de la aplicacion. Se
 * mmostrara la foto de perfil, el nombre y el email del amigo.
 * 
 * @author Martin
 * 
 */
public class MisAmigosActivity extends ListActivity implements
		OnItemClickListener {

	protected IntentFilter misAmigosFiltro;
	protected ProgressDialog progressDialog;
	protected ListView lv;
	public static ArrayList<UsuarioDTO> misAmigos;

	protected static String TAG = "MisAmigosActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mis_amigos_tab);
		if (misAmigos != null)
			misAmigos.clear();
		lv = getListView();
		lv.setOnItemClickListener(this);
		
		misAmigosFiltro = new IntentFilter(Constantes.GET_AMIGOS_FILTRO_ACTION);
		this.registerReceiver(receiverGetAmigos, misAmigosFiltro);
		
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_BUSCANDO);
		Intent intent = new Intent(this, GetAmigosServicio.class);
		startService(intent);
		
	}

	protected void onResume() {
		super.onResume();
		this.registerReceiver(receiverGetAmigos, misAmigosFiltro);
		// esto es por si tengo que actualizar la lista 
		if (misAmigos != null)
			mostrarAmigos();
	}

	protected void onPause() {
		super.onPause();
		this.unregisterReceiver(receiverGetAmigos);
		Util.dismissProgressDialog();
	}

	/**
	 * Recibe la respuesta del servicio que obtiene los amigos del usuario
	 */
	protected BroadcastReceiver receiverGetAmigos = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			@SuppressWarnings("unchecked")
			ArrayList<UsuarioDTO> respuesta = (ArrayList<UsuarioDTO>) intent
					.getSerializableExtra("respuesta");
			String error = intent.getStringExtra("error");
			Util.dismissProgressDialog();

			if (error != null && !error.isEmpty()) {

				Util.showToast(context, error);

			} else {

				misAmigos = respuesta;
			
				mostrarAmigos();

				Util.dismissProgressDialog();

				GDTabActivity padre = (GDTabActivity) getParent();
				LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) padre
						.getActionBar().getItem(AmigosTabActivity.ACTUALIZAR);
				loaderActionBarItem.setLoading(false);
			}

		}
	};

	public void mostrarAmigos() {

		AmigoAdapter adaptador = new AmigoAdapter(this, R.layout.usuario_item,
				misAmigos);
		lv.setAdapter(adaptador);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {

		UsuarioDTO usuario = misAmigos.get(position);

		Log.i(TAG, "Seleccione: " + usuario.getNombre());

	}

}