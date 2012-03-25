package jm.org.data.area;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import static jm.org.data.area.DBConstants.IDS_DOC_ID;
import static jm.org.data.area.DBConstants.IDS_SEARCH_RESULTS;
import static jm.org.data.area.DBConstants.IDS_VIEW_DATE;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArtcileViewActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.article_view);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			 ActionBar actionBar = getActionBar();
			 actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
}
