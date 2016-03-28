package com.xanxus;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	public final static String TAG="MyService";
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "hello,service", Toast.LENGTH_SHORT).show();
		Log.i(TAG, "hello,service");
	}
	

}
