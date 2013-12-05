package jm.org.data.era;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import static jm.org.data.era.DBConstants.CATEGORY_NAME;
import static jm.org.data.era.DBConstants.INDICATOR_NAME;
import jm.org.data.era.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

/**
 * Demonstrates expandable lists backed by Cursors
 */
public class ExpandableListFragment extends Fragment implements OnChildClickListener{

    private AreaExpandableListAdapter mAdapter;
    ExpandableListView menuList = null;
    public static final String TAG = ExpandableListFragment.class
			.getSimpleName();

    private IndicatorActivity act;
	//private HomeActivity hAct;
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.main_list);
        // Set up our adapter
            			   
        
        
        
    }
    
    @Override
    public void onResume(){
    	
    	
    	if(mAdapter == null){
    		Log.d(TAG, "Resuming");
	    	mAdapter = new AreaExpandableListAdapter(
	    			getActivity(), //Context context,  
	                R.layout.row, //int GroupLayout,
	                R.layout.group, //int childLayout,
	                new String[] {CATEGORY_NAME}, // Name for group layouts String[] groupFrom,
	                new int[] { R.id.textView1 }, // int[] groupTo,
	                new String[] { INDICATOR_NAME }, // Number for child layouts String[] childFrom,
	                new int[] { R.id.textView1 },
	                menuList); //int[] childTo
	
	    		    	
	        menuList.setAdapter(mAdapter);
	        try{
		        act = (IndicatorActivity) getActivity();
				mAdapter.setSelectedPosition(act.getGroupPosition(), act.getChildPosition());
				
				menuList.setSelectedChild(act.getGroupPosition(), act.getChildPosition(), true);
				menuList.expandGroup(act.getGroupPosition());
	        }catch (ClassCastException actException){
	        	//hAct = (HomeActivity) getActivity();
				mAdapter.setSelectedPosition(-1);
	        }
	        
    	}else{
    		
    	}
    	super.onResume();
    }
    
    
    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
    	
		super.onActivityCreated(savedInstanceState);
		
        try { // Check if the parent activity is the IndicatorActivity. only need to track indicator selection here
			
			act = (IndicatorActivity) getActivity();
			mAdapter.setSelectedPosition(act.getGroupPosition(), act.getChildPosition());
			
			menuList.setSelectedChild(act.getGroupPosition(), act.getChildPosition(), true);
			menuList.expandGroup(act.getGroupPosition());
			
		} catch (ClassCastException actException) {// otherwise leave list collapsed
			//hAct = (HomeActivity) getActivity();
			mAdapter.setSelectedPosition(-1);
			
		}
        

    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
    	View mainView  = inflater.inflate(R.layout.main_list, container, false); 
    	
        menuList = (ExpandableListView) mainView.findViewById(R.id.list);

    	mAdapter = new AreaExpandableListAdapter(
    			getActivity(), //Context context,  
                R.layout.row, //int GroupLayout,
                R.layout.group, //int childLayout,
                new String[] {CATEGORY_NAME}, // Name for group layouts String[] groupFrom,
                new int[] { R.id.textView1 }, // int[] groupTo,
                new String[] { INDICATOR_NAME }, // Number for child layouts String[] childFrom,
                new int[] { R.id.textView1 },
                menuList); //int[] childTo

        	
        menuList.setAdapter(mAdapter);
        //menuList.setOnChildClickListener(this);
        return mainView;
    	
    }
    
    

    @Override
	public void onDestroy() {
        super.onDestroy();

        // Null out the group cursor. This will cause the group cursor and all of the child cursors
        // to be closed.
        //mAdapter.changeCursor(null);
        mAdapter = null;
        
    }

	public void reload() {
		
		//menuList.getAdapter().
		mAdapter.notifyDataSetChanged();
		
	}

	public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {
		
        Log.d(TAG,"Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " + childPosition);

        return true;
    }
    
	@Override
	public void onStop() {
	    try {
	      //super.onStop();

	      if (this.mAdapter !=null){
	        this.mAdapter.getCursor().close();
	        this.mAdapter = null;
	      }
	      
	      //this.getLoaderManager().destroyLoader(0);
	      
	      /*if (this.mActivityListCursorObj != null) {
	        this.mActivityListCursorObj.close();
	      }*/

	      super.onStop();
	    } catch (Exception error) {
	    	Log.d(TAG, "Error in stopping Adapter");
	    	
	    }// end try/catch (Exception error)
	  }// end onStop

}