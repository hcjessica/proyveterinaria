package com.iloveprogramming.presentacionveterinaria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Fabiola on 5/12/2015.
 */
public class ValidandoUsuario extends Activity {
    private Button btn_ingresar;
    private EditText ed_correo, ed_contraseña;
    private RequestQueue requestQueue;
    private static final String URL = "http://10.0.2.2/Proyecto/user_control.php";
   //private static final String URL = "http://192.168.1.40/Proyecto/user_control.php";
    private StringRequest request;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validandousuario);
      //  InicializarControles();

        ed_correo = (EditText) findViewById(R.id.ed_correo);
        ed_contraseña = (EditText) findViewById(R.id.ed_contraseña);
        btn_ingresar = (Button) findViewById(R.id.btn_ing);
        requestQueue = Volley.newRequestQueue(this);
        //btn_ingresar.setOnClickListener(this);

        //requestQueue = Volley.newRequestQueue(this);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.names().get(0).equals("success")) {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("success"), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Principal.class));
                                //TODO:AKI
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
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
                        hashMap.put("email", ed_correo.getText().toString());
                        hashMap.put("password", ed_contraseña.getText().toString());

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}


