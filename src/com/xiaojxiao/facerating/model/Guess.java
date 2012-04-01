package com.xiaojxiao.facerating.model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Guess extends Pair<String, Integer> implements Comparable<Guess>
{
	// Default to unknown
	public Guess()
	{
		super();

		this.first = "Unknown";
		this.second = 100;
	}

	public Guess(final JSONObject jObj) throws JSONException
	{
		super();

		this.first = jObj.getString("uid");
		this.second = jObj.getInt("confidence");
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("Guess [uid=").append(first).append(", confidence=").append(second).append("]");

		return builder.toString();
	}

	static List<Guess> fromJsonArray(JSONArray jArr) throws JSONException
	{
		final List<Guess> guesses = new LinkedList<Guess>();

		if(jArr != null)
		{
			for(int i = 0; i < jArr.length(); i++)
			{
				guesses.add(new Guess(jArr.getJSONObject(i)));
			}
		}

		return guesses;
	}

	/**
	 * Used for natural ordering
	 */
	@Override
	public int compareTo(Guess that)
	{
		if(this.second > that.second)
		{
			return 1;
		}

		if(this.second < that.second)
		{
			return -1;
		}

		return 0;
	}
}