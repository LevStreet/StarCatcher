package ua.levstreet.game;

import ua.levstreet.game.screen.LoadingScreen;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class StarCatcher extends Game {
	private AssetManager assetManager;

	@Override
	public void create() {
		assetManager = new AssetManager();
		// BitmapFontParameter bitmapFontParameter = new BitmapFontParameter();
		// bitmapFontParameter.genMipMaps = true;
		// bitmapFontParameter.magFilter = TextureFilter.Linear;
		// bitmapFontParameter.minFilter = TextureFilter.MipMapLinearLinear;
		// assetManager.load("font/Consolas.fnt", BitmapFont.class,
		// bitmapFontParameter);

		ParticleEffectParameter particleEffectParameter = new ParticleEffectParameter();
		particleEffectParameter.atlasFile = "atlas/common.atlas";
		assetManager.load("effects/trace.p", ParticleEffect.class,
				particleEffectParameter);

		assetManager.setLoader(BodyEditorLoader.class,
				new BodyEditorLoaderLoader());
		assetManager.load("star.json", BodyEditorLoader.class);
		assetManager.load("leveltest.json", BodyEditorLoader.class);

		setScreen(new LoadingScreen(this));
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
