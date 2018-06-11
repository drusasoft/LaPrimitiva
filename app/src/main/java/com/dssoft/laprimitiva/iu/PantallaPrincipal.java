package com.dssoft.laprimitiva.iu;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import com.dssoft.laprimitiva.R;
import com.dssoft.laprimitiva.global.LaPrimitiva;
import com.dssoft.laprimitiva.presentador.PresentadorPantallaPrincipal;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PantallaPrincipal extends AppCompatActivity implements VistaPantallaPrincipal
{

    @BindView(R.id.layout_pantalla_principal) ConstraintLayout layoutPantallaPrincipal;
    @BindView(R.id.toolBarPrincipal) Toolbar toolbar;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.leftDrawer) NavigationView nView;
    @BindView(R.id.btnEstadisticas) Button btnEstadisticas;
    @BindView(R.id.btnAleatorio) Button btnAleatorio;
    @BindView(R.id.btnGandoras) Button btnGanadoras;
    @BindView(R.id.btnInsertar) Button btnInsertar;

    private ActionBarDrawerToggle drawerToggle;
    private PresentadorPantallaPrincipal presentadorPantallaPrincipal;
    private ProgressDialog progressDialog;

    private static final int RC_HANDLE_CAMERA_PERM = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Se crea el Presentador para esta pantalla y se llama al metodo que obtiene todos los datos de FB
        LaPrimitiva laPrimitiva = (LaPrimitiva) getApplication();
        presentadorPantallaPrincipal = new PresentadorPantallaPrincipal(laPrimitiva.getDataManagerFB());
        presentadorPantallaPrincipal.setVista(this);
        presentadorPantallaPrincipal.obtencionInicialDatos();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //Se crea la animcion de los botones del menu principal
        Animation animacion = AnimationUtils.loadAnimation(this, R.anim.animacion_botones);
        btnEstadisticas.startAnimation(animacion);
        btnInsertar.startAnimation(animacion);
        btnGanadoras.startAnimation(animacion);
        btnAleatorio.startAnimation(animacion);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();//necesario para que se muestre el icono del NavigationDrawer
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }


    @Override
    protected void onResume()
    {
        super.onResume();

        nView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                switch (item.getItemId())
                {

                    case R.id.drawer_opcion1:   irPantallaEstadisticas();
                                                break;

                    case R.id.drawer_opcion2:   irPantallaCombinaciones();
                                                break;

                    case R.id.drawer_opcion3:   irPantallaAleatoria();
                                                break;

                    case R.id.drawer_opcion4:   irPantallaInsertar();
                                                break;

                    case R.id.drawer_opcion5:   irPantallaEscanear();
                                                break;

                }

                return false;
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawers();
                return true;
            }else
            {
                finish();
            }

        }

        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_pantalla_principal, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);//necesario para que se muestre el icono del NavigationDrawer
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.menu_principal_1: presentadorPantallaPrincipal.obtencionInicialDatos();
                                                 return true;
        }

        //Necesario para que se abra y cierra le NavigationDrawer al pulsar el boton
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return false;

    }

    @Override
    public void finProgressDatos()
    {
        progressDialog.dismiss();
    }

    @Override
    public void mostrarDialogConexion()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Conectando...");
        progressDialog.setMessage("Obteniendo Datos del Servidor...");
        progressDialog.show();
    }

    @OnClick(R.id.btnEstadisticas)
    public void irPantallaEstadisticas()
    {
        if(presentadorPantallaPrincipal.getSizeListaCombinaciones()>0)
        {
            Intent intent = new Intent(this, PantallaEstadisticas.class);
            startActivity(intent);
        }else
        {
            Snackbar.make(layoutPantallaPrincipal, R.string.mens_lista_vacia, Snackbar.LENGTH_LONG).show();
        }


        drawerLayout.closeDrawers();
    }


    @OnClick(R.id.btnGandoras)
    public void irPantallaCombinaciones()
    {

        if(presentadorPantallaPrincipal.getSizeListaCombinaciones()>0)
        {

            Intent intent = new Intent(this, PantallaCombinacionesGanadoras.class);
            startActivity(intent);

        }else
        {
            Snackbar.make(layoutPantallaPrincipal, R.string.mens_lista_vacia, Snackbar.LENGTH_LONG).show();
        }

        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.btnInsertar)
    public void irPantallaInsertar()
    {

        Intent intent = new Intent(this, PantallaInsertarCombinacion.class);
        startActivity(intent);

    }


    @OnClick(R.id.btnAleatorio)
    public void irPantallaAleatoria()
    {
        Intent intent = new Intent(this, PantallaAleatoria.class);
        startActivity(intent);
    }


    public void irPantallaEscanear()
    {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            Intent intent = new Intent(this, PantallaEscaneo.class);
            startActivity(intent);

        }else
        {
            final String[] permissions = new String[]{Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
        }

    }

}
