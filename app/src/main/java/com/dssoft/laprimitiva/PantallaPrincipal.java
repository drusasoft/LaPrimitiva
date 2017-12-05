package com.dssoft.laprimitiva;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import butterknife.BindView;
import butterknife.ButterKnife;

public class PantallaPrincipal extends AppCompatActivity
{

    @BindView(R.id.toolBarPrincipal) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setContentView(R.layout.activity_pantalla_principal);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
