package home.call;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class CSpinnerAdapter extends ArrayAdapter<Names>{
		Context context;
		ArrayList<Names> names;
		public CSpinnerAdapter(Context context,ArrayList<Names> names) {
			super(context,0,names);
			this.context = context;
			this.names = names;
		// TODO Auto-generated constructor stub
		}

				
		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public int getViewTypeCount() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_entry,
					null);
			
			TextView lblFrom = (TextView) convertView.findViewById(R.id.textView1);
			lblFrom.setText(names.get(position).toString());
			
			//CheckBox addtolist = (CheckBox)convertView.findViewById(R.id.addtolist);
			
			
			return convertView;
		}
		
		@Override
		public int getItemViewType(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			//return names.get(arg0).name;
			return arg0;
		}
		
		@Override
		public Names getItem(int arg0) {
			// TODO Auto-generated method stub
			return names.get(arg0);
			
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return names.size();
		}
		
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.list_entry,
					null);
			
			TextView lblFrom = (TextView) convertView.findViewById(R.id.textView1);
			lblFrom.setText(names.get(position).toString());
			return convertView;
		}
}
