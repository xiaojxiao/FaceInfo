package com.xiaojxiao.facerating.model;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class UserStatus
{
	private final String uid;

	private final int training_set_size;

	private final long last_trained;

	private final boolean training_in_progress;

	public UserStatus(final JSONObject jObj) throws JSONException
	{
		uid = jObj.getString("uid");
		training_set_size = jObj.getInt("training_set_size");
		last_trained = jObj.getLong("last_trained");
		training_in_progress = jObj.getBoolean("training_in_progress");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.UserStatus#getUID()
	 */
	public String getUID()
	{
		return this.uid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.UserStatus#setSize()
	 */
	public int setSize()
	{
		return this.training_set_size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.UserStatus#isInProgress()
	 */
	public boolean isInProgress()
	{
		return this.training_in_progress;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.UserStatus#getLastTrained()
	 */
	public Date getLastTrained()
	{
		return new Date(this.last_trained);
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UserStatus [last_trained=").append(last_trained).append(", training_in_progress=")
				.append(training_in_progress).append(", training_set_size=").append(training_set_size).append(", uid=")
				.append(uid).append("]");
		return builder.toString();
	}

}