package home.call;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;

public class FlyBitch extends Service {


	private WindowManager windowManager;
	private ImageView chatHead;
	int tempX , tempY;
	static int deviceMode = AudioManager.RINGER_MODE_SILENT;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
	public void onCreate() {
		super.onCreate();
		SharedPreferences prefs=getSharedPreferences("bzoor",Activity.MODE_PRIVATE);
		if(prefs.getBoolean("silentOnCall",false)){
			AudioManager aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
			if(aManager.getRingerMode() != AudioManager.RINGER_MODE_SILENT){
				deviceMode = aManager.getRingerMode();
				aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			}
		}
		
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		chatHead = new ImageView(this);
		
		chatHead.setImageResource(R.drawable.floating);
		
		final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_PHONE,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);

		params.gravity = Gravity.TOP | Gravity.RIGHT;
		params.x = 30;
		params.y = 90;
		tempX=params.x;

		
		
		tempY=params.y;
			chatHead.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View x) {
				
				// TODO Auto-generated method stub
				if(params.x==tempX&&tempY==params.y)
				{
					Intent homeIntent= new Intent(Intent.ACTION_MAIN);
					homeIntent.addCategory(Intent.CATEGORY_HOME);
					homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(homeIntent);
					AudioManager aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
					if(aManager.getRingerMode() != AudioManager.RINGER_MODE_SILENT){
						deviceMode = aManager.getRingerMode();
						aManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					}
			}
			}
		});


		windowManager.addView(chatHead, params);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (chatHead != null) windowManager.removeView(chatHead);
		AudioManager aManager=(AudioManager)getSystemService(AUDIO_SERVICE);		
		if(aManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT
		 && deviceMode != AudioManager.RINGER_MODE_SILENT){
			aManager.setRingerMode(deviceMode);
			deviceMode = AudioManager.RINGER_MODE_SILENT;
		}

	}

}
