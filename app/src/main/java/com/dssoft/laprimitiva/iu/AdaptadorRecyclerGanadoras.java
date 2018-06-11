package com.dssoft.laprimitiva.iu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.dssoft.laprimitiva.R;
import com.dssoft.laprimitiva.pojo.DatosCombinacion;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptadorRecyclerGanadoras extends RecyclerView.Adapter<AdaptadorRecyclerGanadoras.ViewHolder>
{

    private List<DatosCombinacion> listaGanadoras;


    //Constructor
    public AdaptadorRecyclerGanadoras(List<DatosCombinacion> listaGanadoras)
    {
        this.listaGanadoras = listaGanadoras;
    }


    @Override
    // Create new views (invoked by the layout manager)
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_ganadoras,parent, false);
        ViewHolder vh = new ViewHolder(itemView);

        return vh;
    }


    @Override
    // Replace the contents of a view (invoked by the layout manager)
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        //Se pasan los datos obtenidos de la lista a los componente que forman el layout del RecycleView
        holder.bindDatos(listaGanadoras.get(position));
    }


    @Override
    public int getItemCount() {
        return listaGanadoras.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.txtCombinacion) TextView txtCombinacion;
        @BindView(R.id.txtFechaCombinacion) TextView txtFechaCombinacion;
        @BindView(R.id.txtComplementario) TextView txtComplementario;
        @BindView(R.id.txtReintegro) TextView txtReintegro;

        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //En este metodo se asocian los datos con los componentes que forman el layout que muestra cada elemento del RecycleView
        public void bindDatos(DatosCombinacion datosCombinacion)
        {
            txtFechaCombinacion.setText(datosCombinacion.getFecha());
            txtCombinacion.setText(datosCombinacion.getCombinacion());
            txtComplementario.setText("Complementario: "+datosCombinacion.getComp());
            txtReintegro.setText("Reintegro: "+datosCombinacion.getReint());
        }

    }


}
