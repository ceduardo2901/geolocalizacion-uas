package com.uas.gsiam.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uas.gsiam.ui.R;

/**
 * Este adapter es el encargado de mostrar la lista de rating de un sitio para
 * que el usuario pueda filtrar la lista de sitios a partir de la calificacion
 * de este
 * 
 * @author Antonio
 * 
 */
public class RatingAdapter extends ArrayAdapter<String> {

	Activity context;
	ViewHolder holder;
	String[] items;

	/**
	 * Constructor del adapter
	 * 
	 * @param context
	 *            Contexto del activity
	 * @param resource
	 *            Recurso de la interfaz grafica
	 * @param objects
	 *            Lista de calificaciones a mostrar
	 */
	public RatingAdapter(Activity context, int resource, String[] objects) {
		super(context, resource, objects);
		this.context = context;
		this.items = objects;
	}

	class ViewHolder {
		ImageView icon0;
		ImageView icon1;
		ImageView icon2;
		ImageView icon3;
		ImageView icon4;
		//TextView title;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {

			convertView = inflater.inflate(R.layout.rating_item, null);

			holder = new ViewHolder();
			holder.icon0 = (ImageView) convertView.findViewById(R.id.icon0);
			holder.icon1 = (ImageView) convertView.findViewById(R.id.icon1);
			holder.icon2 = (ImageView) convertView.findViewById(R.id.icon2);
			holder.icon3 = (ImageView) convertView.findViewById(R.id.icon3);
			holder.icon4 = (ImageView) convertView.findViewById(R.id.icon4);
		//	holder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(holder);

		} else {

			// view already defined, retrieve view holder
			holder = (ViewHolder) convertView.getTag();

		}

		//holder.title.setText(items[position]);

		switch (position) {
		case 0:
			holder.icon0.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon1.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			holder.icon2.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			holder.icon3.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			holder.icon4.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			break;

		case 1:
			holder.icon0.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon1.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon2.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			holder.icon3.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			holder.icon4.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			break;

		case 2:
			holder.icon0.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon1.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon2.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon3.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			holder.icon4.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			break;

		case 3:
			holder.icon0.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon1.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon2.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon3.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon4.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_off_normal));
			break;

		case 4:
			holder.icon0.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon1.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon2.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon3.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			holder.icon4.setImageDrawable(context.getResources().getDrawable(
					R.drawable.btn_rating_star_on_normal));
			break;

		}

		return convertView;

	}

}
