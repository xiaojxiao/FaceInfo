package com.xiaojxiao.facerating.model;

import org.json.JSONException;
import org.json.JSONObject;

public class SavedTag
{
	private final String tid;

	private String detected_tid;

	public SavedTag(final JSONObject jObj) throws JSONException
	{
		tid = jObj.getString("tid");
		detected_tid = jObj.optString("detected_tid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.SavedTag#getTID()
	 */
	public String getTID()
	{
		return this.tid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.SavedTag#getDetectedTID()
	 */
	public String getDetectedTID()
	{
		return this.detected_tid;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("tid : ").append(tid).append("\n").append("detected_tid: ").append(detected_tid).append("\n")
				.trimToSize();

		return sb.toString();
	}
}