package com.pes.enfaixapp;


import android.app.Activity;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.pes.enfaixapp.Models.Esdeveniment;

import java.io.File;
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
    private ImageView crearEsdv;
    final static int RESULTADO_FOTO = 0;
    final static int RESULTADO_GALERIA = 1;
    final static int RESULTADO_BORRAR_FOTO = 2;

    private Esdeveniment esdv = new Esdeveniment();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


       View viewCrearEsdv = inflater.inflate(R.layout.activity_crear_esdeveniment, container, false);

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

        else if(requestCode == RESULTADO_BORRAR_FOTO) {           //PER LA POSAR FOTO A TRAVES DE CAM => DE MOMENT HO DEIXEM
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

    //Abrir Galeria o similar
    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULTADO_GALERIA);
    }






}
