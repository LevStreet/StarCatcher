package ua.levstreet.game.screen;

import ua.levstreet.game.StarCatcher;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LoadingScreen extends ScreenAdapter {
	private StarCatcher starCatcher;
	private ShapeRenderer shapeRenderer;

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
		float progress = assetManager.getProgress();
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0.2f, 1);
		shapeRenderer.rect(0, 0, progress, 1);
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, 1, 1);
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
