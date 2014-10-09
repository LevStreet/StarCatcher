package ua.levstreet.game.actor;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ObstacleActor extends Actor {
	public ObstacleActor(World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(3, 3);
		Body body = world.createBody(bodyDef);
		{
			FixtureDef fixtureDef = new FixtureDef();
			PolygonShape polygonShape = new PolygonShape();
			float size = .5f;
			float vertices[] = { -size, -size, 0, -.4f, size, -size, size,
					size, -size, size };
			polygonShape.set(vertices);
			fixtureDef.shape = polygonShape;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
		{
			FixtureDef fixtureDef = new FixtureDef();
			CircleShape circleShape = new CircleShape();
			circleShape.setRadius(.1f);
			fixtureDef.shape = circleShape;
			body.createFixture(fixtureDef);
			circleShape.dispose();
		}
	}
}
