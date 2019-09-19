package alex.com.gdxdemo.awesome;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
//import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.badoo.mobile.util.WeakHandler;
import com.example.myapplication.R;

//import alex.com.gdxdemo.R;
import alex.com.gdxdemo.balloon.BalloonParticleEffectView;
import alex.com.gdxdemo.base.InterceptableViewGroup;
import alex.com.gdxdemo.fireworks.GiftParticleContants;


/**
 * Created by QJoy on 2017.12.25.
 */
@SuppressWarnings("All")
public class AwesomeFragment extends AndroidFragmentApplication implements InputProcessor {

	public static boolean openDEBUGLog = false;
    private static final String TAG = AwesomeFragment.class.getSimpleName();
    private View m_viewRooter = null;
    //粒子效果UI容器层
    private InterceptableViewGroup mContainer;
    //粒子效果绘制层
    private BalloonParticleEffectView particleEffectView;
    //Fragment 处于销毁过程中标志位
    private boolean m_isDestorying = false;
    //Fragment 处于OnStop标志位
    private boolean m_isStoping = false;
    //Screen 是否需要重建播放
    private boolean m_isNeedBuild =true;

	private boolean m_hasBuilt = false;

    @Override
    public void startActivity(Intent intent) {

    }

    private WeakHandler m_WeakHandler = new WeakHandler();

    public void preDestory(){

	    if (openDEBUGLog)
	        Log.d(TAG, "preDestory");

	    if (!m_hasBuilt)
		    return;

	    particleEffectView.forceOver();
	    particleEffectView.setCanDraw(false);

        m_isDestorying = true;
        m_isStoping = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	    if (openDEBUGLog)
	        Log.d(TAG, "onCreateView");

        m_viewRooter = inflater.inflate(R.layout.lf_layout_giftparticle, null);
        return m_viewRooter;
    }

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

		if (openDEBUGLog)
			Log.d(TAG, "onViewCreated");

		super.onViewCreated(view, savedInstanceState);
		buildGDX();
	}

    public void buildGDX(){

	    if (openDEBUGLog)
	        Log.d(TAG, "buildGDX");

        particleEffectView = new BalloonParticleEffectView();
        View effectview = CreateGLAlpha(particleEffectView);
        mContainer = (InterceptableViewGroup) m_viewRooter.findViewById(R.id.container);
        mContainer.addView(effectview);
	    mContainer.setIntercept(true);
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
	    m_hasBuilt = true;
    }

    @Override
    public void onStart() {

	    if (openDEBUGLog)
            Log.d(TAG, "onStart");

        m_isStoping = false;
        super.onStart();

	    if (particleEffectView != null)
	        particleEffectView.setCanDraw(true);
    }

    @Override
    public void onStop() {

	    if (openDEBUGLog)
            Log.d(TAG, "onStop");

        m_isStoping = true;
	    particleEffectView.setCanDraw(false);
        super.onStop();
    }

    @Override
    public void onResume() {

	    if (openDEBUGLog)
            Log.d(TAG, "onResume");

        super.onResume();

	    if (particleEffectView != null) {
		    particleEffectView.closeforceOver();
	    }
    }

    @Override
    public void onPause() {

	    if (openDEBUGLog)
            Log.d(TAG, "onPause");

	    if (particleEffectView != null) {
		    particleEffectView.forceOver();
	    }

	    super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration config) {

	    if (openDEBUGLog)
		    Log.d(TAG, "onConfigurationChanged");

        super.onConfigurationChanged(config);

        mContainer.removeAllViews();
        buildGDX();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private View CreateGLAlpha(ApplicationListener application) {

	    if (openDEBUGLog)
		    Log.d(TAG, "CreateGLAlpha");

        //	    GLSurfaceView透明相关
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.r = cfg.g = cfg.b = cfg.a = 8;

        View view = initializeForView(application, cfg);

        if (view instanceof SurfaceView) {
            GLSurfaceView glView = (GLSurfaceView) graphics.getView();
            glView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
            glView.setZOrderMediaOverlay(true);
            glView.setZOrderOnTop(true);
        }

        return view;
    }

    @Override
    public boolean keyDown(int i) {

	    if (openDEBUGLog)
		    Log.d(TAG, "CreateGLAlpha");

        if (i == Input.Keys.BACK) {
            Intent intent = new Intent();
            intent.setAction(GiftParticleContants.BROADCAST_GIFTPARTICLE_BACKKEY);
	        if (getActivity() != null)
                getActivity().sendBroadcast(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(int i) {
        return false;
    }

    private boolean isScreenLock(){
        try {
            PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
            boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
            return !isScreenOn;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
