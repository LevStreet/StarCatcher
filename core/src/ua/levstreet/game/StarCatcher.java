package ua.levstreet.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class StarCatcher extends Game {
	private AssetManager assetManager;

	@Override
	public void create() {
		assetManager = new AssetManager();
		assetManager.load("smiling-gold-star.png", Texture.class);
		while (!assetManager.update()) {
			Gdx.app.log("assetManager.getProgress()",
					String.valueOf(assetManager.getProgress()));
		}
		setScreen(new GameScreen(this));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
