package com.danilostudios.games.smash;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.gdacarv.engine.androidgame.Sprite;

public class Background extends Sprite 
{
	
	private Bitmap mBitmap;

	public Background(Random random, int stageWidth, int stageHeigth, Bitmap bmp) 
	{
		super(bmp);
		mBitmap = Bitmap.createBitmap(stageWidth, stageHeigth, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mBitmap);
		
		Rect source = new Rect(0, 0, 32, 32);
		Rect destiny = new Rect();
		for(int i = 0; i < canvas.getWidth(); i += 32)
		{
			destiny.right = (destiny.left = i) + 32;
			for(int j = 0; j < canvas.getHeight(); j += 32)
			{
				destiny.bottom = (destiny.top = j) + 32;
				canvas.drawBitmap(bmp, source, destiny, null);
			}
		}
		
		int tilesX = stageWidth/32 + 1,
			tilesY = stageHeigth/32 + 1,
			qts;
		qts = (int) (random.nextInt((int) (tilesX*tilesY*0.3f))+tilesX*tilesY*0.1f);
		source.right = (source.left = 32) + 32;
		for(int i = 0; i < qts; i++)
		{
			destiny.right = (destiny.left = random.nextInt(tilesX)*32) + 32;
			destiny.bottom = (destiny.top = random.nextInt(tilesY)*32) + 32;
			canvas.drawBitmap(bmp, source, destiny, null);
		}
		
		qts = (int) (random.nextInt((int) (tilesX*tilesY*0.1f))+tilesX*tilesY*0.05f);
		source.right = (source.left = 64) + 32;
		for(int i = 0; i < qts; i++)
		{
			destiny.right = (destiny.left = random.nextInt(tilesX)*32) + 32;
			destiny.bottom = (destiny.top = random.nextInt(tilesY)*32) + 32;
			canvas.drawBitmap(bmp, source, destiny, null);
		}	
		
		qts = (int) (random.nextInt((int) (tilesX*tilesY*0.1f))+tilesX*tilesY*0.05f);
		source.right = (source.left = 96) + 32;
		for(int i = 0; i < qts; i++)
		{
			destiny.right = (destiny.left = random.nextInt(tilesX)*32) + 32;
			destiny.bottom = (destiny.top = random.nextInt(tilesY)*32) + 32;
			canvas.drawBitmap(bmp, source, destiny, null);
		}
	}
	
	@Override
	public void onDraw(Canvas canvas) 
	{
		canvas.drawBitmap(mBitmap, 0, 0, null);
	}

}
