package com.tcg.olga.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.olga.*;
import com.tcg.olga.entities.*;
import com.tcg.olga.managers.*;

public class PlayState extends GameState {

	private MyCamera cam;
	private Cursor c;
	private Olga o;
	private Crayon cr;
	private float time;
	private HUD hud;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(Game.SIZE, true);
		c = new Cursor();
		o = new Olga();
		cr = new Crayon();
		Game.SCORE = 0;
		time = 90;
		hud = new HUD();
		Game.res.getMusic("play").play();
	}

	@Override
	public void handleInput() {
		c.update();
		o.handleInput();
	}

	@Override
	public void update(float dt) {
		o.update(c, cr);
		time -= dt;
		if(Game.SCORE > Game.HIGHSCORE) {
			Game.HIGHSCORE = Game.SCORE;
		}
		if(time <= 0) {
			gsm.setState(gsm.GAMEOVER);
		}
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		o.draw(sr, sb, dt);
		cr.draw(sr, sb, dt);
		hud.render(sb, time);
		sb.end();

	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(size, true);
	}

	@Override
	public void dispose() {
		o.dispose();
		cr.dispose();
	}

}
