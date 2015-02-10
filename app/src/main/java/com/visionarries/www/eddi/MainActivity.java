package com.visionarries.www.eddi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;



public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView image = (ImageView)findViewById(R.id.imageView);
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


        final Button switchact =(Button)findViewById(R.id.startbutton);
        final CheckBox want_user = (CheckBox) findViewById(R.id.newUserCheckBox);

        switchact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



if(want_user.isChecked()){
                Intent act2 = new Intent(view.getContext(),CustomerName.class);
                startActivity(act2);}
                else{

    Context context = getApplicationContext();
    CharSequence text = "You didn't check the box!";
    int duration = Toast.LENGTH_SHORT;
    Toast toast = Toast.makeText(context, text, duration);
    toast.show();
}





            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
