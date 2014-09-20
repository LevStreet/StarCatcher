package ua.levstreet.game;

import ua.levstreet.game.entity.Star;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private final int WIDTH = 800;
	private final int HEIGHT = 480;
	private Vector3 touchPos = new Vector3();
	private BitmapFont bitmapFont;
	private Star star;

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		touchPos.set(WIDTH / 2, HEIGHT / 2, 0);
		bitmapFont = new BitmapFont();
		star = new Star(WIDTH / 2, HEIGHT / 2, 50, 50);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			star.setPosition(touchPos.x - star.getWidth() / 2, touchPos.y
					- star.getHeight() / 2);
		}
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		star.draw(batch);
		String fps = "FPS: " + Gdx.graphics.getFramesPerSecond();
		bitmapFont.draw(batch, fps, 0, HEIGHT - bitmapFont.getXHeight());
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("resize", width + " " + height);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}
