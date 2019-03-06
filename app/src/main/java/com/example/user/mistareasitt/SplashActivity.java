package com.example.user.mistareasitt;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();  //ocultar la barra

        Typeface miFuente = Typeface.createFromAsset(getAssets(),"athika.ttf");//obtenemos los assets y le decimos el nombre de la fuente
        TextView titulo = (TextView) findViewById(R.id.titulo); //declaramos el objeto titulo y buscamos el View que esta
        //R.id con el nombre titulo
        titulo.setTypeface(miFuente);//tenemos la fuente y la etiqueta y tenemos que decirle a la etiqueta que use esa fuente


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.animacion);
        titulo.startAnimation(anim);


        anim.setAnimationListener(this);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
