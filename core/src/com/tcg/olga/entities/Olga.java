package com.tcg.olga.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tcg.olga.Game;
import com.tcg.olga.managers.MyInput;

public class Olga extends Entity {

	private Texture temp;
	private TextureRegion tex;
	
	private float originX, originY, degrees, radians;
	
	private final float max_speed = 5f;
	
	public Olga() {
		super();
		temp = new Texture("entities/olga.png");
		tex = new TextureRegion(temp);
		setWidth(tex.getRegionWidth());
		setHeight(tex.getRegionHeight());
		setX(Game.CENTER.x - (getWidth() * .5f));
		setY(Game.CENTER.y - (getHeight() * .5f));
	}
	
	public void handleInput() {
		if(MyInput.keyDown(MyInput.LEFT)) {
			if(Math.abs(vel.x) < max_speed) vel.x -= .5f;
		}
		if(MyInput.keyDown(MyInput.RIGHT)) {
			if(Math.abs(vel.x) < max_speed) vel.x += .5f;
		}
		if(MyInput.keyDown(MyInput.DOWN)) {
			if(Math.abs(vel.y) < max_speed) vel.y -= .5f; 
		}
		if(MyInput.keyDown(MyInput.UP)) {
			if(Math.abs(vel.y) < max_speed) vel.y += .5f;
		}
		vel.x *= .95f;
		vel.y *= .95f;
	}

	public void update(Cursor c, Crayon cr) {
		
		float dx = (getX() + vel.x) - getX();
		float dy = (getY() + vel.y) - getY();
		
		radians = MathUtils.atan2(dy, dx);
		degrees = radians * MathUtils.radiansToDegrees;
		
		bounds.x += vel.x;
		bounds.y += vel.y;
		
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
			Game.res.getSound("crayon").play();
			Game.SCORE++;
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
