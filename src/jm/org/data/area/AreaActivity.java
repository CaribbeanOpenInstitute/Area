package jm.org.data.area;


import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import android.widget.TextView;

public class AreaActivity extends Activity {
    /** Called when the activity is first created. */
	private TextView jsonText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

		jsonText = (TextView) findViewById(R.id.txtView1);
		
		//to make the text scrollable
		jsonText.setMovementMethod(new ScrollingMovementMethod());
		
		APIPull apiAccess = new APIPull();
		JSONParse apiParser = new JSONParse();
		
		String jsonData = apiAccess.HTTPRequest(1, "http://api.ids.ac.uk/openapi/eldis/get/documents/A59947/full/the-global-status-of-ccs-2011/");
		jsonText.append("test\n " + jsonData);
		//String output = apiParser.parseWB(jsonData);
		//jsonText.append("test\n " + output);
		//ArrayList indicators = new ArrayList(1);
						
    }
}