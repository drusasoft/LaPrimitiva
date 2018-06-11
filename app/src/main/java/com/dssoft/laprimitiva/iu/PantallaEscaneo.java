package com.dssoft.laprimitiva.iu;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;
import com.dssoft.laprimitiva.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 17/01/2018.
 */

public class PantallaEscaneo extends AppCompatActivity implements SurfaceHolder.Callback
{

    @BindView(R.id.toolBarEscaneo) Toolbar toolbar;
    @BindView(R.id.surfaceCaptura) SurfaceView surfaceCaptura;
    @BindView(R.id.txtCodigo) TextView txtCodigo;

    private CameraSource mCameraSource;
    private BarcodeDetector mBarcodeDetector;
    private DetectorCodigo mDetectorCodigo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_escaneo);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        surfaceCaptura.getHolder().addCallback(this);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        liberarRecursos();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        //Toast.makeText(this, "SurfaceView Creado", Toast.LENGTH_LONG).show();
        iniciarCamara();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    private void liberarRecursos()
    {
        if(mCameraSource != null)
        {
            mCameraSource.stop();
            mCameraSource.release();
            mCameraSource = null;
        }

        if(mBarcodeDetector != null)
        {
            mBarcodeDetector.release();
            mBarcodeDetector = null;
        }

        if(mDetectorCodigo != null)
        {
            mDetectorCodigo.release();
            mDetectorCodigo = null;
        }
    }


    private void iniciarCamara()
    {

        mBarcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE | Barcode.DATA_MATRIX | Barcode.PDF417 | Barcode.AZTEC|
                        Barcode.EAN_8| Barcode.EAN_13| Barcode.UPC_A| Barcode.UPC_E| Barcode.CODE_39| Barcode.CODE_93|
                        Barcode.CODE_128| Barcode.ITF| Barcode.CODABAR)
                .build();

        mCameraSource = new CameraSource.Builder(getApplicationContext(), mBarcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(surfaceCaptura.getWidth(), surfaceCaptura.getHeight())
                .setAutoFocusEnabled(true)
                .build();

        //Necesario para lo moviles Api23 comprueba si el usuario ha dado permiso para usar la camara
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {

            Toast.makeText(this, "Sin Permisos Camara", Toast.LENGTH_LONG).show();
            return;

        }else
        {
            try
            {
                mDetectorCodigo = new DetectorCodigo();
                mCameraSource.start(surfaceCaptura.getHolder());
                mBarcodeDetector.setProcessor(mDetectorCodigo);

                Log.e("Detecion", "Iniciada");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void mostrarMensaje(String valorCodigo)
    {
        txtCodigo.setText(valorCodigo);
    }


    private class DetectorCodigo implements Detector.Processor
    {

        @Override
        public void release()
        {

        }

        @Override
        public void receiveDetections(Detector.Detections detections)
        {


            if(detections.getDetectedItems().size() > 0)
            {
                Log.e("Detecion", "Realizada");

                final Barcode barcode = (Barcode) detections.getDetectedItems().valueAt(0);
                Log.e("barcode" , barcode.rawValue);

                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mostrarMensaje(barcode.rawValue);
                    }
                });
            }

        }
    }
}
