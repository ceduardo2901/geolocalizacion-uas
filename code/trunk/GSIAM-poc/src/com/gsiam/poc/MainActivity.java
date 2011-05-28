package com.gsiam.poc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener{

	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.geoloc);          
            Button button = (Button) findViewById(R.id.findDetail);
            button.setOnClickListener(this);
            
    }



    public void onClick(View v) {
            Bundle bundle = new Bundle();
            bundle.putString("postalCode", ((EditText) findViewById(R.id.PostalCodeText)).getText().toString());
            bundle.putString("countryCode", ((EditText) findViewById(R.id.CountryCodeText)).getText().toString());
            bundle.putString("radius", ((EditText) findViewById(R.id.RadiusText)).getText().toString());
            Intent intent = new Intent();
            intent.setClass(this, GeolocationPoc.class);
        intent.putExtras(bundle);
            startActivity(intent);          
    }

}
