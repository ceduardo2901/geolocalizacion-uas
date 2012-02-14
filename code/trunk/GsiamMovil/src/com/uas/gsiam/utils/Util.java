package com.uas.gsiam.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;

/**
 * Esta clase contiene metodos auxiliares que se usan en toda la aplicacion
 * 
 * @author Martin
 * 
 */
public class Util {

	private static ProgressDialog progressDialog;

	/**
	 * Valida que email ingresado tenga un formato correcto. Se usa la expresion
	 * regular definida en las contantes para validarla {@link Constantes}
	 * 
	 * @param email
	 *            Email ingresado para validar
	 * @return Retorna true en caso que el email tenga un formato correcto de lo
	 *         contrario falso
	 */
	public static boolean validaMail(String email) {

		boolean result = true;

		Matcher match = Pattern.compile(Constantes.REGEX_EMAIL).matcher(email);
		if (!match.matches()) {
			result = false;
		}

		return result;

	}

	/**
	 * Metodo que mustra un progress dialog con el mensaje con un mensaje
	 * determinado.
	 * 
	 * @param context
	 *            Contexto de la aplicación
	 * @param mensaje
	 *            Mensaje a desplegar
	 */
	public static void showProgressDialog(Context context, String mensaje) {
		synchronized (Util.class) {
			progressDialog = ProgressDialog.show(context, "", mensaje, true);
		}
	}

	/**
	 * Cierra un progress dialog
	 */
	public static void dismissProgressDialog() {

		if (progressDialog != null) {

			progressDialog.dismiss();
		}
	}

	/**
	 * Muestra un Alert dialog para la confirmacion de una acción. Si se
	 * presiona si la acción se confirma si se presiona no se cancela
	 * 
	 * @param contexto
	 *            Contecto de la aplicacion
	 * @param titulo
	 *            Titulo a desplegar
	 * @param mensaje
	 *            Mensaje a desplegar
	 */
	public static void showAlertDialogConfirm(Context contexto, String titulo,
			String mensaje) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(contexto);

		dialog.setTitle(titulo);
		dialog.setMessage(mensaje);
		dialog.setCancelable(false);
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		dialog.show();

	}

	/**
	 * Muestra un Alert dialog para la confirmacion de una acción. Solo se puede
	 * confirmar la acción
	 * 
	 * @param contexto
	 *            Contecto de la aplicacion
	 * @param titulo
	 *            Titulo a desplegar
	 * @param mensaje
	 *            Mensaje a desplegar
	 */
	public static void showAlertDialogOk(Context contexto, String titulo,
			String mensaje) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(contexto);

		dialog.setTitle(titulo);
		dialog.setMessage(mensaje);
		dialog.setCancelable(false);
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		dialog.show();

	}

	/**
	 * 
	 * Muestra un Alert dialog para la confirmacion de una acción. Este metodo
	 * solo es de caracter informativo y no permite la interacción del usuario
	 * 
	 * @param contexto
	 *            Contecto de la aplicacion
	 * @param titulo
	 *            Titulo a desplegar
	 * @param mensaje
	 *            Mensaje a desplegar
	 */
	public static void showAlertDialog(Context contexto, String titulo,
			String mensaje) {

		AlertDialog.Builder dialog = new AlertDialog.Builder(contexto);
		dialog.setTitle(titulo);
		dialog.setMessage(mensaje);
		dialog.setIcon(android.R.drawable.ic_dialog_info);
		dialog.show();

	}

	/**
	 * Muestra un mensaje por pantalla por unos segundos.
	 * 
	 * @param contexto
	 *            Contexto de la aplicación
	 * @param mensaje
	 *            Mensaje a desplegar
	 */
	public static void showToast(Context contexto, String mensaje) {

		Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show();

	}

	/**
	 * Transforma una imagen en formato BitmaoDrawable en un arreglo de byte
	 * 
	 * @param drawable
	 *            Imagen a transformar
	 * @return Retorna un byte[] con la imagen transformada
	 * @throws IOException
	 */
	public static byte[] BitmapToArray(BitmapDrawable drawable)
			throws IOException {

		Bitmap bitmap = drawable.getBitmap();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		out.flush();
		out.close();
		
		
		return out.toByteArray();

	}

	/**
	 * Transforma un arreglo de byte en un Bitmap
	 * 
	 * @param byteArray
	 *            Imagen en formato de arreglo de byte a ser transformada
	 * @return Retorna un bitmap con la imagen transformada
	 */
	public static Bitmap ArrayToBitmap(byte[] byteArray) {

		return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

	}

	/**
	 * Transforma un Bitmap en un objeto Drawable
	 * 
	 * @param Drawable
	 *            Imagen en formato de Bitmap a ser transformada
	 * @return Retorna un Drawable con la imagen transformada
	 */
	public static Drawable BitmapToDrawable(Bitmap bitmap) {

		return new BitmapDrawable(bitmap);

	}

	
	/**
	 * 
	 * Metodo que ajusta una imagen con el ancho y largo ingresado
	 * 
	 * @param bitmap
	 *            Imagen a procesar
	 * @param width
	 *            Ancho
	 * @param height
	 *            Largo
	 * @return Retorna la imagen transformada
	 */
	public static Bitmap getResizedBitmap(Bitmap bitmap, int width, int height) {

		final int bitmapWidth = bitmap.getWidth();
		final int bitmapHeight = bitmap.getHeight();

		final float scale = Math.min((float) width / (float) bitmapWidth,
				(float) height / (float) bitmapHeight);

		final int scaledWidth = (int) (bitmapWidth * scale);
		final int scaledHeight = (int) (bitmapHeight * scale);

		final Bitmap decored = Bitmap.createScaledBitmap(bitmap, scaledWidth,
				scaledHeight, true);

		return decored;
	}

	/**
	 * 
	 * Metodo para guardar una iamgen como byte[] en la memoria
	 * 
	 * @param ctx
	 * @param foto
	 * @param nombre
	 */
	public static void guardarImagenMemoria(Context ctx, byte[] foto, String nombre) {

		try { 
		    FileOutputStream fileOutStream = ctx.openFileOutput(nombre, android.content.Context.MODE_PRIVATE); 
		    fileOutStream.write(foto);  
		    fileOutStream.close(); 
		    
		} catch (IOException ioe) { 
		    ioe.printStackTrace(); 
		} 
		
	}
	
	/**
	 * 
	 * Metodo para recuperar la imagen de la memoria 
	 * 
	 * @param context
	 * @param nombreArchivo
	 * @return
	 */
	public static byte[] recuperarImagenMemoria (Context context, String nombreArchivo){
		
		File filePath = context.getFileStreamPath(nombreArchivo); 
		Drawable d = Drawable.createFromPath(filePath.toString()); 

		Bitmap bitmap = ((BitmapDrawable)d).getBitmap(); 
		ByteArrayOutputStream stream = new ByteArrayOutputStream(); 
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream); 
		return stream.toByteArray();
		
	}
	
	
	
	/**
	 * Metodo que obtiene el identificador de un Drawable a partir de su nombre
	 * 
	 * @param context
	 *            Contexto de la aplicación
	 * @param name
	 *            Nombre del recurso a buscar
	 * @return Retorna el identificador unico del recurso
	 */
	public static int getDrawableIdFromString(Context context, String name) {

		return context.getResources().getIdentifier(name, "drawable",
				context.getPackageName());

	}
	
	
	/**
	 * Metodo que retrona un arraylist de usuario a partir de un arreglo de
	 * usuarios
	 * 
	 * @param usuarios
	 *            Arreglo de usuarios a procesar
	 * @return Retorna un ArrayList con los usuarios ingresados
	 */
	public static ArrayList<UsuarioDTO> getArrayListUsuarioDTO(
			UsuarioDTO[] usuarios) {
		ArrayList<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
		for (UsuarioDTO usuario : usuarios) {
			lista.add(usuario);
		}
		return lista;
	}

	/**
	 * Metodo que retrona un arraylist de sitios a partir de un arreglo de
	 * sitios
	 * 
	 * @param sitios
	 *            Arreglo de sitios a procesar
	 * @return Retorna un ArrayList con los sitios ingresados
	 */
	public static ArrayList<SitioDTO> getArrayListSitioDTO(SitioDTO[] sitios) {
		ArrayList<SitioDTO> lista = new ArrayList<SitioDTO>();
		for (SitioDTO sitio : sitios) {
			lista.add(sitio);
		}
		return lista;
	}

	/**
	 * Metodo que retorna un array list de categorias a partir de un arreglo de
	 * categorias
	 * 
	 * @param categorias
	 *            Arreglos de categorias a procesar
	 * @return Retorna un ArrayList con las categorias ingresadas
	 */
	public static ArrayList<CategoriaDTO> getArrayListCategoriaDTO(
			CategoriaDTO[] categorias) {
		ArrayList<CategoriaDTO> lista = new ArrayList<CategoriaDTO>();
		for (CategoriaDTO categoria : categorias) {
			lista.add(categoria);
		}
		return lista;
	}

	

}
