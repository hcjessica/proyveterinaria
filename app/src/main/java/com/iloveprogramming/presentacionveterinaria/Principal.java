package com.iloveprogramming.presentacionveterinaria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Fabiola on 6/12/2015.
 */
public class Principal extends Activity implements View.OnClickListener{
        Button btn_logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        InicializarControles();


    }

    public void InicializarControles(){
        btn_logout=(Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                startActivity(new Intent(getApplicationContext(),ValidandoUsuario.class));
                finish();
                break;

        }

    }
}
