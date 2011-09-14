package com.uas.gsiam.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.EditarUsuarioServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;

import com.uas.gsiam.utils.Util;

public class EditarUsuarioActivity extends Activity {

	protected String email;
	protected String nombre;
	protected Byte [] array;
	protected UsuarioDTO userLogin;

	protected EditText nombreTxt;
	protected EditText emailTxt;
	protected ImageView avatar;
	protected Button cambiarAvatarBtn;
	protected Button editarPerfilBtn;
	protected IntentFilter editarPerfilFiltro;
	protected ProgressDialog progressDialog;
	
	
	protected String path = "";
	
	protected static String TAG = "EditarPerfilActivity";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.editarperfil);
		
		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		cambiarAvatarBtn = (Button) findViewById(R.id.cambiarAvatarBtn);  
		editarPerfilBtn = (Button) findViewById(R.id.editarPerfilBtn);  
		avatar = (ImageView) findViewById(R.id.avatar);
		
		ApplicationController app = ((ApplicationController)getApplicationContext());
		userLogin = app.getUserLogin();
	    
	    nombreTxt.setText(userLogin.getNombre());
	    emailTxt.setText(userLogin.getEmail());
	  
	    
	    if (userLogin.getAvatar() != null)
	    	avatar.setImageBitmap(Util.ArrayToBitmap(userLogin.getAvatar()));
	    
	    editarPerfilFiltro = new IntentFilter(Constantes.EDITAR_USUARIO_FILTRO_ACTION);
		
	}
		
		
		

	
	protected void onResume() {
		 super.onResume();
		 registerReceiver(receiverEditarUsuario, editarPerfilFiltro);
	 }
	
	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiverEditarUsuario);
	}
	 
	
	public void cambiarAvatar(View v) {
		
		final CharSequence[] items = {"Camara", "Galeria"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Seleccionar una acción");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
		        
		        if (item == 0){
		        	getCamara();
		        }
		        else{
		        	
		        	getGaleria();
		        }
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
	}
	
	
	private void getCamara(){
		
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		Date ahora = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("ddMMyy-hhmmss");
	    String diaHora = formateador.format(ahora);
	        
	    path = Environment.getExternalStorageDirectory() + "/" + diaHora + ".jpg";
	    
	    Uri output = Uri.fromFile(new File(path));
	    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);
	    
		startActivityForResult(cameraIntent, Constantes.REQUEST_CAMERA);
	}
	
	
	private void getGaleria(){
		
		Intent selectIntent = new Intent(Intent.ACTION_PICK) ;
		selectIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  
		MediaStore.Images.Media.CONTENT_TYPE) ;
		startActivityForResult(selectIntent,Constantes.REQUEST_SELECT_PHOTO) ;
	
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		Bitmap myBitmap;

		switch (requestCode){
		
			case Constantes.REQUEST_CAMERA:
				if( resultCode != 0 ){
	
					myBitmap = BitmapFactory.decodeFile(path);
					avatar.setImageBitmap(myBitmap);	
					almacenarEnMemoria();
					
				}
	
				break ;
			case Constantes.REQUEST_SELECT_PHOTO:
				if( resultCode != 0 ){
	
					Uri selectedImage = data.getData();
					InputStream is;
	
					try {
						is = getContentResolver().openInputStream(selectedImage);
	
						BufferedInputStream bis = new BufferedInputStream(is);
						myBitmap = BitmapFactory.decodeStream(bis);     
	
						avatar.setImageBitmap(myBitmap);	
	
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
	
					break ;
				}
		}

	}
	
	
	private void almacenarEnMemoria (){
		new MediaScannerConnectionClient() {
			private MediaScannerConnection msc = null; {
				msc = new MediaScannerConnection(getApplicationContext(), this); msc.connect();
			}
			public void onMediaScannerConnected() { 
				msc.scanFile(path, null);
			}
			public void onScanCompleted(String path, Uri uri) { 
				msc.disconnect();
			} 
		};	
	}
	
	
	
	public void editarUsuario(View v) {


		nombre = nombreTxt.getText().toString().trim();
		email = emailTxt.getText().toString().trim();
		
		
		if (!Util.validaMail(email)) {
			Util.showToast(v.getContext(), Constantes.MSG_ERROR_MAIL);
					
		} 
		else {
			
			UsuarioDTO usuario = new UsuarioDTO();
			usuario.setNombre(nombre);
			usuario.setEmail(email);
			
			usuario.setPassword(userLogin.getPassword());
			usuario.setId(userLogin.getId());
			
			Drawable drawable= avatar.getDrawable();
			
			byte[] arrayBytes = null;
			try {
				arrayBytes = Util.BitmapToArray((BitmapDrawable) drawable);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			usuario.setAvatar(arrayBytes);
			
			Bundle bundle = new Bundle();
			bundle.putSerializable("usuario", usuario);
			
			Intent intent = new Intent(this,EditarUsuarioServicio.class);
			intent.putExtras(bundle);
			startService(intent);
			
			Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

		}

	}

	
	
	
	protected BroadcastReceiver receiverEditarUsuario = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(TAG, "onReceive");
	    	Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");
		
			Util.dismissProgressDialog();
			
	    	if (respuesta.equals(Constantes.RETURN_OK)){
	    		
	    		Util.showToast(context, Constantes.MSG_USUARIO_CREADO_OK);
	    		
	    		UsuarioDTO usuarioEditado = (UsuarioDTO) bundle.getSerializable("usuario");
	    		ApplicationController app = ((ApplicationController)getApplicationContext());
				app.setUserLogin(usuarioEditado);
	    		
				Intent actividadPrincipal = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(actividadPrincipal);
				
			}
			else{
				Util.showToast(context, respuesta);
			}
	    	
			
	    }
	  };
  

}
