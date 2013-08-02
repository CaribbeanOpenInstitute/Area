package jm.org.data.area;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class ReportDetailViewActivity extends BaseActivity {
	public final String TAG = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_detail_view);
		Log.e(TAG, "Creating Reports View");

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
