package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import greendroid.widget.LoaderActionBarItem;
import greendroid.widget.QuickAction;
import greendroid.widget.QuickActionBar;
import greendroid.widget.QuickActionWidget;
import greendroid.widget.QuickActionWidget.OnQuickActionClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.uas.gsiam.adapter.CategoriaAdapter;
import com.uas.gsiam.adapter.RatingAdapter;
import com.uas.gsiam.adapter.SitiosAdapter;
import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.EliminarSitioServicio;
import com.uas.gsiam.servicios.SitioServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.LocationHelper;
import com.uas.gsiam.utils.LocationHelper.LocationResult;
import com.uas.gsiam.utils.Util;

public class SitiosActivity extends GDActivity implements
		OnItemLongClickListener {

	private final int BUSCAR = 1;
	private final int ACTUALIZAR = 2;
	private final int FILTRAR = 0;
	protected static final String TAG = "SitioActivity";
	protected LocationManager locationManager;
	protected IntentFilter sitioAccion;
	protected IntentFilter intentEliminarSitio;
	protected SitiosAdapter adapter;
	protected List<SitioDTO> sitios;
	protected Location loc;
	protected ListView lw;
	private QuickActionWidget quickActions;
	private SitiosAdapter adaptador;
	private LocationHelper locHelper;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.lista_sitios);

		Util.showProgressDialog(this, Constantes.MSG_ESPERA_BUSCANDO);
		lw = (ListView) findViewById(R.id.listaSitios);
		locHelper = new LocationHelper();
		locHelper.getLocation(this, locationResult);

		sitioAccion = new IntentFilter(Constantes.SITIO_FILTRO_ACTION);
		intentEliminarSitio = new IntentFilter(
				Constantes.ELIMINAR_SITIO_FILTRO_ACTION);

		inicializarActionBar();
		initQuickActionBar();
		sitios = (List<SitioDTO>) getLastNonConfigurationInstance();
		if (sitios != null) {
			mostrarSitios(sitios);
		}
		
		if (loc == null) {
			// buildAlertMessageNoGps();
			Util.dismissProgressDialog();
			Util.showToast(this, Constantes.MSG_GPS_DISABLE);
		} else {
			if (sitios == null) {

				actualizarSitios(loc);
			}else{
				Util.dismissProgressDialog();
			}
		}

	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		final List<SitioDTO> data = getSitios();
		return data;
	}

	private void inicializarActionBar() {
		addActionBarItem(Type.List, FILTRAR);
		addActionBarItem(Type.Search, BUSCAR);
		addActionBarItem(Type.Refresh, ACTUALIZAR);

		getActionBar().setTitle("GSIAM - Sitios");
	}

	private void initQuickActionBar() {
		quickActions = new QuickActionBar(this);
		quickActions.addQuickAction(new QuickAction(this,
				R.drawable.filtro_categorias, "Categorias"));
		quickActions.addQuickAction(new QuickAction(this,
				R.drawable.filtro_ranking, "Ranking"));
		quickActions.addQuickAction(new QuickAction(this,
				R.drawable.filtro_todos, "Todos"));
		quickActions
				.setOnQuickActionClickListener(new OnQuickActionClickListener() {

					@Override
					public void onQuickActionClicked(QuickActionWidget widget,
							int position) {

						switch (position) {
						case 0:
							mostarCategoria();
							break;
						case 1:
							mostrarRaiting();
							break;

						case 2:
							mostrarSitios(getSitios());
							break;

						}

					}

				});
	}

	private void mostrarRaiting() {
	
String[] items = {"Malo", "Regular", "Bueno", "Muy Bueno", "Excelente"};
		ListAdapter adapter = new RatingAdapter(this, R.layout.rating_item, items); 
		   
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.rating);
		builder.setAdapter(adapter,new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int item) {

				mostrarSitios(filtrarPorRating(item + 1));

			}

		});

		AlertDialog alert = builder.create();
		alert.show();
		  

	}

	private AlertDialog dialog = null;

	public void mostarCategoria() {

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
				new String[] {}, // the name of the field data
				new int[] { android.R.id.text1 }, // the text field to populate
													// with the field data
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

				CategoriaDTO categoriaSeleccionada = (CategoriaDTO) map
						.get("CategoriaDTO");

				if (dialog != null) {
					dialog.dismiss();
					mostrarSitios(filtrarPorCategoria(categoriaSeleccionada
							.getIdCategoria()));

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

	private ArrayList<SitioDTO> filtrarPorCategoria(Integer categoria) {
		ArrayList<SitioDTO> sitiosFiltro = new ArrayList<SitioDTO>();
		if (!sitios.isEmpty()) {
			for (SitioDTO s : sitios) {
				if (s.getCategoria().getIdCategoria() == categoria.intValue()) {
					sitiosFiltro.add(s);
				}
			}
		}
		return sitiosFiltro;
	}

	private ArrayList<SitioDTO> filtrarPorRating(Integer rating) {
		ArrayList<SitioDTO> sitiosFiltro = new ArrayList<SitioDTO>();
		if (!sitios.isEmpty()) {
			if (rating == 1) {
				rating = 0;
			}
			for (SitioDTO s : sitios) {

				if (getPromedioPuntaje(s).intValue() == rating) {
					sitiosFiltro.add(s);
				}
			}
		}
		return sitiosFiltro;
	}

	private Integer getPromedioPuntaje(SitioDTO s) {
		Float result = new Float(0);
		if (!s.getPublicaciones().isEmpty()) {
			for (PublicacionDTO p : s.getPublicaciones()) {
				result = result + p.getPuntaje();
			}
			result = result / s.getPublicaciones().size();

		}

		return result.intValue();
	}

	private void launchGPSOptions() {
		final ComponentName toLaunch = new ComponentName(
				"com.android.settings", "com.android.settings.SecuritySettings");
		final Intent intent = new Intent(
				Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(toLaunch);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivityForResult(intent, 0);
	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(Constantes.MSG_GPS_DISABLE)
				.setCancelable(false)
				.setPositiveButton(Constantes.MSG_ACEPTAR,
						new DialogInterface.OnClickListener() {
							public void onClick(
									final DialogInterface dialog,
									final int id) {
								launchGPSOptions();
							}
						})
				.setNegativeButton(Constantes.MSG_CANCELAR,
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								dialog.cancel();
							}
						});
		final AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			buscarSitio(query);
		}
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case BUSCAR:
			startSearch(null, false, null, false);
			break;
		case ACTUALIZAR:
			// loc = PosicionGPS.getPosicion(getApplicationContext());
			locHelper.getLocation(this, locationResult);
			actualizarSitios(loc);

			break;
		case FILTRAR:
			quickActions.show(item.getItemView());
			break;
		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;

	}

	private void buscarSitio(String query) {
		Log.i(TAG, "Estoy en la busqueda");
		SitioDTO sitio = new SitioDTO();
		sitio.setNombre(query);
		Intent intentBuscarSitio = new Intent(this, SitioServicio.class);
		intentBuscarSitio.putExtra("sitio", sitio);

		startService(intentBuscarSitio);

		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

	}

	public void onStart() {
		super.onStart();
		Log.d(TAG, "prueba");
	}

	public void onResume() {
		super.onResume();
		registerReceiver(sitiosReceiver, sitioAccion);
		registerReceiver(eliminarSitioReceiver, intentEliminarSitio);

		// startListening();

	}

	public List<SitioDTO> getSitios() {
		return sitios;
	}

	public void setSitios(List<SitioDTO> sitios) {
		this.sitios = sitios;
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(sitiosReceiver);
		unregisterReceiver(eliminarSitioReceiver);
		// stopListening();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sitios_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.agregarSitioId:

			Intent agregarSitioIntent = new Intent(this,
					CrearSitioActivity.class);
			agregarSitioIntent.putExtra("ubicacion", loc);
			startActivity(agregarSitioIntent);
			break;

		default:
			return super.onOptionsItemSelected(item);

		}
		return true;
	}

	/**
	 * 
	 */
	protected BroadcastReceiver sitiosReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");

			Bundle b = intent.getExtras();
			ArrayList<SitioDTO> sitios = (ArrayList<SitioDTO>) b
					.getSerializable("sitios");

			if (sitios != null) {
				setSitios(sitios);
				mostrarSitios(sitios);
			}
			Util.dismissProgressDialog();

			LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) getActionBar()
					.getItem(ACTUALIZAR);
			loaderActionBarItem.setLoading(false);

			if (sitios.size() == 0) {

				mostrarMensaje(Constantes.MSG_NO_EXISTEN_SITIOS);

			}

		}
	};

	public void mostrarMensaje(String mensaje) {
		Util.showToast(this, mensaje);
	}

	/**
	 * 
	 */
	protected BroadcastReceiver eliminarSitioReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");
			Util.dismissProgressDialog();

		

			actualizarSitios(loc);

		}
	};

	public void mostrarSitios(final List<SitioDTO> sitios) {

		adaptador = new SitiosAdapter(this, R.layout.sitio, sitios, loc);

		// lw.setAdapter(adaptador);
		lw.setAdapter(adaptador);

		lw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent sitioDetalleIntent = new Intent(getApplicationContext(),
						SitioTabActivity.class);
				sitioDetalleIntent.putExtra("sitio", sitios.get(position));
				sitioDetalleIntent.putExtra("ubicacion", loc);
				startActivity(sitioDetalleIntent);

			}
		});
		lw.setOnItemLongClickListener(this);

	}

	private void actualizarSitio(SitioDTO sitio) {
		Intent intentModificar = new Intent(this, ModificarSitioActivity.class);
		intentModificar.putExtra("sitio", sitio);
		startActivity(intentModificar);

	}

	private void eliminarSitio(Integer sitioId) {
		Intent intent = new Intent(this, EliminarSitioServicio.class);
		intent.putExtra("sitio", sitioId);
		startService(intent);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
	}

	// private void startListening() {
	// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
	// 0, this);
	// }
	//
	// private void stopListening() {
	// if (locationManager != null)
	// locationManager.removeUpdates(this);
	// }

	private void actualizarSitios(Location loc) {
		if (loc != null) {

			Bundle bundle = new Bundle();
			SitioDTO sitio = new SitioDTO();
			sitio.setLat(loc.getLatitude());
			sitio.setLon(loc.getLongitude());

			bundle.putDouble("lat", loc.getLatitude());
			bundle.putDouble("lon", loc.getLongitude());

			Intent intent = new Intent(this, SitioServicio.class);
			intent.putExtra("sitio", sitio);
			// intent.putExtras(bundle);
			startService(intent);

		}
	}

	public LocationResult locationResult = new LocationResult() {

		@Override
		public void obtenerUbicacion(final Location location) {
			loc = location;
		}
	};

	// @Override
	// public void onLocationChanged(Location location) {
	// this.loc = location;
	// actualizarSitios(loc);
	//
	// }
	//
	// @Override
	// public void onStatusChanged(String provider, int status, Bundle extras) {
	//
	// }
	//
	// @Override
	// public void onProviderEnabled(String provider) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void onProviderDisabled(String provider) {
	// Util.showToast(getApplicationContext(), "EL gps no esta encendido");
	//
	// }

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final SitioDTO sitio = sitios.get(position);
		CharSequence[] items = { "Modificar", "Eliminar" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Seleccionar una acci�n");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {

					actualizarSitio(sitio);
				} else {

					eliminarSitio(sitio.getIdSitio());
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

		return false;

	}

}
