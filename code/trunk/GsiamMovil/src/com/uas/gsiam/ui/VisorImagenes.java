package com.uas.gsiam.ui;

import java.util.ArrayList;
import java.util.List;

import com.uas.gsiam.utils.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * 
 * Esta activity permite ver una galeria con las imagenes del sitio de interes
 * seleccionado
 * 
 * @author Antonio
 * 
 */
public class VisorImagenes extends Activity implements
		AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {

	private ImageSwitcher mSwitcher;
	/**
	 * Lista de fotos a mostrar
	 */
//	private ArrayList<byte[]> fotos;
	private ArrayList<String> fotosRuta;
	private List<Drawable> imagenes = new ArrayList<Drawable>();
	private Integer seleccionada;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.visor_imagenes);

		mSwitcher = (ImageSwitcher) findViewById(R.id.visor);
		mSwitcher.setFactory(this);
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));
		fotosRuta = (ArrayList<String>) getIntent().getSerializableExtra("fotosRuta");
		seleccionada = getIntent().getIntExtra("indice", 0);
		
		for (String nombre : fotosRuta) {
			
			byte[] foto = Util.recuperarImagenMemoria(this, nombre);
			//fotos.add(foto);
			
			ImageView i = new ImageView(this);
			i.setImageBitmap(Util.ArrayToBitmap(foto));
			imagenes.add(i.getDrawable());
		}
		Gallery g = (Gallery) findViewById(R.id.galeria);
		g.setAdapter(new ImageAdapter(this));
		g.setSelection(seleccionada);
		g.setOnItemSelectedListener(this);
	}

	@Override
	public View makeView() {
		ImageView i = new ImageView(this);
		i.setBackgroundColor(0xFF000000);
		i.setScaleType(ImageView.ScaleType.FIT_CENTER);
		i.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return i;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {

		mSwitcher.setImageDrawable(imagenes.get(position));

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	public class ImageAdapter extends BaseAdapter {

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return imagenes.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);

			i.setImageDrawable(imagenes.get(position));
			i.setAdjustViewBounds(true);
			i.setLayoutParams(new Gallery.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			i.setBackgroundResource(R.drawable.picture_frame);
			return i;
		}

		private Context mContext;

	}

}
