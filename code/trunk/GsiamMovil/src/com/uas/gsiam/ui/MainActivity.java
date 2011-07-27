package com.uas.gsiam.ui;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends AbstractMenuActivity { 

	//***************************************
    // AbstractMenuActivity methods
    //***************************************
	@Override
	protected String getDescription() {
		return getResources().getString(R.string.text_main);
	}

	@Override
	protected String[] getMenuItems() {
		return getResources().getStringArray(R.array.main_menu_items);
	}

	@Override
	protected OnItemClickListener getMenuOnItemClickListener() {
		return new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parentView, View childView, int position, long id) {
				Class<?> cls = null;
				switch (position) {
				case 0:
					//cls = HttpGetActivity.class;
					break;
				case 1:
					//cls = HttpPostActivity.class;
					break;
				case 2:
					//cls = TwitterActivity.class;
					break;
				case 3:
					cls = FaceBookLogin.class;
					break;
				default:
					break;
				}
				startActivity(new Intent(parentView.getContext(), cls));
			}
		};
	}
}
