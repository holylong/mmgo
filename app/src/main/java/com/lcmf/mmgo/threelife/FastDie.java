package com.lcmf.mmgo.threelife;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lcmf.mmgo.simpleutil.DateUtils;
import com.lcmf.mmgo.threelife.PhysicsUtil.BombCollision;
import com.lcmf.mmgo.threelife.music.TLAssetManager;
import com.lcmf.mmgo.threelife.sprite.BombBlock;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class FastDie implements ApplicationListener {

    private static String TAG = "FastDie";
    Stage stage;
    Sprite sprite;
    Sprite spriteUpper;
    Sprite spriteDown;
    SpriteBatch spriteBatch;
    Texture background;

    Texture backUpper;
    //Texture backDown;

    Texture airPlane;
    TextureAtlas atlas;
    Touchpad touchpad;  //控制面板
    Button   touchbtn;  //加速按钮
    TextureRegion btntr; //按钮背景
    TextureRegionDrawable txup;
    TextureRegionDrawable txdown;
    Texture  btnbn;     //背景图片

    Touchpad.TouchpadStyle touchpadStyle;
    TextureRegionDrawable knobRegion;
    TextureRegionDrawable touchBackground;
    Texture textureBack;
    Texture textureFront;
    //Texture killer;     //精灵
    int speed;
    int speedX;
    int speedY;
    int x = 150;
    int y = 150;
    VulThread vtThread;

    //List<Sprite> arrBomb;
    List<BombBlock> arrBomb;
    Texture bomb;
    TextureRegion bombRegion;
    float fStart;
    long lStart;

    TLAssetManager assetManager;


    private int randomGenerateBlock(){
        //float fEnd = Gdx.graphics.getDeltaTime();  //只是距离上一帧的时间值
        //float fEnd = Gdx.graphics.getRawDeltaTime();
        //Log.d(TAG, "delta time:" + fEnd + "s");
        long lEnd = DateUtils.getCurentTimes();
        Log.d(TAG, "cur times:" + DateUtils.getCurentTimes());

        if(Math.abs(lEnd - lStart) > 1000)
        {
            lStart = lEnd;

            int option = MathUtils.random(0,3);
            switch (option){
                case 0:
                    arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
                            0, Gdx.graphics.getHeight()/MathUtils.random(1,10), Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
                    break;
                case 1:
                    arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
                            Gdx.graphics.getWidth()/MathUtils.random(1,10), 0, Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
                    break;
                case 2:
                    arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
                            Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/MathUtils.random(1,10), Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
                    break;
                case 3:
                    arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
                            Gdx.graphics.getWidth()/MathUtils.random(1,10), Gdx.graphics.getHeight(), Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
                    break;
                default:
                        arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
                                Gdx.graphics.getWidth()/MathUtils.random(1,10), 0, Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
                        break;
            }

        }

        return arrBomb.size();
    }

    @Override
    public void create() {
        //fStart = Gdx.graphics.getDeltaTime();
        spriteBatch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("threelife/mubu.jpg"));
        airPlane = new Texture(Gdx.files.internal("threelife/enemy1.png"));
        backUpper = new Texture(Gdx.files.internal("threelife/backer.jpg"));
        bomb = new Texture(Gdx.files.internal("threelife/rock.png"));
        bombRegion = new TextureRegion(bomb, 13,13, 45,45);

        spriteDown = new Sprite(backUpper, 0,0, 100,100);
        spriteUpper = new Sprite(backUpper, 0,0, 100,100);

        spriteUpper.setSize(Gdx.graphics.getWidth(), 300);
        spriteDown.setSize(Gdx.graphics.getWidth(), 300);

        spriteUpper.setPosition(0,0);
        spriteDown.setPosition(0,Gdx.graphics.getHeight() - 300);

        arrBomb = new ArrayList<BombBlock>();

        /**music loader*/
        assetManager = new TLAssetManager();
        assetManager.InitAsset();

        //初始化陨石时，需要将陨石一个一个的从边界处释放，并且跟随时间戳变化增多
        //打算，一个石头消失就生成两个石头，或者是每两秒增加一个石头
//        for(int i = 0 ;i < 50; i++){
//            if(i < 10)
//            arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
//                    Gdx.graphics.getWidth()/ MathUtils.random(i,10), Gdx.graphics.getWidth()/MathUtils.random(i,10), Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
//            else if(i > 10 && i < 20){
//                arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
//                        Gdx.graphics.getWidth()/ MathUtils.random(i,20), Gdx.graphics.getWidth()/MathUtils.random(i,20), Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
//            }else{
//                arrBomb.add(new BombBlock(new Sprite(bombRegion,0,0,Gdx.graphics.getWidth()/20,Gdx.graphics.getWidth()/20),
//                        Gdx.graphics.getWidth()/ MathUtils.random(i,50), Gdx.graphics.getWidth()/MathUtils.random(i,50), Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10));
//            }
//        }
        //atlas = new TextureAtlas(Gdx.files.internal(""));
        //sprite = atlas.createSprite("");
        //sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //sprite = new Sprite(airPlane, 0,0,49,37);
        //sprite = new Sprite(airPlane, 11,6,140,98);
        sprite = new Sprite(airPlane, 11,6,45,35);
        sprite.setSize(Gdx.graphics.getWidth()/15*4/3, Gdx.graphics.getWidth()/15);
        sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        textureBack = new Texture(Gdx.files.internal("threelife/panzi.jpg"));
        textureFront = new Texture(Gdx.files.internal("threelife/pn.jpg"));
        //killer = new Texture(Gdx.files.internal("shoot/bomb.png"));

        touchBackground = new TextureRegionDrawable(new TextureRegion(textureBack, 50, 50, 400,400));
        knobRegion = new TextureRegionDrawable(new TextureRegion(textureFront, 170, 170, 155,155));
        touchpadStyle = new Touchpad.TouchpadStyle(touchBackground, knobRegion);
        touchpad = new Touchpad(15, touchpadStyle);
        touchpad.setBounds(0,Gdx.graphics.getHeight() - 300, 300,300);
        touchbtn = new Button();
        btntr = new TextureRegion();
        btnbn = new Texture(Gdx.files.internal("bubble.png"));
        btntr.setRegion(btnbn);

        //btntr.setRegion(0,0,100,100);
        txdown = new TextureRegionDrawable(btntr);
        txup = new TextureRegionDrawable(btntr);
        touchbtn = new ImageButton(txup, txdown);
        touchbtn.setBounds(0,0,300,300);


        touchbtn.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                speed = 20;
                Log.d(TAG, "down speed:" + speed);
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                speed = 10;
                Log.d(TAG, "up speed:" + speed);
            }
        });

        speed = 10;
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.addActor(touchpad);
        stage.addActor(touchbtn);

        speedX = 0;
        vtThread = new VulThread();
        vtThread.start();
        y = Gdx.graphics.getHeight()/2;
        x = Gdx.graphics.getWidth()/2;
    }

    @Override
    public void resize(int i, int i1) {

    }

    public void updateBombInfo(){
        for(BombBlock s:arrBomb){
            //s.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
            //s.setSize(Gdx.graphics.getWidth()/10, Gdx.graphics.getWidth()/10);
            s.update();
        }
    }

    public void updateAipPlaneInfo(){
        //sprite.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
        sprite.setPosition(x, y);
    }

    public void update(){
        if(touchpad.isTouched()){
            x += touchpad.getKnobPercentX()*speed;
            y += touchpad.getKnobPercentY()*speed;

            speedX = (int)(touchpad.getKnobPercentX()*speed);
            speedY = (int)(touchpad.getKnobPercentY()*speed);
            vtThread.setVulX(speedX);
            vtThread.setVulY(speedY);
            Log.d(TAG, " speedX: " + speedX + " speedY:" + speedY);

            if(x > Gdx.graphics.getWidth() - (int)sprite.getWidth()){
                x = Gdx.graphics.getWidth() - (int)sprite.getWidth();
            }
            if(x < 0){
                x = 0;
            }
            if(y > Gdx.graphics.getHeight() - (int)sprite.getHeight() - 300){
                y = Gdx.graphics.getHeight() - (int)sprite.getHeight() - 300;
            }
            if(y < 300){
                y = 300;
            }
        }else{
            x += vtThread.getVulX();
            y += vtThread.getVulY();

            if(x > Gdx.graphics.getWidth() - (int)sprite.getWidth()){
                x = Gdx.graphics.getWidth() - (int)sprite.getWidth();
            }
            if(x < 0){
                x = 0;
            }
            if(y > Gdx.graphics.getHeight() - (int)sprite.getHeight()- 300){
                y = Gdx.graphics.getHeight() - (int)sprite.getHeight()- 300;
            }
            if(y < 300){
                y = 300;
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        this.update();
        randomGenerateBlock();
        updateAipPlaneInfo();
        updateBombInfo();

        spriteBatch.begin();
        spriteBatch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sprite.draw(spriteBatch);
        spriteDown.draw(spriteBatch);
        spriteUpper.draw(spriteBatch);

        for(BombBlock s:arrBomb){
            s.getSprite().draw(spriteBatch);
        }
        spriteBatch.end();

        stage.act();
        stage.draw();

        checkCollision();

//        for(BombBlock s:arrBomb){
//            if(BombCollision.checkBombCollision(s, sprite)){
//                assetManager.PlayHitMusic();
//                arrBomb.remove(s);
//            }
//        }
    }

    private void checkCollision(){
        for(int i =0; i < this.arrBomb.size(); i++){
            if(BombCollision.checkBombCollision(this.arrBomb.get(i), sprite)){
                assetManager.PlayHitMusic();
                arrBomb.remove(this.arrBomb.get(i));
                i--;
            }
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
