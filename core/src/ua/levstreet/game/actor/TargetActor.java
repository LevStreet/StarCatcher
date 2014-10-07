package ua.levstreet.game.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TargetActor extends Actor {
	private static float RADIUS = .5f;
	private Texture texture;
	private Body body;
	private Fixture fixture;

	public TargetActor(AssetManager assetManager) {
		texture = assetManager.get("blackhole.png");
		setSize(RADIUS * 2, RADIUS * 2);
		setOrigin(RADIUS, RADIUS);
	}

	public void putInWorld(World world, float centerX, float centerY) {
		setCenterPosition(centerX, centerY);
		// this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(centerX, centerY);
		body = world.createBody(bodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(.1f);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circleShape;
		fixtureDef.isSensor = true;
		fixture = body.createFixture(fixtureDef);
		circleShape.dispose();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
				false, false);
	}

	public Fixture getFixture() {
		return fixture;
	}
}
