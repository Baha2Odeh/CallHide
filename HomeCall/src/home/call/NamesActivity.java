package home.call;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class NamesActivity extends Activity {
	ArrayList<Names> names = new ArrayList<Names>();
	ListView listview;
	Numberdapter nm;
	NamesActivity ac = this; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_names);
		listview = (ListView)findViewById(R.id.listview);
		//names.add(new Names("1","AA","AA"));
		nm = new Numberdapter(ac, names);
		listview.setAdapter(nm);
    	///nm.notifyDataSetChanged();
		
		Thread thread = new Thread() {
		    @Override
		    public void run() {
		        
		    	//ContentResolver cr = getContentResolver();
		        Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,
		        		   ContactsContract.Contacts.HAS_PHONE_NUMBER + " = 1", 
		        		   null, 
		        		   "UPPER(" + ContactsContract.Contacts.DISPLAY_NAME + ") ASC");
		        if (cur.getCount() > 0) {
		        	
		           while (cur.moveToNext()) {
		                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
		                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		                Log.i("Names", name);
		                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) 
		                {
		                    
		                	Set<String> c = new HashSet<String>();
		                	// Query phone here. Covered next
		                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null); 
		                    while (phones.moveToNext()) { 
		                  	  String phoneId = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
		                	  String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
		                	  c.add(phoneNumber.replace("-",""));	
		                    } 
		                    phones.close(); 
		                    	names.add(new Names(id,name, c));
		                    	
		                    	
		                }
		            }
		        }
		        runOnUiThread(new Runnable() {
		            @Override
		            public void run() {
		            //sendJoinRequest();
		            	nm.notifyDataSetChanged();
		            }
		        });
		    	
		    	
		        
		    }
		};
		thread.start();
	}
}
