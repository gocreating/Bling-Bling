package com.test.blingbling;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class Main extends Activity {
	private static final String TAG = "BlingBling";
	
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if ("com.test.blingbling.HookPhoneService".equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if(!isMyServiceRunning())
			startService(new Intent(Main.this, HookPhoneService.class));
		
		Button btnStop = (Button)findViewById(R.id.btnStop);        
        btnStop.setOnClickListener(btnListener);
	}
	
	private Button.OnClickListener btnListener = new Button.OnClickListener(){
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.btnStop:
				Log.d(TAG, "stop service");
		        stopService(new Intent(Main.this, HookPhoneService.class));
				break;
			}
		}    	
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
