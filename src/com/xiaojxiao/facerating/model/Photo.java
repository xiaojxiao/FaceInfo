package com.xiaojxiao.facerating.model;

import static com.xiaojxiao.facerating.model.Face.fromJsonArray;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo
{
	private final String url;

	private final String pid;

	private final int width;

	private final int height;

	private List<Face> tags;

	public Photo(final JSONObject jObj) throws JSONException
	{
		url = jObj.getString("url");
		pid = jObj.getString("pid");

		width = jObj.getInt("width");
		height = jObj.getInt("height");

		tags = fromJsonArray(jObj.getJSONArray("tags"));
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Photo [height=").append(height).append(", pid=").append(pid).append(", url=").append(url)
				.append(", width=").append(width).append("]").append("\ntags=").append(tags);

		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.Photo#getFaceCount()
	 */
	public int getFaceCount()
	{
		return getFaces().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.Photo#getURL()
	 */
	public String getURL()
	{
		return this.url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.Photo#getPID()
	 */
	public String getPID()
	{
		return this.pid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.Photo#getWidth()
	 */
	public int getWidth()
	{
		return this.width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.Photo#getHeight()
	 */
	public int getHeight()
	{
		return this.height;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.face.api.client.model.Photo#getFaces()
	 */
	public List<Face> getFaces()
	{
		return this.tags;
	}

	public void scaleFaceRects(float width, float height)
	{
		for(Face f : getFaces())
		{
			final Rect r = f.getRectangle();

			r.left *= (width / 100);
			r.right *= (width / 100);
			r.top *= (height / 100);
			r.bottom *= (height / 100);
		}
	}

	public Face getFace()
	{
		try
		{
			return getFaces().get(0);
		}

		catch (IndexOutOfBoundsException ioob)
		{
			return null;
		}
	}
}