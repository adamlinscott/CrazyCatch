package cn.scu.DrawAreaEvents;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import java.util.Random;

public class DrawArea extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener {

	SurfaceHolder mDrawSurface = null;

	int mWidth, mHeight; // Width and Height of the drawing area
	Paint mPaint; 	// the paint for drawing

	//Random
	Random rand = new Random();

	// user changeable variables
	public float mTouchX = 0f, mTouchY = -60f;

	public DrawArea(Context context) {
		super(context);

		initSurface();
	}

	public DrawArea(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initSurface();
	}

	private void initSurface()
	{
		mDrawSurface = getHolder();

		// enable the calling of SurfaceHolder.Callback functions!
		mDrawSurface.addCallback(this);

		//create paint to draw with on canvas
		mPaint = new Paint();

		mTouchX = rand.nextInt(718);
	}

	// Region: functions from SurfaceHolder.Callback
	// these are from SurfaceHodler.CallBack
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("MyDebug", "Surface:Created");
		setWillNotDraw(false);
		setBackgroundColor(Color.GRAY);

		// enable onTouch
		setOnTouchListener(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {
		Log.i("MyDebug", "Surface:" + width + "x" + height);
		mWidth = width;
		mHeight = height;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
	//EndRegion


	// Region: function from OnTouchListener
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if((mTouchX - event.getX() <= 50 && mTouchX - event.getX() >= -50)
						&& (mTouchY - event.getY() <= 50 && mTouchY - event.getY() >= -50)){
					mTouchY = 50f;
					SimpleFragment.speed = rand.nextInt(8)+7;
					mTouchX = rand.nextInt(718);
					invalidate();
					SimpleFragment.iScore ++;
					SimpleFragment.mScore.setText("Score: " + SimpleFragment.iScore);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
		}

		invalidate();  // forces redraw!
		return true; // event handled
	}

	@Override
	protected void onDraw(Canvas c) {

		int r = 0, g = 0, b = 0, a = 255;
		float radius = 50f;

		r = 0;
		g = b = 255;
		mPaint.setARGB(a, r, g, b);
		c.drawCircle(mTouchX, mTouchY, radius, mPaint);

	}
}
