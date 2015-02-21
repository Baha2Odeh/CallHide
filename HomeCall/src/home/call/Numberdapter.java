package home.call;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class Numberdapter  extends BaseAdapter {
  private final Context context;
  private final ArrayList<Names> values;
  SharedPreferences prefs;

  public Numberdapter(Context context, ArrayList<Names> values) {
    this.context = context;
    this.values = values;
	prefs=context.getSharedPreferences("bzoor",Activity.MODE_PRIVATE);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View rowView = inflater.inflate(R.layout.list_entry, parent, false);
    //TextView textView = (TextView) rowView.findViewById(R.id.textView1);
    //textView.setText("sss");
    
	TextView textView = (TextView) rowView.findViewById(R.id.textView1);
	textView.setText(values.get(position).toString());
	
	CheckBox addtolist = (CheckBox)rowView.findViewById(R.id.checkBox1);
	addtolist.setChecked(getFav(position));
	addtolist.setTag(position);
	addtolist.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			CheckBox cb = (CheckBox)arg0;
			setFav((Integer)arg0.getTag(),cb.isChecked());
			
		}
	});
    return rowView;
  }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return values.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	private void setFav(int pos,boolean result)
	{	
		
		Names name = values.get(pos);
		for (String num : name.numbers) {
			prefs.edit().putBoolean(context.getPackageName()+"_LIST_NUM_"+num, result).commit();
		}
		
	}
	private Boolean getFav(int pos)
	{
		
		Names name = values.get(pos);
		boolean isFav = false;
		for (String num : name.numbers) {
			isFav = prefs.getBoolean(context.getPackageName()+"_LIST_NUM_"+num, false);
			if(isFav==true)
				break;
		}
		
		return isFav;
	}
} 
