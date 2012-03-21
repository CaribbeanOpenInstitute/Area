package jm.org.data.area;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ArticlesFragment extends Fragment {
public static final String TAG = ArticlesFragment.class.getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.articles, container, false);
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.article_list, menu);
        
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
                
            case R.id.menu_reload:
                Toast.makeText(getActivity(), "Fake refreshing...", Toast.LENGTH_SHORT).show();
                /*parentActivity.getActionBarHelper().setRefreshActionItemState(true);
                getWindow().getDecorView().postDelayed(
                        new Runnable() {
                            @Override
                            public void run() {
                                getActionBarHelper().setRefreshActionItemState(false);
                            }
                        }, 1000);*/
                break; 
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
	
	/*@Override
		public void onPrepareOptionsMenu(Menu menu) {
			menu.removeItem(R.menu.)
			super.onPrepareOptionsMenu(menu);
		}*/
	
	public void setText(String item) {
		Log.d(TAG, item);
		TextView view = (TextView) getView().findViewById(R.id.articlesText);
		view.setText(item);
	}

}
