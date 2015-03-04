package com.tcg.olga.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.olga.Constants;
import com.tcg.olga.Game;

public class Olga extends Entity {

	private Texture temp;
	private TextureRegion tex;
	
	private float originX, originY, degrees, radians;
	
	private float speed = 2.5f;
	
	public Olga() {
		super();
		temp = new Texture("entities/olga.png");
		tex = new TextureRegion(temp);
		setWidth(tex.getRegionWidth());
		setHeight(tex.getRegionHeight());
		setX(Game.CENTER.x - (getWidth() * .5f));
		setY(Game.CENTER.y - (getHeight() * .5f));
	}

	public void update(Cursor c, Crayon cr) {
		
		float dx = c.getX() - getCenter().x;
		float dy = c.getY() - getCenter().y;
		
		radians = MathUtils.atan2(dy, dx);
		degrees = radians * MathUtils.radiansToDegrees;
		
		vel.x = MathUtils.cos(radians) * speed;
		vel.y = MathUtils.sin(radians) * speed;
		
		bounds.x += vel.x;
		bounds.y += vel.y;
		
		if(Math.abs(dx) < 5 && Math.abs(dy) < 5) {
			speed = 0;
		} else {
			speed = 5 * (Constants.distance(getPosition(), c.getPosition()) / Constants.distance(new Vector2(0, 0), Game.CENTER));
		}
		
		originX = (getWidth() * .5f);
		originY = (getHeight() * .5f);
		
		if(bounds.x + bounds.width > Game.SIZE.x) {
			bounds.x = Game.SIZE.x - bounds.width;
		}
		if(bounds.x < 0) {
			bounds.x = 0;
		}
		if(bounds.y + bounds.height > Game.SIZE.y) {
			bounds.y = Game.SIZE.y - bounds.height;
		}
		if(bounds.y < 0) {
			bounds.y = 0;
		}
		
		if(collidingWith(cr)) {
//			Game.SCORE++;
			cr.resetPos();
		}
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sb.draw(tex, getX(), getY(), originX, originY, getWidth(), getHeight(), 1, 1, degrees);
	}
	
	public void dispose() {
		temp.dispose();
	}

}
