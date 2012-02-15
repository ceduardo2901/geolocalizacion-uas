package com.uas.gsiam.ui;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
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
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.PublicarServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SessionStore;
import com.uas.gsiam.utils.Util;

/**
 * Esta activity es la encargada de manejar una publicacion en el sistema. Se
 * puede ingresar un comentario, un puntaje del 1 al 5 y una foto. Tambien se da
 * la posibilidad de compartir estos datos en facebook
 * 
 * @author Antonio
 * 
 */
public class PublicarActivity extends GDActivity implements
		OnRatingBarChangeListener {

	protected static final String TAG = "PublicarActivity";
	private static final int COMPARTIR = 1;
	private EditText comentario;
	private CheckBox comentarFaceBook;
	private RatingBar puntaje;
	private AsyncFacebookRunner mAsyncRunner;
	private Facebook facebook;
	private IntentFilter publicarFiltro;
	private ImageButton fotoButton;
	private SitioDTO sitio;
	private UsuarioDTO usuario;
	private ApplicationController app;
	private static final int RESULT = 1001;
	private String nombre;
	// private ImageView fotoPub;
	private byte[] foto;
	private String APP_ID;
	private PublicacionDTO publicacion;
	
	
	protected String path = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.publicar);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		puntaje = (RatingBar) findViewById(R.id.puntajeId);
		comentario = (EditText) findViewById(R.id.txtComentarioId);
		comentarFaceBook = (CheckBox) findViewById(R.id.cheBoxFaceBook);
		// fotoPub = (ImageView) findViewById(R.id.fotoPubId);
		fotoButton = (ImageButton) findViewById(R.id.fotoPubId);
		puntaje.setOnRatingBarChangeListener(this);
		APP_ID = getString(R.string.facebook_app_id);
		facebook = new Facebook(APP_ID);
		sitio = (SitioDTO) getIntent().getSerializableExtra("sitio");
		publicarFiltro = new IntentFilter(
				Constantes.CREAR_PUBLICACION_FILTRO_ACTION);

		app = ((ApplicationController) getApplicationContext());

		inicializarActionBar();

	}
	
	private void inicializarActionBar() {

		getActionBar().setTitle(getString(R.string.gsiam_publicar));
	}

	@Override
	public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
		switch (item.getItemId()) {
		case COMPARTIR:
			compartir();
			break;

		default:
			return super.onHandleActionBarItemClick(item, position);
		}

		return true;

	}

	protected void onResume() {
		super.onResume();
		registerReceiver(receiverPublicar, publicarFiltro);
		usuario = app.getUserLogin();

	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverPublicar);

	}

	
	final Context ctx = this;
	
	/**
	 * Metodo que escucha la respuesta del servicio de
	 * {@link PublicarServicio} 
	 */
	protected BroadcastReceiver receiverPublicar = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "onReceive");
			int respuesta = intent.getIntExtra("respuesta", 0);
			Log.i(TAG, "Respuesta = "+ respuesta);
			String error = intent.getStringExtra("error");
			Util.dismissProgressDialog();
			if (error != null) {
				Util.showToast(getApplicationContext(), error);
			} else {
				Util.showToast(getApplicationContext(), Constantes.MSG_PUBLICACION_CREADA);
				
		
				publicacion.setIdPublicacion(respuesta);
				String nombreArchivo =  "publicacion_" + publicacion.getIdPublicacion() + ".jpg"; 
				Util.guardarImagenMemoria(ctx, publicacion.getFoto(), nombreArchivo);
				publicacion.setFoto(null);
				publicacion.setFotoRuta(nombreArchivo);
				
				volverPublicaciones();
			}

		}
	};

	private void volverPublicaciones() {

		Intent volver = new Intent(this, SitioTabActivity.class);
		sitio.getPublicaciones().add(publicacion);
		volver.putExtra("sitio", sitio);
		startActivity(volver);
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {

		puntaje.setRating(rating);
	}

	public void publicar(View v) {

		if (comentario.getText().toString().trim().length() == 0) {
			Util.showToast(this, "Debe ingresar \nun comentario");
		} else {
			publicacion = new PublicacionDTO();
			if (comentarFaceBook.isChecked()) {
				comentarFacebook();
			} else {
				publicacion.setComentario(comentario.getText().toString()
						.trim());
				publicacion.setIdSitio(sitio.getIdSitio());
				publicacion.setIdUsuario(usuario.getId());
				publicacion.setPuntaje(puntaje.getRating());
				publicacion.setFecha(new Date());
				publicacion.setNombreUsuario(usuario.getNombre());
				publicacion.setFoto(foto);
			
				Intent intent = new Intent(this, PublicarServicio.class);
				intent.putExtra("publicacion", publicacion);
				Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

				startService(intent);
			}

		}

	}

	public void cargarFoto(View v) {

		final CharSequence[] items = getResources().getStringArray(
				R.array.cargarFoto);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.seleccionar));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

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

	private void getGaleria() {

		Intent selectIntent = new Intent(Intent.ACTION_PICK);
		selectIntent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				MediaStore.Images.Media.CONTENT_TYPE);
		startActivityForResult(selectIntent, Constantes.REQUEST_SELECT_PHOTO);

	}

	public void compartir() {

		Intent compartirIntent = new Intent(Intent.ACTION_SEND);
		compartirIntent.setType("plain/text");
		compartirIntent.putExtra(Intent.EXTRA_TEXT, "Mira el sitio " + nombre
				+ " en Gsiam");

		startActivityForResult(Intent.createChooser(compartirIntent, "Title:"),
				RESULT);

	}

	private void comentarFacebook() {
		setResult(RESULT_OK);

		this.mAsyncRunner = new AsyncFacebookRunner(facebook);
		SessionStore.restore(facebook, getApplicationContext(), APP_ID);

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					getResources().getStringArray(R.array.permissions),
					new FaceBookDialog());
		} else {
			postOnWall(comentario.getText().toString());
		}

	}

	public void postOnWall(String msg) {
	
		try {
			String response;

			Bundle parameters = new Bundle();
			if (fotoButton.getDrawable() != null) {

				parameters.putByteArray("picture", Util
						.bitmapToArray((BitmapDrawable) fotoButton
								.getDrawable()));
				parameters.putString("caption", msg);
				response = facebook.request("me/photos", parameters, "POST");

			} else {
				parameters.putString("message", msg);
				parameters.putString("description", "test test test");

				response = facebook.request("me/feed", parameters, "POST");
			}

			publicacion = new PublicacionDTO();
			publicacion.setComentario(comentario.getText().toString().trim());
			publicacion.setIdSitio(sitio.getIdSitio());
			publicacion.setIdUsuario(usuario.getId());
			publicacion.setPuntaje(puntaje.getRating());
			publicacion.setFecha(new Date());
			publicacion.setNombreUsuario(usuario.getNombre());
			publicacion.setFoto(foto);

			Intent intent = new Intent(this, PublicarServicio.class);
			intent.putExtra("publicacion", publicacion);
			Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

			startService(intent);

			if (response == null) {
				Log.i(TAG, "error al comentar en facebook");
			}

		} catch (Exception e) {
			Log.v("Error", "Blank response");
			e.printStackTrace();
		}
	}

	public SitioDTO getSitio() {
		return sitio;
	}

	public void setSitio(SitioDTO sitio) {
		this.sitio = sitio;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Bitmap myBitmap;

		switch (requestCode) {

		case RESULT:
			Util.showToast(this, Constantes.MSG_MENSAJE_ENVIADO);
			break;
		case Constantes.REQUEST_CAMERA:
			if (resultCode != 0) {

				myBitmap = BitmapFactory.decodeFile(path);

				if (myBitmap.getHeight() > 1000 || myBitmap.getWidth() > 1000 ){
					myBitmap = Util.getResizedBitmap(myBitmap,  myBitmap.getWidth()/2, myBitmap.getHeight()/2);
				}
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				myBitmap.compress(CompressFormat.JPEG, 95, out);
				
				foto = out.toByteArray();
				fotoButton.setImageBitmap(myBitmap);
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
					
					if (myBitmap.getHeight() > 1000 || myBitmap.getWidth() > 1000 ){
						myBitmap = Util.getResizedBitmap(myBitmap,  myBitmap.getWidth()/2, myBitmap.getHeight()/2);
					}
					
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					myBitmap.compress(CompressFormat.JPEG, 95, out);
					
					foto = out.toByteArray();
					fotoButton.setImageBitmap(myBitmap);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		default:
			facebook.authorizeCallback(requestCode, resultCode, data);

			break;
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

	public class FaceBookDialog implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
			SessionStore.save(facebook, getApplicationContext(), APP_ID);
			postOnWall(comentario.getText().toString());

		}

		@Override
		public void onFacebookError(FacebookError e) {
			Log.e(TAG, "Error al autenticase en facebook");

		}

		@Override
		public void onError(DialogError e) {
			Log.e(TAG, "Error al autenticase en facebook");

		}

		@Override
		public void onCancel() {
		
		}

	}

}
