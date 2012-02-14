package com.uas.gsiam.ui;

import greendroid.app.GDActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
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

/**
 * 
 * Esta activity permite modificar los datos del usuario, los datos modificables
 * son el nombre, email, password y la foto del perfil de usuario
 * 
 * @author Martin
 * 
 */
public class EditarUsuarioActivity extends GDActivity implements TextWatcher{

	protected String email;
	protected String nombre;
	protected String pass;
	protected Byte[] array;
	protected UsuarioDTO userLogin;
	private byte[] foto;
	protected EditText nombreTxt;
	protected EditText emailTxt;
	protected EditText passTxt;
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
		setActionBarContentView(R.layout.editar_perfil);

		nombreTxt = (EditText) findViewById(R.id.nombreTxt);
		emailTxt = (EditText) findViewById(R.id.emailTxt);
		passTxt = (EditText) findViewById(R.id.passTxt);
		cambiarAvatarBtn = (Button) findViewById(R.id.cambiarAvatarBtn);
		editarPerfilBtn = (Button) findViewById(R.id.editarPerfilBtn);
		avatar = (ImageView) findViewById(R.id.avatar);

		ApplicationController app = ((ApplicationController) getApplicationContext());
		userLogin = app.getUserLogin();

		nombreTxt.setText(userLogin.getNombre());
		emailTxt.setText(userLogin.getEmail());
		passTxt.setText(userLogin.getPassword());
		
		nombreTxt.addTextChangedListener(this);
		emailTxt.addTextChangedListener(this);
		passTxt.addTextChangedListener(this);

		if (userLogin.getAvatar() != null)
			avatar.setImageBitmap(Util.ArrayToBitmap(userLogin.getAvatar()));

		editarPerfilFiltro = new IntentFilter(
				Constantes.MODIFICAR_USUARIO_FILTRO_ACTION);

		inicializarBar();
	}

	private void inicializarBar() {

		getActionBar().setTitle("GSIAM - Perfil");

	}

	protected void onResume() {
		super.onResume();
		registerReceiver(receiverEditarUsuario, editarPerfilFiltro);
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverEditarUsuario);
	}

	
	@Override
	public void afterTextChanged(Editable s) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		if (!nombreTxt.getText().toString().equals("")
				&& !emailTxt.getText().toString().equals("")
				&& !passTxt.getText().toString().equals("")
				&& s.length() != 0) {
			editarPerfilBtn.setEnabled(true);
		} else {
			editarPerfilBtn.setEnabled(false);
		}
	}
	
	
	/**
	 * Accion que cambia la foto de perfil del usuario
	 * 
	 * @param v
	 */
	public void cambiarAvatar(View v) {

		final CharSequence[] items = { "Camara", "Galeria" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Seleccionar una acción");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				Toast.makeText(getApplicationContext(), items[item],
						Toast.LENGTH_SHORT).show();

				if (item == 0) {
					getCamara();
				} else {

					getGaleria();
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}

	/**
	 * Llama a la camara de fotos del telefono
	 */
	private void getCamara() {

		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("ddMMyy-hhmmss");
		String diaHora = formateador.format(ahora);

		path = Environment.getExternalStorageDirectory() + "/" + diaHora
				+ ".jpg";

		Uri output = Uri.fromFile(new File(path));
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, output);

		startActivityForResult(cameraIntent, Constantes.REQUEST_CAMERA);
	}

	/**
	 * abre la galeria de fotos del telefono para seleccionar una.
	 */
	private void getGaleria() {

		Intent selectIntent = new Intent(Intent.ACTION_PICK);
		selectIntent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(selectIntent, Constantes.REQUEST_SELECT_PHOTO);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Bitmap myBitmap;
		
		switch (requestCode) {

		case Constantes.REQUEST_CAMERA:
			if (resultCode != 0) {

				myBitmap = BitmapFactory.decodeFile(path);

				if (myBitmap.getHeight() > 500 || myBitmap.getWidth() > 500 ){
					myBitmap = Util.getResizedBitmap(myBitmap,  myBitmap.getWidth()/2, myBitmap.getHeight()/2);
				}
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				myBitmap.compress(CompressFormat.JPEG, 95, out);
				foto = out.toByteArray();
				avatar.setImageBitmap(myBitmap);
				almacenarEnMemoria();

			}

			break;
		case Constantes.REQUEST_SELECT_PHOTO:
			if (resultCode != 0) {

				Uri selectedImage = data.getData();
				InputStream is;

				try {
					is = getContentResolver().openInputStream(selectedImage);

					BufferedInputStream bis = new BufferedInputStream(is);
					myBitmap = BitmapFactory.decodeStream(bis);
					
					if (myBitmap.getHeight() > 500 || myBitmap.getWidth() > 500 ){
						myBitmap = Util.getResizedBitmap(myBitmap,  myBitmap.getWidth()/2, myBitmap.getHeight()/2);
					}
					
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					myBitmap.compress(CompressFormat.JPEG, 95, out);
					foto = out.toByteArray();

					avatar.setImageBitmap(myBitmap);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			}
		}

	}

	private void almacenarEnMemoria() {
		new MediaScannerConnectionClient() {
			private MediaScannerConnection msc = null;
			{
				msc = new MediaScannerConnection(getApplicationContext(), this);
				msc.connect();
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

		final Context ctx = v.getContext();
		
		nombre = nombreTxt.getText().toString().trim();
		email = emailTxt.getText().toString().trim();
		pass = passTxt.getText().toString().trim();

		if (!Util.validaMail(email)) {
			Util.showToast(ctx, Constantes.MSG_ERROR_MAIL);

		} else {

		
			AlertDialog.Builder dialogConfirmar = new AlertDialog.Builder(this);
			dialogConfirmar.setTitle("Confirmar"); 
			dialogConfirmar.setMessage(Constantes.MSG_CONFIRMAR_MODIFICACION_USUARIO);
			dialogConfirmar.setCancelable(true);
			dialogConfirmar.setIcon(android.R.drawable.ic_dialog_alert);  

			dialogConfirmar.setPositiveButton("Si", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {

					dialog.cancel();
					
					UsuarioDTO usuario = new UsuarioDTO();
					usuario.setNombre(nombre);
					usuario.setEmail(email);

					usuario.setPassword(pass);
					usuario.setId(userLogin.getId());

					if (foto != null)
						usuario.setAvatar(foto);
					else {

						Drawable drawable = avatar.getDrawable();

						byte[] arrayBytes = null;
						try {
							arrayBytes = Util.BitmapToArray((BitmapDrawable) drawable);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						Log.i(TAG, "getHeight " + Util.ArrayToBitmap(arrayBytes).getHeight() + "getWidth " +Util.ArrayToBitmap(arrayBytes).getWidth());
						usuario.setAvatar(arrayBytes);
						usuario.setAvatar(arrayBytes);
					}

					Intent intent = new Intent(ctx, EditarUsuarioServicio.class);
					intent.putExtra("usuario", usuario);
					startService(intent);

					Util.showProgressDialog(ctx, Constantes.MSG_ESPERA_GENERICO);

				
				}
			});

			dialogConfirmar.setNegativeButton("No", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int id) {

					dialog.cancel();
				}
			});

			dialogConfirmar.show();
		}

	}

	/**
	 * Recibe la respuesta del servicio que modifica los datos del usuario
	 */
	protected BroadcastReceiver receiverEditarUsuario = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			String error = intent.getStringExtra("error");
			String respuesta = intent.getStringExtra("respuesta");

			Util.dismissProgressDialog();

			if (error != null) {

				Util.showToast(context, error);
			} else {

				Util.showToast(context, respuesta);
				UsuarioDTO usuarioEditado = (UsuarioDTO) intent
						.getSerializableExtra("usuario");
				ApplicationController app = ((ApplicationController) getApplicationContext());
				app.setUserLogin(usuarioEditado);

				Intent intentPerfil = new Intent(getApplicationContext(),
						PerfilActivity.class);
				startActivity(intentPerfil);

			}

		}
	};

	

}
