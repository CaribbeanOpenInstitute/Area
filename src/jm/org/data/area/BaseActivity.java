package jm.org.data.area;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.android.actionbarcompat.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
	private final static String TAG = BaseActivity.class.getSimpleName();
	private AreaService areaService;
	private AreaApplication areaApp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		areaApp = (AreaApplication) getApplication();
		
		if(!areaApp.isServiceRunning) {
			startService(new Intent(this, AreaService.class));
			areaApp.isServiceRunning = true;
		}
		
	}
	
	private ServiceConnection mConnection = new ServiceConnection() {
    	@Override
    	public void onServiceConnected(ComponentName className, IBinder binder) {
    		areaService = ((AreaService.MyBinder) binder).getService();
    		Log.d(TAG, "AreaService connected");
    		//Toast.makeText(, "Connected", Toast.LENGTH_SHORT).show();
    	}
    	
    	@Override
    	public void onServiceDisconnected(ComponentName className) {
    		areaService = null;
    	}
    };
	
	void doBindService() {
    	bindService(new Intent(this, AreaService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

}
