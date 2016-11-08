package com.snalopainen.android_router;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class ComponentRouter {
	private static ComponentRouter mInstance;
	private Context mContext;
	private final Map<String, RouterOption> routerTable = new HashMap<String, RouterOption>();
	private final Map<String, RouterParams> cachedTable = new HashMap<String, RouterParams>();

	/**
	 * 
	 * @return
	 */
	public static ComponentRouter getSingleRouter() {
		return mInstance;
	}

	/**
	 * 
	 * @param context
	 */
	public void setContext(Context context) {
		this.mContext = context;
	}

	public void mapping(String url, Class<? extends Activity> _class) {
		if (url.isEmpty())
			return;
		RouterOption option = new RouterOption();
		option.setOpenClass(_class);
		routerTable.put(url, option);
	}

	public void open(String url) {
		this.open(url, null, this.mContext);
	}

	public void open(String url, Bundle extra) {
		this.open(url, extra, this.mContext);
	}

	public void open(String url, Context context) {
		this.open(url, null, context);
	}

	public void open(String url, Bundle extra, Context context) {
		if (context == null) {
			throw new ContextNotProvided(
					"You need to supply a context for Router "
							+ this.toString());
		}
	}

	private RouterParams parseParamsFromUrl(String url) {
		if (cachedTable.get(url) != null) {
			return cachedTable.get(url);
		}
		String[] givenParams = url.split("/");
		for (Entry entry : this.routerTable.entrySet()) {
			String routerUrl = (String) entry.getKey();
			RouterOption routerOption = (RouterOption) entry.getValue();
			String[] routerParams = routerUrl.split("/");

			Map<String, String> params = getParamsMap(givenParams, routerParams);

		}
		return null;
	}

	private Map<String, String> getParamsMap(String[] givenParams,
			String[] routerParams) {
		Map<String, String> params = new HashMap<String, String>();
		// ["users", "42"]
		// ["users", ":id"]
		for (int index = 0; index < routerParams.length; index++) {
			String givenSegment = givenParams[index];
			String routerSegment = routerParams[index];
			if( routerSegment.charAt(0) == ':'){
				String paramsKey = routerSegment.substring(1,routerSegment.length());
				String paramsValue = givenSegment;
				params.put(paramsKey, paramsValue);
				continue;
			}
			if( !givenSegment.equals(routerSegment) )
				return null;
			
		}
		return null;
	}
}
