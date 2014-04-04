package com.web_server;

import com.web_server.model.Javascript_Bridge;
import com.web_service_server.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web_Activity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.web_view);

		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.setWebViewClient(new WebViewClient());
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setUseWideViewPort(true);
    webSettings.setLoadWithOverviewMode(true);
    webSettings.setAppCacheEnabled(false);
		webSettings.setJavaScriptEnabled(true);
		
		Javascript_Bridge bridge = new Javascript_Bridge(this);
		myWebView.addJavascriptInterface(bridge, "Javascript_Bridge");
		
		myWebView.loadUrl(get_url());
	}
	
	private String get_url() {
	  Bundle extra = getIntent().getExtras();
	  return extra.getString("url");
	}
}