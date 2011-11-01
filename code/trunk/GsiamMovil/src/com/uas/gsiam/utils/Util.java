package com.uas.gsiam.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class Util {
	
	private static ProgressDialog progressDialog;

	public static boolean validaMail (String email){
		
		boolean result = true;
		
		Matcher match = Pattern.compile(Constantes.REGEX_EMAIL).matcher(email);
		if (!match.matches()) {
			result = false;
		} 
		
		return result;
		
	}
	
	
	
	public static void showProgressDialog(Context context, String mensaje) {
		synchronized (Util.class) {
		progressDialog = ProgressDialog.show(context, "",
				mensaje, true);
		}
	}

	
	
	public static void dismissProgressDialog() {
		
		if (progressDialog != null) {

			progressDialog.dismiss();
		}
	}
	
	
	
	public static void showAlertDialogConfirm (Context contexto, String titulo, String mensaje) {
		
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
	
	public static void showAlertDialogOk (Context contexto, String titulo, String mensaje) {
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(contexto);
		
		dialog.setTitle(titulo); 
		dialog.setMessage(mensaje);
		dialog.setCancelable(false);
		dialog.setIcon(android.R.drawable.ic_dialog_info);  
		dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
		    	   
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.cancel();
		           }
		       });
		dialog.show(); 
		
		
	}
	
	public static void showAlertDialog (Context contexto, String titulo, String mensaje){
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(contexto);  
		dialog.setTitle(titulo);  
		dialog.setMessage(mensaje);  
		dialog.setIcon(android.R.drawable.ic_dialog_info);  
		dialog.show();  

	}
	   	
	
	public static void showToast(Context contexto, String mensaje){
		
		Toast.makeText(contexto, mensaje,
				Toast.LENGTH_LONG).show();
		
	}

	
	public static byte[] BitmapToArray(BitmapDrawable drawable) throws IOException {

		Bitmap bitmap = drawable.getBitmap();
		int size = bitmap.getWidth() * bitmap.getHeight() * 4;
		ByteArrayOutputStream out = new ByteArrayOutputStream(size);

		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		out.flush();
		out.close();
		return out.toByteArray();

	}		
	
	
	public static Bitmap ArrayToBitmap(byte[] byteArray) {

		return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

	}	
	
	public static Drawable BitmapToDrawable(Bitmap bitmap){

		return new BitmapDrawable(bitmap);

	}
	
	
	public static ArrayList<UsuarioDTO> getArrayListUsuarioDTO(UsuarioDTO[] usuarios){
        ArrayList<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
        for(UsuarioDTO usuario : usuarios){
                lista.add(usuario);
        }
        return lista;
	}
	
	public static ArrayList<SitioDTO> getArrayListSitioDTO(SitioDTO[] sitios) {
		ArrayList<SitioDTO> lista = new ArrayList<SitioDTO>();
		for (SitioDTO sitio : sitios) {
			lista.add(sitio);
		}
		return lista;
	}

	
	
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
	
	
}
