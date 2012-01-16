package org.codemash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class JSONSessionAdapter extends BaseAdapter {

	private Context context;
	private final JSONArray values;
	private ViewStates viewState;
	
	public enum ViewStates{
		FULL,
		PARTIAL;
	}
	
	public JSONSessionAdapter(Context context, JSONArray list, ViewStates viewState) {
		super();
		this.context = context;
		values=list;
		this.viewState = viewState;
	}
	
	@Override
	public int getCount() {
		return values.length();
	}

	@Override
	public Object getItem(int position){
		try {
			return values.getJSONObject(position);
		} catch (JSONException e) {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, android.R.layout.two_line_list_item, null);
		JSONObject item = (JSONObject)getItem(position);
		switch(viewState){
			case PARTIAL:
				getPartialView(item, convertView);
				break;
			case FULL:
				getFullView(item, convertView);
				break;
				
		}
		return convertView;
	}
	
	private void getPartialView(JSONObject item, View convertView){
		TextView title = (TextView)convertView.findViewById(android.R.id.text1);
		TextView speaker = (TextView)convertView.findViewById(android.R.id.text2);
		//Could use either 2 trys or transactional work
		try {
			title.setText(item.getString("title"));
			speaker.setText(item.getString("SpeakerName"));
		} catch (JSONException e) {
			//could add logging
		}
	}
	
	private void getFullView(JSONObject item, View convertView){
		TextView title = (TextView)convertView.findViewById(R.id.txtTitle);
		TextView speaker = (TextView)convertView.findViewById(R.id.txtSpeaker);
		TextView abstractText = (TextView)convertView.findViewById(R.id.txtAbstract);
		TextView room = (TextView)convertView.findViewById(R.id.txtRoom);
		TextView startTime = (TextView)convertView.findViewById(R.id.txtStartTime);
		TextView difficulty = (TextView)convertView.findViewById(R.id.txtDifficulty);
		TextView technology = (TextView)convertView.findViewById(R.id.txtTechnology);
		//Could use either 2 trys or transactional work
		try {
			title.setText(item.getString("Title"));
			speaker.setText(item.getString("SpeakerName"));
			abstractText.setText(item.getString("Abstract"));
			room.setText(item.getString("Room"));
			startTime.setText(item.getString("Start"));
			difficulty.setText(item.getString("Difficulty"));
			technology.setText(item.getString("Technology"));
//			interested.setChecked(new Boolean(item.getString("Abstract")));
		} catch (JSONException e) {
			//could add logging
		}
	}
}
