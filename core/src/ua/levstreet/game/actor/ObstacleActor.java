package ua.levstreet.game.actor;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ObstacleActor extends Actor {
	private Texture texture;

	public ObstacleActor(AssetManager assetManager, World world, float height) {
		BodyEditorLoader bodyEditorLoader = assetManager.get(
				"levels/level0.json", BodyEditorLoader.class);
		BodyDef bodyDef = new BodyDef();
		Body body = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		bodyEditorLoader.attachFixture(body, "Name", fixtureDef, height);
		texture = assetManager.get("levels/level0.png", Texture.class);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getWidth(),
				getWidth() * texture.getHeight() / texture.getWidth());
	}
}
