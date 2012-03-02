package jm.org.data.area;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * AreaService
 * Background service used to make API calls to cloud services 
 * and store in local database
 */
public class AreaService extends Service{
	private static final String TAG = AreaService.class.getSimpleName();
	private apiUpdater updater;
	private AreaApplication area;
	

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "Service Binded");
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		updater = new apiUpdater();	//Pass API call
		Log.d(TAG, "Searvice created");
	}
	
	/**
	 * apiThread
	 * Thread to pull API requests			
	 */
	class apiUpdater extends Thread {
		public apiUpdater() {
			super("apiUpdater");
			area = ((AreaApplication)getApplication());
		}
		
		@Override
		public void run() {
			while (area.isOnline) {
				//Go fetch API data
				//Store in local DB
				//Broadcast data intent {chart | report | article} 
			}
			super.run();
		}
	}

}
