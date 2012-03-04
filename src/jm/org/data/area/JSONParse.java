
package jm.org.data.area;


import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import static jm.org.data.area.AreaConstants.*;
import static jm.org.data.area.DBConstants.*;

public class JSONParse {
	private static final String TAG = JSONParse.class.getSimpleName();
	private AREAData areaData;
	private Context appContext;
	private ContentValues apiRecord;
	
	
	public JSONParse(Context context){
		areaData = new AREAData(context);
		apiRecord = new ContentValues();
	}
	
	public String parseWB(String jsonData){
		//ArrayList indicators = new ArrayList(1);
		//String jsonText = "";
		StringBuilder jsonText = new StringBuilder();
		jsonText.append("Number");
		try {
			//jsonText.append("Number");
			JSONArray jsonArray = new JSONArray(jsonData);
			//jsonText.append(": 1 test");
			//Log.i(ParseJSON.class.getName(),
				//	"Number of entries " + jsonArray.length());
			
			jsonText.append("Number of entries " + String.valueOf(jsonArray.length()));
			
			jsonText.append("\n-------------------------------------\n");
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			jsonText.append("Total: " +jsonObject.getString("total"));
			
			int numofObjects = Integer.parseInt(jsonObject.getString("total"));
			if (numofObjects > 0){
				jsonArray = jsonArray.getJSONArray(1);
			}
			
			for (int i = 0; i < 10; i++) {
				JSONObject jsonInnerObject = jsonArray.getJSONObject(i);
				
				//jsonObject.
				jsonText.append(jsonInnerObject.toString()); 
				//jsonText.append("\n-------------------------------------\n");
				//Log.i(ParseJSON.class.getName(), jsonObject.getString("text"));
						
				// get the text of tweet from feed from the twitter feed
				//jsonText.append(jsonObject.getString("text"));
				
				// get the text id from
				//jsonText.append(jsonObject.getString("name"));

				jsonText.append("\n--------------------------------------\n\n");
				JSONArray names = jsonInnerObject.names();
				for(int x = 0; x < names.length(); x++){
					
					if(jsonInnerObject.optJSONObject(names.getString(x)) == null){
						jsonText.append("\n" + names.getString(x) + ": " + jsonInnerObject.getString(names.getString(x)));
					}else{
						jsonText.append("\n" + names.getString(x) + ": ");
						jsonText.append("\n\t" +jsonInnerObject.optJSONObject(names.getString(x)).toString());
					}
				}
				jsonText.append("\n--------------------------------------\n");
				jsonText.append("--------------------------------------\n\n");
				
			}					
			
		} catch (Exception e) {
			//e.printStackTrace();
			Log.d("some", e.toString());
			jsonText.append("\n--------------------------------------\n\n");
		}
		
		return jsonText.toString();
	}
	
	public String parseWBData(String jsonData){
		
		StringBuilder jsonText = new StringBuilder();
		
		try {
			
			JSONArray jsonArray = new JSONArray(jsonData);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			
			int numofObjects = Integer.parseInt(jsonObject.getString("total"));
			
			if (numofObjects > 0){
				jsonArray = jsonArray.getJSONArray(1);
			}else{
				// no data returned from World bank API pull
			}
			// get Data returned from the world bank 
			for (int i = 0; i < 10; i++) {
				
				JSONObject jsonInnerObject = jsonArray.getJSONObject(i);
				
				jsonText.append(jsonInnerObject.toString()); 
				
				JSONArray names = jsonInnerObject.names();
				for(int x = 0; x < names.length(); x++){
					
					if(jsonInnerObject.optJSONObject(names.getString(x)) == null){
						jsonText.append("\n" + names.getString(x) + ": " + jsonInnerObject.getString(names.getString(x)));
					}else{
						jsonText.append("\n" + names.getString(x) + ": ");
						jsonText.append("\n\t" +jsonInnerObject.optJSONObject(names.getString(x)).toString());
					}
				}
				
			}					
			
		} catch (Exception e) {
			//e.printStackTrace();
			Log.d("some", e.toString());
			jsonText.append("\n--------------------------------------\n\n");
		}
		
		return jsonText.toString();
	}

	public String parseIndicators(String jsonData){
				
		Hashtable indicator_data;
		
		StringBuilder jsonText = new StringBuilder();
		try {
			
			JSONArray jsonArray = new JSONArray(jsonData);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			
			int numReturned  = Integer.parseInt(jsonObject.getString("per_page"));
			int numofObjects = Integer.parseInt(jsonObject.getString("total"));
			
			if (numofObjects > 0){
				jsonArray = jsonArray.getJSONArray(1);
			}else{
				// no data returned from World bank API pull
			}
			// get Data returned from the world bank 
			for (int i = 0; i < numReturned; i++) {
				
				indicator_data = parseJSON(jsonArray.getJSONObject(i)); 
				for (int a = 0; a < WB_IND_LIST.length; a++){
					apiRecord.put(FROM_INDICATOR[a+1], (String)indicator_data.get(WB_IND_LIST[a]));	
					Log.d("Indicators", ""+FROM_INDICATOR[a+1] + ":-> " + (String)indicator_data.get(WB_IND_LIST[a]));
				}
				
				areaData.insert(INDICATOR, apiRecord);
			}					
			
		} catch (Exception e) {
			//e.printStackTrace();
			Log.e("Parsing", e.toString());
			jsonText.append("\n--------------------------------------\n\n");
		}		
		
		return jsonText.toString();
	}
	
	// implemented on the basis that parseIndicators is working
	public String parseCountries(String jsonData){
		
		Hashtable country_data;
		
		StringBuilder jsonText = new StringBuilder();
		try {
			
			JSONArray jsonArray = new JSONArray(jsonData);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			
			int numReturned  = Integer.parseInt(jsonObject.getString("per_page"));
			int numofObjects = Integer.parseInt(jsonObject.getString("total"));
			
			if (numofObjects > 0){
				jsonArray = jsonArray.getJSONArray(1);
			}else{
				// no data returned from World bank API pull
			}
			// get Data returned from the world bank 
			for (int i = 0; i < numReturned; i++) {
				
				//test this 
				country_data = parseJSON(jsonArray.getJSONObject(i));
				
				for (int a = 0; a < WB_COUN_LIST.length; a++){
					apiRecord.put(FROM_COUNTRY[a+1], (String)country_data.get(WB_COUN_LIST[a]));	
					Log.d("Countries", ""+FROM_COUNTRY[a+1] + ":-> " + (String)country_data.get(WB_COUN_LIST[a]));
				}
				
				areaData.insert(COUNTRY, apiRecord);
			}					
			
		} catch (Exception e) {
			//e.printStackTrace();
			Log.e("Parsing", e.toString());
			jsonText.append("\n--------------------------------------\n\n");
		}		
		
		return jsonText.toString();
	}
	
	
	private Hashtable<String, String> parseJSON(JSONObject jsonInnerObject){
		Hashtable<String, String> data = new Hashtable<String, String>();
		try {
			data.put("json", jsonInnerObject.toString());
					
			JSONArray names = jsonInnerObject.names();
			
			for(int x = 0; x < names.length(); x++){
				
				if(jsonInnerObject.optJSONObject(names.getString(x)) == null){
					
					data.put(names.getString(x) , jsonInnerObject.getString(names.getString(x)));
					
				}else{
					//jsonText.append("\n" + names.getString(x) + ": ");
					//jsonText.append("\n\t" +jsonInnerObject.optJSONObject(names.getString(x)).toString());
					data.put(names.getString(x) , jsonInnerObject.optJSONObject(names.getString(x)).toString());
				}
			}
		}
		catch (JSONException e){
			Log.e(TAG,"Exception in parsing Indicators List "+e.toString());
		}
		return data;
	}
}
