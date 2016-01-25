package cn.scu.DrawAreaEvents;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    SimpleFragment mSimpleFragment;


    public static boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // without this check, we would create a new fragment at each orientation change!
        if (null == savedInstanceState)
            createFragment();
    }

    private void createFragment()
    {
        FragmentManager fManager = getFragmentManager();
        FragmentTransaction fTransaction = fManager.beginTransaction();

        mSimpleFragment = new SimpleFragment();

        // Adding the new fragment
        fTransaction.add(R.id.mainContainer, mSimpleFragment);
        fTransaction.commit();
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "Do you really want to exit?", Toast.LENGTH_SHORT).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }else {
            finish();
        }
    }

}
