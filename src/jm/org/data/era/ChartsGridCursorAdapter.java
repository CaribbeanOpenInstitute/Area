package jm.org.data.era;

import static android.provider.BaseColumns._ID;
import static jm.org.data.era.AreaConstants.WORLD_SEARCH;
import static jm.org.data.era.DBConstants.I_ID;
import jm.org.data.era.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChartsGridCursorAdapter extends CursorAdapter {

	public static final String TAG = ChartsGridCursorAdapter.class.getSimpleName();
	private LinearLayout layout;
	private String indicator, category;
	private String[] countryList;
	
	private ProgressDialog dialog;
	private GetChartData chart_data;
	
	private AreaApplication area;
	
	public ChartsGridCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		this.swapCursor(runQueryOnBackgroundThread(""));
		this.notifyDataSetChanged();
		//area = (AreaApplication) mContext.getApplicationContext();
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Log.d(TAG, "Bind View");
		layout 		= (LinearLayout) view.findViewById(R.id.home_chart_view);
		dialog = new ProgressDialog(mContext);
		
		indicator = area.areaData.getIndicatorName(cursor.getInt(cursor
				.getColumnIndexOrThrow(I_ID)));
		countryList = area.areaData.getSearchCountries(cursor.getInt(cursor
				.getColumnIndexOrThrow(_ID)));
		
		chart_data = new GetChartData(layout, mContext, dialog, indicator, countryList, category);
		layout.removeAllViewsInLayout();
		layout.setBackgroundColor(Color.BLUE);
		layout.addView(chart_data.renderChart(1), new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    //get the item corresponding to your position
		Log.d(TAG, "Get View:");
	    LinearLayout chart = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.charts, parent, false);
	    
	    ((TextView)chart.findViewById(R.id.chartText)).setText("first text");
	    layout 		= (LinearLayout) chart.findViewById(R.id.chart_view);
		dialog = new ProgressDialog(mContext);
		mCursor.moveToPosition(position);
		
		indicator = area.areaData.getIndicatorName(mCursor.getInt(mCursor
				.getColumnIndexOrThrow(I_ID)));
		countryList = area.areaData.getSearchCountries(mCursor.getInt(mCursor
				.getColumnIndexOrThrow(_ID)));
		
		chart_data = new GetChartData(layout, mContext, dialog, indicator, countryList, category);
		layout.removeAllViewsInLayout();
		layout.setBackgroundColor(Color.BLUE);
		layout.addView(chart_data.renderChart(1));
		//chart.refreshDrawableState();
		
	    
	    return chart;
	}
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.home_chart, parent, false);
		bindView(v, context, cursor);
		Log.d(TAG, "New View");
		return null;
	}


	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		
		Log.d(TAG, "getting Charts:");
		
		area = (AreaApplication) mContext.getApplicationContext();
		
		Cursor result = area.areaData.getRecentData(WORLD_SEARCH);
		
		Log.d(TAG, String.format("Cursor size returned: %d",
					result.getCount()));
		
		return result;
	}
	

}
