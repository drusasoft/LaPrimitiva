package com.dssoft.laprimitiva.iu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dssoft.laprimitiva.R;
import com.dssoft.laprimitiva.global.LaPrimitiva;
import com.dssoft.laprimitiva.pojo.DatosNumero;
import com.dssoft.laprimitiva.presentador.PresentadorPantallaEstadisticas;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Angel on 13/12/2017.
 */

public class PantallaEstadisticas extends AppCompatActivity implements VistaPantallaEstadisticas
{

    @BindView(R.id.layoutPantallaEstadisticas) LinearLayout layoutPantallaEstadisiticas;
    @BindView(R.id.toolbar_pantalla_estadisticas) Toolbar toolbar;
    @BindView(R.id.textViewGanador1) TextView textViewGanador1;
    @BindView(R.id.textViewGanador2) TextView textViewGanador2;
    @BindView(R.id.textViewGanador3) TextView textViewGanador3;
    @BindView(R.id.textViewGanador4) TextView textViewGanador4;
    @BindView(R.id.textViewGanador5) TextView textViewGanador5;
    @BindView(R.id.textViewGanador6) TextView textViewGanador6;
    @BindView(R.id.cardViewGanadores) CardView cardViewGanadoras;
    @BindView(R.id.cardViewResto) CardView cardViewResto;
    @BindView(R.id.gridRestoNumeros)  GridView gridNumeros;
    @BindView(R.id.scrollViewEstadisticas) ScrollView scrollViewEstadisticas;
    @BindView(R.id.textViewVerResto) TextView textViewResto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_estadisticas);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Se obtiene las lista con todas las combinaciones ganadoras
        LaPrimitiva laPrimitiva = (LaPrimitiva) getApplication();
        final PresentadorPantallaEstadisticas presentador = new PresentadorPantallaEstadisticas(laPrimitiva.getDataManagerFB());
        presentador.setVista(this);

        scrollViewEstadisticas.smoothScrollTo(0,0);//para que el scroll del ScrollView se situe arriba

        layoutPantallaEstadisiticas.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
            {
                v.removeOnLayoutChangeListener(this);
                presentador.calcularEstadisticas();
                presentador.getCombinacionGanadora();
                presentador.getRestoNumeros();
            }
        });

    }


    @OnClick(R.id.textViewVerResto)
    public void verRestoNumeros()
    {

        if(cardViewResto.isShown())
        {
            textViewResto.setText(R.string.txtRestoNumeros);
            efecto_ocultar_circular(cardViewResto);

        }else
        {
            textViewResto.setText(R.string.txtRestoNumeros2);
            efecto_mostrar_circular(cardViewResto);
        }


    }


    @Override
    public void mostrarGanadora(List<DatosNumero> listGanadora)
    {

        if(listGanadora.size()==6)
        {
            textViewGanador1.setText(String.valueOf(listGanadora.get(0).getNumero()));
            textViewGanador2.setText(String.valueOf(listGanadora.get(1).getNumero()));
            textViewGanador3.setText(String.valueOf(listGanadora.get(2).getNumero()));
            textViewGanador4.setText(String.valueOf(listGanadora.get(3).getNumero()));
            textViewGanador5.setText(String.valueOf(listGanadora.get(4).getNumero()));
            textViewGanador6.setText(String.valueOf(listGanadora.get(5).getNumero()));
        }

        efecto_mostrar_circular(cardViewGanadoras);
    }


    @Override
    public void mostrarRestoNumeros(List<DatosNumero> listResto, List<DatosNumero> listGanadora)
    {
        if(listResto.size()>0)
        {
            AdaptadorGridNumeros agn = new AdaptadorGridNumeros(this, listResto, listGanadora);
            gridNumeros.setAdapter(agn);
        }

    }


    private void efecto_mostrar_circular(View view)
    {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight())/2;
        int cy = (view.getTop() + view.getBottom())/2;

        // get the final radius for the clipping circle
        int finalRadious = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadious);
        anim.setDuration(1500);//Establezco una duracion mayor al la animacion para ver mejor el efecto

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.start();


    }

    private void efecto_ocultar_circular(final View view)
    {
        // get the center for the clipping circle
        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        // get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);

        // create the animation (the final radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.setDuration(1000);//Establezco una duracion mayor al la animacion para ver mejor el efecto
        anim.start();

        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }

        });

    }
}
