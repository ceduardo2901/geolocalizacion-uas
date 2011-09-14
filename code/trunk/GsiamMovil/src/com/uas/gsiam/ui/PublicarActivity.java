package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.servicios.CrearSitioServicio;
import com.uas.gsiam.servicios.PublicarServicio;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SessionEvents;
import com.uas.gsiam.utils.Util;
import com.uas.gsiam.utils.SessionEvents.AuthListener;

public class PublicarActivity extends Activity implements OnRatingBarChangeListener{

	protected static final String TAG = "PublicarActivity";
	private EditText comentario;
	private CheckBox comentarFaceBook;
	private RatingBar puntaje;
	private AsyncFacebookRunner mAsyncRunner;
	private Facebook facebook;
	private IntentFilter publicarFiltro;
	private String sitioId;
	private UsuarioDTO usuario;
	private ApplicationController app;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publicar);
		
		puntaje = (RatingBar) findViewById(R.id.puntajeId);
		comentario = (EditText) findViewById(R.id.txtComentarioId);
		comentarFaceBook = (CheckBox) findViewById(R.id.cheBoxFaceBook);
		
		puntaje.setOnRatingBarChangeListener(this);
		
		publicarFiltro = new IntentFilter(Constantes.CREAR_PUBLICACION_FILTRO_ACTION);
		
		 app = ((ApplicationController)getApplicationContext());
	    
		
	}
	
	protected void onResume(){
		super.onResume();
		registerReceiver(receiverPublicar, publicarFiltro);
		
		sitioId = getIntent().getStringExtra("sitioId");
		usuario = app.getUserLogin();
	}
	
	protected void onPause(){
		super.onPause();
		unregisterReceiver(receiverPublicar);
	}
	
	protected BroadcastReceiver receiverPublicar = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	Log.i(TAG, "onReceive");
	    	Bundle bundle = intent.getExtras();
			String respuesta = bundle.getString("respuesta");
		
			Util.dismissProgressDialog();
			comentarFacebook();
//	    	if (respuesta.equals(Constantes.RETURN_OK)){
//	    		
//	    		Util.showToast(context, Constantes.MSG_USUARIO_CREADO_OK);
//				Intent actividadPrincipal = new Intent(getApplicationContext(), SitiosActivity.class);
//				startActivity(actividadPrincipal);
//				
//			}
//			else{
//				Util.showToast(context, respuesta);
//			}
	    	
			
	    }
	  };
	  
	private void comentarFacebook() {
		if(comentarFaceBook.isChecked()){
			facebook = new Facebook(getString(R.string.facebook_app_id));
			
			facebook.authorize(this, getResources().getStringArray(R.array.permissions) ,new FaceBookDialog());
			mAsyncRunner = new AsyncFacebookRunner(facebook);
			
			//SessionEvents.addAuthListener(new SampleAuthListener());
			
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
		Bundle publicacion = new Bundle();
		publicacion.putSerializable("publicacion", publicar);
		Intent intent = new Intent(this,PublicarServicio.class);
		intent.putExtras(publicacion);
		startService(intent);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
				
		
	}
	
	public void postOnWall(String msg) {
        Log.d("Tests", "Testing graph API wall post");
         try {
                String response = facebook.request("me");
                Bundle parameters = new Bundle();
                parameters.putString("message", msg);
                parameters.putString("description", "test test test");
                response = facebook.request("me/feed", parameters, 
                        "POST");
                Log.d("Tests", "got response: " + response);
                if (response == null || response.equals("") || 
                        response.equals("false")) {
                   Log.v("Error", "Blank response");
                }
         } catch(Exception e) {
             e.printStackTrace();
         }
    }
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        facebook.authorizeCallback(requestCode, resultCode, data);
    }
	
	public class FaceBookDialog implements DialogListener {

		@Override
		public void onComplete(Bundle values) {
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
