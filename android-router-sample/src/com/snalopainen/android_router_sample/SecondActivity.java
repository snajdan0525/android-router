package com.snalopainen.android_router_sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		Bundle intentExtras = getIntent().getExtras();
		// Corresponds to the ":name" above
		String name = (String) intentExtras.get("name");
		// Corresponds to the ":zip" above
		String zip = (String) intentExtras.get("zip");
		TextView v = ((TextView) findViewById(R.id.tv_second));
		v.setText("name :" + name + " " + "zip: " + zip);
	}
}
