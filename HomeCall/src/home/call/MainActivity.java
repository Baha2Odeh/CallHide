package home.call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;



public class MainActivity extends Activity {
	SharedPreferences  prefs;
	EditText message;
	CheckBox silentToall,reject , messageCheck;
	Spinner choose;
	Button contacts;
	Button save;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
    	prefs=getSharedPreferences("bzoor",Activity.MODE_PRIVATE);
		save = (Button)findViewById(R.id.button2);
		choose=(Spinner)findViewById(R.id.spinner1);
		contacts=(Button)findViewById(R.id.button1);

		message = (EditText)findViewById(R.id.editText1);
		message.setText(prefs.getString("MessageText","I'm Busy"));
		silentToall = (CheckBox)findViewById(R.id.checkBox1);
		messageCheck = (CheckBox)findViewById(R.id.checkBox3);
		silentToall.setChecked(prefs.getBoolean("silentOnCall",false));
		reject = (CheckBox)findViewById(R.id.checkBox2);
		reject.setChecked(prefs.getBoolean("reject",false));
		messageCheck.setChecked(prefs.getBoolean("Message",false));

		if(reject.isChecked())
		{
			choose.setVisibility(View.VISIBLE);
			contacts.setVisibility(View.VISIBLE);
			messageCheck.setVisibility(View.VISIBLE);

		}
		else 
		{
			choose.setVisibility(View.GONE);
			contacts.setVisibility(View.GONE);
			messageCheck.setVisibility(View.GONE);


		}
		if(messageCheck.isChecked())
			{
			message.setVisibility(View.VISIBLE);
			save.setVisibility(View.VISIBLE);
			}
		else 
		{
			message.setVisibility(View.GONE);
			save.setVisibility(View.GONE);	
		}	
		reject.setOnCheckedChangeListener(new  OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				prefs.edit().putBoolean("reject", reject.isChecked()).commit();
				if(isChecked)
				{
	    			choose.setVisibility(View.VISIBLE);
	    			messageCheck.setVisibility(View.VISIBLE);
	    			if(messageCheck.isChecked())
	    			{
					message.setVisibility(View.VISIBLE);
					save.setVisibility(View.VISIBLE);
	    			}
					if(choose.getSelectedItemId()!=0)
		    			contacts.setVisibility(View.VISIBLE);
				}
				else
				{
	    			messageCheck.setVisibility(View.GONE);
	    			choose.setVisibility(View.GONE);
	    			message.setVisibility(View.GONE);
					save.setVisibility(View.GONE);
					contacts.setVisibility(View.GONE);
	    			
				}
				
			}
		});
		
		
	//	contacts.setOnCheckedChangeListener(new  OnCheckedChangeListener() {
	//		
	//		@Override
	//		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	//			// TODO Auto-generated method stub
	//			
	//		}
	//	});
		
		contacts.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,NamesActivity.class);
				startActivity(i);
			}
		});
		save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(messageCheck.isChecked())
				prefs.edit().putString("MessageText",message.getText()+" ").commit();
					

			}
		});
		
		silentToall.setOnCheckedChangeListener(new  OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				prefs.edit().putBoolean("silentOnCall", silentToall.isChecked()).commit();
			}
		});
		messageCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				prefs.edit().putBoolean("Message", messageCheck.isChecked()).commit();

				if (isChecked)
    			{
				message.setVisibility(View.VISIBLE);
				save.setVisibility(View.VISIBLE);
    			}
				else 
    			{
				message.setVisibility(View.GONE);
				save.setVisibility(View.GONE);
    			}
	
					
			}
		});
		
		
		

		
		
		
		
		
		
		// from here
                   
            // to here
            
    		
    		ArrayList<String> choice = new ArrayList<String>();
    		choice.add("Reject All");
    		choice.add("Black List");
    		choice.add("White List");
 		    ArrayAdapter<String> choiceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,choice);
	   		choose.setAdapter(choiceAdapter);
	   		choiceAdapter.setNotifyOnChange(true);
    		choose.setSelection(prefs.getInt("CHOOSE",0));
    		
    		choose.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					prefs.edit().putInt("CHOOSE",(int)choose.getSelectedItemId()).commit();

					if(choose.getSelectedItemId()==0)
		    			contacts.setVisibility(View.GONE);
					else 
		    			contacts.setVisibility(View.VISIBLE);
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
	    			contacts.setVisibility(View.GONE);
				}
			});        
    		
    		
    		//ArrayAdapter<Names> namesAdapter = new ArrayAdapter<Names>(this, R.id.textView1,R.layout.list_entry,names); 
    		//contacts.setAdapter(namesAdapter);
    		
    		//namesAdapter.setNotifyOnChange(true);
    		//CSpinnerAdapter cSipn= new CSpinnerAdapter(getApplicationContext(),names);
        	//contacts.setAdapter(cSipn);
        	//cSipn.setNotifyOnChange(true);

    		
		
		
		
	}

	@Override
	protected void onResume() {
		/*Bundle bundle = getIntent().getExtras();

		if(bundle != null && bundle.getString("LAUNCH").equals("YES")) {
			startService(new Intent(MainActivity.this, FlyBitch.class));
		}*/
		super.onResume();
	}
	
	
	
	
	
}
