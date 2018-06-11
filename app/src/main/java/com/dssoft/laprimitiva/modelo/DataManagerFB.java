package com.dssoft.laprimitiva.modelo;

import com.dssoft.laprimitiva.pojo.DatosCombinacion;
import com.dssoft.laprimitiva.presentador.PresentadorPantallaInsertar;
import com.dssoft.laprimitiva.presentador.PresentadorPantallaPrincipal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 05/12/2017.
 */

public class DataManagerFB
{

    private FireBaseHelper fireBaseHelper;
    private List<DatosCombinacion> listaCombinaciones = new ArrayList<DatosCombinacion>();
    private PresentadorPantallaPrincipal presentadorPP;
    private PresentadorPantallaInsertar presentadorPI;

    public DataManagerFB(FireBaseHelper fireBaseHelper)
    {
        this.fireBaseHelper = fireBaseHelper;
    }


    //Se obtiene la lista con todos los datos guardados en Firebase
    public void obtencionInicialDatos(PresentadorPantallaPrincipal presentador)
    {
        presentadorPP = presentador;
        fireBaseHelper.getCombinacionesFB(this, listaCombinaciones);
    }

    //Una vez obtendidos todos los datos de FireBase se oculta el progressDialog de la pantalla principal
    public void finObtencionIncialDatos()
    {
        presentadorPP.finObtencionInicialDatos();
    }

    public void setListaCombinaciones(List<DatosCombinacion> listaCombinaciones)
    {
        this.listaCombinaciones = listaCombinaciones;
    }

    //Se devuelve la lista con todos los datos guardados en Firebase
    public List<DatosCombinacion> getListaCombinaciones()
    {
        return listaCombinaciones;
    }

    //Se obtiene las combinaciones ganadoras del mes y año elegidos por el usuario
    public List<DatosCombinacion> getGanadoras(String año, String mes)
    {

        List<DatosCombinacion> listaGanadoras = new ArrayList<DatosCombinacion>();

        for(DatosCombinacion combinacion:listaCombinaciones)
        {
            if(String.valueOf(combinacion.getAño()).equals(año) && combinacion.getMes().equals(mes))
                listaGanadoras.add(combinacion);
        }

        return listaGanadoras;
    }

    public List<String> getAñosCombinacion()
    {
        List<String> añosCombinacion = new ArrayList<String>();

        for(DatosCombinacion combinacion:listaCombinaciones)
        {
            if(!añosCombinacion.contains(String.valueOf(combinacion.getAño())))
                añosCombinacion.add(String.valueOf(combinacion.getAño()));
        }

        return añosCombinacion;

    }


    public void insertarCombinacion(String key, DatosCombinacion datosCombinacion, PresentadorPantallaInsertar presentador)
    {

        presentadorPI = presentador;
        fireBaseHelper.insertarCombinacionFB(key, datosCombinacion, this);
    }


    public void insercionCombinacionFinalizada(int mensaje)
    {

        presentadorPI.insercionCombinacionFinalizada(mensaje);

    }


}
