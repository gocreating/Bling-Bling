package com.test.blingbling;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class HookPhoneService extends Service {

	private static final String TAG = "BlingBling";
	private static HookPhoneService instance = null;
	
    private Timer timer = new Timer();
    int i = 0;
    
    private TelephonyManager manager ;
    
    public static boolean isInstanceCreated() { 
        return instance != null; 
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }
 
    @Override
    public void onCreate() {
    	Log.d(TAG, "onCreate");
    	instance = this;
    	timer.schedule(new timerTask(), 0, 1000);
        manager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        manager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }
  
    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.d(TAG, "onStartCpmmand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
    public void onDestroy() {
		Log.d(TAG, "onDestroy");
		timer.cancel();
		instance = null;
        super.onDestroy();
    }
	
	public class timerTask extends TimerTask
	{
	    public void run()
	    {
	    	Log.d(TAG, Integer.toString(++i));
	    }
	};
	
    class MyPhoneStateListener extends PhoneStateListener{
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			case TelephonyManager.CALL_STATE_RINGING:
				Intent i = new Intent();
				i.setClass(HookPhoneService.this, Main.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;
			default:
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}    	
    }
	
}