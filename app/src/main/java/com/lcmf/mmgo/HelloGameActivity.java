package com.lcmf.mmgo;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
//import com.baidu.mobstat.StatService;
import com.lcmf.mmgo.threelife.FastDie;

import android.os.Bundle;
import android.util.Log;


public class HelloGameActivity extends AndroidApplication {
    private String TAG = "--- HelloGameActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Login Main");
        //setContentView(R.layout.activity_main);
//        initialize(new FirstGame());
//        StatService.start(this);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useWakelock = true;
//        initialize(new MyGdxGameCamera(), config);
//        initialize(new MyGdxGameLabel(), config);
//        initialize(new MyGdxGameButton(), config);
//        initialize(new MyGdxGameImage(), config);
//        initialize(new MyGdxGameAction(), config);
//        initialize(new MyGdxGameScreen(), config);
//        initialize(new MyGdxGameBox2d(), config);
//        initialize(new MyGdxGameBox2dModel(), config);
//        initialize(new MyGdxGameBox2dLight(), config);
//        initialize(new MyGdxGameMulti(), config);
//        initialize(new MyGdxGameParallax(), config);
//        initialize(new MyGdxGameParallaEffect(), config);
//        initialize(new MyGdxGameRunnableAction(), config);
//        initialize(new MyGdxGameShader(), config);   //Shader 例子
//        initialize(new MainGame(), config);      //flappy bird 例子
//        initialize(new SuperBall(), config);     //旱地冰壶例子，未完成
//        initialize(new MarioGame(), config);     //马里奥例子
//        initialize(new SuperJumper(), config);   //跳跳例子
//        initialize(new MyAdapter(), config);       //画线例子
//        initialize(new TZFEMainGame(), config);       //2048
//        initialize(new TenWaterGame(), config);       //10滴水游戏
//        initialize(new SnakeGame(), config);       //贪吃蛇
//        initialize(new Lib004_Actor(), config);       //Actor
//        initialize(new Lib004_Actor(), config);       //Actor

        //initialize(new GamePad(), config); //虚拟摇杆
        Class c = null;
        try {
            c = Class.forName("com.lcmf.mmgo.advanbox.MyGdxGameBox2d");
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
//       initialize(new ActionUser(), config); //勇往直前
        //initialize(new FirstGame(), config); //勇往直前
        //initialize(new MyGdxGameBox2d(), config);
    }
}
