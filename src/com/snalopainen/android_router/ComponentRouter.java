package com.snalopainen.android_router;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ComponentRouter {
	private static ComponentRouter mInstance = new ComponentRouter();
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
		this.open(url, null, this.mContext, null);
	}

	public void open(String url, RouterCallBack callback) {
		this.open(url, null, this.mContext, callback);
	}

	public void open(String url, Bundle extra) {
		this.open(url, extra, this.mContext, null);
	}

	public void open(String url, Bundle extra, RouterCallBack callback) {
		this.open(url, extra, this.mContext, callback);
	}

	public void open(String url, Context context) {
		this.open(url, null, context, null);
	}

	public void open(String url, Context context, RouterCallBack callback) {
		this.open(url, null, context, callback);
	}

	public void open(String url, Bundle extras, Context context,
			RouterCallBack callback) {
		if (context == null) {
			throw new ContextNotProvided(
					"You need to supply a context for Router "
							+ this.toString());
		}
		if( callback!=null ){
			callback.beforeOpen(context, url);
		}
		RouterParams routerParams = parseParamsFromUrl(url);

		/*
		 * 根据params生成intent
		 */
		Intent intent = makeIntent(context, routerParams);
		if (intent == null)
			return;
		if (extras != null) {
			intent.putExtras(extras);
		}
		context.startActivity(intent);// 调用此函数前，一定要add
										// Intent.FLAG_ACTIVITY_NEW_TASK
		
		if(callback!=null){
			callback.afterOpen(context, url);
		}
	}

	private Intent makeIntent(Context context, RouterParams routerParams) {
		RouterOption option = routerParams._routerOption;

		Intent intent = makeIntent(routerParams);
		intent.setClass(context, option.getOpenClass());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		return intent;
	}

	private Intent makeIntent(RouterParams routerParams) {
		Map<String, String> params = routerParams._parms;
		Intent intent = new Intent();
		for (Entry<String, String> entry : params.entrySet()) {
			intent.putExtra(entry.getKey(), entry.getValue());
		}
		return intent;
	}

	private RouterParams parseParamsFromUrl(String url) {
		if (cachedTable.get(url) != null) {
			return cachedTable.get(url);
		}
		String[] givenSegments = url.split("/");
		RouterParams routerParams = null;
		for (Entry entry : this.routerTable.entrySet()) {
			String routerUrl = (String) entry.getKey();
			RouterOption routerOption = (RouterOption) entry.getValue();
			String[] routerSegments = routerUrl.split("/");

			Map<String, String> params = getParamsMap(givenSegments,
					routerSegments);
			if (params == null)
				continue;
			routerParams = new RouterParams();
			routerParams._routerOption = routerOption;
			routerParams._parms = params;
			this.cachedTable.put(url, routerParams);
			break;
		}
		return routerParams;
	}

	private Map<String, String> getParamsMap(String[] givenSegments,
			String[] routerSegments) {
		Map<String, String> params = new HashMap<String, String>();
		for (int index = 0; index < routerSegments.length; index++) {
			String givenSegment = givenSegments[index];
			String routerSegment = routerSegments[index];
			if (routerSegment.charAt(0) == ':') {
				String paramsKey = routerSegment.substring(1,
						routerSegment.length());
				String paramsValue = givenSegment;
				params.put(paramsKey, paramsValue);
				continue;
			}
			if (!givenSegment.equals(routerSegment))
				return null;
		}
		return params;
	}
}
