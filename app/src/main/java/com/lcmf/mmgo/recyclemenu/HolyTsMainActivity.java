package com.lcmf.mmgo.recyclemenu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lcmf.mmgo.R;

import java.util.ArrayList;
import java.util.List;

public class HolyTsMainActivity extends AppCompatActivity {

    private List<HolyMenuItem> menuItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_main);

        initMenus();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        HolyMenuAdapter adapter = new HolyMenuAdapter(menuItems, this);
        recyclerView.setAdapter(adapter);
    }

    private void initMenus() {
        for (int i = 0; i < 1; i++) {
            HolyMenuItem apple = new HolyMenuItem("SplashActivity", R.drawable.draw_btn_circle_blue, "com.lcmf.mmgo.SplashActivity");
            menuItems.add(apple);
            HolyMenuItem banana = new HolyMenuItem("HelloGameActivity", R.drawable.draw_btn_circle_blue, "com.lcmf.mmgo.HelloGameActivity");
            menuItems.add(banana);
            HolyMenuItem orange = new HolyMenuItem("MainActivity", R.drawable.draw_btn_circle_blue, "com.lcmf.mmgo.MainActivity");
            menuItems.add(orange);
            HolyMenuItem threelife = new HolyMenuItem("AndroidFastDieLauncher", R.drawable.draw_btn_circle_blue, "com.lcmf.mmgo.threelife.AndroidFastDieLauncher");
            menuItems.add(threelife);
            HolyMenuItem bigbrick = new HolyMenuItem("BigBrick", R.drawable.draw_btn_circle_blue, "com.lcmf.mmgo.advancestrangely.BigBrickMainLauncher");
            menuItems.add(bigbrick);
        }
    }
}