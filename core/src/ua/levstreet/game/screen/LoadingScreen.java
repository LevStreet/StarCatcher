package ua.levstreet.game.screen;

import ua.levstreet.game.StarCatcher;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LoadingScreen extends ScreenAdapter {
	private StarCatcher starCatcher;
	private ShapeRenderer shapeRenderer;
	private int width;

	public LoadingScreen(StarCatcher starCatcher) {
		this.starCatcher = starCatcher;
	}

	@Override
	public void render(float delta) {
		AssetManager assetManager = starCatcher.getAssetManager();
		if (assetManager.update()) {
			starCatcher.setScreen(new GameScreen(starCatcher));
			return;
		}
		float progress = assetManager.getProgress() * width;
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(0, 100, progress, 200);
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
	}

	@Override
	public void show() {
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void hide() {
		shapeRenderer.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
