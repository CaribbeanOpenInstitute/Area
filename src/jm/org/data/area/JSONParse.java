
package jm.org.data.area;


import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class JSONParse {

	public JSONParse(){
		
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
}
