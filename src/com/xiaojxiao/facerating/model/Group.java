package com.xiaojxiao.facerating.model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Group
{
	private final int gid;

	private final String uid;

	private final List<String> tids;

	public Group(final JSONObject jObj) throws JSONException
	{
		this.gid = jObj.optInt("gid");
		this.uid = jObj.optString("uid");

		this.tids = new LinkedList<String>();

		final JSONArray jArr = jObj.getJSONArray("tids");

		for(int i = 0; i < jArr.length(); i++)
		{
			tids.add(jArr.getString(i));
		}
	}

	public int getGid()
	{
		return gid;
	}

	public String getUid()
	{
		return uid;
	}

	public List<String> getTids()
	{
		return tids;
	}
}