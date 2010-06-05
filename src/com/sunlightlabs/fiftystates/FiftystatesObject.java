package com.sunlightlabs.fiftystates;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FiftystatesObject {
	public String created_at, updated_at;
	
	public ArrayList<FiftystatesObject.Source> sources = new ArrayList<FiftystatesObject.Source>();
	
	public FiftystatesObject(JSONObject json) throws JSONException {
		if (!json.isNull("created_at")) {
			created_at = json.getString("created_at");
		}
		
		if (!json.isNull("updated_at")) {
			updated_at = json.getString("updated_at");
		}
		
		if (!json.isNull("sources")) {
			JSONArray j_sources = json.getJSONArray("sources");
			int length = j_sources.length();
			for (int i = 0; i < length; i++) {
				sources.add(new FiftystatesObject.Source(j_sources.getJSONObject(i)));
			}
		}
	}
	
	public class Source {
		public String url;
		public String retrieved;

		public Source(JSONObject json) throws JSONException {
			url = json.getString("url");
			retrieved = json.getString("retrieved");
		}
	}
}
