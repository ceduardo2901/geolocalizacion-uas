package com.uas.gsiam.ui;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class PublicarActivity extends Activity implements OnRatingBarChangeListener{

	protected static final String TAG = "PublicarActivity";
	private EditText comentario;
	private CheckBox comentarFaceBook;
	private RatingBar puntaje;
	private AsyncFacebookRunner mAsyncRunner;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publicar);
		
		puntaje = (RatingBar) findViewById(R.id.puntajeId);
		comentario = (EditText) findViewById(R.id.txtComentarioId);
		comentarFaceBook = (CheckBox) findViewById(R.id.cheBoxFaceBook);
		
		puntaje.setOnRatingBarChangeListener(this);
		
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		
		puntaje.setRating(rating);
	}
	
	
	public void publicar(View v) {
		
		if(comentarFaceBook.isChecked()){
			Facebook facebook = new Facebook(getString(R.string.facebook_app_id));
			mAsyncRunner = new AsyncFacebookRunner(facebook);
			
		}
		
		
	}
	
}
