package ua.levstreet.game.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackGroundActor extends Actor {
	private Texture texture;
	
	
	public BackGroundActor(AssetManager assetManager,int width,float height){
		texture = assetManager.get("background1.png");
		setSize(width,height);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture,0,0,(float)getWidth(),(float)getHeight());
	}

}
