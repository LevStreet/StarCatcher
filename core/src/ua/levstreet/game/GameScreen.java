package ua.levstreet.game;

import ua.levstreet.game.actor.BackgroundActor;
import ua.levstreet.game.actor.DebugInfo;
import ua.levstreet.game.actor.ScoreActor;
import ua.levstreet.game.actor.StarActor;
import ua.levstreet.game.actor.TargetActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen extends InputAdapter implements Screen {
	private final int WIDTH = 10;
	private final float HEIGHT = WIDTH * 9 / 16;
	private final int FLOW_WIDTH = 1;
	private final int STAR_EVERY = 1000;
	private final int TOUCH_STOP = 2;
	private StarCatcher starCatcher;
	private Stage stage;
	private BitmapFont bitmapFont;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Body walls;
	private Pool<StarActor> starPool = new Pool<StarActor>() {
		@Override
		protected StarActor newObject() {
			return new StarActor(starCatcher.getAssetManager(), world);
		}
	};
	private MouseJointDef mouseJointDef;
	private MouseJoint mouseJoint;
	private Vector2 touchCoordinates = new Vector2();
	private long sinceLastStar = System.currentTimeMillis();
	private DebugInfo debugInfo;
	private TargetActor targetActor;
	private ScoreActor scoreActor;

	public GameScreen(StarCatcher starCatcher) {
		this.starCatcher = starCatcher;
		stage = new Stage(new FitViewport(WIDTH, HEIGHT));
		stage.addActor(new BackgroundActor(starCatcher.getAssetManager()));
		// bitmapFont = starCatcher.getAssetManager().get("font/Consolas.fnt");
		bitmapFont = new BitmapFont(Gdx.files.internal("font/Consolas.fnt"),
				starCatcher.getAssetManager()
						.get("atlas/common.atlas", TextureAtlas.class)
						.findRegion("Consolas"));
		bitmapFont.setUseIntegerPositions(false);
		bitmapFont.setScale(.0075f);
		bitmapFont.setColor(1, 0, 0, 1);
		scoreActor = new ScoreActor(bitmapFont);
		stage.addActor(scoreActor);
		debugInfo = new DebugInfo(bitmapFont);
		stage.getRoot().addActorAt(100500, debugInfo);
		world = new World(new Vector2(0, 0), true);
		debugRenderer = new Box2DDebugRenderer();
		createWalls(TOUCH_STOP, WIDTH, HEIGHT);
		mouseJointDef = new MouseJointDef();
		mouseJointDef.bodyA = walls;
		mouseJointDef.maxForce = 100500;
		mouseJointDef.collideConnected = false;
		targetActor = new TargetActor(starCatcher.getAssetManager());
		targetActor.putInWorld(world, WIDTH - 1, HEIGHT / 2);
		targetActor.addAction(Actions.parallel(Actions.forever(Actions
				.rotateBy(100, 1)), Actions.forever(Actions.sequence(
				Actions.moveBy(0, 1.5f, 5), Actions.moveBy(0, -1.5f, 5)))));
		stage.addActor(targetActor);
		// new ObstacleActor(world, HEIGHT);
		stage.addAction(Actions.sequence(Actions.moveBy(0, HEIGHT),
				Actions.moveTo(0, 0, 1, Interpolation.bounceOut)));
	}

	private void createWalls(float x, float width, float height) {
		if (walls != null) {
			world.destroyBody(walls);
		}
		BodyDef bodyDef = new BodyDef();
		walls = world.createBody(bodyDef);
		ChainShape chainShape = new ChainShape();
		float offset = .0f;
		float[] vertices = { x + offset, 0 + offset, width - offset,
				0 + offset, width - offset,
				height - offset - bitmapFont.getLineHeight(), x + offset,
				height - offset - bitmapFont.getLineHeight() };
		chainShape.createLoop(vertices);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = chainShape;
		walls.createFixture(fixtureDef);
		chainShape.dispose();
	}

	private void putInFlow(StarActor star) {
		float centerX = MathUtils.random(0.f, FLOW_WIDTH) + star.getWidth() / 2;
		float centerY = HEIGHT + star.getHeight() / 2;
		star.putInWorld(world, centerX, centerY);
		Vector2 center = star.getBody().getWorldCenter();
		star.getBody().applyLinearImpulse(0, -MathUtils.random(.1f, .5f),
				center.x, center.y, false);
	}

	private void checkTarget() {
		for (Contact contact : world.getContactList()) {
			Fixture fixtureA = contact.getFixtureA();
			Fixture fixtureB = contact.getFixtureB();
			Fixture target = targetActor.getFixture();
			if (contact.isTouching()) {
				Fixture fixture = null;
				if (fixtureA == target) {
					fixture = fixtureB;
				} else if (fixtureB == target) {
					fixture = fixtureA;
				}
				if (fixture != null) {
					StarActor star = (StarActor) fixture.getBody()
							.getUserData();
					// if (star.isKilled()) {
					scoreActor.incScore();
					star.kill();
					// }
				}
			}
		}
	}

	private void killEscaped(StarActor star) {
		Body bodyB = mouseJointDef.bodyB;
		if ((star.getRight() < 0 || star.getX() > WIDTH || star.getTop() < 0 || star
				.getY() > HEIGHT)
				&& star.getBody() != bodyB
				&& !star.isKilled()) {
			star.kill();
		}
	}

	private void gatherDead(StarActor star) {
		if (star.isDead()) {
			starPool.free(star);
		}
	}

	@Override
	public void render(float delta) {
		checkTarget();
		if (System.currentTimeMillis() - sinceLastStar > STAR_EVERY) {
			StarActor star = starPool.obtain();
			putInFlow(star);
			stage.addActor(star);
			sinceLastStar = System.currentTimeMillis();
		}
		stage.act(delta);
		for (Actor actor : stage.getActors()) {
			if (StarActor.class.isInstance(actor)) {
				StarActor star = (StarActor) actor;
				killEscaped(star);
				gatherDead(star);
			}
		}
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		// debugRenderer.render(world, stage.getCamera().combined);
		world.step(Gdx.graphics.getDeltaTime(), 8, 3); // TODO
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchCoordinates.x = screenX;
		touchCoordinates.y = screenY;
		stage.screenToStageCoordinates(touchCoordinates);
		if (touchCoordinates.x > TOUCH_STOP) {
			return false;
		}
		world.QueryAABB(new QueryCallback() {
			@Override
			public boolean reportFixture(Fixture fixture) {
				// if (fixture.testPoint(touchCoordinates)) {
				mouseJointDef.bodyB = fixture.getBody();
				mouseJointDef.target.set(touchCoordinates);
				mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
				return false;
				// }
				// return true;
			}
		}, touchCoordinates.x, touchCoordinates.y, touchCoordinates.x,
				touchCoordinates.y);
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (mouseJoint != null) {
			world.destroyJoint(mouseJoint);
			mouseJoint = null;
		}
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		touchCoordinates.x = screenX;
		touchCoordinates.y = screenY;
		stage.screenToStageCoordinates(touchCoordinates);
		if (mouseJoint != null) {
			if (mouseJointDef.bodyB.getPosition().x > TOUCH_STOP + .5f) {
				world.destroyJoint(mouseJoint);
				mouseJoint = null;
				mouseJointDef.bodyB = null;
				return false;
			} else {
				mouseJoint.setTarget(touchCoordinates);
			}
		}
		return false;
	}

	@Override
	public void dispose() {
		debugRenderer.dispose();
		world.dispose();
		bitmapFont.dispose();
		stage.dispose();
	}
}
