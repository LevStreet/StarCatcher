package ua.levstreet.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class StarCatcher extends Game {
	private AssetManager assetManager;

	@Override
	public void create() {
		assetManager = new AssetManager();
		TextureParameter textureParameter = new TextureParameter();
		textureParameter.genMipMaps = true;
		textureParameter.magFilter = TextureFilter.Linear;
		textureParameter.minFilter = TextureFilter.MipMapLinearLinear;
		assetManager.load("smiling-gold-star.png", Texture.class,
				textureParameter);
		assetManager.load("blackhole4.png", Texture.class, textureParameter);
		
		assetManager.load("background1.jpg", Texture.class, textureParameter);
		
		while (!assetManager.update()) {
//			Gdx.app.log("assetManager.getProgress()",					String.valueOf(assetManager.getProgress()));
		}
		setScreen(new GameScreen(this));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
