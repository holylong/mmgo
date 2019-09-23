package com.example.myapplication.tzfe.tzfestage.base;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.myapplication.tzfe.TZFEMainGame;
//import com.example.myapplication.tzfe.MainGame;
//import cn.appkf.game2048.MainGame;

/**
 * 舞台基类
 *
 * @author xietansheng
 */
public abstract class BaseStage extends Stage {

    private TZFEMainGame mainGame;

    /** 舞台是否可见（是否更新和绘制） */
    private boolean visible = true;

    public BaseStage(TZFEMainGame mainGame, Viewport viewport) {
        super(viewport);
        this.mainGame = mainGame;
    }

    public TZFEMainGame getMainGame() {
        return mainGame;
    }

    public void setMainGame(TZFEMainGame mainGame) {
        this.mainGame = mainGame;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}















