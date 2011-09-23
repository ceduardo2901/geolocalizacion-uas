package com.uas.gsiam.ui;

import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SitioMovilDTO;

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
			item = inflater.inflate(R.layout.sitios, null);
			
			holder = new ViewHolder();
			holder.Nombre = (TextView) item.findViewById(R.id.LblNombre);
			holder.Direccion = (TextView) item.findViewById(R.id.LblDireccion);
			holder.distancia = (TextView) item.findViewById(R.id.LblDistancia);
			item.setTag(holder);
			
		}else{
			holder = (ViewHolder) item.getTag();
				
		}
		
		
		holder.Nombre.setText(sitioMovil.getNombre());
		holder.Direccion.setText(sitioMovil.getDireccion());
		holder.distancia.setText(distancia.intValue() +" "+Constantes.METROS);
		return item;
	}
	
	static class ViewHolder {
		TextView Nombre;
		TextView Direccion;
		TextView distancia;
		}
}
