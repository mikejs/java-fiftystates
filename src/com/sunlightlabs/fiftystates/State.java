package com.sunlightlabs.fiftystates;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class State {
	public String name;
	public String abbreviation;
	public String legislature_name;
	public String upper_chamber_name, lower_chamber_name;
	public String upper_title, lower_title;
	public int upper_term, lower_term;
	
	ArrayList<State.Session> sessions = new ArrayList<State.Session>();

	public State(JSONObject json) throws JSONException {
		name = json.getString("name");
		abbreviation = json.getString("abbreviation");
		legislature_name = json.getString("legislature_name");
		upper_chamber_name = json.getString("upper_chamber_name");
		lower_chamber_name = json.getString("lower_chamber_name");
		upper_title = json.getString("upper_chamber_title");
		lower_title = json.getString("lower_chamber_title");
		upper_term = json.getInt("upper_chamber_term");
		lower_term = json.getInt("lower_chamber_term");
			
		JSONArray j_sessions = json.getJSONArray("sessions");
		int length = j_sessions.length();
		for (int i = 0; i < length; i++) {
			sessions.add(new State.Session(j_sessions.getJSONObject(i)));
		}
	}
	
	public static State get(String abbreviation) throws FiftystatesException {
		String url = Fiftystates.url(abbreviation, "");
		try {
			return new State(new JSONObject(Fiftystates.fetchJSON(url)));
		} catch (JSONException e) {
			throw new FiftystatesException(e, "Problem parsing the JSON from " + url);
		}
	}
	
	public class Session {
		public String name;
		public int start_year, end_year;
		
		public Session(JSONObject json) throws JSONException {
			name = json.getString("name");
			start_year = json.getInt("start_year");
			end_year = json.getInt("end_year");
		}
	}
}
