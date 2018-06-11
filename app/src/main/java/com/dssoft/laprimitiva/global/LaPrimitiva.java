package com.dssoft.laprimitiva.global;

import android.app.Application;

import com.dssoft.laprimitiva.modelo.DataManagerFB;
import com.dssoft.laprimitiva.modelo.FireBaseHelper;

/**
 * Created by Angel on 05/12/2017.
 */

public class LaPrimitiva extends Application
{

    DataManagerFB dataManagerFB;

    @Override
    public void onCreate()
    {
        super.onCreate();

        FireBaseHelper fireBaseHelper = new FireBaseHelper();
        dataManagerFB = new DataManagerFB(fireBaseHelper);

    }

    public DataManagerFB getDataManagerFB() {
        return dataManagerFB;
    }
}
