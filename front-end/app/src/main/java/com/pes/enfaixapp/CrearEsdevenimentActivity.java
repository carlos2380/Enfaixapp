package com.pes.enfaixapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.pes.enfaixapp.Adapters.ListadoNoticiasFragmentSeguidas;
import com.pes.enfaixapp.Models.Esdeveniment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.EmptyStackException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CrearEsdevenimentActivity extends Fragment {


    private ImageView imageView;
    private Uri uriFoto;

    public CrearEsdevenimentActivity() {
        // Required empty public constructor
    }

    private Button afegirFotoViaDisp;
    private Button afegirFotoViaCam;
    private Button eliminarFoto;
    private ImageButton crearEsdv;
    final static int RESULTADO_FOTO = 0;
    final static int RESULTADO_GALERIA = 1;
    final static int RESULTADO_BORRAR_FOTO = 2;
    static View viewCrearEsdv;
    private Esdeveniment esdv = new Esdeveniment();

    private EditText titolEsdv;
    private EditText etdireccio;
    private EditText etdescript;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


       viewCrearEsdv = inflater.inflate(R.layout.activity_crear_esdeveniment, container, false);

        Bundle bundle = this.getArguments();

        String s = "CASA";
        if (bundle != null) {
            s = bundle.getString("CLAVE");
        }
        Toast toast = Toast.makeText(viewCrearEsdv.getContext(), s, Toast.LENGTH_LONG);
        toast.show();

        titolEsdv = (EditText) viewCrearEsdv.findViewById(R.id.titolEsdv);
        etdireccio = (EditText) viewCrearEsdv.findViewById(R.id.localitzacioEsdv);
        etdescript = (EditText) viewCrearEsdv.findViewById(R.id.descrEsdv);

        imageView = (ImageView) viewCrearEsdv.findViewById(R.id.imatgeCrearEsdeveniment);
        //afegirFotoViaCam = (Button) viewCrearEsdv.findViewById(R.id.afegirViaCamara);
        afegirFotoViaDisp = (Button) viewCrearEsdv.findViewById(R.id.afegirViaDispositiu);
        eliminarFoto = (Button) viewCrearEsdv.findViewById(R.id.eliminarFoto);
        crearEsdv = (ImageButton) viewCrearEsdv.findViewById(R.id.crearEsv);
        /*afegirFotoViaCam.setOnClickListener(new View.OnClickListener() {   //PER LA POSAR FOTO A TRAVES DE CAM => DE MOMENT HO DEIXEM
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                uriFoto = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + File.separator + "img_" + (System.currentTimeMillis() / 1000) + ".jpg"));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
                ((AppCompatActivity)getActivity()).startActivityForResult(intent, RESULTADO_FOTO);
            }

        });*/
        afegirFotoViaDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULTADO_GALERIA);            }
        });

        eliminarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULTADO_BORRAR_FOTO);
            }
        });

        crearEsdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //CRIDES HTTP PER FER UN POST SOBRE ESDEVENIMENTS
                CrearEsdevenimentActivity.MyAsync async = new CrearEsdevenimentActivity.MyAsync(viewCrearEsdv.getContext());
                try {
                    async.createEsdeveniment(viewCrearEsdv.getContext());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });

        return viewCrearEsdv;
    }

    //Recoger la vuelta a la actividad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == RESULTADO_EDITAR) {
            actualizarVistas();

            //Forzar a acutalizar la vista
            findViewById(R.id.scrollView1).invalidate();
        }*/
        if (/* Si no a cancelado la accion y esta correcto*/ requestCode == RESULTADO_GALERIA
               ) {
            esdv.setFoto(data.getDataString());
            ponerFoto(imageView, esdv.getFoto());

        }
        /*if(requestCode == RESULTADO_FOTO) {           //PER LA POSAR FOTO A TRAVES DE CAM => DE MOMENT HO DEIXEM
            esdv.setFoto(uriFoto.toString());
            ponerFoto(imageView, esdv.getFoto());
        }*/

        else if(requestCode == RESULTADO_BORRAR_FOTO) {
            esdv.setFoto(null);
            ponerFoto(imageView, esdv.getFoto());
        }
    }

    public void ponerFoto(ImageView img, String foto) {
        //Compruba que es una foto y la pone
        imageView.setImageURI(uriFoto);
        if (foto != null) {
        } else {
            imageView.setImageBitmap(null);
        }
    }

    public void guardaryvolver() {
        //***********************************//
        //     TOAST DE GUARDADO OK
        //     VOLVER
        //***********************************//
    }

    //Abrir Galeria o similar
    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULTADO_GALERIA);
    }


    private class MyAsync implements AsyncResult {
        Context context;
        public MyAsync(Context context) {
            this.context = context;
        }

        public void createEsdeveniment(Context context) throws JSONException {
            JSONObject jsonUser = new JSONObject();
            jsonUser.accumulate("title", titolEsdv.getText().toString());
            jsonUser.accumulate("description", etdescript.getText().toString());
            jsonUser.accumulate("path", uriFoto);
            jsonUser.accumulate("address", etdireccio.getText().toString());
            //***********************************//
            //      PONER USUARIO                //
            //***********************************//
            jsonUser.accumulate("id_user", "1");
            //***********************************//
            //      PONER COLLA                  //
            //***********************************//
            jsonUser.accumulate("id_colla", "1");
            HTTPHandler httphandler = new HTTPHandler();
            httphandler.setAsyncResult(this);
            httphandler.execute("POST", "http://10.4.41.165:5000/wall", null);
        }

        @Override
        public void processFinish(JSONObject output) {
            guardaryvolver();
        }
    }




}
