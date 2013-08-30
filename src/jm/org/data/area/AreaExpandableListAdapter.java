package jm.org.data.area;

import static jm.org.data.area.AreaConstants.S_INDICATORS;
import static jm.org.data.area.DBConstants.CATEGORY_NAME;
import static jm.org.data.area.DBConstants.INDICATOR_NAME;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import static jm.org.data.area.DBConstants.WB_CATEGORY_ID;
import static jm.org.data.area.DBConstants.WB_INDICATOR_ID;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;


public class AreaExpandableListAdapter extends SimpleCursorTreeAdapter  {

	private static final String TAG = AreaExpandableListAdapter.class
			.getSimpleName();
	private Context mContext;
	private Cursor mCursor, group, child;
	private final String GROUP_POSITION = "group_position";
	private final String CHILD_POSITION = "child_position";
    
	IndicatorActivity act;
	HomeActivity hAct;
	protected AreaApplication area;
	private int mSelectedPosition = 0, lastmSelectedPosition = 0, mSelectedChildPosition;
	private ExpandableListView parentView;
	private SimpleCursorTreeAdapter thisAdapter;
	
	
	public AreaExpandableListAdapter(Context context, 
			int GroupLayout, int childLayout, String[] groupFrom, int[] groupTo,
			 String[] childFrom, int[] childTo, ExpandableListView parent) {
		super(context, null, GroupLayout, groupFrom,
				groupTo, childLayout, childFrom, childTo);
		
		parentView =(ExpandableListView) parent;
		mContext = context;
		group = runQueryOnBackgroundThread("group");
		this.setGroupCursor(group);
		// TODO Auto-generated constructor stub
		
	}

	
	
	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		
		child = runQueryOnBackgroundThread(groupCursor.getString(groupCursor.getColumnIndex(WB_CATEGORY_ID)));
		
		return child;
	}

	
	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		
		area = (AreaApplication) mContext.getApplicationContext();
		if(constraint.toString().equals("group")){
			mCursor = area.areaData.getCategoryList();
			Log.d(TAG, String.format("Cursor size returned: %d",
					mCursor.getCount()));
		}else{
			Log.d(TAG, "getting indicators:");
			mCursor  = area.areaData.getIndicatorList(constraint.toString());
			Log.d(TAG, String.format("Cursor size returned: %d",
					mCursor.getCount()));
		}
		
		return mCursor;
	}
	
	public void setSelectedPosition(int position) {
		if (mSelectedPosition != position) {
			// View view = (View) l.getItemAtPosition(position);
			lastmSelectedPosition = mSelectedPosition + 1 -1;
			
			mSelectedPosition = position;
			Log.d(TAG, String.format("previous position: %d, Selected Position %d",
					lastmSelectedPosition, mSelectedPosition));
		}

	}

	public void setSelectedPosition(int group, int child) {
		//if (mSelectedPosition != group) {
			// View view = (View) l.getItemAtPosition(position);
			Log.e(TAG, String.format("Current position: %d. New position: %d. Child position: %d",
					mSelectedPosition, group, child));
			//notifyDataSetChanged();
			//this.setSelectedPosition(group, child);
			mSelectedPosition = group;
			mSelectedChildPosition = child;

		//}
		

	}
	
	
	@Override
	protected void bindGroupView(View view, Context context, Cursor groupCursor, boolean isExpanded){
		
		group = groupCursor;
		TextView list_item = (TextView) view.findViewById(R.id.textView1);
		list_item.setText( groupCursor.getString(groupCursor.getColumnIndex(CATEGORY_NAME)));
		
		
		int position = groupCursor.getPosition();
		
		if (mSelectedPosition == position) {
			
			view.setBackgroundColor(Color.parseColor("#8AC7E3"));
			
			Log.d(TAG, String.format("Current position: %d, Selected Parent Position %d",
					position, mSelectedPosition));
			
		} else {
			view.setBackgroundColor(Color.parseColor("#CCFF99"));
			
		}
		
		
		
	}
	
	@Override
	protected void bindChildView (View view, Context context, Cursor childCursor, boolean isLastChild){
		
		child = childCursor;
		TextView list_item = (TextView) view.findViewById(R.id.textView1);
		list_item.setText( childCursor.getString(childCursor.getColumnIndex(INDICATOR_NAME)));
		
		
		int position = childCursor.getPosition();
		
		if (mSelectedChildPosition == position) {
			
			view.setBackgroundColor(Color.parseColor("#8AC7E3"));
			Log.d(TAG, String.format("current position: %d, Selected Child Position %d",
					position, mSelectedChildPosition));
			
		} else {
			view.setBackgroundColor(Color.parseColor("#C8DEB1"));
			
		}
		
		
		
		view.setOnClickListener(new OnClickListener(){

			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				
				thisAdapter = (SimpleCursorTreeAdapter) parentView.getExpandableListAdapter();
				// Get Cursor at list item row
				child = thisAdapter.getChild(mSelectedPosition, parentView.getPositionForView(view)-(mSelectedPosition+1));
				group = thisAdapter.getGroup(mSelectedPosition);
				
				Log.d(TAG, "Child Position is : "  + (parentView.getPositionForView(view)- (mSelectedPosition + 1)) + 
						"\nParent Position is : " + mSelectedPosition +
						"\nView Position is : "  + parentView.getPositionForView(view));
				
				String item = child.getString(child.getColumnIndex(INDICATOR_NAME));
				String item_id = child.getString(child
						.getColumnIndex(WB_INDICATOR_ID));
				Log.d(TAG, "Indicator selected is: " + item + "-> ID: " + item_id);
				
				// May return null if a EasyTracker has not yet been initialized with a
				// property ID.
				EasyTracker easyTracker = EasyTracker.getInstance(mContext);

				// MapBuilder.createEvent().build() returns a Map of event fields and values
				// that are set and sent with the hit.
				easyTracker.send(MapBuilder
				    .createEvent("ui_action",     // Event category (required)
				                 "Indicator_Expandable_List_Selction",  // Event action (required)
				                 "Indicator selected is: " + item + " Unique ID is: " + item_id + 
				                 "From Group" + group.getString(group.getColumnIndex(CATEGORY_NAME)),   // Event label
				                 null)            // Event value
				    .build()
				);
				
				try { // Check if the parent activity is the IndicatorActivity
					act = (IndicatorActivity) mContext;
					Intent intent = new Intent(mContext.getApplicationContext(),
							IndicatorActivity.class);
					intent.putExtra(WB_INDICATOR_ID, item_id);
					intent.putExtra(GROUP_POSITION, mSelectedPosition);
					intent.putExtra(CHILD_POSITION, child.getPosition());
					intent.putExtra(SELECTION_ID, S_INDICATORS);
					intent.putExtra(SELECTION_NAME, "Indicators");
					mContext.startActivity(intent);
					act.finish();
				} catch (ClassCastException actException) {
					Intent intent = new Intent( mContext.getApplicationContext(),
							IndicatorActivity.class);
					intent.putExtra(WB_INDICATOR_ID, item_id);
					intent.putExtra(GROUP_POSITION, mSelectedPosition);
					intent.putExtra(CHILD_POSITION, child.getPosition());
					intent.putExtra(SELECTION_ID, S_INDICATORS);
					intent.putExtra(SELECTION_NAME, "Indicators");
					mContext.startActivity(intent);
				}
	
				if (act != null) {
					act.setIndicator(item);
					act.setPosition(mSelectedPosition, child.getPosition());
					act.setSelection(S_INDICATORS);
					act.setSelection("Indicators");
					
					
				}
				
			}

	    	
	    });
	}
	
	
	@Override
	public void  onGroupExpanded(int groupPosition) {
		
		setSelectedPosition(groupPosition);
		if(lastmSelectedPosition == groupPosition){
			
		}else{
			parentView.collapseGroup(lastmSelectedPosition);
		}
		
		super.onGroupExpanded(groupPosition);
		
		
	}
	
	
		
		
}
