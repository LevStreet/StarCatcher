package ua.levstreet.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ScoreActor extends Actor {
	private BitmapFont bitmapFont;
	private int score;

	public ScoreActor(BitmapFont bitmapFont) {
		this.bitmapFont = bitmapFont;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		String info = String.format("Score: %3d", score);
		TextBounds bounds = bitmapFont.getBounds(info);
		bitmapFont.drawMultiLine(batch, info, getStage().getWidth()
				- bounds.width, getStage().getHeight());
	}

	public void incScore() {
		score++;
	}
}
