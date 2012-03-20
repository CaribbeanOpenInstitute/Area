package jm.org.data.area;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import jm.org.data.area.R;

public class ViewArticleFragment extends Fragment {
public static final String TAG = ViewArticleFragment.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "Hello");
		
		
		WebView articleWebView = (WebView)getView().findViewById(R.id.articleWebView);
		articleWebView.loadUrl("http://bing.com");
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
	
	public void setText(String item) {
		Log.d(TAG, item);
		TextView view = (TextView) getView().findViewById(R.id.articlesText);
		view.setText(item);
	}

}
