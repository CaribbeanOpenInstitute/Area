/**
 * 
 */
package jm.org.data.area;

import jm.org.data.area.R;
//import jm.org.data.area.SearchSuggestionSampleProvider;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
//import android.provider.SearchRecentSuggestions;
import android.widget.TextView;

/**
 * @author Earl
 *
 */
public class SearchableActivity extends Activity {
	
    // UI elements
    TextView mQueryText;
    TextView mAppDataText;
    TextView mDeliveredByText;
    
    /** Called with the activity is first created.
    * 
    *  After the typical activity setup code, we check to see if we were launched
    *  with the ACTION_SEARCH intent, and if so, we handle it.
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Inflate our UI from its XML layout description.
        setContentView(R.layout.search_query_results);
        
        // Get active display items for later updates
        mQueryText = (TextView) findViewById(R.id.txt_query);
        mAppDataText = (TextView) findViewById(R.id.txt_appdata);
        mDeliveredByText = (TextView) findViewById(R.id.txt_deliveredby);
        
        // get and process search query here
        final Intent queryIntent = getIntent();
        final String queryAction = queryIntent.getAction();
        if (Intent.ACTION_SEARCH.equals(queryAction)) {
            doSearchQuery(queryIntent, "onCreate()");
        }
        else {
            mDeliveredByText.setText("onCreate(), but no ACTION_SEARCH intent");
        }
    }
    
    /** 
     * Called when new intent is delivered.
     *
     * This is where we check the incoming intent for a query string.
     * 
     * @param newIntent The intent used to restart this activity
     */
    @Override
    public void onNewIntent(final Intent newIntent) {
        super.onNewIntent(newIntent);
        
        // get and process search query here
        final Intent queryIntent = getIntent();
        final String queryAction = queryIntent.getAction();
        if (Intent.ACTION_SEARCH.equals(queryAction)) {
            doSearchQuery(queryIntent, "onNewIntent()");
        }
        else {
            mDeliveredByText.setText("onNewIntent(), but no ACTION_SEARCH intent");
        }
    }
    
    /**
     * Generic search handler.
     * 
     * In a "real" application, you would use the query string to select results from
     * your data source, and present a list of those results to the user.
     */
    private void doSearchQuery(final Intent queryIntent, final String entryPoint) {
        
        // The search query is provided as an "extra" string in the query intent
        final String queryString = queryIntent.getStringExtra(SearchManager.QUERY);
        mQueryText.setText(queryString);
        
        // Record the query string in the recent queries suggestions provider.
        // SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, 
        //        SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
        //suggestions.saveRecentQuery(queryString, null);
        
        // If your application provides context data for its searches, 
        // you will receive it as an "extra" bundle in the query intent. 
        // The bundle can contain any number of elements, using any number of keys;
        // For this Api Demo we're just using a single string, stored using "demo key".
        final Bundle appData = queryIntent.getBundleExtra(SearchManager.APP_DATA);
        if (appData == null) {
            mAppDataText.setText("<no app data bundle>");
        }
        if (appData != null) {
            String testStr = appData.getString("demo_key");
            mAppDataText.setText((testStr == null) ? "<no app data>" : testStr);
        }
        
        // Report the method by which we were called.
        mDeliveredByText.setText(entryPoint);
    }

}
