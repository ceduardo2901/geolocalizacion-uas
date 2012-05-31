package com.uas.gsiam.ui;

import greendroid.app.GDMapActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.uas.gsiam.adapter.CategoriaAdapter;
import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.ModificarSitioServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Esta activity despliega la interfaz grafica para modificar un sitio de
 * interes.
 * 
 * @author Antonio
 * 
 */
public class ModificarSitioActivity extends GDMapActivity implements
		TextWatcher {

	protected static String TAG = "ModificarSitioActivity";
	protected EditText nombre;
	protected EditText direccion;
	protected EditText telefono;
	protected EditText web;
	protected SitioDTO sitio;
	private MapView mapa;
	protected IntentFilter filtroModificarSitio;
	private MapController mapControl;
	private CategoriaDTO categoriaSeleccionada;
	private Location loc;
	private EditText categoriaSitioTxt;
	private AlertDialog dialog = null;
	private GeoPoint geoPoint;
	private Button btnModificar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.modificar_sitio);
		sitio = (SitioDTO) getIntent().getSerializableExtra("sitio");

		nombre = (EditText) findViewById(R.id.txtNombreId);
		direccion = (EditText) findViewById(R.id.txtDireccionId);
		telefono = (EditText) findViewById(R.id.txtTelefonoId);
		web = (EditText) findViewById(R.id.txtWebId);
		categoriaSitioTxt = (EditText) findViewById(R.id.txtCategoriaId);
		mapa = (MapView) findViewById(R.id.crearSitioMapaId);
		btnModificar = (Button) findViewById(R.id.modificarBtn);
		mapControl = mapa.getController();
		mapControl.setZoom(15);
		mapa.setBuiltInZoomControls(true);
		mapa.setClickable(true);

		geoPoint = new GeoPoint((int) (sitio.getLat() * 1000000),
				(int) (sitio.getLon() * 1000000));

		mapControl.animateTo(geoPoint);
		mapControl.setCenter(geoPoint);

		Drawable marca = getResources().getDrawable(R.drawable.gd_map_pin_pin);
		marca.setBounds(0, 0, marca.getIntrinsicWidth(),
				marca.getIntrinsicHeight());
		PosicionOverlay miPosicionOverlay = new PosicionOverlay(marca);
		miPosicionOverlay.addOverlay(new OverlayItem(geoPoint, null, null));

		mapa.getOverlays().add(miPosicionOverlay);
		nombre.addTextChangedListener(this);
		direccion.addTextChangedListener(this);
		categoriaSitioTxt.addTextChangedListener(this);
		inicializarActionBar();
		filtroModificarSitio = new IntentFilter(
				Constantes.MODIFICAR_SITIO_FILTRO_ACTION);
	}

	private void inicializarActionBar() {

		getActionBar().setTitle(getString(R.string.gsiam_modificar));
	}

	public void onResume() {
		super.onResume();

		nombre.setText(sitio.getNombre());
		direccion.setText(sitio.getDireccion());
		telefono.setText(sitio.getTelefono());
		web.setText(sitio.getWeb());
		categoriaSitioTxt.setText(sitio.getCategoria().getDescripcion());
		registerReceiver(modificarSitioReceiver, filtroModificarSitio);
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(modificarSitioReceiver);
	}

	/**
	 * Accion para invocar el servicio externo que modifica el sitio
	 * 
	 * @param v
	 */
	public void modificar(View v) {

		ConfirmarModificacion();
	}

	/**
	 * Modifica el sitio correspondiente
	 */
	public void modificarSitio() {

		if (nombre.getText().length() == 0 || direccion.getText().length() == 0) {
			Util.showToast(this, Constantes.MSG_CAMPOS_OBLIGATORIOS);
		} else {
			Intent intentModificarSitio = new Intent(this,
					ModificarSitioServicio.class);
			sitio.setNombre(nombre.getText().toString());
			sitio.setDireccion(direccion.getText().toString());
			sitio.setTelefono(telefono.getText().toString());
			sitio.setWeb(web.getText().toString());

			sitio.setCategoria(categoriaSeleccionada);
			intentModificarSitio.putExtra("sitio", sitio);
			startService(intentModificarSitio);
			Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
		}
	}

	private void mostarSitios() {
		Intent intentMostarSitios = new Intent(this, SitiosActivity.class);
		startActivity(intentMostarSitios);
	}

	/**
	 * Recibe la respuesta del servicio que modifica el sitio de interes
	 */
	protected BroadcastReceiver modificarSitioReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");
			Util.dismissProgressDialog();
			String error = intent.getStringExtra("error");
			String respuesta = intent.getStringExtra("respuesta");
			if (error != null) {
				Util.showToast(context, error);
			} else {
				if (respuesta != null) {
					Util.showToast(context, respuesta);
					mostarSitios();
				}

			}

		}
	};

	public void ConfirmarModificacion() {

		AlertDialog.Builder dialogResponder = new AlertDialog.Builder(this);
		dialogResponder.setTitle("Confirmar");
		dialogResponder.setMessage(Constantes.MSG_CONFIRMAR_MODIFICAR_SITIO);
		dialogResponder.setCancelable(true);
		dialogResponder.setIcon(android.R.drawable.ic_dialog_alert);

		dialogResponder.setPositiveButton("Si",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

						modificarSitio();
						dialog.cancel();
					}
				});

		dialogResponder.setNegativeButton("No",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {

						dialog.cancel();
					}
				});

		dialogResponder.show();

	}

	public void mostarCategoria(View v) {

		ExpandableListView myList = new ExpandableListView(this);
		myList.setDividerHeight(2);
		ApplicationController app = ((ApplicationController) getApplicationContext());
		ArrayList<HashMap<String, Object>> gruposCategorias = app
				.getGruposCategorias();
		ArrayList<ArrayList<HashMap<String, Object>>> subCategorias = app
				.getSubCategorias();

		CategoriaAdapter adaptador = new CategoriaAdapter(this,
				gruposCategorias,
				android.R.layout.simple_expandable_list_item_1,
				new String[] {}, new int[] { android.R.id.text1 },
				subCategorias, 0, null, new int[] {});

		myList.setAdapter(adaptador);

		myList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) parent
						.getExpandableListAdapter().getChild(groupPosition,
								childPosition);

				categoriaSeleccionada = (CategoriaDTO) map.get("CategoriaDTO");
				categoriaSitioTxt.setText(categoriaSeleccionada
						.getDescripcion());

				if (dialog != null) {
					dialog.dismiss();

				}

				return true;
			}
		});
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Selecione Categoria");
		builder.setView(myList);

		dialog = builder.create();
		dialog.show();

	}

	private class PosicionOverlay extends ItemizedOverlay<OverlayItem> {

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		Drawable marker;
		Boolean MoveMap;

		public PosicionOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			marker = defaultMarker;
			populate();

		}

		public void addOverlay(OverlayItem overlay) {
			mOverlays.add(overlay);
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return mOverlays.get(i);
		}

		@Override
		public int size() {
			return mOverlays.size();
		}

		@Override
		protected boolean onTap(int index) {
			Log.d(TAG, "prueba click");
			return true;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);

			boundCenterBottom(marker);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event, MapView mapView) {
			Integer action = event.getAction();

			if (action == MotionEvent.ACTION_UP) {

				if (!MoveMap) {
					Projection proj = mapView.getProjection();
					GeoPoint punto = proj.fromPixels((int) event.getX(),
							(int) event.getY());

					mapa.getOverlays().remove(0);

					sitio.setLat((double)punto.getLatitudeE6() / 1000000F);
					sitio.setLon((double)punto.getLongitudeE6() / 1000000F);
					mapControl.setCenter(punto);
					PosicionOverlay miPosicionOverlay = new PosicionOverlay(
							getResources().getDrawable(
									R.drawable.gd_map_pin_pin));
					miPosicionOverlay.addOverlay(new OverlayItem(punto, null,
							null));
					mapa.getOverlays().add(miPosicionOverlay);
					populate();

				}
			} else if (action == MotionEvent.ACTION_DOWN) {

				MoveMap = false;

			} else if (action == MotionEvent.ACTION_MOVE) {
				MoveMap = true;
			}

			return super.onTouchEvent(event, mapView);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (!nombre.getText().toString().equals("")
				&& !direccion.getText().toString().equals("")
				&& !categoriaSitioTxt.getText().toString().equals("")
				&& s.length() != 0) {
			btnModificar.setEnabled(true);
		} else {
			btnModificar.setEnabled(false);
		}

	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

}
