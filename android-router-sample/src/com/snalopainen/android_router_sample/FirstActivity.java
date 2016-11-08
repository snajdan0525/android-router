package com.snalopainen.android_router_sample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_activity);
		Bundle intentExtras = getIntent().getExtras();
		// Note this extra, and how it corresponds to the ":id" above
		String userId = (String) intentExtras.get("id");
		TextView v = ((TextView) findViewById(R.id.tv_first));
		v.setText("id: "+userId);
	}
}