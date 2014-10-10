package ua.levstreet.game.actor;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ObstacleActor extends Actor {
	public ObstacleActor(World world, float height) {
		BodyEditorLoader bodyEditorLoader = new BodyEditorLoader(
				Gdx.files.internal("leveltest.json"));
		BodyDef bodyDef = new BodyDef();
		Body body = world.createBody(bodyDef);
		FixtureDef fixtureDef = new FixtureDef();
		bodyEditorLoader.attachFixture(body, "Name", fixtureDef, height);
	}
}
