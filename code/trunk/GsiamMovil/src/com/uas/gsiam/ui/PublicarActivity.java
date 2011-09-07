package com.uas.gsiam.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class PublicarActivity extends Activity implements OnRatingBarChangeListener{

	protected static final String TAG = "PublicarActivity";
	private EditText comentario;
	private CheckBox comentarFaceBook;
	private RatingBar puntaje;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publicar);
		
		puntaje = (RatingBar) findViewById(R.id.puntajeId);
		comentario = (EditText) findViewById(R.id.txtComentarioId);
		comentarFaceBook = (CheckBox) findViewById(R.id.cheBoxFaceBook);
		
	}

	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		
		
	}
	
	
}
