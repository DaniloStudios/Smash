package com.danilostudios.games.smash;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.app.Activity;

import com.gdacarv.engine.androidgame.GameView;
import com.gdacarv.engine.androidgame.Sprite;

public class MainGameView extends GameView {
	
	protected int level = 1, score = 0;
	
	protected ArrayList<Pink> pinks;
	protected ArrayList<Gold> golds;
	protected Background background;
	private long startTime;
	private Context context;
	protected Sprite nextLevelSprite;

	private Paint paintText;
	float scoreX, scoreY;
	
	private int alivePinks;
	
	public MediaPlayer musica;
	public SoundPool sound;
	private int soundIdPinkHit, soundIdGoldHit, soundIdWin;

	public MainGameView(Context context) {
		super(context);
		this.context = context;
		
	}

	@Override
	public void TouchEvents(MotionEvent event) {
		if((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_DOWN){
			if(alivePinks > 0){
				float x = event.getX(), y = event.getY();
				for(Gold gold : golds)
					if(x > gold.x && y > gold.y && x < gold.x + gold.width && y < gold.y + gold.height){
						sound.play(soundIdGoldHit, 1f, 1f, 0, 0, 1f);
						((Activity) context).finish();
						Intent intent = new Intent(context, GameOverActivity.class);
						intent.putExtra("SCORE", score);
						context.startActivity(intent);
					}
				for(Pink pink : pinks)
					if(!pink.isDead() && x > pink.x && y > pink.y && x < pink.x + pink.width && y < pink.y + pink.height){
						sound.play(soundIdPinkHit, 1f, 1f, 0, 0, 1f);
						pink.kill();
						int add;
						score += add = (int) Math.max(100 - level*3 - (System.currentTimeMillis() - startTime)/500, 1);
						alivePinks--;
						if(alivePinks == 0){
							nextLevelSprite.visible = true;
							sound.play(soundIdWin, 1f, 1f, 0, 0, 1f);
						}
						Log.d("Score", "Valeu: " + add);
					}
			}else{
				newStage();
			}
		}
	}

	@Override
	protected void onLoad() {
		pinks = new ArrayList<Pink>();
		golds = new ArrayList<Gold>();
		Random random = new Random();
		Resources res = getResources();
		Bitmap bitmapGold = BitmapFactory.decodeResource(res, R.drawable.gold_head);
		int limitGoldX = getWidth()-bitmapGold.getWidth()/8,
			limitGoldY = getHeight()-bitmapGold.getHeight();
		for(int i = 0; i < 10; i++)
			golds.add(new Gold(random.nextInt(limitGoldX), random.nextInt(limitGoldY), random, bitmapGold, 1, 8));
		background = new Background(random, getWidth(), getHeight(), BitmapFactory.decodeResource(res, R.drawable.background));
		mSprites.add(background);
		mSprites.addAll(golds);

		paintText = new Paint();
		paintText.setColor(Color.WHITE);
		paintText.setTextSize(25);
		scoreX = getWidth()*0.05f;
		scoreY = getHeight()*0.95f;
		
		mSprites.add(nextLevelSprite = new Sprite(BitmapFactory.decodeResource(res, R.drawable.nextlevel)));
		nextLevelSprite.x = getWidth()/2 - nextLevelSprite.width/2;
		nextLevelSprite.y = (int) (getHeight()*0.2f);
		
		newStage();
		
		musica = MediaPlayer.create(context, R.raw.background_music);
		musica.setLooping(true);
		musica.start();
		
		sound = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundIdPinkHit = sound.load(context, R.raw.pink_hit, 1);
		soundIdGoldHit = sound.load(context, R.raw.gold_hit, 1);
		soundIdWin = sound.load(context, R.raw.win, 1);
	}
	
	@Override
	public void update() {
		super.update();
		Pink pink;
		for(int i = 0; i < pinks.size(); i++){
			pink = pinks.get(i);
			if(pink.dead){
				mSprites.remove(pink);
				pinks.remove(pink);
				i--;
			}else if(!pink.isDead()){
				if(pink.x < 0 && pink.direction >= 5 && pink.direction <= 7)
					pink.changeDirection((byte) (4 - pink.direction % 4));
				else if(pink.x > getWidth()-pink.width && pink.direction >= 1 && pink.direction <= 3)
					pink.changeDirection((byte) (8 - pink.direction));
				else if(pink.y < 0 && pink.direction >= 3 && pink.direction <= 5)
					pink.changeDirection((byte) ((12 - pink.direction) % 8));
				else if(pink.y > getHeight()-pink.height && (pink.direction >= 7 || pink.direction <= 1))
					pink.changeDirection((byte) (5 - (pink.direction+1) % 8));
			}
		}
		for(Gold gold : golds)
			if(gold.x < 0 || gold.x > getWidth()-gold.width)
				gold.speedX *= -1;
			else if(gold.y < 0 || gold.y > getHeight()-gold.height)
				gold.speedY *= -1;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawText(context.getString(R.string.score) + " " + score, scoreX, scoreY, paintText);
		if(nextLevelSprite.visible)
			canvas.drawText(context.getString(R.string.nextlevel_msg), 50, getHeight()*0.7f, paintText);
	}

	private void newStage() {
		level++;
		nextLevelSprite.visible = false;
		alivePinks = 5+level;
		startTime = System.currentTimeMillis();
		
		Random random = new Random();
		Resources res = getResources();
		Bitmap bitmapPink = BitmapFactory.decodeResource(res, R.drawable.pink);
		Bitmap bitmapGold = BitmapFactory.decodeResource(res, R.drawable.gold_head);
		Pink pink;
		Gold gold;
		int limitPinkX = getWidth()-bitmapPink.getWidth()/6,
			limitPinkY = getHeight()-bitmapPink.getHeight()/5,
			limitGoldX = getWidth()-bitmapGold.getWidth()/8,
			limitGoldY = getHeight()-bitmapGold.getHeight();
		for(int i = 0; i < alivePinks; i++){
			pinks.add(pink = new Pink(random.nextInt(limitPinkX), random.nextInt(limitPinkY), random, bitmapPink, 5, 6));
			pink.speed = level;
		}
		golds.add(gold = new Gold(random.nextInt(limitGoldX), random.nextInt(limitGoldY), random, bitmapGold, 1, 8));
		gold.speedX *= 1+level/3;
		gold.speedY *= 1+level/3;
		mSprites.addAll(1, pinks);
		mSprites.add(mSprites.size()-1, gold);
		
	}
	
}
