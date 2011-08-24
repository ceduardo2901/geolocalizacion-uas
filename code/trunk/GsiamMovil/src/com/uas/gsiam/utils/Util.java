package com.uas.gsiam.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class Util {
	

	public static boolean validaMail (String email){
		
		boolean result = true;
		
		Matcher match = Pattern.compile(Constantes.REGEX_EMAIL).matcher(email);
		if (!match.matches()) {
			result = false;
		} 
		
		return result;
		
	}
	
	
	
	public static void showProgressDialog(Context context, ProgressDialog progressDialog, String mensaje) {
		
		progressDialog = ProgressDialog.show(context, "",
				mensaje, true);
	}

	
	
	public static void dismissProgressDialog(ProgressDialog progressDialog) {
		
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

}
