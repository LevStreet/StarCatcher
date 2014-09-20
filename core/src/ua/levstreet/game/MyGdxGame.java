package ua.levstreet.game;

import ua.levstreet.game.entity.Star;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {
	private final int WIDTH = 800;
	private final int HEIGHT = 480;
	private final int NUMBER_OF_STARS = 10;
	private final int FLOW_WIDTH = 100;
	private final int STAR_WIDTH = 50;
	private final int STAR_HEIGHT = 50;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Vector3 touchPos = new Vector3();
	private Vector3 grabbedPos = new Vector3();
	private BitmapFont bitmapFont;
	private Star grabbed;
	private Star[] stars = new Star[NUMBER_OF_STARS];

	@Override
	public void create() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		touchPos.set(WIDTH / 2, HEIGHT / 2, 0);
		bitmapFont = new BitmapFont();
		for (int i = 0; i < NUMBER_OF_STARS; i++) {
			stars[i] = new Star(MathUtils.random(0, FLOW_WIDTH), HEIGHT
					+ MathUtils.random(0, 500), STAR_WIDTH, STAR_HEIGHT);
			stars[i].setBoundary(0, 0, WIDTH, HEIGHT);
			stars[i].setDirection(0, -MathUtils.random(10, 100));
		}
	}

	@Override
	public void render() {
		if (Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			for (Star star : stars) {
				if (grabbed == null
						&& star.getRectangle().contains(touchPos.x, touchPos.y)) {
					grabbed = star;
					grabbedPos.set(touchPos);
				}
			}
		} else {
			if (grabbed != null) {
				grabbed.setState(Star.State.RELEASED);
				grabbed = null;
			}
		}
		if (grabbed != null) {
			grabbed.setDirection(touchPos.x - grabbedPos.x, touchPos.y
					- grabbedPos.y);
		}
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Star star : stars) {
			if (star.getState() == Star.State.DEAD) {
				star.getRectangle().x = MathUtils.random(0, FLOW_WIDTH);
				star.getRectangle().y = HEIGHT + MathUtils.random(0, 500);
				star.setDirection(0, -MathUtils.random(10, 100));
				star.setState(Star.State.IN_FLOW);
			}
			star.draw(batch);
		}
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
