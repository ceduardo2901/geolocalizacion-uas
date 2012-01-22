package com.uas.gsiam.utils;

import java.util.Hashtable;
import java.util.Stack;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

/**
 * 
 * Clase encargada de obtener y cargar las imagenes del perfil de facebook
 * 
 * @author Antonio
 * 
 */
public class FriendsGetProfilePics {

	Hashtable<String, Bitmap> friendsImages;
	Hashtable<String, String> positionRequested;
	BaseAdapter listener;
	int runningCount = 0;
	Stack<ItemPair> queue;

	/*
	 * Maximo de tareas simultaneas
	 */
	final int MAX_ALLOWED_TASKS = 15;

	public FriendsGetProfilePics() {
		friendsImages = new Hashtable<String, Bitmap>();
		positionRequested = new Hashtable<String, String>();
		queue = new Stack<ItemPair>();
	}

	/**
	 * Este listener se encarga de informar cuando la imagen se descargo.
	 * 
	 * @param listener
	 */

	public void setListener(BaseAdapter listener) {
		this.listener = listener;
		reset();
	}

	private void reset() {
		positionRequested.clear();
		runningCount = 0;
		queue.clear();
	}

	/**
	 * Si la imagen del perfil ya ha sido descargada y almacenado en caché la
	 * retorna sino ejecutar una tarea asíncrona para buscarla si el total de
	 * las tareas asincrónicas es mayor a 15, se encolan para su posterior
	 * procesamiento
	 * 
	 * @param uid
	 *            Identificador de usuario
	 * @param url
	 *            URL de la imagen
	 * @return Retorna la imagen en Bitmap
	 */

	public Bitmap getImage(String uid, String url) {
		Bitmap image = (Bitmap) friendsImages.get(uid);
		if (image != null) {
			return image;
		}
		if (!positionRequested.containsKey(uid)) {
			positionRequested.put(uid, "");
			if (runningCount >= MAX_ALLOWED_TASKS) {
				queue.push(new ItemPair(uid, url));
			} else {
				runningCount++;
				new GetProfilePicAsyncTask().execute(uid, url);
			}
		}
		return null;
	}

	public void getNextImage() {
		if (!queue.isEmpty()) {
			ItemPair item = (ItemPair) queue.pop();
			new GetProfilePicAsyncTask().execute(item.uid, item.url);
		}
	}

	/*
	 * Comenzar la tarea para procesar las solicitudes de forma asincronica
	 */
	private class GetProfilePicAsyncTask extends
			AsyncTask<Object, Void, Bitmap> {
		String uid;

		protected Bitmap doInBackground(Object... params) {
			this.uid = (String) params[0];
			String url = (String) params[1];
			return FacebookUtil.getBitmap(url);
		}

		protected void onPostExecute(Bitmap result) {
			runningCount--;
			if (result != null) {
				friendsImages.put(uid, result);
				listener.notifyDataSetChanged();
				getNextImage();
			}
		}
	}

	class ItemPair {
		String uid;
		String url;

		public ItemPair(String uid, String url) {
			this.uid = uid;
			this.url = url;
		}
	}
}
