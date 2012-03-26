package jm.org.data.area;

import static jm.org.data.area.DBConstants.BING_TITLE;
import static jm.org.data.area.DBConstants.BING_URL;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import static jm.org.data.area.DBConstants.IDS_SEARCH_RESULTS;
import static jm.org.data.area.DBConstants.IDS_VIEW_DATE;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleViewFragment extends Fragment {
	private WebView articleWebView;
	
	public static final String TAG = ArticleViewFragment.class.getSimpleName();
		
	/*	
	// Columns unique to the BING Results table
	public static final String BING_TITLE		= "title"		;
	public static final String BING_DESC		= "description"	;
	public static final String BING_URL			= "result_url"	;
	public static final String BING_DISP_URL	= "display_url"	;
	public static final String BING_DATE_TIME	= "datetime"	;
	*/
	
	private String bingTitle;
	private String bingUrl;	
	//private String bingDesc;
	//private String bingDispUrl;
	//private String bingDateTime;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "ArticleViewFragment");
				
		// To retrieve the information from the activity that called this intent 	
		final Bundle indicatorBundle = getActivity().getIntent().getExtras();
		bingTitle = indicatorBundle.getString(BING_TITLE);
		bingUrl = indicatorBundle.getString(BING_URL);
		
		Log.d(TAG, String.format("BIng Title ID: %s at URL %s", bingTitle, bingUrl));
				
		
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		articleWebView = (WebView)getView().findViewById(R.id.articleWebView);
		showWebArticle(bingUrl);
		
		articleWebView.setWebViewClient(new ArticleViewClient()); 
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }*/
		View view = inflater.inflate(R.layout.article_view_frag, container, false);
		return view;
	}
	
	public void showWebArticle(String articleUrl) {
		Log.d(TAG, articleUrl);
		//TextView view = (TextView) getView().findViewById(R.id.txtArticleUrl);
		//view.setText(articleUrl);
		//Receive the actual URL from the parent intent
		//articleWebView = (WebView)getView().findViewById(R.id.articleWebView);
		articleWebView.loadUrl(articleUrl);
		
		
		
	}
	
	/**
	 *Private class used to load prevent browser windows loading in external applications 
	 *Solution explained: http://developer.android.com/resources/tutorials/views/hello-webview.html
	 */
	private class ArticleViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}

}
