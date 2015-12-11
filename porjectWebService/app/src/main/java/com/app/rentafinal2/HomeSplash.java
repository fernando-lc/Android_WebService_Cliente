package com.app.rentafinal2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;

import com.app.movie.R;

/**
 * Created by ForeverTuCode on 11/12/2015.
 */
public class HomeSplash extends Activity {  public static final int segundos=8;
    public static final int milisegundos=segundos*1000;
    public static final int delay=2;
    private ProgressBar pbprogreso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        pbprogreso= (ProgressBar) findViewById(R.id.pbprogreso);
        pbprogreso.setMax(maximo_progreso());
        empezarSplash();
    }
    public  void empezarSplash(){
        new CountDownTimer(milisegundos, 1000 )
        {

            @Override
            public void onTick(long millisUntilFinished) {
                //hacemos que la barra de progreso avance
                pbprogreso.setProgress(progreso_barra(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent nuevofrom=new Intent(HomeSplash.this,MainActivity.class);
                startActivity(nuevofrom);
                finish();

            }
        }.start();
    }
    public int progreso_barra(long miliseconds)
    {
        return (int) ((milisegundos-miliseconds)/1000);
    }
    public int maximo_progreso(){
        return segundos-delay;
    }
}
