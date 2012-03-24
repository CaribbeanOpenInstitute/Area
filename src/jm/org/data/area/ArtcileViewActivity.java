package jm.org.data.area;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArtcileViewActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);
	}
	
	private class ArticleViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}

}
