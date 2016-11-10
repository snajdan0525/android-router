package com.snalopainen.android_router;

import android.content.Context;

public interface RouterCallBack {
	void notFound(Context context, String uri);

	void beforeOpen(Context context, String uri);

	void afterOpen(Context context, String uri);

	void error(Context context, String uri, Throwable e);
}
