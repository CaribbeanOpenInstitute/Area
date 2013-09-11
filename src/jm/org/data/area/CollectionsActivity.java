package jm.org.data.area;

import static jm.org.data.area.AreaConstants.POSITION;
import static jm.org.data.area.AreaConstants.SUCCESS;
import static jm.org.data.area.AreaConstants.S_COLLECTIONS;
import static jm.org.data.area.AreaConstants.S_COLL_ACT;
import static jm.org.data.area.DBConstants.COLLECTIONS;
import static jm.org.data.area.DBConstants.COLLECTION_DESC;
import static jm.org.data.area.DBConstants.COLLECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_ID;
import static jm.org.data.area.DBConstants.SELECTION_NAME;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public class CollectionsActivity extends BaseActivity {

	
	private static final String TAG = CountryActivity.class.getSimpleName();
	private String selection;
	private int mSelection, lPosition, col_id;
	
	private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;
	
	private EditText name_view, desc_view;
	private TextView name_lbl, desc_lbl;
	private String col_name, col_desc;
	
	private Bundle actBundle;
	private TabHost mTabHost;
	private ViewPager mViewPager;
	private AreaTabsAdapter mTabsAdapter;
	private Context mContext;
	
	private CollectionsListFragment cFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.collections_dashboard);
		mContext = this;
		actBundle = getIntent().getExtras();
		if(actBundle != null ){
			if(actBundle.getString(SELECTION_NAME) != null){
				
				mSelection = actBundle.getInt(SELECTION_ID  );
				selection  = actBundle.getString(SELECTION_NAME);
				
			}else{//set values to default
				mSelection = S_COLLECTIONS;
				selection = "Collections";
			}
			if (actBundle.getString("col_name") != null ){
				lPosition 	= actBundle.getInt(POSITION);
				col_id		= actBundle.getInt("col_id");
				col_name	= actBundle.getString("col_name");
				col_desc	= actBundle.getString(COLLECTION_DESC);
			}else{
				lPosition	= -1;
				col_id		= -1;
				col_name	= "";
				col_desc	= "";
			}
			
		}else{//set vallues to default
			mSelection 	= S_COLLECTIONS;
			selection	= "Collections";
			lPosition	= -1;
			col_id		= -1;
			col_name	= "";
			col_desc	= "";
		}
		
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mTabsAdapter = new AreaTabsAdapter(this, mTabHost, mViewPager);

		mTabsAdapter.addTab(mTabHost.newTabSpec("charts")
				.setIndicator("Charts"), ChartsListFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("reports").setIndicator("Reports"),
				ReportsFragment.class, null);
		mTabsAdapter.addTab(
				mTabHost.newTabSpec("articles").setIndicator("Articles"),
				ArticlesFragment.class, null);

		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.collections, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			// TODO Implement a Search Dialog fall back for compatibility with
			// Android 2.3 and lower
			// Currently crashes on Gingerbread or lower

			// Get the SearchView and set the searchable configuration
			SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			SearchView searchView = (SearchView) menu
					.findItem(R.id.menu_search).getActionView();
			searchView.setSearchableInfo(searchManager
					.getSearchableInfo(getComponentName()));
			searchView.setIconifiedByDefault(true); // Do not iconify the
													// widget; expand it by
													// default
		}
		if (lPosition < 0){
			// do not show delete or add to collections
			MenuItem delete = menu.findItem(R.id.menu_delete);
			MenuItem collection = menu.findItem(R.id.menu_view);
			
			delete.setEnabled(false);
			delete.setVisible(false);
			
			collection.setEnabled(false);
			collection.setVisible(false);
			
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_add:
			//Toast.makeText(this, "Tapped Add", Toast.LENGTH_SHORT)
			//		.show();
			CreateDialog("","");
			
			aDialog.show();
			// Get image and initiative share intent
			break;
			
		case R.id.menu_delete:
			if (col_id < 1){
				Toast.makeText(this, "No Collections to Delete", Toast.LENGTH_SHORT).show();
				break;
			}
			//Toast.makeText(this, "Tapped Delete", Toast.LENGTH_SHORT).show();
			aBuilder = new AlertDialog.Builder(this);

			aBuilder.setTitle("Delete Selected Collection");
			aBuilder.setIcon(R.drawable.ic_launcher);

			aBuilder.setMessage("Are you sure you want to delete this collection: \"" +col_name + "\" ?")
					// Add action buttons
					.setPositiveButton(R.string.delete_collection,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									// FROM_SAVED_DATA = { SAVED_DATA_ID,
									// D_T_ID, ENTITY_ID};
									if (!((area.areaData.delete(COLLECTIONS, COLLECTION_ID + " = '" + col_id + "'", col_id)) > 0)){
										Toast.makeText(
												mContext,"Collection " + col_name + " Deleting Failed :( ",
												Toast.LENGTH_SHORT).show();
									}

									Toast.makeText(mContext,
											"Collection " + col_name + " Deleted :) ",
											Toast.LENGTH_SHORT).show();
									
									// May return null if a EasyTracker has not
									// yet been initialized with a
									// property ID.
									EasyTracker easyTracker = EasyTracker
											.getInstance(mContext);

									// MapBuilder.createEvent().build() returns
									// a Map of event fields and
									// values
									// that are set and sent with the hit.
									easyTracker.send(MapBuilder.createEvent(
											"ui_action", // Event category
															// (required)
											"Delete Collection", // Event
																	// action
																	// (required)
											"collection deleted is: " + col_name
													+ " ID: " + col_id, // Event
																			// label
											null) // Event value
											.build());

								}
							})
					.setNegativeButton(R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Toast.makeText(mContext, "Canceled",
											Toast.LENGTH_SHORT).show();
									aDialog.cancel();
								}
							});
			aDialog = aBuilder.create();
			aDialog.show();
			break;
		case R.id.menu_view:
			//Toast.makeText(this, "Tapped View", Toast.LENGTH_SHORT).show();
			if (col_name == null || col_name.equals("")) {
				Toast.makeText(this, "No Collection to View",
						Toast.LENGTH_SHORT).show();
				break;
			}

			CreateDialog(col_name, col_desc);

			aDialog.show();
			
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	public String getSelection() {
		return selection;
	}
	
	public int getColPosition() {
		return lPosition;
	}
	
	public int getCollection() {
		return col_id;
	}
	
	public int getParentNum() {
		return S_COLL_ACT;
	}

	public int getSelectionID(){
		return mSelection;
	}
	
	public void setSelection(String indicator) {
		selection = indicator;
		Log.d(TAG, "Selection changed to " + selection);

	}
	
	public void setSelection(int lPos) {
		mSelection = lPos;
	}
	
	public void setColPosition(int id) {
		lPosition = id;
	}
	
	public void setCollection(int id) {
		col_id = id;
	}
	
	private void CreateDialog(String name, String desc){
		aBuilder = new AlertDialog.Builder(this);

		aBuilder.setTitle("Create New Colection");
		aBuilder.setIcon(R.drawable.ic_launcher);
		View layout = this.getLayoutInflater().inflate(R.layout.save_chart_dialogue, null);
		aBuilder.setView(layout)
				// Add action buttons
				.setPositiveButton(R.string.save_chart,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								col_name = name_view.getText().toString();

								col_desc = desc_view.getText().toString();
								/*
								Toast.makeText(
										mContext,
										"Saving Collections " + col_name
												+ " your description: "
												+ col_desc, Toast.LENGTH_SHORT)
										.show();
								*/
								int result = area.areaData.saveCollection(0, col_name, col_desc);
								if (result == SUCCESS){
									Toast.makeText(mContext,
											"Collection " + col_name + " saved :) ",
											Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(mContext,
											"Error Saving Collection !!! :( ",
											Toast.LENGTH_SHORT).show();
								}
								// May return null if a EasyTracker has not
								// yet been initialized with a
								// property ID.
								EasyTracker easyTracker = EasyTracker
										.getInstance(mContext);

								// MapBuilder.createEvent().build() returns
								// a Map of event fields and
								// values
								// that are set and sent with the hit.
								easyTracker.send(MapBuilder.createEvent(
										"ui_action", // Event category
														// (required)
										"Collection_Save_Selction", // Event
																// action
																// (required)
										"Collection saved is: " + col_name + " : "
												+ col_desc, // Event
															// label
										null) // Event value
										.build());
								cFragment = (CollectionsListFragment) getSupportFragmentManager()
										.findFragmentById(R.id.clistFragment);
								cFragment.reload();

							}
							
						})
				.setNegativeButton(R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Toast.makeText(mContext, "Cancel",
										Toast.LENGTH_SHORT).show();
								aDialog.cancel();
							}
						});
		aDialog = aBuilder.create();
		
		name_lbl = (TextView) layout.findViewById(R.id.chart_name_label);
		name_lbl.setText("Collections Name");
		
		desc_lbl = (TextView) layout.findViewById(R.id.chart_desc_label);
		desc_lbl.setText("Collections Description");
		
		name_view = (EditText) layout.findViewById(R.id.chart_name);
		name_view.setText(name);

		desc_view = (EditText) layout.findViewById(R.id.chart_description);
		desc_view.setText(desc);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		EasyTracker.getInstance(this).activityStart(this);  // Add this method.
		}
	
	@Override
	public void onStop() {
		super.onStop();
	
		EasyTracker.getInstance(this).activityStop(this);  // Add this method.
	}
}
