package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.image.ImageLoader;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.uas.gsiam.adapter.ComentarioAdapter;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Util;

public class SitioDetalleActivity extends GDActivity implements OnItemClickListener{

	protected static final String TAG = "SitioDetalleActivity";
	protected TextView txtNombre;
	protected TextView txtDireccion;
	protected TextView txtTelefono;
	protected TextView txtWeb;
	protected Location loc;
	private Double lat;
	private Double lon;
	private Integer sitioId;
	private ListView listComentarios;
	private SitioDTO sitio;
	private static final int MAPA = 1;
	private Gallery galeria;
	private ArrayList<byte[]> fotos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.sitio_detalle);
		galeria = (Gallery) findViewById(R.id.gallery);
		fotos = new ArrayList<byte[]>();

		sitio = (SitioDTO) getIntent().getSerializableExtra("sitio");
		cargarFotos(sitio.getPublicaciones());
		galeria.setAdapter(new FotosAdapter(this));
		txtNombre = (TextView) findViewById(R.id.txtSitioNombreId);
		txtDireccion = (TextView) findViewById(R.id.txtSitioDireccionId);
		txtTelefono = (TextView) findViewById(R.id.txtSitioTelefonoId);
		txtWeb = (TextView) findViewById(R.id.txtSitioWebId);
		listComentarios = (ListView) findViewById(R.id.listComentariosId);
		
		inicializarBarra();
		
		galeria.setOnItemClickListener(this);

	}

	private void cargarFotos(List<PublicacionDTO> publicaciones) {
		if (!publicaciones.isEmpty()) {
			for (PublicacionDTO p : publicaciones) {
				if (p.getFoto() != null) {
					fotos.add(p.getFoto());
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

	private void inicializarBarra() {
		addActionBarItem(Type.Locate, MAPA);
		setTitle(R.string.app_name);
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case MAPA:
			mostarMapa();
			break;

		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;

	}

	public void onResume() {
		super.onResume();
		Intent intent = getIntent();
		sitio = (SitioDTO) intent.getSerializableExtra("sitio");

		txtNombre.setText(sitio.getNombre());
		txtDireccion.setText(sitio.getDireccion());
		txtTelefono.setText(sitio.getTelefono());
		txtWeb.setText(sitio.getWeb());
		
		loc = intent.getParcelableExtra("ubicacion");

		if (!sitio.getPublicaciones().isEmpty()) {
			ComentarioAdapter adaptador = new ComentarioAdapter(this,
					R.layout.comentario, sitio.getPublicaciones());
			// ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
			listComentarios.setAdapter(adaptador);
		}

	}

	public ArrayList<byte[]> getFotos() {
		return fotos;
	}

	public void setFotos(ArrayList<byte[]> fotos) {
		this.fotos = fotos;
	}

	public void onPause() {
		super.onPause();

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

			imgView.setLayoutParams(new Gallery.LayoutParams(80, 70));
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
			imgView.setBackgroundResource(mGalleryItemBackground);

			return imgView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	
		Intent intentVisor = new Intent(this, VisorImagenes.class);
		intentVisor.putExtra("fotos", getFotos());
		intentVisor.putExtra("indice", position);
		startActivity(intentVisor);
		
		
	}

	

}
