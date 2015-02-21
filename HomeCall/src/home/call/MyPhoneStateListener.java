package home.call;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Binder;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyPhoneStateListener extends PhoneStateListener {

    public static Boolean phoneRinging = false;
    private static Context context;
	SharedPreferences  prefs;
    
    MyPhoneStateListener(Context context){
    	MyPhoneStateListener.setContext(context);
    	prefs=context.getSharedPreferences("bzoor",Activity.MODE_PRIVATE);
    	//prefs = context.getApplicationContext().getSharedPreferences("MyPref", 0);
    }
    public static Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		MyPhoneStateListener.context = context;
	}
    @SuppressWarnings("deprecation")
	public void onCallStateChanged(int state, String incomingNumber) {

        switch (state) {
        case TelephonyManager.CALL_STATE_IDLE:
            Log.d("DEBUG", "IDLE");
            if(phoneRinging){
            	getContext().stopService(new Intent(getContext(), FlyBitch.class));
            }
            phoneRinging = false;
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            Log.d("DEBUG", "OFFHOOK");
            if(phoneRinging){
            	getContext().stopService(new Intent(getContext(), FlyBitch.class));
            }
            phoneRinging = false;
            break;
            
        case TelephonyManager.CALL_STATE_RINGING:
        	if(!phoneRinging && prefs.getBoolean("reject",false)){
        		
        		boolean isFav = prefs.getBoolean(context.getPackageName()+"_LIST_NUM_"+incomingNumber, false);
        		Toast.makeText(context, isFav+"" +incomingNumber, Toast.LENGTH_LONG).show();
        		if( (prefs.getInt("CHOOSE",0)==2 && !isFav)  
        		 || (prefs.getInt("CHOOSE",0)==1 && isFav) 
        		 || (prefs.getInt("CHOOSE",0)==0)){
        		
        			int deviceMode;
	        		AudioManager aManager=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
	        		deviceMode = aManager.getRingerMode();
					aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					aManager.setStreamVolume(AudioManager.STREAM_ALARM, 0, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
	        		disconnectCall();
	        		aManager.setRingerMode(deviceMode);
					deviceMode = AudioManager.RINGER_MODE_SILENT;
	
					if(prefs.getBoolean("Message",false)){
						try{
						 CompatibilitySmsManager mSmsManager = CompatibilitySmsManager.getDefault();
						 mSmsManager.sendTextMessage(incomingNumber, null, prefs.getString("MessageText","I'm Busy"), null, null);
						 break;
						}catch(Exception i){
							
						}
					}	
					
					break;
				
        			
        			
        		}
        		
				
        	}
            Log.d("DEBUG", "RINGING");
            if(!phoneRinging)
            	getContext().startService(new Intent(getContext(), FlyBitch.class));
            phoneRinging = true;
            break;
        }
    }
    public void disconnectCall(){
    	 try {

    	    String serviceManagerName = "android.os.ServiceManager";
    	    String serviceManagerNativeName = "android.os.ServiceManagerNative";
    	    String telephonyName = "com.android.internal.telephony.ITelephony";
    	    Class<?> telephonyClass;
    	    Class<?> telephonyStubClass;
    	    Class<?> serviceManagerClass;
    	    Class<?> serviceManagerNativeClass;
    	    Method telephonyEndCall;
    	    Object telephonyObject;
    	    Object serviceManagerObject;
    	    telephonyClass = Class.forName(telephonyName);
    	    telephonyStubClass = telephonyClass.getClasses()[0];
    	    serviceManagerClass = Class.forName(serviceManagerName);
    	    serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
    	    Method getService = // getDefaults[29];
    	    serviceManagerClass.getMethod("getService", String.class);
    	    Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
    	    Binder tmpBinder = new Binder();
    	    tmpBinder.attachInterface(null, "fake");
    	    serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
    	    IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
    	    Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
    	    telephonyObject = serviceMethod.invoke(null, retbinder);
    	    telephonyEndCall = telephonyClass.getMethod("endCall");
    	    telephonyEndCall.invoke(telephonyObject);
    	    
    	  } catch (Exception e) {
    	    e.printStackTrace();
    	   // Log.error(DialerActivity.this,
    	    //        "FATAL ERROR: could not connect to telephony subsystem");
    	    //Log.error(DialerActivity.this, "Exception object: " + e); 
    	 }
    	}

}