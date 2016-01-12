package com.iloveprogramming.presentacionveterinaria;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fabiola on 3/12/2015.
 */

public class CrearCuenta extends Activity {
    Button btn_fotoo, btn_crearcta;
    EditText ed_nom, ed_ape, ed_correo, ed_pass, ed_phone;
    TextView txtfoto;
    ImageView img_fotoperfil;
    private RequestQueue requestQueue;
    private static final String URL = "http://10.0.2.2/Proyecto/registrar_usuario.php";
    //private static final String URL = "http://192.168.1.40/Proyecto/user_control.php";
    private StringRequest request;


    //CONSTANTES
   //public final static int RUTA_FOTO_REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crearcuenta);
        InicializarControles();

        Intent  inreceiver=getIntent();
        String codigo=inreceiver.getStringExtra(FotoUser.CODIGOFOTO);
        //Extra   de url  de la foto tomada
        String ruta=inreceiver.getStringExtra(FotoUser.RUTA);
        //Extra de la ruta  de  la foto elegida  de la galería
        Uri ruta_galeria=inreceiver.getData();

       if(codigo!=null) {
           if (codigo.equals("uno")) {
               txtfoto.setText(ruta);
               img_fotoperfil.setImageBitmap(BitmapFactory.decodeFile(ruta));
           }

           if (codigo.equals("dos")) {
               txtfoto.setText(ruta_galeria.toString());
               // img_fotoperfil.setImageBitmap(BitmapFactory.decodeFile(ruta));

               img_fotoperfil.setImageURI(ruta_galeria);
           }


       }

        }

  public void InicializarControles() {
        btn_fotoo = (Button) findViewById(R.id.btnquierofoto);
        btn_crearcta = (Button) findViewById(R.id.btncrearcta);
        ed_nom = (EditText) findViewById(R.id.edNomUser);
        ed_ape = (EditText) findViewById(R.id.edApeUser);
        ed_correo = (EditText) findViewById(R.id.edCorUser);
        ed_pass = (EditText) findViewById(R.id.edPassUser);

        ed_phone = (EditText) findViewById(R.id.edPhoneUser);
        txtfoto = (TextView) findViewById(R.id.txtruta);
        img_fotoperfil = (ImageView) findViewById(R.id.imagePerfil);
        requestQueue = Volley.newRequestQueue(this);



         btn_fotoo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(getApplicationContext(),FotoUser.class));
                 //TODO:AKI
                 finish();;

             }
         });

        btn_crearcta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("registrado")) {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("registrado"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                               //TODO:aki
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("noregistro"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("nombre", ed_nom.getText().toString());
                        hashMap.put("apellidos", ed_ape.getText().toString());
                        hashMap.put("foto_url", txtfoto.getText().toString());
                        hashMap.put("email", ed_correo.getText().toString());
                        hashMap.put("password", ed_pass.getText().toString());
                        hashMap.put("telefono", ed_phone.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}


