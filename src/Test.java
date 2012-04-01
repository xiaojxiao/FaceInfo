import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

public class Test
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("test");

		String url = "http://api.face.com/faces/detect.json?api_key=3753005dc2b2b4394de124d2ae622ed1&api_secret=6469d58282d4968306c8c97d24aef5f1&urls=http://media-cdn.pinterest.com/upload/237494580319506362_MQ9371Tx.jpg&detector=Aggressive&attributes=all&";

		String filePath = "C:\\Users\\Jing\\Desktop\\IMG_20120331_162920.jpg";

		File file = new File(filePath);
		System.out.println(file.getAbsolutePath());

		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);

		HttpPost httppost = new HttpPost(
				"http://api.face.com/faces/detect.json?api_key=3753005dc2b2b4394de124d2ae622ed1&api_secret=6469d58282d4968306c8c97d24aef5f1&detector=Aggressive&attributes=all&");

		MultipartEntity mpEntity = new MultipartEntity();
		ContentBody cbFile = new FileBody(file, "image/jpeg");
		mpEntity.addPart("userfile", cbFile);

		httppost.setEntity(mpEntity);

		// ArrayList<NameValuePair> postParameters = new
		// ArrayList<NameValuePair>();
		// postParameters.add(new BasicNameValuePair("param1", "param1_value"));
		// postParameters.add(new BasicNameValuePair("param2", "param2_value"));
		//
		// httppost(new UrlEncodedFormEntity(postParameters));

		HttpResponse response;

		try
		{
			response = httpclient.execute(httppost);
			System.out.println(response);

			HttpEntity entity = response.getEntity();

			InputStream instream = entity.getContent();
			String result = convertStreamToString(instream);

			System.out.println(result);

			JSONObject json = new JSONObject(result);
			
			JSONArray photos = json.getJSONArray("photos");
			
			for (int i = 0; i < photos.length(); i++)
			{
				JSONObject photo = photos.getJSONObject(i);
				JSONArray tags = photo.getJSONArray("tags");
				
				JSONObject tag = tags.getJSONObject(0);
				JSONObject attributes = tag.getJSONObject("attributes");
				JSONObject age_est = attributes.getJSONObject("age_est");
				String value = age_est.getString("value");
				
				System.out.println(value);
				
				//JSONArray attributes = tag.getJSONArray("attributes");
				
				
				
				
				
				//System.out.println(nameArray.getString(i));
				
				//JSONObject item = new JSONObject(valArray.getString(i));
				//JSONArray item_names = item.names();
			}

			JSONArray nameArray = json.names();
			JSONArray valArray = json.toJSONArray(nameArray);

			
			//System.out.println(valArray.getJSONArray(0).get(0));
		}
		catch (Exception e)
		{

		}
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
