package com.sundevs.ihsan.plnservice.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sundevs.ihsan.plnservice.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AppCompatActivity implements Animation.AnimationListener {
    private static int intervalsplash = 3000;
    // Animation
    @Bind(R.id.imageView)
    ImageView imageView;
    Animation animBlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //layar full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        // load the animation
        ButterKnife.bind(this);
        animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink);
        imageView.startAnimation(animBlink);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                    startActivity(new Intent(getApplicationContext(), SliderActivity.class));
                    this.finish();

            }

            private void finish() {
                // TODO Auto-generated method stub
            }
        }, intervalsplash);
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // check for blink animation
        if (animation == animBlink) {
        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }
}
