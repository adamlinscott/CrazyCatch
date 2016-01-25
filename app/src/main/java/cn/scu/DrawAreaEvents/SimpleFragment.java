package cn.scu.DrawAreaEvents;

import android.app.Application;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SimpleFragment extends Fragment {

	MainActivity mainActivity = (MainActivity) getActivity();

	SharedPreferences mSharedPreferences;
	/*mainActivity.getSharedPreferences("SimpleFragmentSave" , 0);*/
	SharedPreferences.Editor mEditor ;
	/*mSharedPreferences.edit();*/

	TextView mEcho;
	public static TextView mScore;
	TextView mTimer;

	Button mStart;
	Button mExit;

	DrawArea mDrawArea;

	public static int iScore = 0;
	int iTime = 30000;
	int iHighScore = 0;

	//Timer
	final int kUpdatePeriod = 10;
	public static Handler mFutureTask;

	public static int speed = 10;

	//Random
	Random rand = new Random();
	
	public SimpleFragment() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.simple_fragment_layout, container, false);
					// simple_fragment_layout: is the name of the layout (name of the xml file)!

		//mSharedPreferences = mainActivity.getSharedPreferences("SimpleFragmentSave" , 0);

		mSharedPreferences = getActivity().getSharedPreferences("High Score" , 0);
		mEditor = mSharedPreferences.edit();
		//SharedPreferences.Editor mEditor = mSharedPreferences.edit();

		String TempString = mSharedPreferences.getString("High Score" , "");


		mEcho = (TextView) rootView.findViewById(R.id.titleView);
		mScore = (TextView) rootView.findViewById(R.id.scoreView);
		mTimer = (TextView) rootView.findViewById(R.id.timerView);

		mStart = (Button) rootView.findViewById(R.id.startButton);
		mExit = (Button) rootView.findViewById(R.id.exitButton);

		mDrawArea = (DrawArea) rootView.findViewById(R.id.drawArea);

		// CANNOT do this! we dragged the area into the fragment layout in UI editor!
		// so the object is already created! Just like the mEcho(TextView)
		// 		mDrawArea = new DrawArea(rootView.getContext());

		mFutureTask = new Handler();

		mScore.setText(TempString);
		mEcho.setText("Fragment CreateView done!");
		mStart.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				mFutureTask.postDelayed(periodicTask, kUpdatePeriod);
				iTime = 30000;
			}
		});

		mExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mainActivity.exit();
			}
		});

		return rootView;
	}



	//Timer
	Runnable periodicTask = new Runnable(){
		public void run() {
			iTime -= kUpdatePeriod +20;
			mTimer.setText(String.valueOf(iTime/1000) + "s");

			mDrawArea.mTouchY += speed;
			if(mDrawArea.mTouchY > 950){
				mDrawArea.mTouchY = 50f;
				speed = rand.nextInt(8)+7;
				mDrawArea.mTouchX = rand.nextInt(718);
			}

			mDrawArea.invalidate();
			if(iTime > 0)
				mFutureTask.postDelayed(this, kUpdatePeriod);
			else
			{
				mDrawArea.mTouchY = -60f;
				mStart.setText("Restart");
				if(iScore > iHighScore) {
					iHighScore = iScore;
					mScore.setText("High Score: " + iHighScore);
					mEditor.putString("High Score", mScore.getText().toString());
					mEditor.commit();
				}
			}
		}
	};
}
