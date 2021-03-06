package com.uas.gsiam.adapter;

import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.location.LocationManager;
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
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Adapter para manejar la lista de sitios. Esta clase permite mostrar la lista
 * de sitios con los atributos de ranking, distancia del sitio a la ubicacion
 * del movil y la imagen que identifica la categoria del sitio
 * 
 * @author Antonio
 * 
 */
public class SitiosAdapter extends ArrayAdapter<SitioDTO> {

	Activity context;
	private List<SitioDTO> sitiosDTO;
	private Location loc;

	/**
	 * Contructor del adaptador
	 * 
	 * @param context
	 *            Contexto del activity donde se desplegara la lista de sitios
	 * @param resource
	 *            Identificador de la interfaz grafica de un sitio
	 * @param objects
	 *            Lista de sitios a mostrar
	 * @param loc
	 *            Ubicacion geografica del usuario
	 */
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
		locSitio.setLatitude(sitioMovil.getLat());
		locSitio.setLongitude(sitioMovil.getLon());

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

		} else {
			holder = (ViewHolder) item.getTag();

		}

		int idImagen = Util.getDrawableIdFromString(context, sitioMovil
				.getCategoria().getImagen());

		// imagen por defecto
		if (idImagen == 0) {
			idImagen = Util.getDrawableIdFromString(context, "logo");
		}

		holder.icono.setImageResource(idImagen);

		holder.Nombre.setText(sitioMovil.getNombre());
		holder.Direccion.setText(sitioMovil.getDireccion());
		if (sitioMovil.getDistancia() != null
				&& sitioMovil.getDistancia().length() > 0) {
			holder.distancia.setText(convertirDistancia(sitioMovil
					.getDistancia()) + " " + Constantes.METROS);
		}
		holder.rating.setRating(obtenerPromedioPuntaje(sitioMovil));
		return item;
	}

	/**
	 * Calcula la distancia al sitio de interes desde la posicion del usuario
	 * 
	 * @param sitio
	 * @return
	 */
	private String calcularDistancia(SitioDTO sitio) {
		Location locSitio = new Location("");
		locSitio.setLatitude(sitio.getLat());
		locSitio.setLongitude(sitio.getLon());
		float dis = loc.distanceTo(locSitio);

		return convertirDistancia(String.valueOf(dis));
	}

	/**
	 * Obtiene el promedio del puntaje obtenido por el sitio
	 * 
	 * @param sitio
	 *            Sitio que se le calculara el promedio
	 * @return Retorna un entero con el puntaje obtenido
	 */
	private Integer obtenerPromedioPuntaje(SitioDTO sitio) {
		Float promedio = new Float(0);

		if (!sitio.getPublicaciones().isEmpty()) {
			for (PublicacionDTO pub : sitio.getPublicaciones()) {
				promedio = promedio + pub.getPuntaje();
			}
		}
		promedio = promedio / sitio.getPublicaciones().size();
		return promedio.intValue();
	}

	private String convertirDistancia(String distancia) {
		String dis;

		if (distancia.contains(".")) {
			int index = distancia.indexOf(".");
			dis = distancia.substring(0, index);

		} else {
			dis = distancia;
		}
		return dis;
	}

	static class ViewHolder {
		ImageView icono;
		TextView Nombre;
		TextView Direccion;
		TextView distancia;
		RatingBar rating;
	}
}
