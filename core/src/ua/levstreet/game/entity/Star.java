package ua.levstreet.game.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Star {
	private final int SPEED = 10;
	private Rectangle rectangle;
	private Rectangle boundary = new Rectangle();
	private Vector2 direction = new Vector2();
	private Texture texture = new Texture("smiling-gold-star.png");

	public Star(float x, float y, float width, float height) {
		rectangle = new Rectangle(x, y, width, height);
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
		if (rectangle.x < boundary.x && direction.x < 0
				|| rectangle.x > boundary.width - rectangle.width
				&& direction.x > 0) {
			direction.x *= -1;
		}
		if (rectangle.y < boundary.y && direction.y < 0
				|| rectangle.y > boundary.height - rectangle.height
				&& direction.y > 0) {
			direction.y *= -1;
		}
		float deltaTime = Gdx.graphics.getDeltaTime();
		rectangle.x += direction.x * deltaTime * SPEED;
		rectangle.y += direction.y * deltaTime * SPEED;
		direction.x *= 1 - deltaTime;
		direction.y *= 1 - deltaTime;
		batch.draw(texture, rectangle.x, rectangle.y, rectangle.width,
				rectangle.height);
	}
}
