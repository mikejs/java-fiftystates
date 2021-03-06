package com.sunlightlabs.fiftystates;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Legislator extends FiftystatesObject implements Comparable<Legislator> {
	public String full_name, first_name, last_name, middle_name;
	public String id;
	public String photo_url;

	public ArrayList<Legislator.Role> roles = new ArrayList<Legislator.Role>();

	public Legislator(JSONObject json) throws JSONException {
		super(json);

		full_name = json.getString("full_name");
		first_name = json.getString("first_name");
		last_name = json.getString("last_name");
		middle_name = json.getString("middle_name");
		id = json.getString("id");

		try {
			photo_url = json.getString("photo_url");
		} catch (JSONException e) {}

		JSONArray j_roles = json.getJSONArray("roles");
		int length = j_roles.length();
		for (int i = 0; i < length; i++) {
			roles.add(new Legislator.Role(j_roles.getJSONObject(i)));
		}
	}

	public static Legislator find(String id) throws FiftystatesException {
		return legislatorFor(Fiftystates.url("legislators/" + id, ""));
	}

	public static ArrayList<Legislator> allForChamber(String state, String session, String chamber) throws FiftystatesException {
		String method = "legislators/search/";
		String queryString = "state=" + state + "&session=" + session + "&chamber=" + chamber;
		return legislatorsFor(Fiftystates.url(method, queryString));
	}

	public static ArrayList<Legislator> allForState(String state, String session) throws FiftystatesException {
		String method = "legislators/search/";
		String queryString = "state=" + state + "&session=" + session;
		return legislatorsFor(Fiftystates.url(method, queryString));
	}

	private static Legislator legislatorFor(String url) throws FiftystatesException {
		try {
			JSONObject json = new JSONObject(Fiftystates.fetchJSON(url));
			return new Legislator(json);
		} catch (JSONException e) {
			throw new FiftystatesException(e, "Problem parsing JSON from " + url);
		}
	}

	private static ArrayList<Legislator> legislatorsFor(String url) throws FiftystatesException {
		try {
			JSONArray array = new JSONArray(Fiftystates.fetchJSON(url));
			ArrayList<Legislator> legs = new ArrayList<Legislator>();

			int length = array.length();
			for (int i = 0; i < length; i++) {
				legs.add(new Legislator(array.getJSONObject(i)));
			}

			return legs;
		} catch (JSONException e) {
			throw new FiftystatesException(e, "Problem parsing JSON from " + url);
		}
	}

	public Legislator.Role getActiveRole() {
		return roles.get(0);
	}

	public int compareTo(Legislator other) {
		return last_name.compareTo(other.last_name);
	}

	public class Role {
		public String state;
		public String session;
		public String chamber;
		public String type;
		public String party;
		public String district;
		public String start_date, end_date;

		public Role(JSONObject json) throws JSONException {
			state = json.getString("state");
			session = json.getString("session");
			type = json.getString("type");

			if (!json.isNull("chamber")) {
				chamber = json.getString("chamber");
			}

			if (!json.isNull("party")) {
				party = json.getString("party");
			}

			if (!json.isNull("district")) {
				district = json.getString("district");
			}

			if (!json.isNull("start_date")) {
				start_date = json.getString("start_date");
			}

			if (!json.isNull("end_date")) {
				end_date = json.getString("end_date");
			}
		}
	}
}
