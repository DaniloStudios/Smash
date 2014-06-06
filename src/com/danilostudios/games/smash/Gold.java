package com.danilostudios.games.smash;

import java.util.Random;

import android.graphics.Bitmap;

import com.gdacarv.engine.androidgame.Sprite;

public class Gold extends Sprite {
	
	public int speedX, speedY;

	public Gold(int x, int y, Random random, Bitmap bmp, int bmp_rows, int bmp_columns) {
		super(bmp, bmp_rows, bmp_columns);
		setAnimation(ANIM_GO);
		speedX = random.nextInt(7) - 3;
		speedY = random.nextInt(7) - 3;
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() {
		super.update();
		x += speedX;
		y += speedY;
	}
}
