package ua.levstreet.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Star {
	public static enum State {
		IN_FLOW, RELEASED, DYING, DEAD
	}

	private final int SPEED = 10;
	private final float DEAD_DELTA = .01f;
	private State state;
	private Rectangle rectangle = new Rectangle();
	private Rectangle boundary = new Rectangle();
	private Vector2 direction = new Vector2();
	private static Texture texture = new Texture("smiling-gold-star.png");
	private float angle;
	private float rotation;

	static {
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}

	public Star() {
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setDirection(float x, float y) {
		direction.x = x;
		direction.y = y;
	}

	public void setBoundary(float x, float y, float width, float height) {
		boundary.x = x;
		boundary.y = y;
		boundary.width = width;
		boundary.height = height;
	}

	public void draw(SpriteBatch batch) {
		float deltaTime = Gdx.graphics.getDeltaTime();
		switch (state) {
		case IN_FLOW:
			if (rectangle.y < -rectangle.height) {
				state = State.DEAD;
			}
			break;
		case RELEASED:
			if (rectangle.x < boundary.x && direction.x < 0
					|| rectangle.x > boundary.width - rectangle.width
					&& direction.x > 0) {
				direction.x *= -1;
				rotation = 1 * direction.x;
			}
			if (rectangle.y < boundary.y && direction.y < 0
					|| rectangle.y > boundary.height - rectangle.height
					&& direction.y > 0) {
				direction.y *= -1;
				rotation = 1 * direction.y;
			}
			direction.x *= 1 - deltaTime;
			direction.y *= 1 - deltaTime;
			if (Math.abs(direction.x) < DEAD_DELTA
					&& Math.abs(direction.y) < DEAD_DELTA) {
				state = State.DYING;
			}
			break;
		case DYING:
			float delta = deltaTime * 50;
			rectangle.x += delta;
			rectangle.width -= delta * 2;
			rectangle.y += delta;
			rectangle.height -= delta * 2;
			if (rectangle.width < 1 || rectangle.height < 1) {
				state = State.DEAD;
			}
			break;
		case DEAD:
			return;
		default:
			Gdx.app.error(getClass().toString(), state.toString());
			return;
		}
		rectangle.x += direction.x * deltaTime * SPEED;
		rectangle.y += direction.y * deltaTime * SPEED;
		angle += rotation;
		rotation *= 1 - deltaTime;
		batch.draw(texture, rectangle.x, rectangle.y, rectangle.width / 2,
				rectangle.height / 2, rectangle.width, rectangle.height, 1, 1,
				angle, 0, 0, texture.getWidth(), texture.getHeight(), false,
				false);
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
