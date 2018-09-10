package com.example.conwaysgameoflife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private GameOfLife gameOfLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        gameOfLife = (GameOfLife) findViewById(R.id.gameOfLife);
    }

    @OnClick(R.id.start)
    public void startGame() {
        gameOfLife.start();
    }

    @OnClick(R.id.restart)
    public void restartGame() {
        gameOfLife.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameOfLife.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameOfLife.stop();
    }
}
