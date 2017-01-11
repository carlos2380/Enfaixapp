package com.pes.enfaixapp;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pes.enfaixapp.Controllers.AsyncResult;
import com.pes.enfaixapp.Controllers.ContextUser;
import com.pes.enfaixapp.Controllers.HTTPHandler;
import com.pes.enfaixapp.Controllers.JSONConverter;
import com.pes.enfaixapp.Models.Colla;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    NoticiaActivity fragmentNoticia = new NoticiaActivity();
    private DrawerActivity context;
    Spinner collaDisplay;
    TextView nomUsuariDrawer;
    TextView correuUsuariDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        context = this;

        //------------------------------------
        //INSERTAR FRAGMENTO INICIAL


        NoticiaActivity fragment = new NoticiaActivity();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new NoticiaActivity());
        fragmentTransaction.commit();

        //----------------------------
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView= navigationView.getHeaderView(0);
        collaDisplay = (Spinner) navHeaderView.findViewById(R.id.collaSpinner);

        //View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_drawer);
       // collaDisplay = (Spinner) findViewById(R.id.collaSpinner);
        ArrayList<String> nomsColles = new ArrayList<>();
        for (int i =0; i < ContextUser.getInstance().getCollesPertany().size(); ++i) {
            nomsColles.add(ContextUser.getInstance().getCollesPertany().get(i).getName());
        }
         if (nomsColles.size() > 0) {
             final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, nomsColles);

             //collaDisplay = new Spinner(getApplicationContext());
             collaDisplay.setAdapter(spinnerAdapter);
             collaDisplay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                 @Override
                 public void onItemSelected(AdapterView<?> parent, View v,
                                            int pos, long id) {
                     ArrayList<Colla> colles = ContextUser.getInstance().getCollesPertany();
                     ContextUser.getInstance().setId_collaSwitch(String.valueOf(colles.get(pos).getId()));

                     spinnerAdapter.notifyDataSetChanged();


                 }

                 @Override
                 public void onNothingSelected(AdapterView<?> arg0) {

                 }
             });
         }

        else {
             collaDisplay.setVisibility(View.GONE);
         }

        nomUsuariDrawer = (TextView) navHeaderView.findViewById(R.id.nomUserDrawer);
        nomUsuariDrawer.setText(ContextUser.getInstance().getNom() + " " + ContextUser.getInstance().getCognoms());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Sortir de l'aplicació?")
                    .setMessage("Estàs segur que vols sortir?")
                    .setPositiveButton("Sí",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            })
                    .setNegativeButton("Cancel·la",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_news) {
            //------------------------------------
            //INSERTAR FRAGMENTO
            getSupportActionBar().setTitle("Notícies");

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new NoticiaActivity());
            fragmentTransaction.commit();

            //----------------------------
        } else if (id == R.id.nav_classification) {
            //------------------------------------
            //INSERTAR FRAGMENTO INICIAL
            getSupportActionBar().setTitle("Classificació");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new RankingActivity());
            fragmentTransaction.commit();

            //----------------------------
        } else if (id == R.id.nav_esdv) {

            getSupportActionBar().setTitle("Esdeveniments");
            EsdevenimentListActivity fragment = new EsdevenimentListActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if(id == R.id.nav_logout) {
            SharedPreferences preferences = getSharedPreferences("Shared", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("session-token");
            editor.apply();
            Intent intent = new Intent(DrawerActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        }

        else if (id == R.id.perfil) {

            getSupportActionBar().setTitle("El meu perfil");
            PerfilActivity fragment = new PerfilActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_colles) {

            getSupportActionBar().setTitle("Colles");
            CollasActivity fragment = new CollasActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }

        else if (id == R.id.nav_assajos) {

            getSupportActionBar().setTitle("Assajos");
            Toast.makeText(this, "Not implemented yet!",
                    Toast.LENGTH_LONG).show();
            AssajosActivity fragment = new AssajosActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        }

        else if (id == R.id.nav_results) {
            getSupportActionBar().setTitle("Resultats");
            Toast.makeText(this, "Not implemented yet!",
                    Toast.LENGTH_LONG).show();
            ResultsActivity fragment = new ResultsActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        else if (id == R.id.nav_about) {
            getSupportActionBar().setTitle("About");
            AboutActivity fragment = new AboutActivity();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}