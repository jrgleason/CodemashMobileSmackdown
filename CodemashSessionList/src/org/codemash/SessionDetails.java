package org.codemash;

import org.json.JSONArray;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;

public class SessionDetails extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
        		String pos = extras.getString("position");
        		String uri = extras.getString("JSONObject");
        		try{
        			JSONSessionAdapter jsa = new JSONSessionAdapter(getApplicationContext(), new JSONArray(uri), JSONSessionAdapter.ViewStates.PARTIAL);
        			Integer posInt = new Integer(pos);
            		jsa.getView(posInt.intValue(), this.findViewById(android.R.id.content), (ViewGroup)this.findViewById(android.R.id.content));
        		}
        		catch (Exception e) {
					// TODO: handle exception
				}
        		
        }
    }
    

	@Override
	protected void onPause() {
//		super.onPause();
//		GWUtil util = new GWUtil(getApplicationContext());
//		String sessionXML = util.getPreference("http://codemash.org/rest/sessions");
//		Serializer serializer = new Persister();
//		StringWriter sw = new StringWriter();
//		try {
//			serializer.write(codemash, sw);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
