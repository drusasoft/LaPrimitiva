package com.dssoft.laprimitiva.presentador;

import java.lang.reflect.Array;
import java.util.List;

public interface PresentadorMVPPantallaCombinaciones extends PresentadorBase
{

    public List<String> getListaAños();
    public void getGanadoras(String año, String mes);

}
