package com.uas.gsiam.ui;

import greendroid.app.GDMapActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;
import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.CrearSitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class CrearSitioActivity extends GDMapActivity implements TextWatcher {

	private static final String TAG = "CrearSitioActivity";
	private EditText nombreSitioTxt;
	private EditText direccionSitioTxt;
	private EditText telefonoSitioTxt;

	private EditText webTxt;
	private EditText categoriaSitioTxt;
	private Button crearSitioBtn;
	private SitioDTO sitioDto;
	private Location loc;
	private Integer index;
	protected IntentFilter crearSitioFiltro;
	private MapView mapa;
	private GeoPoint geoPoint;
	private MapController mapControl;

	// private ArrayList<CategoriaIconoMenu> listCategorias = new
	// ArrayList<CategoriaIconoMenu>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.agregar_sitio);
		Bundle bundle = getIntent().getExtras();
		loc = bundle.getParcelable("ubicacion");
		nombreSitioTxt = (EditText) findViewById(R.id.txtNombreId);
		direccionSitioTxt = (EditText) findViewById(R.id.txtDireccionId);
		telefonoSitioTxt = (EditText) findViewById(R.id.txtTelefonoId);
		crearSitioBtn = (Button) findViewById(R.id.btnAgregarSitioId);
		categoriaSitioTxt = (EditText) findViewById(R.id.txtCategoriaId);
		webTxt = (EditText) findViewById(R.id.txtWebId);

		mapa = (MapView) findViewById(R.id.crearSitioMapaId);
		mapControl = mapa.getController();
		mapControl.setZoom(15);
		mapa.setBuiltInZoomControls(true);
		geoPoint = new GeoPoint((int) (loc.getLatitude() * 1000000),
				(int) (loc.getLongitude() * 1000000));

		mapControl.animateTo(geoPoint);
		mapControl.setCenter(geoPoint);

		Drawable marca = getResources().getDrawable(R.drawable.gd_map_pin_pin);
		marca.setBounds(0, 0, marca.getIntrinsicWidth(),
				marca.getIntrinsicHeight());
		PosicionOverlay miPosicionOverlay = new PosicionOverlay(marca);
		miPosicionOverlay.addOverlay(new OverlayItem(geoPoint, null, null));

		mapa.getOverlays().add(miPosicionOverlay);

		sitioDto = new SitioDTO();
		crearSitioFiltro = new IntentFilter(
				Constantes.CREAR_SITIO_FILTRO_ACTION);
		nombreSitioTxt.addTextChangedListener(this);
		direccionSitioTxt.addTextChangedListener(this);
		categoriaSitioTxt.addTextChangedListener(this);
		crearSitioBtn.setEnabled(false);
		inicializarBarra();
	}

	private void inicializarBarra() {

		setTitle(R.string.app_name);
	}

	protected void onResume() {
		super.onResume();

		registerReceiver(receiverCrearSitio, crearSitioFiltro);

	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverCrearSitio);
	}

	public void crearSitio(View v) {
		sitioDto.setDireccion(direccionSitioTxt.getText().toString());
		sitioDto.setNombre(nombreSitioTxt.getText().toString());
		sitioDto.setTelefono(telefonoSitioTxt.getText().toString());
		sitioDto.setWeb(webTxt.getText().toString());
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setDescripcion(categoriaSitioTxt.getText().toString());
		index++;
		categoria.setIdCategoria(index);
		sitioDto.setLat(loc.getLatitude());
		sitioDto.setLon(loc.getLongitude());
		sitioDto.setCategoria(categoria);
		Bundle bundle = new Bundle();
		bundle.putSerializable("sitio", sitioDto);

		Intent intent = new Intent(this, CrearSitioServicio.class);
		intent.putExtras(bundle);
		startService(intent);

		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
	}

	public void mostarCategoria(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final String[] categorias = getResources().getStringArray(
				R.array.listNames);
		builder.setTitle(R.string.categoria);
		builder.setItems(categorias, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialoginterface, int i) {
				index = i;
				categoriaSitioTxt.setText(categorias[i]);

			}
		});
		builder.show();
	}

	protected BroadcastReceiver receiverCrearSitio = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "onReceive");
			Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");

			Util.dismissProgressDialog();

			if (!respuesta.isEmpty()) {

				Util.showToast(context, respuesta);
				// Intent actividadPrincipal = new
				// Intent(getApplicationContext(), SitiosActivity.class);
				// startActivity(actividadPrincipal);

			} else {
				Util.showToast(context, Constantes.MSG_SITIO_CREADO);
			}

		}
	};

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		if (!nombreSitioTxt.getText().toString().isEmpty()
				&& !direccionSitioTxt.getText().toString().isEmpty()
				&& !categoriaSitioTxt.getText().toString().isEmpty()
				&& s.length() != 0) {
			crearSitioBtn.setEnabled(true);
		} else {
			crearSitioBtn.setEnabled(false);
		}

	}

	@Override
	public void afterTextChanged(Editable s) {

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
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
										
					loc.setLatitude(punto.getLatitudeE6() / 1000000F);
					loc.setLongitude(punto.getLongitudeE6() / 1000000F);
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

}
