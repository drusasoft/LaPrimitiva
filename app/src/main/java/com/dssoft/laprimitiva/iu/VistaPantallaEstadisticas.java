package com.dssoft.laprimitiva.iu;

import com.dssoft.laprimitiva.pojo.DatosNumero;

import java.util.List;

/**
 * Created by Angel on 16/01/2018.
 */

public interface VistaPantallaEstadisticas extends VistaBase
{

    public void mostrarGanadora(List<DatosNumero> listGanadora);
    public void mostrarRestoNumeros(List<DatosNumero> listResto, List<DatosNumero> listGanadora);

}
