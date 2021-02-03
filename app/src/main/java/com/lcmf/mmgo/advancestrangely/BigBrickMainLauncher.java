package com.lcmf.mmgo.advancestrangely;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class BigBrickMainLauncher extends AndroidApplication {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useWakelock = true;
        Class c = null;
        try {
            c = Class.forName("com.lcmf.mmgo.advancestrangely.FirstGame");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            initialize((ApplicationListener)(c.newInstance()), config); //勇往直前
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
