package com.dssoft.laprimitiva.iu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Spinner;

import com.dssoft.laprimitiva.R;
import com.dssoft.laprimitiva.global.LaPrimitiva;
import com.dssoft.laprimitiva.pojo.DatosCombinacion;
import com.dssoft.laprimitiva.presentador.PresentadorPantallaCombinaciones;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PantallaCombinacionesGanadoras extends AppCompatActivity implements VistaPantallaCombinaciones
{

    @BindView(R.id.toolBar_pantalla_combinacion) Toolbar toolbar;
    @BindView(R.id.spinnerAños) Spinner spinnerAños;
    @BindView(R.id.spinnerMeses) Spinner spinnerMeses;
    @BindView(R.id.recyclerGanadoras) RecyclerView recyclerGanadoras;

    PresentadorPantallaCombinaciones presentadorPantallaCombinaciones;
    AdaptadorRecyclerGanadoras arg;
    List<DatosCombinacion> listaGanadoras = new ArrayList<DatosCombinacion>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_combinacion);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        LaPrimitiva laPrimitiva = (LaPrimitiva) getApplication();
        presentadorPantallaCombinaciones = new PresentadorPantallaCombinaciones(laPrimitiva.getDataManagerFB());
        presentadorPantallaCombinaciones.setVista(this);

        List<String> listaAños = presentadorPantallaCombinaciones.getListaAños();

        //Se configuran los Spinners
        ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(this, R.array.array_meses, android.R.layout.simple_spinner_item);
        adapterMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMeses.setAdapter(adapterMeses);

        ArrayAdapter<String> adapterAños = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaAños);
        adapterAños.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAños.setAdapter(adapterAños);

        //Se configura el RecyclerView
        arg = new AdaptadorRecyclerGanadoras(listaGanadoras);
        recyclerGanadoras.setHasFixedSize(true);
        recyclerGanadoras.setItemAnimator(new DefaultItemAnimator());
        recyclerGanadoras.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerGanadoras.setAdapter(arg);

    }


    @OnClick(R.id.txtVerCombinaciones)
    public void getGandoras()
    {
        String año = (String) spinnerAños.getItemAtPosition(spinnerAños.getSelectedItemPosition());
        String mes = (String) spinnerMeses.getItemAtPosition(spinnerMeses.getSelectedItemPosition());

        Log.e("Años y Mes", año+"  "+mes);

        presentadorPantallaCombinaciones.getGanadoras(spinnerAños.getItemAtPosition(spinnerAños.getSelectedItemPosition()).toString(),
                spinnerMeses.getItemAtPosition(spinnerMeses.getSelectedItemPosition()).toString());

    }


    @Override
    public void showListaGanadoras(List<DatosCombinacion> lisGanadoras)
    {

        //Se actualizan en el RecycleView los datos

        Log.e("Lista Ganadoras", String.valueOf(listaGanadoras.size()));

        listaGanadoras.clear();
        listaGanadoras.addAll(lisGanadoras);
        arg.notifyDataSetChanged();

    }
}
