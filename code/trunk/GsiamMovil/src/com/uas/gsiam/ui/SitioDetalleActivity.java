package com.uas.gsiam.ui;

import greendroid.app.GDTabActivity;
import greendroid.widget.LoaderActionBarItem;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.LocationHelper;
import com.uas.gsiam.utils.Util;
import com.uas.gsiam.utils.LocationHelper.LocationResult;

public class SitioDetalleActivity extends Activity implements
		OnItemClickListener {

	protected static final String TAG = "SitioDetalleActivity";
	protected TextView txtNombre;
	protected TextView txtDireccion;
	protected TextView txtTelefono;
	protected TextView txtWeb;
	protected TextView txtFotos;
	protected Location loc;
	private SitioDTO sitio;
	private Gallery galeria;
	private ArrayList<byte[]> fotos;
	private ArrayList<String> fotosRutas;
	private IntentFilter detalleFiltro;
	private LocationHelper locHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sitio_detalle);
		galeria = (Gallery) findViewById(R.id.gallery);
		fotos = new ArrayList<byte[]>();
		fotosRutas = new ArrayList<String>();
		sitio = SitioTabActivity.sitio;
		cargarFotos(sitio.getPublicaciones());
		galeria.setAdapter(new FotosAdapter(this));

		if (fotos.size() > 1) {
			galeria.setSelection(1);
		}

		txtNombre = (TextView) findViewById(R.id.txtSitioNombreId);
		txtDireccion = (TextView) findViewById(R.id.txtSitioDireccionId);
		txtTelefono = (TextView) findViewById(R.id.txtSitioTelefonoId);
		txtWeb = (TextView) findViewById(R.id.txtSitioWebId);
		txtFotos = (TextView) findViewById(R.id.txtFotosId);
	    if (fotos.isEmpty()) {
			txtFotos.setText(null);
			Log.i(TAG, "oncreate -no hay fotos: ");
		} else {
			txtFotos.setText("Fotos");
			Log.i(TAG, "oncreate - hay fotos: ");

		}

		galeria.setOnItemClickListener(this);
		detalleFiltro = new IntentFilter(Constantes.SITIO_FILTRO_ACTION);

		locHelper = new LocationHelper();
		locHelper.getLocation(this, locationResult);

	}

	private void cargarFotos(List<PublicacionDTO> publicaciones) {
		if (!publicaciones.isEmpty()) {
			for (PublicacionDTO p : publicaciones) {
				if (p.getFotoRuta() != null) {
					 
					fotos.add(Util.recuperarImagenMemoria(this, p.getFotoRuta()));
					fotosRutas.add(p.getFotoRuta());
				}
			}
		}
	}

	public SitioDTO getSitio() {
		return sitio;
	}

	public void setSitio(SitioDTO sitio) {
		this.sitio = sitio;
	}

	protected BroadcastReceiver receiverSitio = new BroadcastReceiver() {
		@SuppressWarnings("unchecked")
		@Override
		public void onReceive(Context context, Intent intent) {

			Bundle bundle = intent.getExtras();
			ArrayList<SitioDTO> sitios = (ArrayList<SitioDTO>) bundle
					.getSerializable("sitios");
			if (!sitios.isEmpty()) {
				setSitio(sitios.get(0));
				List<PublicacionDTO> publicaciones = sitios.get(0)
						.getPublicaciones();
				if (!publicaciones.isEmpty()) {

					cargarFotos(sitio.getPublicaciones());
				}

			}
			Util.dismissProgressDialog();

			GDTabActivity padre = (GDTabActivity) getParent();
			LoaderActionBarItem loaderActionBarItem = (LoaderActionBarItem) padre
					.getActionBar().getItem(AmigosTabActivity.ACTUALIZAR);
			loaderActionBarItem.setLoading(false);

		}
	};

	public void onResume() {
		super.onResume();
	
		txtNombre.setText(sitio.getNombre());
		txtDireccion.setText(sitio.getDireccion());
		txtTelefono.setText(sitio.getTelefono());
		txtWeb.setText(sitio.getWeb());
		if (fotos.isEmpty()) {
			txtFotos.setText(null);
			Log.i(TAG, "oncreate -no hay fotos: ");
		} else {
			txtFotos.setText("Fotos");
			Log.i(TAG, "oncreate - hay fotos: ");

		}

		registerReceiver(receiverSitio, detalleFiltro);

	}

	public ArrayList<byte[]> getFotos() {
		return fotos;
	}

	public void setFotos(ArrayList<byte[]> fotos) {
		this.fotos = fotos;
	}
	
	
	public ArrayList<String> getFotosRutas() {
		return fotosRutas;
	}

	public void setFotosRutas(ArrayList<String> fotosRutas) {
		this.fotosRutas = fotosRutas;
	}
	

	public void onPause() {
		super.onPause();
		unregisterReceiver(receiverSitio);

	}

	public void comoIr(View v) {
		String uri = "http://maps.google.com/maps?saddr=" + loc.getLatitude()
				+ "," + loc.getLongitude() + "&daddr=" + sitio.getLat() + ","
				+ sitio.getLon();
		Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

		startActivity(navigation);
	}

	public void mostarMapa() {

		Bundle bundle = new Bundle();
		bundle.putDouble("lat", sitio.getLat());
		bundle.putDouble("lon", sitio.getLon());
		Intent intentMostrarMapa = new Intent(this, MostrarMapaActivity.class);
		intentMostrarMapa.putExtras(bundle);
		intentMostrarMapa.putExtra("ubicacion", loc);
		startActivity(intentMostrarMapa);

	}

	public void publicar(View v) {

		Intent intent = new Intent(this, PublicarActivity.class);
		intent.putExtra("sitioId", sitio.getIdSitio());
		intent.putExtra("nombre", sitio.getNombre());

		startActivity(intent);

	}

	private class FotosAdapter extends BaseAdapter {
		int mGalleryItemBackground;
		private Context mContext;
		private List<byte[]> fotos = new ArrayList<byte[]>();

		public FotosAdapter(Context c) {
			this.mContext = c;
			TypedArray attr = mContext
					.obtainStyledAttributes(R.styleable.fotosPublicacion);
			mGalleryItemBackground = attr.getResourceId(
					R.styleable.fotosPublicacion_android_galleryItemBackground,
					0);
			attr.recycle();
			this.fotos = getFotos();
		

		}

		@Override
		public int getCount() {

			return fotos.size();
		}

		@Override
		public Object getItem(int position) {

			return position;
		}

		@Override
		public long getItemId(int position) {

			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ImageView imgView = new ImageView(mContext);

			imgView.setImageBitmap(Util.ArrayToBitmap(fotos.get(position)));

			imgView.setLayoutParams(new Gallery.LayoutParams(130, 100));
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
			imgView.setBackgroundResource(mGalleryItemBackground);

			return imgView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intentVisor = new Intent(this, VisorImagenes.class);
		intentVisor.putExtra("fotosRuta", getFotosRutas());
		intentVisor.putExtra("indice", position);
		startActivity(intentVisor);

	}

	public LocationResult locationResult = new LocationResult() {

		@Override
		public void obtenerUbicacion(final Location location) {
			loc = location;
		}
	};

}
