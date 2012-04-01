package com.xiaojxiao.facerating.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class FaceAPI
{
	public static String queryFaceAPI(File file)
	{
		String rsp_string = "no response";
		
		HttpClient httpclient = new DefaultHttpClient();
		
		HttpPost httppost = new HttpPost("http://api.face.com/faces/detect.json?api_key=3753005dc2b2b4394de124d2ae622ed1&api_secret=6469d58282d4968306c8c97d24aef5f1&detector=Aggressive&attributes=all&");
		
		MultipartEntity mpEntity = new MultipartEntity();
	    ContentBody cbFile = new FileBody(file, "image/jpeg");
	    mpEntity.addPart("userfile", cbFile);
	    
		httppost.setEntity(mpEntity);
		
		HttpResponse response;
		
		try
		{
			response = httpclient.execute(httppost);
			MyDebug.print(response.toString());
			
			HttpEntity entity = response.getEntity();
			
			InputStream instream = entity.getContent();
			rsp_string = convertStreamToString(instream);
		}
		catch(Exception e)
		{
			
		}
		
		return rsp_string;
	}

	private static String convertStreamToString(InputStream is)
	{
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return sb.toString();
	}
}
