package com.dssoft.laprimitiva.presentador;

import com.dssoft.laprimitiva.iu.VistaBase;
import com.dssoft.laprimitiva.iu.VistaPantallaPrincipal;
import com.dssoft.laprimitiva.modelo.DataManagerFB;

/**
 * Created by Angel on 05/12/2017.
 */

public class PresentadorPantallaPrincipal implements PresentadorMVPPantallaPrincipal
{

    private DataManagerFB dataManagerFB;
    private VistaPantallaPrincipal vista;

    public PresentadorPantallaPrincipal(DataManagerFB dataManagerFB)
    {
        this.dataManagerFB = dataManagerFB;
    }


    @Override
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaPantallaPrincipal) vista;
    }



    @Override
    //Se obtiene de Firebase todos los datos Almacenados y se guardan en un lista en memoria
    public void obtencionInicialDatos()
    {
        vista.mostrarDialogConexion();
        dataManagerFB.obtencionInicialDatos(this);
    }


    @Override
    //Se llama cuando se han obtenido todos los datos de FireBse y se han guradado en la lista
    public void finObtencionInicialDatos()
    {
        vista.finProgressDatos();
    }

    @Override
    //Se obiene el tamaño de la lista de combinaciones (para comprobar si esta vacia o no)
    public int getSizeListaCombinaciones()
    {
        return dataManagerFB.getListaCombinaciones().size();
    }


}
