package com.danilostudios.games.smash;

import android.app.Activity;
import android.os.Bundle;

public class MainGameActivity extends Activity {
	
	private MainGameView gameView;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gameView = new MainGameView(this));
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		gameView.musica.stop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		gameView.sound.release();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(gameView.musica != null)
			gameView.musica.start();
	}
}
