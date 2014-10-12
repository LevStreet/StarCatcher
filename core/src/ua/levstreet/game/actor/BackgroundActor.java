package ua.levstreet.game.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BackgroundActor extends Actor {
	private AtlasRegion atlasRegion;

	public BackgroundActor(AssetManager assetManager) {
		atlasRegion = assetManager
				.get("atlas/common.atlas", TextureAtlas.class).findRegion(
						"background1");
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(atlasRegion, 0, 0, getStage().getWidth(), getStage()
				.getHeight());
	}
}
