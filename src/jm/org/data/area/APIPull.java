

package jm.org.data.area;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;


public class APIPull {
	
	private final String TAG = APIPull.class.toString();
	public APIPull() {}
		
	public String HTTPRequest(int api, String uri)  {
		String errorMsg = "";
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		String encodedBytesstr = "";
		byte[] encodedBytes;
		if(api == 2){
			Log.e(APIPull.class.toString(), "Bing API Pull " + uri);
			try{
				
				encodedBytes = Base64.encodeBase64((":avYmuwluAWdg0uT50kqYtgAoKcnr+xp972yHvr6Brx4=").getBytes());
				encodedBytesstr = new String(encodedBytes);
				/*byte[] decstr = Base64.decode(encodedBytes);
				Log.e(APIPull.class.toString(), encodedBytes + " - " + ));
				Log.e(APIPull.class.toString(), encodedBytesstr);
				httpGet.setHeader("Authorization", "Basic " +  encodedBytesstr);
				
				Log.e(APIPull.class.toString(),"executing request " + httpGet.getURI());

	            // Create a response handler
	            ResponseHandler<String> responseHandler = new BasicResponseHandler();
	            String responseBody = client.execute(httpGet, responseHandler);
	            Log.e(APIPull.class.toString(),"----------------------------------------");
	            Log.e(APIPull.class.toString(),responseBody);
	            Log.e(APIPull.class.toString(),"----------------------------------------");
*/	            
			}catch(Exception e){
				e.printStackTrace();
				errorMsg.concat(e.toString());
			}
			
			Log.e(APIPull.class.toString(), encodedBytesstr);
			httpGet.setHeader("Authorization", "Basic " +  encodedBytesstr);
		}else if (api == 1){
			httpGet.addHeader("Token-Guid", "47040d85-719b-460c-8c4c-8786614e31e6");
		}
				//"http://api.worldbank.org/country?per_page=10&region=WLD&lendingtype=IDX&format=json");
				//"http://api.ids.ac.uk/openapi/eldis/get/documents/A59947/full/the-global-status-of-ccs-2011/");
				//"http://twitter.com/statuses/user_timeline/vogella.json");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(APIPull.class.toString(), "Failed to download file " + response.getStatusLine().getReasonPhrase() + " " + statusCode );
				errorMsg.concat(APIPull.class.toString());
				errorMsg.concat("Failed to download file " + uri);
				errorMsg.concat("\n-----------------------\n");
			}
			client.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			errorMsg.concat(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			errorMsg.concat(e.toString());
		}

		if (errorMsg.equals("")){
			return builder.toString();
		}else{
			return errorMsg;
		}
		
	}
	
	public String getPDF(String pdfurl, String dirPath){
    	String path = pdfurl;
    	
    	try {
    
    	    //this is the name of the local file you will create
    		if(pdfurl.indexOf(",") > 1){
    			pdfurl = pdfurl.substring(pdfurl.indexOf(",") + 1).trim();
    			
    			Log.v(TAG, String.format("New URL: %s ", pdfurl));
    		}
    		
    		int slashIndex = pdfurl.lastIndexOf('/');
            int dotIndex = pdfurl.lastIndexOf('.');
            String ext = pdfurl.substring(dotIndex + 1).trim();
            Log.v(TAG, String.format("File Extension: -%s- ", ext));
            if (ext.equals("pdf")){
	    	    String targetFileName;
	    	    
	    	    targetFileName = pdfurl.substring(slashIndex + 1);
	    	    Log.d(TAG, String.format("File Name: %s ", targetFileName));
	    	    //boolean eof = false;
	    	    URL u = new URL(pdfurl);
	    	    
	    	    HttpURLConnection c = (HttpURLConnection) u.openConnection();
	    	    c.setRequestMethod("GET");
	    	    
	    	    if (c.getResponseCode() == HttpURLConnection.HTTP_OK){
	    	    	Log.d(TAG, String.format("File Exists %s ", pdfurl));
	    	    	
		    	    //c.setDoOutput(true);
		    	    //c.connect();
	    	    	File file = new File(dirPath + targetFileName);
	    	    	if (!file.exists()) {
	    	    	    file.createNewFile();
	    	    	}

		    	    FileOutputStream f = new FileOutputStream(file.getPath());
		    	    Log.d(TAG, "Downloading pdf");
	    	        InputStream in = c.getInputStream();
	    	        byte[] buffer = new byte[1024];
	    	        int len1 = 0;
	    	        
	    	        while ( (len1 = in.read(buffer)) != -1 ) {
	    	        	f.write(buffer,0, len1);
	    	        }
	    	        
	    	        f.close();
	    	        c.disconnect();
	    	        path = dirPath+targetFileName;
	    	        Log.d(TAG, String.format("File Path: %s ", path));
	    	    }
            }
    	} catch (MalformedURLException e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	} catch (IOException e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	}catch (Exception e){
    		e.printStackTrace();
    	}

    	
    	return path;
    }
	
}