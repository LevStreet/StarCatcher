package ua.levstreet.game.actor;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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
	private Texture texture;
	private World world;
	private Body body;
	private static BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(
			Gdx.files.internal("star.json"));
	private ParticleEffect particleEffect;
	private float prevX;
	private float prevY;

	public StarActor(AssetManager assetManager, World world) {
		texture = assetManager.<Texture> get("smiling-gold-star.png");
		particleEffect = new ParticleEffect(
				assetManager.<ParticleEffect> get("effects/trace.p"));
		setSize(RADIUS * 2, RADIUS * 2);
		setOrigin(RADIUS, RADIUS);
		addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				Gdx.app.log("EventListener", event.toString());
				return false;
			}
		});
		prevX = getCenterX();
		prevY = getCenterY();
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
	}

	@Override
	public void act(float delta) {
		Vector2 position = body.getPosition();
		setCenterPosition(position.x, position.y);
		setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		float interpol = body.getLinearVelocity().len() * 50;
		particleEffect.getEmitters().get(0).getEmission().setHigh(interpol);
		float deltaX = getCenterX() - prevX;
		float deltaY = getCenterY() - prevY;
		for (int i = 0; i < interpol; i++) {
			particleEffect.setPosition(prevX + deltaX * i / interpol, prevY
					+ deltaY * i / interpol);
			particleEffect.update(delta / interpol);
		}
		Gdx.app.log("emi", particleEffect.getEmitters().get(0).getActiveCount() + "");
		prevX = getCenterX();
		prevY = getCenterY();
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		particleEffect.draw(batch);
		batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(),
				getWidth(), getHeight(), getScaleX(), getScaleY(),
				getRotation(), 0, 0, texture.getWidth(), texture.getHeight(),
				false, false);
	}

	public Body getBody() {
		return body;
	}

	@Override
	public void reset() {
		world.destroyBody(body);
		remove();
	}
}
