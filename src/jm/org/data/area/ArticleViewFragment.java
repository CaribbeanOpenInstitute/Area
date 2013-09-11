package jm.org.data.area;

import static android.provider.BaseColumns._ID;
import static jm.org.data.area.AreaConstants.ARTICLES_DATA;
import static jm.org.data.area.DBConstants.BING_TITLE;
import static jm.org.data.area.DBConstants.BING_URL;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class ArticleViewFragment extends Fragment {
	private WebView articleWebView;

	public static final String TAG = ArticleViewFragment.class.getSimpleName();

	/*
	 * // Columns unique to the BING Results table public static final String
	 * BING_TITLE = "title" ; public static final String BING_DESC =
	 * "description" ; public static final String BING_URL = "result_url" ;
	 * public static final String BING_DISP_URL = "display_url" ; public static
	 * final String BING_DATE_TIME = "datetime" ;
	 */
	private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;
	private AreaApplication area;
	private String bingTitle;
	private String bingUrl;
	private int bingid;
	private ProgressDialog dialog;

	// private String bingDesc;
	// private String bingDispUrl;
	// private String bingDateTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "ArticleViewFragment");
		
		area 			= (AreaApplication) getActivity().getApplication();
		// To retrieve the information from the activity that called this intent
		final Bundle indicatorBundle = getActivity().getIntent().getExtras();
		bingTitle = indicatorBundle.getString(BING_TITLE);
		bingUrl = indicatorBundle.getString(BING_URL);
		bingid = indicatorBundle.getInt(_ID);

		Log.d(TAG, String.format("BIng Title ID: %s at URL %s", bingTitle,
				bingUrl));

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dialog = ProgressDialog.show(getActivity(), "",
				"Loading Reports Data. Please wait...", true);
		
		articleWebView = (WebView) getView().findViewById(R.id.articleWebView);
		showWebArticle(bingUrl);

		articleWebView.setWebViewClient(new ArticleViewClient());
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/*
		 * if (container == null) { // We have different layouts, and in one of
		 * them this // fragment's containing frame doesn't exist. The fragment
		 * // may still be created from its saved state, but there is // no
		 * reason to try to create its view hierarchy because it // won't be
		 * displayed. Note this is not needed -- we could // just run the code
		 * below, where we would create and return // the view hierarchy; it
		 * would just never be used. return null; }
		 */
		View view = inflater.inflate(R.layout.article_view_frag, container,
				false);
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater menuInflater = getActivity().getMenuInflater();
		menuInflater.inflate(R.menu.articles, menu);
		
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_reload:
			Toast.makeText(getActivity(), "Refreshing report list...",
					Toast.LENGTH_LONG).show();
			dialog = ProgressDialog.show(getActivity(), "",
					"Loading Article. Please wait...", true);
			articleWebView.reload();
			break;
		case R.id.menu_save:
			Toast.makeText(getActivity(), "Tapped Save", Toast.LENGTH_SHORT)
					.show();
			aBuilder = new AlertDialog.Builder(getActivity());
			
			aBuilder.setTitle("Save My Chart");
			aBuilder.setIcon(R.drawable.ic_launcher);
			
			aBuilder.setMessage("Save Current Article?")
						// Add action buttons
					.setPositiveButton(R.string.save_chart, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							//FROM_SAVED_DATA = { SAVED_DATA_ID, D_T_ID, ENTITY_ID};
							area.areaData.saveData(ARTICLES_DATA, "" + bingid);
							
							Toast.makeText(getActivity(), "Article " + bingTitle + 
									" saved :) ", Toast.LENGTH_SHORT)
							.show();
							// May return null if a EasyTracker has not yet been initialized with a
							// property ID.
							EasyTracker easyTracker = EasyTracker.getInstance(getActivity());

							// MapBuilder.createEvent().build() returns a Map of event fields and
							// values
							// that are set and sent with the hit.
							easyTracker.send(MapBuilder.createEvent("ui_action", // Event category
																					// (required)
									"Articles_Save_Selction", // Event action (required)
									"article saved is: " + bingTitle + " : " + bingUrl, // Event
																								// label
									null) // Event value
									.build());
							
						}
					})
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
								.show();
							aDialog.cancel();
						}
					}); 
			aDialog = aBuilder.create();
			aDialog.show();
			// Get image and initiative share intent
			break;
		case R.id.menu_save_collection:
			Toast.makeText(getActivity(), "Tapped Save to Collections", Toast.LENGTH_SHORT)
			.show();
			break;
		default:
			
		}
		return super.onOptionsItemSelected(item);
	}

	public void showWebArticle(String articleUrl) {
		Log.d(TAG, articleUrl);
		// TextView view = (TextView)
		// getView().findViewById(R.id.txtArticleUrl);
		// view.setText(articleUrl);
		// Receive the actual URL from the parent intent
		// articleWebView =
		// (WebView)getView().findViewById(R.id.articleWebView);
		articleWebView.loadUrl(articleUrl);

	}

	/**
	 * Private class used to load prevent browser windows loading in external
	 * applications Solution explained:
	 * http://developer.android.com/resources/tutorials/views/hello-webview.html
	 */
	private class ArticleViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}
