package com.sunlightlabs.fiftystates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class State extends FiftystatesObject {
	public String name;
	public String abbreviation;
	public String legislature_name;
	public String upper_chamber_name, lower_chamber_name;
	public String upper_chamber_title, lower_chamber_title;
	public int upper_chamber_term, lower_chamber_term;

	public ArrayList<State.Session> sessions = new ArrayList<State.Session>();

	private static Map<String, State> states = new HashMap<String, State>();

	public State(JSONObject json) throws JSONException {
		super(json);

		name = json.getString("name");
		abbreviation = json.getString("abbreviation");
		legislature_name = json.getString("legislature_name");
		upper_chamber_name = json.getString("upper_chamber_name");
		lower_chamber_name = json.getString("lower_chamber_name");
		upper_chamber_title = json.getString("upper_chamber_title");
		lower_chamber_title = json.getString("lower_chamber_title");
		upper_chamber_term = json.getInt("upper_chamber_term");
		lower_chamber_term = json.getInt("lower_chamber_term");

		JSONArray j_sessions = json.getJSONArray("sessions");
		int length = j_sessions.length();
		for (int i = 0; i < length; i++) {
			sessions.add(new Session(j_sessions.getJSONObject(i)));
		}
	}

	public static State find(String abbreviation) throws FiftystatesException {
		if (states.containsKey(abbreviation)) {
			return states.get(abbreviation);
		}

		String url = Fiftystates.url(abbreviation, "");
		try {
			State s = new State(new JSONObject(Fiftystates.fetchJSON(url)));
			states.put(abbreviation, s);
			return s;
		} catch (JSONException e) {
			throw new FiftystatesException(e, "Problem parsing the JSON from " + url);
		}
	}

	public String getActiveSessionName() {
		return sessions.get(sessions.size() - 1).name;
	}

	public class Session {
		public String name;
		public int start_year, end_year;

		public ArrayList<String> sub_sessions = new ArrayList<String>();

		public Session(JSONObject json) throws JSONException {
			name = json.getString("name");
			start_year = json.getInt("start_year");
			end_year = json.getInt("end_year");

			JSONArray j_subs = json.getJSONArray("sub_sessions");
			int length = j_subs.length();
			for (int i = 0; i < length; i++) {
				sub_sessions.add(j_subs.getString(i));
			}
		}
	}
}
