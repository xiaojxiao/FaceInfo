package com.xiaojxiao.facerating.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.xiaojxiao.facerating.R;
import com.xiaojxiao.facerating.model.Photo;
import com.xiaojxiao.facerating.util.FaceAPI;
import com.xiaojxiao.facerating.util.MyDebug;
import com.xiaojxiao.facerating.view.CustomImageView;

public class FaceCapture extends Activity
{
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 188;
	private Uri fileUri;
	
	public static Context context;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture_view);
		
		context = this;

		// Add a listener to the Capture button
		Button button_take_photo = (Button) findViewById(R.id.button_take_photo);
		button_take_photo.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// get an image from the camera
				//mCamera.takePicture(null, null, mPicture);
				
				// create Intent to take a picture and return control to the calling application
			    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

			    // start the image capture Intent
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
		
		Button button_get_info = (Button) findViewById(R.id.button_get_info);
		button_get_info.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(fileUri != null)
				{
					try
					{
						File imageFile = new File(fileUri.getPath());
						String rsp_string = FaceAPI.queryFaceAPI(imageFile);
						
						JSONObject json = new JSONObject(rsp_string);
						JSONObject photoJSON = json.getJSONArray("photos").getJSONObject(0);
						
						Photo photo = new Photo(photoJSON);
						
						//String age = photo.getFace().getAgeEst() + "Ëê";
						//Toast.makeText(context, age, 5000).show();
						
						CustomImageView imageView = (CustomImageView) findViewById(R.id.capture_preview);
						imageView.drawInfo(photo);
					}
					catch (Exception e)
					{
						MyDebug.print(e.getMessage());
					}
				}
			}
		});
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type)
	{
		return Uri.fromFile(getOutputImageFile());
	}

	private static File getOutputImageFile()
	{
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						"MyCameraApp");
		
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists())
		{
			if (!mediaStorageDir.mkdirs())
			{
				MyDebug.print("failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		
		File imageFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");

		return imageFile;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());
				
				CustomImageView imageView = (CustomImageView) findViewById(R.id.capture_preview);
				imageView.clearInfo();
				imageView.setImageBitmap(bitmap);
			}
		}
	}
	
}