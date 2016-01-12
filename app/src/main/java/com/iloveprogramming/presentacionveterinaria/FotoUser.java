package com.iloveprogramming.presentacionveterinaria;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Fabiola on 1/12/2015.
 */
public class FotoUser extends Activity implements View.OnClickListener {
    //PARA IDENTIFICAR LA ACCIÓN REALIZADA
    private static final int TAKE_PICTURE=1;
    private static final int SELECT_PICTURE=2;
    public static final String CODIGOFOTO="com.iloveprogramming.presentacionveterinaria.CODIGOFOTO";
    public static final String RUTA="com.iloveprogramming.presentacionveterinaria.RUTAFOTO";
    public static final String GARUTA="com.iloveprogramming.presentacionveterinaria.GAFOTO";
    //DEFINE EL NOMBRE DEL ARCHIVO DONDE SE ESCRIBE LA FOTOGRAFÍA DE
    //TAMAÑO COMPLETO
    private String name="";
    private ImageView img_foto;
    Bitmap  scaledBitMap;
    private Button btn_elegir,btn_tomar,btn_aceptar;
    Bitmap bitmap;
    Uri selectedImage;
    String codigo="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_fotouser);

        InicializaControles();
        name = Environment.getExternalStorageDirectory() + "/test.jpg";
}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //
        if (requestCode == TAKE_PICTURE) {
            if (data == null) {
                img_foto.setImageBitmap(BitmapFactory.decodeFile(name));
                codigo="se tomó";



                new MediaScannerConnection.MediaScannerConnectionClient() {
                    private MediaScannerConnection msc = null;

                    {
                        msc = new MediaScannerConnection(getApplicationContext(), this);
                        msc.connect();
                    }

                    public void onMediaScannerConnected() {
                        msc.scanFile(name, null);
                    }

                    public void onScanCompleted(String path, Uri uri) {
                        msc.disconnect();
                    }
                };
            } else {
               Toast.makeText(getApplicationContext(), "No se tomó  foto con éxito", Toast.LENGTH_LONG).show();


            }
            // }
        } else if (requestCode == SELECT_PICTURE) {
            if (data != null) {
                selectedImage = data.getData();
                codigo="se subió";
                InputStream is;
                try {
                    is = getContentResolver().openInputStream(selectedImage);
                    BufferedInputStream bis = new BufferedInputStream(is);
                     bitmap = BitmapFactory.decodeStream(bis);
                    img_foto.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Toast.makeText(getApplicationContext(), "Seleccione Una Foto de su Galería", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public void InicializaControles(){
        btn_aceptar=(Button)findViewById(R.id.btnaceptarfoto);
        btn_aceptar.setOnClickListener(this);
        btn_elegir=(Button)findViewById(R.id.btnsubir);
        btn_elegir.setOnClickListener(this);
        btn_tomar=(Button)findViewById(R.id.btntomar);
        btn_tomar.setOnClickListener(this);
        img_foto=(ImageView)findViewById(R.id.imageVFotoUser);
     }

    @Override
    public void onClick(View v) {

       switch (v.getId()){
           case R.id.btntomar:
               Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
               int code = TAKE_PICTURE;
        //       Log.i("hola",name,);
              //RUTA DEL ARCHIVO
               Uri output=Uri.fromFile(new File(name));
               intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
               startActivityForResult(intent, code);
               break;
           case R.id.btnsubir:
               Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
               code=SELECT_PICTURE;
               startActivityForResult(i,code);
               break;

           case R.id.btnaceptarfoto:
               if(codigo.equals("se tomó")) {
                   Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();
                   Intent intomo = new Intent(this, CrearCuenta.class);
                   intomo.putExtra(CODIGOFOTO,"uno");
                   intomo.putExtra(RUTA, name);
                   startActivity(intomo);
                   finish();

               }
               if(codigo.equals("se subió")){
                   Toast.makeText(getApplicationContext(),selectedImage.toString(), Toast.LENGTH_LONG).show();
                   Intent insubio = new Intent(this, CrearCuenta.class);
                   insubio.putExtra(CODIGOFOTO, "dos");
                   insubio.setData(selectedImage);
                   startActivity(insubio);
                   finish();
               }
               if(codigo.equals("")){
                   Toast.makeText(getApplicationContext(), "Por favor  seleccione una foto o tómese una", Toast.LENGTH_SHORT).show();
               }
       }

    }



}
