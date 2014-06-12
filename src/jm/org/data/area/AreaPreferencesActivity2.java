package jm.org.data.area;

import static jm.org.data.area.DBConstants.CHART_ID;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AreaPreferencesActivity2 extends Activity{
	private AlertDialog.Builder aBuilder;
	private AlertDialog aDialog;
	private Context context;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preference_layout);
		context = this;
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		editor = prefs.edit();
		
		LinearLayout pref1 = (LinearLayout) findViewById(R.id.pref1);
		LinearLayout pref2 = (LinearLayout) findViewById(R.id.pref2);
		LinearLayout pref3 = (LinearLayout) findViewById(R.id.pref3);
		LinearLayout pref4 = (LinearLayout) findViewById(R.id.pref4);
		pref1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				aBuilder = new AlertDialog.Builder(context);
				View view = getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
				TextView title = (TextView) view.findViewById(R.id.title);
				title.setText(R.string.prefs_time);
				aBuilder.setCustomTitle(view);
				//aBuilder.setTitle(R.string.prefs_time);
				aBuilder.setSingleChoiceItems(R.array.prefs_timeEntries, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch(which){
						case(1):
							editor.putString("timePeriod", "15");
						case(2):
							editor.putString("timePeriod", "20");
							
						}
						
					}

									
					
				});
				aBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
						//	.show();
						aDialog.cancel();
					}
				}); 
				aDialog = aBuilder.create();
				aDialog.show();
				
				 
				Button cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
				cancel.setBackgroundColor(Color.parseColor("#777777"));
				cancel.setTextColor(Color.WHITE);
			}
		
		});
		pref2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				aBuilder = new AlertDialog.Builder(context);
				View view = getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
				TextView title = (TextView) view.findViewById(R.id.title);
				title.setText(R.string.prefResultNumberTitle);
				aBuilder.setCustomTitle(view);
				//aBuilder.setTitle(R.string.prefResultNumberTitle);
				aBuilder.setSingleChoiceItems(R.array.prefs_resultNumbersEntries, -1, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						switch(which){
						case(1):
							editor.putString("resultNumber", "10");
						case(2):
							editor.putString("resultNumber", "15");
						case(3):
							editor.putString("resultNumber", "20");
							
						}
						
					}

					
					
					
				});
				aBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
						//	.show();
						aDialog.cancel();
					}
				}); 
				aDialog = aBuilder.create();
				aDialog.show();
				 
				Button cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE); 
				cancel.setBackgroundColor(Color.parseColor("#777777"));
				cancel.setTextColor(Color.WHITE);
			}
		
		});
		pref3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				aBuilder = new AlertDialog.Builder(context);
				View view = getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
				TextView title = (TextView) view.findViewById(R.id.title);
				title.setText(R.string.prefs_idsKeyTitle);
				aBuilder.setCustomTitle(view);
				//aBuilder.setTitle(R.string.prefs_idsKeyTitle);
				View ids_view = getLayoutInflater().inflate(R.layout.alert_dialog_edit_text, null);
				aBuilder.setView(ids_view);
				aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						EditText editText = (EditText) aDialog.findViewById(R.id.editText1);
						String idsKey = editText.getText().toString();
						editor.putString("idsKey", idsKey);
						
						
					}
				});
				aBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
						//	.show();
						aDialog.cancel();
					}
				}); 
				
				aDialog = aBuilder.create();
				aDialog.show();
				Button ok = aDialog.getButton(DialogInterface.BUTTON_POSITIVE);  
				Button cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
				ok.setBackgroundColor(Color.parseColor("#61BF8B"));
				ok.setTextColor(Color.WHITE);
				cancel.setBackgroundColor(Color.parseColor("#777777"));
				cancel.setTextColor(Color.WHITE);
			}
		
		});
		pref4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				aBuilder = new AlertDialog.Builder(context);
				View view = getLayoutInflater().inflate(R.layout.alert_dialog_title, null);
				TextView title = (TextView) view.findViewById(R.id.title);
				title.setText(R.string.prefs_bingKeyTitle);
				aBuilder.setCustomTitle(view);
				//aBuilder.setTitle(R.string.prefs_bingKeyTitle);
				View bing_view = getLayoutInflater().inflate(R.layout.alert_dialog_edit_text, null);
				aBuilder.setView(bing_view);
				aBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						
						EditText editText = (EditText) aDialog.findViewById(R.id.editText1);
						String bingKey = editText.getText().toString();
						editor.putString("idsKey", bingKey);
						
						
					}
				});
				aBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						//Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT)
						//	.show();
						aDialog.cancel();
					}
				}); 
				aDialog = aBuilder.create();
				aDialog.show();
				Button save = aDialog.getButton(DialogInterface.BUTTON_POSITIVE);  
				Button cancel = aDialog.getButton(DialogInterface.BUTTON_NEGATIVE);  
				save.setBackgroundColor(Color.parseColor("#61BF8B"));
				save.setTextColor(Color.WHITE);
				cancel.setBackgroundColor(Color.parseColor("#777777"));
				cancel.setTextColor(Color.WHITE);
			}
		
		});
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	

}
