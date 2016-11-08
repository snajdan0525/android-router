package com.snalopainen.android_router_sample;

import com.snalopainen.android_router.ComponentRouter;

import android.app.Application;

public class RouterApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		// Set the global context
		ComponentRouter.getSingleRouter().setContext(getApplicationContext());
		// Symbol-esque params are passed as intent extras to the activities
		ComponentRouter.getSingleRouter().mapping("users/:id",
				FirstActivity.class);
		ComponentRouter.getSingleRouter().mapping("users/new/:name/:zip",
				SecondActivity.class);
	}
}
