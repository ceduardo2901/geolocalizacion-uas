package com.uas.gsiam.ui;

import com.uas.gsiam.adapter.SitiosAdapter;
import com.uas.gsiam.adapter.UsuarioAdapter;

import greendroid.widget.SegmentedAdapter;
import greendroid.widget.SegmentedHost;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



public class SolicitudesActivity extends Activity{

	private final Handler mHandler = new Handler();
    private PeopleSegmentedAdapter mAdapter;
  
    
    static final String[] PAISES = new String[] {
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina"};
    
    static final String[] COLORES = new String[] {
        "ROjo", "Celeste", "Azukl", "Blanco", "Rosa",
        "Amarillo"};
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        	/* 
        	ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, PAISES);
        	 
        	lv = (ListView)findViewById(R.id.listaSolicitudes);
        	 
        	lv.setAdapter(adaptador);
        */

        
        setContentView(R.layout.solicitudes_amigos_tab);
 
        
        SegmentedHost segmentedHost = (SegmentedHost) findViewById(R.id.segmented_host);
 
        mAdapter = new PeopleSegmentedAdapter();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.mReverse = true;
                mAdapter.notifyDataSetChanged();
            }
        }, 4000);
 
        segmentedHost.setAdapter(mAdapter);
    }
 
    private class PeopleSegmentedAdapter extends SegmentedAdapter {
 
        public boolean mReverse = false;
 
        @Override
        public View getView(int position, ViewGroup parent) {
 
            TextView textView = new TextView(SolicitudesActivity.this);
            textView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            textView.setGravity(Gravity.CENTER);
 
            final int color = getColor(mReverse ? ((getCount() - 1) - position) : position);
            textView.setBackgroundColor(color);
            //textView.setTextColor(ColorUtils.negativeColor(color));
            textView.setTextColor(Color.RED);
            
            // It's not necessary to compute the "reversed" position as the
            // getSegmentTitle will do it automatically
            textView.setText("lalala" + getSegmentTitle(position));
            
 
            return textView;
        }
 
        @Override
        public int getCount() {
            return 2;
        }
 
        @Override
        public String getSegmentTitle(int position) {
 
            switch (mReverse ? ((getCount() - 1) - position) : position) {
                case 0:
                    return "Pendientes";
                case 1:
                    return "Enviadas";
                
            }
 
            return null;
        }
 
        private int getColor(int position) {
            switch (position) {
                case 0:
                    return Color.BLUE;
                case 1:
                    return Color.YELLOW;
               
            }
            return Color.TRANSPARENT;
        }
        
        
        
        
        
        
    }

	  
	  
}