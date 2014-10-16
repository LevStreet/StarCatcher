package ua.levstreet.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BarrierActor extends Actor {
	private final int    FRAME_COUNT = 6;
	private final int FRAME_WIDTH=24;
	
	private Animation barrierAnimation;
	private Texture sheet;
	TextureRegion[] frames;
	TextureRegion currentFrame;
	private float stateTime;
	
	public BarrierActor(AssetManager assetManager,float x ,float y,float width,float height){
	
		setSize(width,height);
		setPosition(x,y);
		
		sheet = assetManager.get("barrier.png");
		frames = new TextureRegion[FRAME_COUNT];
		
		for (int i = 0; i < FRAME_COUNT; i++) {
			frames[i] = new TextureRegion(sheet,i*FRAME_WIDTH,0,FRAME_WIDTH,sheet.getHeight());
		}
		
		barrierAnimation = new Animation(.1f,frames);
		stateTime = 0f;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		  stateTime += Gdx.graphics.getDeltaTime();          
		  currentFrame = barrierAnimation.getKeyFrame(stateTime, true); 
		  batch.draw(currentFrame, getX(), getY(), getWidth(),getHeight());
	}
}
