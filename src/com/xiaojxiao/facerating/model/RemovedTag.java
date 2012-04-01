package com.xiaojxiao.facerating.model;

import org.json.JSONException;
import org.json.JSONObject;

public class RemovedTag
{
	private String removed_tid;

	private String detected_tid;

	public RemovedTag(final JSONObject jObj) throws JSONException
	{
		this.removed_tid = jObj.getString("removed_tid");
		this.detected_tid = jObj.getString("detected_tid");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.RemovedTag#getRemovedTID()
	 */
	public String getRemovedTID()
	{
		return this.removed_tid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.RemovedTag#getDetectedTID()
	 */
	public String getDetectedTID()
	{
		return this.detected_tid;
	}
}