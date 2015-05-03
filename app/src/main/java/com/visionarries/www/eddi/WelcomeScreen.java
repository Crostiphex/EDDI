package com.visionarries.www.eddi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;


public class WelcomeScreen extends ActionBarActivity {
    private static final String TAG = null;
    public static boolean long_or_short;

    public static void doRestart(Context c) {
        try {
            //check if the context is given
            if (c != null) {
                //fetch the package manager so we can get the default launch activity
                // (you can replace this intent with any other activity if you want
                PackageManager pm = c.getPackageManager();
                //check if we got the PackageManager
                if (pm != null) {
                    //create the intent with the default start activity for your application
                    Intent mStartActivity = pm.getLaunchIntentForPackage(
                            c.getPackageName()
                    );
                    if (mStartActivity != null) {
                        mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //create a pending intent so the application is restarted after System.exit(0) was called.
                        // We use an AlarmManager to call this intent in 100ms
                        int mPendingIntentId = 223344;
                        PendingIntent mPendingIntent = PendingIntent
                                .getActivity(c, mPendingIntentId, mStartActivity,
                                        PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        //kill the application
                        System.exit(0);
                    } else {
                        Log.e(TAG, "Was not able to restart application, mStartActivity null");
                    }
                } else {
                    Log.e(TAG, "Was not able to restart application, PM null");
                }
            } else {
                Log.e(TAG, "Was not able to restart application, Context null");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Was not able to restart application");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        final ImageView image = (ImageView)findViewById(R.id.imageView);

        //The animation
        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        Animation.AnimationListener animListener = new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(R.drawable.eddi);
                image.startAnimation(animationFadeIn);
            }
        };
        image.startAnimation(animationFadeOut);
        animationFadeIn.setAnimationListener(animListener);
        animationFadeOut.setAnimationListener(animListener);

//buttons declaration
        final Button switch_act = (Button) findViewById(R.id.startbutton);
        final Button database =(Button)findViewById(R.id.database_button);
        //checkbox declaration
        final CheckBox want_user = (CheckBox) findViewById(R.id.newUserCheckBox);
        final  CheckBox test_time = (CheckBox) findViewById(R.id.longorshort);


        switch_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//if they check yes to the adding profile part
                if (want_user.isChecked()) {
                    long_or_short = test_time.isChecked();
                    Intent act2 = new Intent(view.getContext(), CustomerName.class);
                    startActivity(act2);
                }
//if unchecked it goes to calibration
                else {
                    long_or_short = test_time.isChecked();
                    Intent act2 = new Intent(view.getContext(), Calibration.class);
                    startActivity(act2);
                }
            }
        });
        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act2 = new Intent(view.getContext(),Password_screen.class);
                startActivity(act2);


                }});





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //call this if you want to fully restart the app using a button. put "this" inside the parenthesis

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return false;
        }

        return false;
    }





}
