package com.iloveprogramming.presentacionveterinaria;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Fabiola on 30/11/2015.
 */
public class Login extends Activity implements View.OnClickListener{
    //VideoView video;
    Button btn_reg,btn_ing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InicializarControles();

  //      Reproducir();

    }

    public void InicializarControles(){
       // video=(VideoView)findViewById(R.id.videoVeterinaria);

        btn_reg=(Button)findViewById(R.id.btnregistrar);
        btn_reg.setOnClickListener(this);
        btn_ing=(Button)findViewById(R.id.btningresar);
        btn_ing.setOnClickListener(this);



    }

/*
    public void Reproducir(){

        String urlpath="android.resource://"+getPackageName()+ "/"+R.raw.video_vertical;

        video.setVideoURI(Uri.parse(urlpath));
        video.start();



    }
    @Override
    protected void onStop() {
        super.onStop();
        if(video.isPlaying()) {
            video.stopPlayback();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        video.start();
    }
*/

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getFragmentManager();
        switch (v.getId()){
         case R.id.btnregistrar:
             Intent i=new Intent(this,CrearCuenta.class);
             startActivity(i);

             break;
         case R.id.btningresar:
             Intent in=new Intent(this,ValidandoUsuario.class);
             startActivity(in);

             break;
     }
    }
}
