package com.uas.gsiam.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import greendroid.app.GDActivity;
import greendroid.widget.ActionBarItem;
import greendroid.widget.ActionBarItem.Type;
import android.app.AlertDialog;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.PublicarServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SessionStore;
import com.uas.gsiam.utils.Util;

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
	private Integer sitioId;
	private UsuarioDTO usuario;
	private ApplicationController app;
	private static final int RESULT = 1001;
	private String nombre;
	private ImageView fotoPub;

	protected String path = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.publicar);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		puntaje = (RatingBar) findViewById(R.id.puntajeId);
		comentario = (EditText) findViewById(R.id.txtComentarioId);
		comentarFaceBook = (CheckBox) findViewById(R.id.cheBoxFaceBook);
		fotoPub = (ImageView) findViewById(R.id.fotoPubId);
		puntaje.setOnRatingBarChangeListener(this);

		publicarFiltro = new IntentFilter(
				Constantes.CREAR_PUBLICACION_FILTRO_ACTION);

		app = ((ApplicationController) getApplicationContext());

		inicializarActionBar();

	}

	private void inicializarActionBar() {
		addActionBarItem(Type.Share, COMPARTIR);

		setTitle(R.string.app_name);

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

		sitioId = getIntent().getIntExtra("sitioId", 0);
		nombre = getIntent().getStringExtra("nombre");
		usuario = app.getUserLogin();
	}

	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiverPublicar);
	}

	protected BroadcastReceiver receiverPublicar = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "onReceive");
			Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");

			
			comentarFacebook();
			Util.dismissProgressDialog();
			Util.showToast(getApplicationContext(), Constantes.MSG_PUBLICACION_CREADA);
		}
	};

	private void comentarFacebook() {
		if (comentarFaceBook.isChecked()) {

			facebook = new Facebook(getString(R.string.facebook_app_id));
			SessionStore.restore(facebook, this);
			if (!facebook.isSessionValid()) {
				facebook.authorize(this,
						getResources().getStringArray(R.array.permissions),
						new FaceBookDialog());
			} else {
				postOnWall(comentario.getText().toString());
			}

			// SessionEvents.addAuthListener(new SampleAuthListener());

		}
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {

		puntaje.setRating(rating);
	}

	public void publicar(View v) {
		PublicacionDTO publicar = new PublicacionDTO();
		publicar.setComentario(comentario.getText().toString());
		publicar.setIdSitio(new Integer(sitioId));
		publicar.setIdUsuario(usuario.getId());
		publicar.setPuntaje(puntaje.getRating());
		
		Drawable drawable= fotoPub.getDrawable();
		
		byte[] arrayBytes = null;
		try {
			arrayBytes = Util.BitmapToArray((BitmapDrawable) drawable);
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
		}
		if(fotoPub.getDrawable() != null){
			publicar.setFoto(arrayBytes);
		}
		Bundle publicacion = new Bundle();
		publicacion.putSerializable("publicacion", publicar);
		Intent intent = new Intent(this, PublicarServicio.class);
		intent.putExtras(publicacion);
		startService(intent);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);

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

	public void postOnWall(String msg) {
		Log.d("Tests", "Testing graph API wall post");
		try {
			String response = facebook.request("me");
			Bundle parameters = new Bundle();
			parameters.putString("message", msg);
			parameters.putString("description", "test test test");
			response = facebook.request("me/feed", parameters, "POST");
			Log.d("Tests", "got response: " + response);
			if (response == null || response.equals("")
					|| response.equals("false")) {
				Log.v("Error", "Blank response");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				fotoPub.setImageBitmap(myBitmap);
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

					fotoPub.setImageBitmap(myBitmap);

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
			SessionStore.save(facebook, getApplicationContext());
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
			// TODO Auto-generated method stub

		}

	}

}
