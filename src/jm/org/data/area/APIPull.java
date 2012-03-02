

package jm.org.data.area;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;


public class APIPull{
	
	public APIPull(){
		
	}
	
	
	public String HTTPRequest( int api,String uri) {
		String errorMsg = "";
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uri);
		if (api == 1){
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
				Log.e(APIPull.class.toString(), "Failed to download file");
				errorMsg.concat(APIPull.class.toString());
				errorMsg.concat("Failed to download file");
				errorMsg.concat("\n-----------------------\n");
			}
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
}