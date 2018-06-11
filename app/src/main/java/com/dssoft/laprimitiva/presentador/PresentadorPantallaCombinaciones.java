package com.dssoft.laprimitiva.presentador;

import com.dssoft.laprimitiva.iu.VistaBase;
import com.dssoft.laprimitiva.iu.VistaPantallaCombinaciones;
import com.dssoft.laprimitiva.modelo.DataManagerFB;
import java.util.List;

public class PresentadorPantallaCombinaciones implements PresentadorMVPPantallaCombinaciones
{

    private VistaPantallaCombinaciones vista;
    private DataManagerFB dataManagerFB;

    private List<String> añosCombinacion;


    public PresentadorPantallaCombinaciones(DataManagerFB dataManagerFB)
    {
        this.dataManagerFB = dataManagerFB;
    }


    @Override
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaPantallaCombinaciones) vista;
    }


    @Override
    public List<String> getListaAños()
    {
        añosCombinacion = dataManagerFB.getAñosCombinacion();

        return añosCombinacion;
    }


    @Override
    public void getGanadoras(String año, String mes)
    {
        //Se obtiene las combinaciones ganadoras del año y mes elejido y se actualizan los datos en la lista
        vista.showListaGanadoras(dataManagerFB.getGanadoras(año, mes));
    }


}
