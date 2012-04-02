package com.xiaojxiao.facerating.view;

import com.xiaojxiao.facerating.model.Face;
import com.xiaojxiao.facerating.model.Photo;
import com.xiaojxiao.facerating.util.MyDebug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CustomImageView extends ImageView
{
	private boolean hasExtra;
	private Photo photo;
	
	public CustomImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		
		hasExtra = false;
	}

	public CustomImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public CustomImageView(Context context)
	{
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		if(hasExtra && (photo != null))
		{
			Paint paint = new Paint();
			paint.setColor(Color.RED);
			paint.setTextSize(32);
			paint.setStrokeWidth(5);
			
			MyDebug.print(photo.getWidth() + "  -  " + photo.getHeight());
			
			Face face = photo.getFace();
			
			String gender = "性别:    " + face.getGender().toString();
			String age = "年龄:    " + face.getAgeEst();
			String glass = "戴眼镜:    " + face.isWearingGlasses();
			String mood = "心情:    " + face.getMood();
			
			double ratio = ((face.getRightEye().x - face.getLeftEye().x) / face.getWidth()) / 0.618 * 10;
			String ratioString = "长相评分(0-10):    " + ratio;
			
			canvas.drawText(gender, 50, 150, paint);
			canvas.drawText(age, 50, 250, paint);
			//canvas.drawText(glass, 50, 350, paint);
			canvas.drawText(mood, 50, 350, paint);
			canvas.drawText(ratioString, 50, 450, paint);

//			canvas.drawLine(face.getMouthLeft().x * photo.getWidth() / 100, face.getMouthLeft().y * photo.getHeight() / 100, 
//							face.getMouthRight().x * photo.getWidth() / 100, face.getMouthRight().y * photo.getHeight() / 100, 
//							paint);
		}
	}
	
	public void drawInfo(Photo m_photo)
	{
		this.hasExtra = true;
		this.photo = m_photo;
		this.invalidate();
	}
	
	public void clearInfo()
	{
		if(hasExtra)
		{
			this.hasExtra = false;
			this.invalidate();
		}
	}
}
