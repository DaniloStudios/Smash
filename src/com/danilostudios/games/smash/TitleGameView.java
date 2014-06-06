package com.danilostudios.games.smash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.view.MotionEvent;

import com.gdacarv.engine.androidgame.GameView;
import com.gdacarv.engine.androidgame.Sprite;

public class TitleGameView extends GameView {
	
	private Paint paintText;
	private Context context;
	
	int highScore;

	public TitleGameView(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public void TouchEvents(MotionEvent event) {
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
			Context ctx = getContext();
			((Activity) ctx).finish();
			Intent intent = new Intent(ctx, MainGameActivity.class);
			ctx.startActivity(intent);
		}
	}

	@Override
	protected void onLoad() {
		Resources res = getResources();
		Sprite title; 
		mSprites.add(title = new Sprite(BitmapFactory.decodeResource(res, R.drawable.title)));
		title.x = getWidth()/2 - title.width/2;
		title.y = 20;
		paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(25);
		
		highScore = PreferenceManager.getDefaultSharedPreferences(context).getInt("HIGH_SCORE", 0);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText(context.getString(R.string.iniciar_jogo), 50, getHeight()*0.6f, paintText);
		canvas.drawText(context.getString(R.string.highscore)+" "+highScore, 40, getHeight()*0.8f, paintText);
	}
}
