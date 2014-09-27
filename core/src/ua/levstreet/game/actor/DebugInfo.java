package ua.levstreet.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DebugInfo extends Actor {
	private BitmapFont bitmapFont;
	private String info;

	public DebugInfo(BitmapFont bitmapFont) {
		this.bitmapFont = bitmapFont;
	}

	@Override
	public void act(float delta) {
		SpriteBatch spriteBatch = (SpriteBatch) getStage().getBatch();
		info = String.format("FPS: %2d\nRender calls: %d\nMax sprites: %d",
				Gdx.graphics.getFramesPerSecond(), spriteBatch.renderCalls,
				spriteBatch.maxSpritesInBatch);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		bitmapFont.drawMultiLine(batch, info, 0, getStage().getHeight());
	}
}
