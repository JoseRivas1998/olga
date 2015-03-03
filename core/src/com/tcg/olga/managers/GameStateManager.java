package com.tcg.olga.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tcg.olga.Constants;
import com.tcg.olga.Game;
import com.tcg.olga.MyCamera;
import com.tcg.olga.gamestates.*;

public class GameStateManager {

	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private GameState gamestate;

	public final int CONTROLLER = 0;
	public final int SPLASH = 1;
	public final int TITLE = 2;
	public final int TUTORIAL = 3;
	public final int PLAY = 4;
	public final int GAMEOVER = 5;
	
	private boolean screenShot;
	
	private MyCamera paper;
	
	public GameStateManager() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		paper = new MyCamera(Game.SIZE, true);
		setState(PLAY);
	}
	
	public void setState(int state) {
		Game.res.stopMidi();
		Game.res.stopMusic();
		if(gamestate != null) gamestate.dispose();
		if(state == PLAY) {
			gamestate = new PlayState(this);
		}
	}
	
	public void handleInput() {
		gamestate.handleInput();
		screenShot = MyInput.keyPressed(MyInput.SCREENSHOT);
	}
	
	public void update(float dt) {
		gamestate.update(dt);
	}
	
	public void draw(float dt) {
		
		sr.begin(ShapeType.Line);
		sr.setColor(Constants.rgb(0, 255, 255, 255));
		sr.setProjectionMatrix(paper.combined);
		for(int y = 25; y < Game.SIZE.y; y+= 25) {
			sr.line(0, y, Game.SIZE.x, y);
		}
		sr.end();
		
		gamestate.draw(sb, sr, dt);
		if(screenShot) {
			screenShot = false;
			Screenshot.saveScreenshot();
		}
	}
	
	public void resize(Vector2 size) {
		paper.resize(size, true);
		gamestate.resize(size);
	}
	
	public void dispose() {
		gamestate.dispose();
		sb.dispose();
		sr.dispose();
	}
	
}
