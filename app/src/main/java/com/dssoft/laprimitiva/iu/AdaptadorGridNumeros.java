package com.dssoft.laprimitiva.iu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.dssoft.laprimitiva.R;
import com.dssoft.laprimitiva.pojo.DatosNumero;
import java.util.List;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 07/02/2018.
 */

public class AdaptadorGridNumeros extends ArrayAdapter<DatosNumero>
{

    private Activity context;
    private List<DatosNumero> listNumeros;
    private List<DatosNumero> listGanadora;

    public AdaptadorGridNumeros(@NonNull Activity context, @NonNull List<DatosNumero> listNumeros, @NonNull List<DatosNumero> listGanadora)
    {
        super(context, R.layout.layout_grid_numeros, listNumeros);

        this.context = context;
        this.listNumeros = listNumeros;
        this.listGanadora = listGanadora;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View celda = convertView;
        ViewHolder holder;

        if(celda == null)
        {

            celda = context.getLayoutInflater().inflate(R.layout.layout_grid_numeros, null);
            holder = new ViewHolder(celda);
            celda.setTag(holder);

        }else
        {
            holder = (ViewHolder) celda.getTag();
        }

        //Se cambia el color de los numeros del grid si coinciden con la combinacion ganadora o no
        for(DatosNumero datosNumero:listGanadora)
        {
            if(datosNumero.getNumero() == listNumeros.get(position).getNumero())
            {
                holder.textViewNumero.setTextColor(holder.colorTeal);
                holder.textViewRepeticion.setTextColor(holder.colorTeal);
                break;

            }else
            {
                holder.textViewNumero.setTextColor(holder.colorIndigo);
                holder.textViewRepeticion.setTextColor(holder.colorIndigo);
            }
        }


        holder.textViewNumero.setText(String.valueOf(listNumeros.get(position).getNumero()));
        holder.textViewRepeticion.setText(String.valueOf(listNumeros.get(position).getRepeticiones()));

        return celda;
    }

    static class ViewHolder
    {

        @BindView(R.id.textViewNumero) TextView textViewNumero;
        @BindView(R.id.textViewRepeticion) TextView textViewRepeticion;
        @BindColor(R.color.colorTealClaro) int colorTeal;
        @BindColor(R.color.colorIndigoClaro) int colorIndigo;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }

    }
}
