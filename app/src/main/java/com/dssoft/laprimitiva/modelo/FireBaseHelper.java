package com.dssoft.laprimitiva.modelo;


import com.dssoft.laprimitiva.R;
import com.dssoft.laprimitiva.pojo.DatosCombinacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Angel on 05/12/2017.
 */

public class FireBaseHelper
{

    private FirebaseDatabase databasePrimitiva;

    public FireBaseHelper()
    {
       databasePrimitiva = FirebaseDatabase.getInstance();
    }

    //Se obtiene todas las combinaciones almacenadas en FB y se guardan en una lista
    public List<DatosCombinacion> getCombinacionesFB(final DataManagerFB dataManagerFB, final List<DatosCombinacion> listaCombinaciones)
    {

        final DatabaseReference databaseReference = databasePrimitiva.getReference("combinaciones");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                databaseReference.removeEventListener(this);

                Iterable<DataSnapshot> iterableCombinaciones = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iteratorCombinaciones = iterableCombinaciones.iterator();
                listaCombinaciones.clear();

                while(iteratorCombinaciones.hasNext())
                {
                    DataSnapshot data = iteratorCombinaciones.next();
                    DatosCombinacion datosCombinacion = data.getValue(DatosCombinacion.class);
                    datosCombinacion.setFecha(data.getKey().substring(6));

                    listaCombinaciones.add(datosCombinacion);
                }

                dataManagerFB.finObtencionIncialDatos();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });

        return listaCombinaciones;

    }


    public void insertarCombinacionFB(String key, DatosCombinacion datosCombinacion, final DataManagerFB dataManagerFB)
    {
        DatabaseReference databaseReference = databasePrimitiva.getReference("combinaciones").child(key);

        databaseReference.setValue(datosCombinacion, new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
            {
                dataManagerFB.insercionCombinacionFinalizada(R.string.mensaje_ok);
            }

        });
    }

}
