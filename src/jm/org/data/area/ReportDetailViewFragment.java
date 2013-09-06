package jm.org.data.area;

import static jm.org.data.area.DBConstants.DOCUMENT_ID;
import static jm.org.data.area.DBConstants.IDS_DOC_DATE;
import static jm.org.data.area.DBConstants.IDS_DOC_DESC;
import static jm.org.data.area.DBConstants.IDS_DOC_DWNLD_URL;
import static jm.org.data.area.DBConstants.IDS_DOC_PUB;
import static jm.org.data.area.DBConstants.IDS_DOC_PUB_DATE;
import static jm.org.data.area.DBConstants.IDS_DOC_TITLE;

import java.util.Arrays;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReportDetailViewFragment extends Fragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	public static final String TAG = ReportDetailViewFragment.class
			.getSimpleName();

	//private AreaApplication areaApp;
	private ReportDetailViewActivity parentActivity;
	// Meta Data for a report
	private int docID;
	//private String docTitle, docName, pubDate, publisher, dateCreated, docDesc,
	private String 		url;
	private ProgressDialog dialog;
	private Button btnViewReport;

	// private String journalSite;
	// private String websiteUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG, "ReportDetailViewFragment");
		parentActivity = (ReportDetailViewActivity) getActivity();
		dialog = new ProgressDialog(parentActivity);
		// get Area application inorder to pull from the database
		//areaApp = (AreaApplication) getActivity().getApplication();

		// To retrieve the document ID from the activity that called this intent
		final Bundle reportInfoBundle = getActivity().getIntent().getExtras();

		docID = reportInfoBundle.getInt(DOCUMENT_ID);
		getLoaderManager().initLoader(0, null, this);

		/*
		 * try { //Cursor document = areaApp.areaData.getReport(docID); Cursor
		 * document = null;
		 * 
		 * getActivity().startManagingCursor(document);
		 * 
		 * document.moveToFirst();
		 * 
		 * docID = document.getString(document.getColumnIndex(DOCUMENT_ID));
		 * docTitle = document.getString(document.getColumnIndex(DOC_TITLE));
		 * docName = document.getString(document.getColumnIndex(DOC_NAME));
		 * pubDate =
		 * document.getString(document.getColumnIndex(PUBLICATION_DATE));
		 * publisher = document.getString(document.getColumnIndex(PUBLISHER));
		 * dateCreated =
		 * document.getString(document.getColumnIndex(DATE_CREATED)); docDesc =
		 * document.getString(document.getColumnIndex(DOCUMENT_ID));
		 * 
		 * document.close();
		 * 
		 * } catch(Exception e) { Log.e(TAG,
		 * "Exception in ReportViewFragement"); }
		 */

		// Log.d(TAG, String.format("Doc ID: %s, Doc Name %s", docID, docName));
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		dialog = ProgressDialog.show(parentActivity, "",
				"Loading. Please wait...", true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.report_view_frag, container,
				false);
		return view;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		Log.d(TAG, "onCreateLoader: Calling database to get data");
		return new ReportAdapter(getActivity(), docID);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		if (cursor.moveToFirst()) {
			Log.d(TAG,
					"Cursor columns "
							+ Arrays.toString(cursor.getColumnNames()));
			final TextView txtMainTitle = (TextView) getView().findViewById(
					R.id.txtMainTitle);
			final TextView txtPub = (TextView) getView().findViewById(
					R.id.txtPublisher);
			final TextView txtPubDate = (TextView) getView().findViewById(
					R.id.txtPubDate);
			final TextView txtDateCreated = (TextView) getView().findViewById(
					R.id.txtDateCreated);
			final WebView txtDescription = (WebView) getView().findViewById(
					R.id.txtDescription);

			txtMainTitle.setText(cursor.getString(cursor
					.getColumnIndex(IDS_DOC_TITLE)));
			txtPub.setText("Publisher: "
					+ cursor.getString(cursor.getColumnIndex(IDS_DOC_PUB)));
			txtPubDate
					.setText("Publication Date: "
							+ cursor.getString(cursor
									.getColumnIndex(IDS_DOC_PUB_DATE)));
			txtDateCreated.setText("Date Created: "
					+ cursor.getString(cursor.getColumnIndex(IDS_DOC_DATE)));
			txtDescription.loadData(
					cursor.getString(cursor.getColumnIndex(IDS_DOC_DESC)),
					"text/html", "utf-8");
			url = cursor.getString(cursor.getColumnIndex(IDS_DOC_DWNLD_URL));
			Toast.makeText(parentActivity.getBaseContext(), "URL" + url,
					Toast.LENGTH_SHORT).show();
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}

		btnViewReport = (Button) getView().findViewById(R.id.btnViewReport);

		btnViewReport.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), ReportWebViewActivity.class);
				intent.putExtra(IDS_DOC_DWNLD_URL, url);
				startActivity(intent);
			}
		});

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

	public void reload() {
		getLoaderManager().restartLoader(0, null, this);
	}

}
