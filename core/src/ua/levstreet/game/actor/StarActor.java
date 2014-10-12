package ua.levstreet.game.actor;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.utils.Pool.Poolable;

public class StarActor extends Actor implements Poolable {
	private static float RADIUS = .25f;
	private AtlasRegion atlasRegion;
	private World world;
	private Body body;
	private static BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(
			Gdx.files.internal("star.json"));
	private ParticleEffect particleEffect;
	private float prevX;
	private float prevY;
	private boolean killed;

	public StarActor(AssetManager assetManager, World world) {
		atlasRegion = assetManager
				.get("atlas/common.atlas", TextureAtlas.class).findRegion(
						"smiling-gold-star");
		particleEffect = new ParticleEffect(assetManager.get("effects/trace.p",
				ParticleEffect.class));
		setSize(RADIUS * 2, RADIUS * 2);
		setOrigin(RADIUS, RADIUS);
		addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				Gdx.app.log("EventListener", event.toString());
				return false;
			}
		});
	}

	public void putInWorld(World world, float centerX, float centerY) {
		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(centerX, centerY);
		body = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.restitution = .4f;
		fixtureDef.density = 1f;
		fixtureDef.friction = 1;
		bodyEditorLoader.attachFixture(body, "Name", fixtureDef, RADIUS * 2);
		body.setUserData(this);
		prevX = centerX;
		prevY = centerY;
	}

	@Override
	public void act(float delta) {
		if (!killed) {
			Vector2 position = body.getPosition();
			setCenterPosition(position.x, position.y);
			setRotation(body.getAngle() * MathUtils.radiansToDegrees);
			float interpol = body.getLinearVelocity().len() * 1;
			ParticleEmitter particleEmitter = particleEffect.getEmitters()
					.first();
			particleEmitter.getEmission().setHigh(interpol * 100);
			float deltaX = getCenterX() - prevX;
			float deltaY = getCenterY() - prevY;
			for (int i = 0; i < interpol; i++) {
				float alpha = i / interpol;
				particleEffect.setPosition(prevX + deltaX * alpha, prevY
						+ deltaY * alpha);
				particleEffect.update(Math.max(delta / interpol, .0011f));
			}
			prevX = getCenterX();
			prevY = getCenterY();
		} else {
			particleEffect.update(delta);
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		particleEffect.draw(batch);
		if (!killed) {
			batch.draw(atlasRegion, getX(), getY(), getOriginX(), getOriginY(),
					getWidth(), getHeight(), getScaleX(), getScaleY(),
					getRotation());
		}
	}

	public Body getBody() {
		return body;
	}

	public void kill() {
		killed = true;
		world.destroyBody(body);
		particleEffect.allowCompletion();
	}

	public boolean isKilled() {
		return killed;
	}

	public boolean isDead() {
		return killed && particleEffect.isComplete();
	}

	@Override
	public void reset() {
		remove();
		killed = false;
		particleEffect.reset();
	}
}
