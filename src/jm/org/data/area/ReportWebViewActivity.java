package jm.org.data.area;



import static jm.org.data.area.DBConstants.IDS_DOC_DWNLD_URL;

import java.io.File;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ReportWebViewActivity extends BaseActivity 
{
	
	private static final String TAG = ReportWebViewActivity.class.getSimpleName();
	private String url, PDFfile;
	private WebView articleWebView;
	private String defaultURL = "data.org.jm";
	private Context appContext ;
	private ProgressDialog dialog ;
	private AreaApplication area;
	private String appPath;
	/** Called when the activity is first created. */
    
	@Override
    public void onCreate(Bundle savedInstanceState)   
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_pdf_view);
        appPath = getPath();
        Log.e(TAG, "Path => " + appPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// only for android newer than gingerbread
			 ActionBar actionBar = getActionBar();
			 actionBar.setDisplayHomeAsUpEnabled(true);
		}
        appContext = this;
        dialog = new ProgressDialog(this);
        
        dialog = ProgressDialog.show(this, "", 
                "Loading. Please wait...", true);
        final Bundle reportBundle = getIntent().getExtras();
        
		if(reportBundle.getString(IDS_DOC_DWNLD_URL) != null) {
			url = reportBundle.getString(IDS_DOC_DWNLD_URL);
		}else{
			url = defaultURL;
		}
		
		new getPDF().execute();
		
		/*if(PDFfile == url){
			Log.d(TAG, "No PDF at URL");
			articleWebView = (WebView) findViewById(R.id.reportWebView);
			showWebArticle(url);
			
			articleWebView.setWebViewClient(new ArticleViewClient());
	        //displayPDF(url);
		}else{
			Log.d(TAG, "Displaying PDF File");
			//displayPDF(PDFfile);
		}	*/	
		
		
    }
	

	
	public void showWebArticle(String articleUrl) {
		Log.d(TAG, articleUrl);
		//TextView view = (TextView) getView().findViewById(R.id.txtArticleUrl);
		//view.setText(articleUrl);
		//Receive the actual URL from the parent intent
		//articleWebView = (WebView)getView().findViewById(R.id.articleWebView);
		articleWebView.loadUrl(articleUrl);
		
		if(dialog.isShowing()){
			dialog.dismiss();
		}
		
	}
	
	/**
	 *Private class used to load prevent browser windows loading in external applications 
	 *Solution explained: http://developer.android.com/resources/tutorials/views/hello-webview.html
	 */
	private class ArticleViewClient extends WebViewClient {
	    
	    
	    @Override
	    public void onPageStarted (WebView view, String url, Bitmap favicon){
	    	if(dialog.isShowing()){
    			dialog.dismiss();
    		}
    		dialog = ProgressDialog.show(appContext, "",  "Loading. Please wait...", true);
    		Log.d(TAG, "Dialog Loaded");
    	}
	    
	    @Override
    	public void onPageFinished (WebView view, String url){
	    	Log.d(TAG, "Page Finnished Loading");
    		if(dialog.isShowing()){
    			dialog.dismiss();
    		}

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
    
    private class getPDF extends AsyncTask<Void, Void, Boolean> {

		protected void onPreExecute() {
			//run on UI thread
			
			area = (AreaApplication) getApplication();
			
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				Log.e(TAG, "Getting Pdf");
				PDFfile = area.netserv.getPDF(url, appPath + "/");
				return true; 
				

			} catch (IllegalStateException ilEx) {
				Log.e(TAG, "DATABASE LOCK: Error pulling/storing data for Chart");
			}
			PDFfile = url;
			return false;
		}
		
		@Override
		protected void onPostExecute(Boolean initResult) {
			super.onPostExecute(initResult);
			if (initResult) {
				Log.e(TAG, "Completed PDF Retrieval ");
				displayPDF(PDFfile);
				
			} else {
				Log.e(TAG, "Problem with PDF Retrieval");
				
			}
			
		}
		
	}
    
    private void displayPDF(String pdfurl){
    	File pdfFile = new File(pdfurl); 
        if(pdfFile.exists()) 
        {
            Uri path = Uri.fromFile(pdfFile); 
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(pdfIntent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(this, "No Application available to view pdf", Toast.LENGTH_LONG).show(); 
            }
        }else{
        	Toast.makeText(this, "No Pdf to view", Toast.LENGTH_LONG).show();
        	showWebView(pdfurl);
        	
        }
        if(dialog.isShowing()){
			dialog.dismiss();
		}

    }
    
    private void showWebView(String weburl) 
    {
    	articleWebView = (WebView) findViewById(R.id.reportWebView);
		showWebArticle(url);
		
		articleWebView.setWebViewClient(new ArticleViewClient());
        //displayPDF(url);
    }
    
    private String getPath(){
    	String storage;
    	File path = Environment.getExternalStorageDirectory();
    	
		path = new File(path.getPath() + "/AREA");
		//if (path.mkdirs()){
		storage = path.getPath();
		/*}else{
			storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
		}*/
		
    	return storage;
    }
}
