package com.sunlightlabs.fiftystates;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bill extends FiftystatesObject {
	public String state;
	public String session;
	public String chamber;
	public String bill_id;
	public String title;

	public ArrayList<Bill.Sponsor> sponsors = new ArrayList<Bill.Sponsor>();
	public ArrayList<Bill.Action> actions = new ArrayList<Bill.Action>();

	public Bill(JSONObject json) throws JSONException {
		super(json);

		state = json.getString("state");
		session = json.getString("session");
		chamber = json.getString("chamber");
		bill_id = json.getString("bill_id");
		title = json.getString("title");

		JSONArray j_sponsors = json.getJSONArray("sponsors");
		int length = j_sponsors.length();
		for (int i = 0; i < length; i++) {
			sponsors.add(new Bill.Sponsor(j_sponsors.getJSONObject(i)));
		}

		JSONArray j_actions = json.getJSONArray("actions");
		length = j_actions.length();
		for (int i = 0; i < length; i++) {
			actions.add(new Bill.Action(j_actions.getJSONObject(i)));
		}
	}


	public static Bill get(String state, String session,
			String chamber, String bill_id)  throws FiftystatesException {
		String method = state + "/" + session + "/" + chamber + "/bills/" + bill_id + "/";
		String url = Fiftystates.url(method, "");
		try {
			return new Bill(new JSONObject(Fiftystates.fetchJSON(url)));
		} catch (JSONException e) {
			throw new FiftystatesException(e, "Problem parsing JSON from " + url);
		}
	}

	public static ArrayList<Bill> search(String query, String state, String session, String chamber) throws FiftystatesException {
		String method = "bills/search/";
		String queryString = "q=" + query + "&state=" + state + "&session=" + session + "&chamber=" + chamber;
		String url = Fiftystates.url(method, queryString);
		try {
			JSONArray array = new JSONArray(Fiftystates.fetchJSON(url));
			ArrayList<Bill> bills = new ArrayList<Bill>();

			int length = array.length();
			for (int i = 0; i < length; i++) {
				bills.add(new Bill(array.getJSONObject(i)));
			}

			return bills;
		} catch (JSONException e) {
			throw new FiftystatesException(e, "Problem parsing the JSON from " + url);
		}
	}

	public class Sponsor {
		public String name;
		public String id;

		public Sponsor(JSONObject json) throws JSONException{
			name = json.getString("name");
			id = json.getString("leg_id");
		}
	}

	public class Action {
		public String action;
		public String actor;
		public String date;

		public Action(JSONObject json) throws JSONException{
			action = json.getString("action");
			actor = json.getString("actor");
			date = json.getString("date");
		}
	}
}
