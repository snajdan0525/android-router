package com.snalopainen.android_router_sample;

import com.snalopainen.android_router.ComponentRouter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((Button) findViewById(R.id.button_first))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						ComponentRouter.getSingleRouter().open("users/yh0525",
								MainActivity.this);
					}
				});

		((Button) findViewById(R.id.button_second))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						ComponentRouter.getSingleRouter().open(
								"users/new/sNalopainen/94303",
								MainActivity.this);
					}
				});
	}
}
