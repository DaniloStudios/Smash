package com.danilostudios.games.smash;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.gdacarv.engine.androidgame.Sprite;

public class Pink extends Sprite 
{
	
	public int speed = 1;
	public byte direction = 0;
	private boolean dying = false;
	public boolean dead = false;
	protected byte animationSpeedControl = 0;

	public Pink(int x, int y, Random random, Bitmap bmp, int bmp_rows, int bmp_columns) 
	{
		super(bmp, bmp_rows, bmp_columns);
		direction = (byte) random.nextInt(8);
		int frame = 1 + direction*3;
		setAnimation(frame, frame, frame+3, ANIM_GOBACK);
		this.x = x;
		this.y = y;
	}

	@Override
	public void update() 
	{
		if(!dying)
		{
			animationSpeedControl++;
			if(animationSpeedControl >= 6 - speed)
			{
				super.update();
				animationSpeedControl = 0;
			}
			x += direction % 4 > 0 ? direction > 4 ? -speed : speed : 0;
			y += Math.abs((direction-2) % 4) > 0 ? direction > 6 || direction < 2 ? speed : -speed : 0;
		}
		else
		{
			int alpha = mPaint.getAlpha();
			if(alpha <= 25)
				dead = true;
			else
				mPaint.setAlpha(alpha-10);
		}
	}
	
	public void changeDirection(byte direct)
	{
		direction = direct;
		int frame = 1 + direction*3;
		setAnimation(frame, frame, frame+3, ANIM_GOBACK);
	}
	
	public void kill()
	{
		dying = true;
		setAnimation(0, 0, 1, ANIM_STOP);
		mPaint = new Paint();
	}

	public boolean isDead() 
	{
		return dying || dead;
	}
	
	@Override
	public void onDraw(Canvas canvas) 
	{
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
}
