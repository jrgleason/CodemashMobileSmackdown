package org.codemash;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CodemashSessionListActivity extends Activity{
	private final String URL = "http://www.codemash.org/rest/sessions.json";
	private String sessionXML;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        sessionXML = convertStreamToString(retrieveStream(URL));
    }
    public String convertStreamToString(InputStream is) { 
        return new Scanner(is).useDelimiter("\\A").next();
    }

	private InputStream retrieveStream(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse getResponse = client.execute(getRequest);
			final int statusCode = getResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w(getClass().getSimpleName(), "Error " + statusCode + " for URL " + url);
				return null;
			}
			HttpEntity getResponseEntity = getResponse.getEntity();
			return getResponseEntity.getContent();
		}
		catch (IOException e) {
			getRequest.abort();
			Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
		}
		return null;
	}

	
	private JSONArray getJSONObject() throws JSONException{
		return new JSONArray(sessionXML);
	}
	
	private void parseSessions(String xml) {
		try {
			ListView list = (ListView)findViewById(R.id.sessionList);
			list.setTextFilterEnabled(true);
			list.setAdapter(new JSONSessionAdapter(getApplicationContext(), getJSONObject(), JSONSessionAdapter.ViewStates.PARTIAL));
			list.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int pos, long id) {
					JSONSessionAdapter jsa = null;
					try{
						jsa = new JSONSessionAdapter(getApplicationContext(), getJSONObject(), JSONSessionAdapter.ViewStates.PARTIAL);
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent detail = new Intent(getApplicationContext(), SessionDetails.class);
					detail.putExtra("position", pos);
					detail.putExtra("JSONObject", sessionXML);
					startActivity(detail);
				}
				
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}