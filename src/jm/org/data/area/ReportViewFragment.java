package jm.org.data.area;

import static jm.org.data.area.DBConstants.*;

//import java.util.ArrayList;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


import jm.org.data.area.R;

public class ReportViewFragment extends Fragment {
	
	public static final String TAG = ReportViewFragment.class.getSimpleName();
	
	private AreaApplication  areaApp;
	
	// Meta Data for a report
	private String docID, docTitle, docName, pubDate, publisher, dateCreated, docDesc;
	
	private Button btnSavePDF;
	//private String journalSite;
	//private String websiteUrl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "ReportViewFragment");
	
		// get Area application inorder to pull from the database
		areaApp = (AreaApplication)getActivity().getApplication();
		
		// To retrieve the document ID from the activity that called this intent 	
		final Bundle reportInfoBundle = getActivity().getIntent().getExtras();
		
		docID = reportInfoBundle.getString(DOCUMENT_ID, docID);
		
		try {
			
			//Cursor document = areaApp.areaData.getReport(docID);
			Cursor document = null;
			
			getActivity().startManagingCursor(document);
			
			document.moveToFirst();
			
			docID = document.getString(document.getColumnIndex(DOCUMENT_ID));
			docTitle = document.getString(document.getColumnIndex(DOC_TITLE));
			docName = document.getString(document.getColumnIndex(DOC_NAME));
			pubDate = document.getString(document.getColumnIndex(PUBLICATION_DATE));
			publisher = document.getString(document.getColumnIndex(PUBLISHER));
			dateCreated = document.getString(document.getColumnIndex(DATE_CREATED));
			docDesc = document.getString(document.getColumnIndex(DOCUMENT_ID));
			
			document.close();
			
		} catch(Exception e) {
			Log.e(TAG, "Exception in ReportViewFragement");
		}
						
		//Log.d(TAG, String.format("Doc ID: %s, Doc Name %s", docID, docName));		
	}
	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		final TextView txtMainTitle = (TextView) getView().findViewById(R.id.txtMainTitle);
		final TextView txtPub = (TextView) getView().findViewById(R.id.txtPublisher);
		final TextView txtPubDate = (TextView) getView().findViewById(R.id.txtPubDate);
		final TextView txtDateCreated = (TextView) getView().findViewById(R.id.txtDateCreated);
		final TextView txtDescription = (TextView) getView().findViewById(R.id.txtDescription);
		
		btnSavePDF = (Button) getView().findViewById(R.id.btnSavePdf);
		
		btnSavePDF.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				savePDF();
			}
		});
		
		txtMainTitle.setText(docTitle);
		txtPub.setText("Publisher: " + publisher);
		txtPubDate.setText("Plublisher Date: " + pubDate);
		txtDateCreated.setText("Date Created: " + dateCreated);
		txtDescription.setText(docDesc);		
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
		View view = inflater.inflate(R.layout.report_view_frag, container, false);
		
		return view;
	}
	
	private void savePDF(){}

}

