package ua.levstreet.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Star {
	private Rectangle rectangle;
	private Texture texture = new Texture("smiling-gold-star.png");

	public Star(float x, float y, float width, float height) {
		rectangle = new Rectangle(x, y, width, height);
	}

	public float getWidth() {
		return rectangle.width;
	}

	public float getHeight() {
		return rectangle.height;
	}

	public void setPosition(float x, float y) {
		rectangle.x = x;
		rectangle.y = y;
	}

	public void draw(SpriteBatch batch) {
		batch.draw(texture, rectangle.x, rectangle.y, rectangle.width,
				rectangle.height);
	}
}
