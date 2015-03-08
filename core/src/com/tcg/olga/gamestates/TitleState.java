package com.tcg.olga.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.olga.Game;
import com.tcg.olga.MyCamera;
import com.tcg.olga.managers.GameStateManager;
import com.tcg.olga.managers.MyInput;

public class TitleState extends GameState {

	private MyCamera cam;
	
	private float time, timer, time1, timer1;
	
	private boolean showTitle, showOlga;
	
	private Texture olga, olga1;
	
	private float ox, oy, o1x, o1y;
	
	private String[] menuItems;
	
	private String s1, s2, s3, s4, s5, s6, s7;
	
	private float s1x, s1y, s2x, s2y, s3x, s3y, s4x, s4y, s5x, s5y, s6x, s6y, s7x, s7y;
	
	private Texture cpoint;
	
	private float cpx, cpy;
	
	private float tx, ty;
	
	private int currentItem;
	
	private Texture right, left;
	
	private String hs;
	private float hsx, hsy;
	
	private Texture ic;
	
	public TitleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(Game.SIZE, true);
		showTitle = true;
		showOlga = false;
		timer = 15;
		timer1 = 15;
		time = 0;
		time1 = 0;
		olga = new Texture("olga original size.png");
		olga1 = new Texture("OLGA.png");
		cpoint = new Texture("cpoint.png");
		right = new Texture("right.png");
		left = new Texture("left.png");
		ic = new Texture("indiecade.png");
		currentItem = 0;
		menuItems = new String[] {
			"Play", "Quit", "Clear High Score"	
		};
		setValues();
		Game.res.getMusic("title").play();
	}

	@Override
	public void handleInput() {
		if(MyInput.anyKeyPressed()) {
			showTitle = true;
			showOlga = false;
			time = 0;
			time1 = 0;
			cam.position.set(Game.CENTER, 0);
			cam.update();
			Game.res.getSound("cursor").play();
		}
		if(MyInput.keyPressed(MyInput.UP)) {
			currentItem--;
		}
		if(MyInput.keyPressed(MyInput.DOWN)) {
			currentItem++;
		}
		if(currentItem < 0) {
			currentItem = 2;
		}
		if(currentItem > 2) {
			currentItem = 0;
		}
		if(MyInput.keyPressed(MyInput.START) || MyInput.keyPressed(MyInput.SHOOT) || (MyInput.keyPressed(MyInput.JUMP) && !MyInput.keyDown(MyInput.UP))) {
			select();
		}
	

	}
	
	private void select() {
		Game.res.getSound("select").play();
		if(currentItem == 0) {
			gsm.setState(gsm.PLAY);
		}
		if(currentItem == 1) {
			Gdx.app.exit();
		}
		if(currentItem == 2) {
			Game.HIGHSCORE = 0;
		}
	}

	private void updateCam(float dt) {
		if(showTitle) {
			if(cam.position.x > Game.CENTER.x) {
				cam.position.x--;
			} else if(cam.position.x < Game.CENTER.x) {
				cam.position.x++;
			} else {
				time += dt;
				if(time > timer) {
					showTitle = false;
					time = 0;
					showOlga = true;
				}
			}
		}
		if(showOlga) {
			if(cam.position.x < Game.SIZE.x + Game.CENTER.x) {
				cam.position.x++;
			} else if(cam.position.x > Game.SIZE.x + Game.CENTER.x) {
				cam.position.x--;
			} else {
				time1 += dt;
				if(time1 > timer1) {
					showOlga = false;
					showTitle = true;
					time1 = 0;
				}
			}
		}
		cam.update();
	}
	
	private void setValues() {
		ox = (Game.SIZE.x + Game.CENTER.x) - (olga.getWidth() * .5f);
		oy = Game.CENTER.y - (olga.getHeight() * .5f);
		o1x = ox - (olga1.getWidth() * .95f);
		o1y = (oy + olga.getHeight()) - (olga1.getHeight() * .65f);
		
		s1 = "This is Olga";
		s2 = "Olga eats Crayons";
		s3 = "See?";
		s4 = "Olga food";
		s5 = "Olga loves you";
		s6 = "Olga has wooden feet";
		s7 = "Her teeth are chopsticks";
		
		s1x = s2x = s3x = s5x = Game.SIZE.x + 55;
		s1y = oy;
		s2y = s1y - Game.res.getHeight("main", s1) - 1;
		s3y = s4y = s2y - Game.res.getHeight("main", s2) - 1;
		cpx = s3x + Game.res.getWidth("main", s3);
		cpy = (s3y - (Game.res.getHeight("main", s3) * .5f)) - cpoint.getHeight() * .5f;
		s4x = cpx + cpoint.getWidth();
		s5y = s3y - Game.res.getHeight("main", s3) - 1;
		s6x = s7x = ox + (olga.getWidth() * .75f) + 5;
		s6y = oy - (Game.res.getHeight("main", s6)) - 1;
		s7y = s6y - (Game.res.getHeight("main", s6)) - 1;
		
		tx = Game.res.centerX("large", Game.TITLE);
		ty = (Game.SIZE.y * .75f) - (Game.res.getHeight("large", Game.TITLE) * .5f);
		
		hs = "High Score " + Game.getScore(Game.HIGHSCORE);
		hsx = Game.SIZE.x - Game.res.getWidth("main", hs) - 25;
		hsy = Game.SIZE.y - 12.5f;
	}

	@Override
	public void update(float dt) {
		setValues();
		updateCam(dt);
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		sb.draw(olga1, o1x, o1y);
		sb.draw(olga, ox, oy);
		sb.draw(cpoint, cpx, cpy);
		sb.draw(ic, 0, 0, ic.getWidth() * .052083f, ic.getHeight() * .052083f);
		Game.res.getFont("large").draw(sb, Game.TITLE, tx, ty);
		Game.res.getFont("main").draw(sb, hs, hsx, hsy);
		Game.res.getFont("main").draw(sb, s1, s1x, s1y);
		Game.res.getFont("main").draw(sb, s2, s2x, s2y);
		Game.res.getFont("main").draw(sb, s3, s3x, s3y);
		Game.res.getFont("main").draw(sb, s4, s4x, s4y);
		Game.res.getFont("main").draw(sb, s5, s5x, s5y);
		Game.res.getFont("main").draw(sb, s6, s6x, s6y);
		Game.res.getFont("main").draw(sb, s7, s7x, s7y);
		for(int i = 0; i < menuItems.length; i++) {
			float x, y, rx, ry, lx, ly;
			String s = menuItems[i];
			x = Game.res.centerX("mItems", s);
			y = (ty - (Game.res.getHeight("large", Game.TITLE) * 3)) - ((Game.res.getHeight("mItems", s) * 2) * i);
			rx = x - right.getWidth() - 10;
			lx = x + Game.res.getWidth("mItems", s) + 10;
			ly = ry = (y - (Game.res.getHeight("mItems", s) * .5f)) - right.getHeight() * .5f;
			Game.res.getFont("mItems").draw(sb, s, x, y);
			if(currentItem == i) {
				sb.draw(right, rx, ry);
				sb.draw(left, lx, ly);
			}
		}
		sb.end();
	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(size, false);
		cam.position.y = Game.CENTER.y;
	}

	@Override
	public void dispose() {
		olga.dispose();
		olga1.dispose();
	}

}
