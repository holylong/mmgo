package alex.com.gdxdemo;

import android.app.Activity;
import android.os.Bundle;

import com.example.myapplication.R;

import alex.com.gdxdemo.testcode.SpringEffect;

/**
 * @author AleXQ
 * @Date 16/2/27
 * @Description: 主界面选择demo
 */

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		SpringEffect.doEffectSticky(findViewById(R.id.gift), new Runnable() {
			@Override
			public void run() {
				FireWorksActivity.launch(MainActivity.this);
			}
		});

		SpringEffect.doEffectSticky(findViewById(R.id.balloon), new Runnable() {
			@Override
			public void run() {
				BalloonActivity.launch(MainActivity.this);
			}
		});

		SpringEffect.doEffectSticky(findViewById(R.id.box2d), new Runnable() {
			@Override
			public void run() {
				Box2dActivity.launch(MainActivity.this);
			}
		});

		SpringEffect.doEffectSticky(findViewById(R.id.spine), new Runnable() {
			@Override
			public void run() {
				SpineActivity.launch(MainActivity.this);
			}
		});
	}

}
