package com.snalopainen.android_router;

import android.app.Activity;

public class RouterOption {
	private Class<? extends Activity> mOpenClass;

	public Class<? extends Activity> getOpenClass() {
		return mOpenClass;
	}

	public void setOpenClass(Class<? extends Activity> mOpenClass) {
		this.mOpenClass = mOpenClass;
	}

}
