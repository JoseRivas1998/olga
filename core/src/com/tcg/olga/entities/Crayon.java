package com.tcg.olga.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.tcg.olga.Game;

public class Crayon extends Entity {

	private Texture tex;
	
	public Crayon() {
		super();
		tex = new Texture("entities/crayon.png");
		setWidth(tex.getWidth());
		setHeight(tex.getHeight());
		resetPos();
	}

	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sb.draw(tex, getX(), getY());
	}
	
	public void resetPos() {
		setX(MathUtils.random(Game.SIZE.x - getWidth()));
		setY(MathUtils.random(Game.SIZE.y - getHeight()));
	}
	
	public void dispose() {
		tex.dispose();
	}

}
