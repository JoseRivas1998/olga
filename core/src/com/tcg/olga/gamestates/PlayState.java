package com.tcg.olga.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tcg.olga.*;
import com.tcg.olga.entities.*;
import com.tcg.olga.managers.*;

public class PlayState extends GameState {

	private MyCamera cam;
	private Cursor c;
	private Olga o;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(Game.SIZE, true);
		c = new Cursor();
		o = new Olga();
	}

	@Override
	public void handleInput() {
		c.update();
	}

	@Override
	public void update(float dt) {
		o.update(c);
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		o.draw(sr, sb, dt);
		sb.end();
		
		sr.begin(ShapeType.Line);
		sr.setColor(Constants.randomColor());
		sr.setProjectionMatrix(cam.combined);
		c.draw(sr, sb, dt);
		sr.end();

	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(size, true);
	}

	@Override
	public void dispose() {
		o.dispose();
	}

}
