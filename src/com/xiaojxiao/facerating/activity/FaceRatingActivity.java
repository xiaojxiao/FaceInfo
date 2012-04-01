package com.xiaojxiao.facerating.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.datatype.Duration;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.xiaojxiao.facerating.R;
import com.xiaojxiao.facerating.model.Face;
import com.xiaojxiao.facerating.model.Photo;
import com.xiaojxiao.facerating.util.FaceAPI;
import com.xiaojxiao.facerating.util.MyDebug;
import com.xiaojxiao.facerating.view.CameraPreview;

public class FaceRatingActivity extends Activity
{
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;

	private Camera mCamera;
	private CameraPreview mPreview;
	public static Context context;

	private PictureCallback mPicture = new PictureCallback()
	{

		@Override
		public void onPictureTaken(byte[] data, Camera camera)
		{

			File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
			if (pictureFile == null)
			{
				MyDebug.print("Error creating media file, check storage permissions");
				return;
			}

			try
			{
				FileOutputStream fos = new FileOutputStream(pictureFile);
				fos.write(data);
				fos.close();
				
				String rsp_string = FaceAPI.queryFaceAPI(pictureFile);
				MyDebug.print(rsp_string);
				
				JSONObject json = new JSONObject(rsp_string);
				JSONObject photoJSON = json.getJSONArray("photos").getJSONObject(0);
				
				Photo photo = new Photo(photoJSON);
				
				String age = photo.getFace().getAgeEst() + "Ëê";
				
				/*String age = json.getJSONArray("photos")
								 .getJSONObject(0)
								 .getJSONArray("tags")
								 .getJSONObject(0)
								 .getJSONObject("attributes")
								 .getJSONObject("age_est")
								 .getString("value");*/
				
				
				Toast.makeText(context, age, 5000).show();
				
			}
			catch (FileNotFoundException e)
			{
				MyDebug.print("File not found: " + e.getMessage());
			}
			catch (IOException e)
			{
				MyDebug.print("Error accessing file: " + e.getMessage());
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		context = this;

		if (checkCameraHardware(this))
		{
			MyDebug.print("Camera ready");
		}

		// Create an instance of Camera
		mCamera = getCameraInstance();

		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);

		// Add a listener to the Capture button
		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// get an image from the camera
				mCamera.takePicture(null, null, mPicture);
			}
		});
		
		Button retakeButton = (Button) findViewById(R.id.button_retake);
		retakeButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mCamera.startPreview();
			}
		});

		/*
		 * if(checkCameraHardware(this)) { MyDebug.print("Camera ready"); }
		 * 
		 * // create Intent to take a picture and return control to the calling
		 * application Intent intent = new
		 * Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		 * 
		 * //Uri fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a
		 * file to save the image //intent.putExtra(MediaStore.EXTRA_OUTPUT,
		 * fileUri); // set the image file name
		 * 
		 * // start the image capture Intent startActivityForResult(intent,
		 * 100);
		 */
	}

	private boolean checkCameraHardware(Context context)
	{
		if (context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA))
		{
			// this device has a camera
			return true;
		}
		else
		{
			// no camera on this device
			return false;
		}
	}

	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance()
	{
		Camera c = null;
		try
		{
			c = Camera.open(1); // attempt to get a Camera instance (front camera)
			c.setDisplayOrientation(90);	//set it as portrait
		}
		catch (Exception e)
		{
			// Camera is not available (in use or does not exist)
		}
		return c; // returns null if camera is unavailable
	}

	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type)
	{
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type)
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
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE)
		{
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		}
		else if (type == MEDIA_TYPE_VIDEO)
		{
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		}
		else
		{
			return null;
		}

		return mediaFile;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		releaseCamera(); // release the camera immediately on pause event
	}

	private void releaseCamera()
	{
		if (mCamera != null)
		{
			mCamera.release(); // release the camera for other applications
			mCamera = null;
		}
	}
}