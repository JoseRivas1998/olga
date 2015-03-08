package com.tcg.olga.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tcg.olga.Game;

public class HUD {

	private String s, hs, t;
	
	private float sx, sy, hsx, hsy, tx, ty;

	private void setValues(float time) {
		s = "Crayons " +  Game.getScore(Game.SCORE);
		hs = "High Score " + Game.getScore(Game.HIGHSCORE);float seconds = time;
		float minutes = seconds / 60;
		float remainingSeconds = seconds % 60;
		if(remainingSeconds >= 10) {
			t = (int) minutes + ":" + (int) remainingSeconds;
		} else {
			if(seconds < 0) {
				t = "-" + (int) minutes + ":0" + (int) Math.abs(remainingSeconds);
			} else {
				t = (int) minutes + ":0" + (int) remainingSeconds;
			}
		}
		sx = Game.SIZE.x - Game.res.getWidth("main", s) - 25;
		sy = Game.SIZE.y - 12.5f;
		hsx = Game.SIZE.x - Game.res.getWidth("main", hs) - 25;
		hsy = sy - Game.res.getHeight("main", s) - 12.5f;
		tx = Game.SIZE.x - Game.res.getWidth("main", t) - 25;
		ty = hsy - Game.res.getHeight("main", hs) - 12.5f;
	}
	
	public void render(SpriteBatch sb, float time) {
		setValues(time);
		Game.res.getFont("main").draw(sb, s, sx, sy);
		Game.res.getFont("main").draw(sb, hs, hsx, hsy);
		Game.res.getFont("main").draw(sb, t, tx, ty);
	}
	
}
