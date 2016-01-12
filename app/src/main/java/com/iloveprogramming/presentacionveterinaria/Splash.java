package com.iloveprogramming.presentacionveterinaria;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends Activity {

    private MediaPlayer mp;
    private static final long SPLASH_SCREEN_DELAY = 2000;
    private AnimationDrawable ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Animacion();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

    Intent i = new Intent(Splash.this, Login.class);

                startActivity(i);
                finish();
            }
        };


        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void Animacion() {

        ImageView img = (ImageView) findViewById(R.id.imageViewPets);
        img.setBackgroundResource(R.drawable.lista_imagenes_pets);

        ad = (AnimationDrawable) img.getBackground();
        ad.start();
        mp = MediaPlayer.create(Splash.this, R.raw.dogs);
        mp.start();
    }

  protected void onStop() {
        super.onStop();
        if (mp.isPlaying()) { //Verifica   si  el audio está reproduciendo
            mp.stop(); //Pausa the sound

        }

    }
}
