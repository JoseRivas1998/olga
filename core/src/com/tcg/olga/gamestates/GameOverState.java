package com.tcg.olga.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.olga.Game;
import com.tcg.olga.MyCamera;
import com.tcg.olga.managers.GameStateManager;
import com.tcg.olga.managers.MyInput;

public class GameOverState extends GameState {

	private String gameOver, score;
	
	private float gox, goy, sx, sy;
	
	private boolean first;
	
	private MyCamera cam;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		first = true;
		cam = new MyCamera(Game.SIZE, true);
		Game.res.getMusic("gameover").play();
		setValues();
	}

	private void setValues() {
		gameOver = "Game Over";
		score = "You got " + Game.SCORE + " crayons!";
		gox = Game.res.centerX("large", gameOver);
		goy = Game.res.centerY("large", gameOver);
		sx = Game.res.centerX("large", score);
		sy = (Game.CENTER.y * .75f) - (Game.res.getHeight("large", score));
	}
	
	@Override
	public void handleInput() {
		if(MyInput.anyKeyPressed() && !first) {
			gsm.setState(gsm.TITLE);
		}
	}

	@Override
	public void update(float dt) {
		setValues();
		first = false;
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("large").draw(sb, gameOver, gox, goy);
		Game.res.getFont("large").draw(sb, score, sx, sy);
		sb.end();
	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(size, true);
	}

	@Override
	public void dispose() {}

}
