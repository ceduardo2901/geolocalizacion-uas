package com.uas.gsiam.utils;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.ui.R;

public class SitiosAdapter extends ArrayAdapter<SitioDTO> {

	Activity context;
	private List<SitioDTO> sitiosDTO;
	private Location loc;

	public SitiosAdapter(Activity context, int resource,
			List<SitioDTO> objects, Location loc) {
		super(context, resource, objects);
		this.context = context;
		sitiosDTO = objects;
		this.loc = loc;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		SitioDTO sitioMovil = sitiosDTO.get(position);
		Location locSitio = new Location("");
		locSitio.setLatitude(new Double(sitioMovil.getLat()));
		locSitio.setLongitude(new Double(sitioMovil.getLon()));
		Float distancia = loc.distanceTo(locSitio);
		View item = convertView;
		ViewHolder holder;
		
		if (item == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			item = inflater.inflate(R.layout.sitio, null);
			
			holder = new ViewHolder();
			holder.Nombre = (TextView) item.findViewById(R.id.LblNombre);
			holder.Direccion = (TextView) item.findViewById(R.id.LblDireccion);
			holder.distancia = (TextView) item.findViewById(R.id.LblDistancia);
			holder.rating = (RatingBar) item.findViewById(R.id.puntajeSitioId);
			holder.icono = (ImageView) item.findViewById(R.id.icCategoriaId);
			item.setTag(holder);
			
		}else{
			holder = (ViewHolder) item.getTag();
				
		}
		
		holder.icono.setImageBitmap(obtenerIcono(sitioMovil.getCategoria().getIdCategoria()));
		holder.Nombre.setText(sitioMovil.getNombre());
		holder.Direccion.setText(sitioMovil.getDireccion());
		holder.distancia.setText(distancia.intValue() +" "+Constantes.METROS);
		holder.rating.setRating(obtenerPromedioPuntaje(sitioMovil));
		return item;
	}
	
	private Bitmap obtenerIcono(Integer categoriaId){
		Bitmap bitmap=null;
		switch (categoriaId) {
		case 1:
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cafe);  	
			break;
			
		case 2:
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_restaurant);  	
			break;

		default:
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.avatardefault);  	
			break;
		}
		
		return bitmap;
		
	}
	
	
	private Float obtenerPromedioPuntaje(SitioDTO sitio){
		Float promedio = new Float(0);
		
		if(!sitio.getPublicaciones().isEmpty()){
			for(PublicacionDTO pub : sitio.getPublicaciones()){
				promedio = promedio + pub.getPuntaje();
			}
		}
		
		return promedio/sitio.getPublicaciones().size();
	}
	
	static class ViewHolder {
		ImageView icono;
		TextView Nombre;
		TextView Direccion;
		TextView distancia;
		RatingBar rating;
		}
}
