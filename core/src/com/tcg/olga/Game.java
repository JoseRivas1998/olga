package com.tcg.olga;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.tcg.olga.managers.*;

public class Game extends ApplicationAdapter {
	
	public static final String TITLE = "OLGA";
	
	public static Vector2 SIZE, CENTER;
	
	public GameStateManager gsm;
	
	public static int fps;
	private int frames;
	private float ftime;
	
	public static Content res;
	
	public static boolean FIRST;
	
	public static int SCORE, HIGHSCORE;
	
	private Save s;
	
	@Override
	public void create () {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		SIZE = new Vector2();
		CENTER = new Vector2();
		SIZE.set(width, height);
		CENTER.set(width * .5f, height * .5f);
		
		try {
			FileInputStream file = new FileInputStream("oglasav.dat");
			ObjectInputStream filein = new ObjectInputStream(file);
			s = (Save) filein.readObject();
			filein.close();
			file.close();
		} catch(Exception e) {
			e.printStackTrace();
			boolean f = Gdx.graphics.isFullscreen();
			if(f) {
				Gdx.graphics.setDisplayMode(800, 600, false);
			}
			JOptionPane.showMessageDialog(null, "Could not load \"oglasav.dat\", a new one will be created on exit", Game.TITLE, JOptionPane.INFORMATION_MESSAGE);
			if(f) {
				int width1 = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
				int height1 = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
				Gdx.graphics.setDisplayMode(width1, height1, true);
			}
			s = new Save();
			s.setHighscore(0);
			s.setScore(0);
		}
		
		Game.SCORE = s.getScore();
		Game.HIGHSCORE = s.getHighscore();
		
		ftime = 0;
		frames = 0;
		fps = 0;
		
		res = new Content();
		
		res.loadSound("sound", "cursor.wav", "cursor");
		res.loadSound("sound", "crayon.wav", "crayon");
		res.loadSound("sound", "select.wav", "select");
		
		res.loadMusic("music", "title.ogg", "title", true);
		res.loadMusic("music", "play.ogg", "play", true);
		res.loadMusic("music", "gameover.ogg", "gameover", true);

		res.loadBitmapFont("font", "font.ttf", "main", 36, Color.BLACK);
		res.loadBitmapFont("font", "font.ttf", "large", 56, Color.BLACK);
		res.loadBitmapFont("font", "font.ttf", "mItems", 46, Color.BLACK);
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		Controllers.addListener(new MyControllerProcessor());
		Gdx.input.setCursorCatched(Gdx.graphics.isFullscreen());
		gsm = new GameStateManager();
	}
 
	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime();
		
		ftime += dt;
		frames++;
		if(ftime >= 1) {
			fps = frames;
			frames = 0;
			ftime = 0;
		}
		Gdx.graphics.setTitle(Game.TITLE + " | " + Game.fps + "fps");
		
		
		gsm.handleInput();
		gsm.update(dt);
		gsm.draw(dt);
		
		if(MyInput.keyPressed(MyInput.FULLSCREEN)) {
			if(Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setDisplayMode(800, 600, false);
			} else {
				int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
				int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
				Gdx.graphics.setDisplayMode(width, height, true);
			}
		}
		
		MyInput.update();
	}
 
	@Override
	public void resize(int width, int height) {
		SIZE.set(width, height);
		CENTER.set(width * .5f, height * .5f);
		gsm.resize(Game.SIZE);
		Gdx.input.setCursorCatched(Gdx.graphics.isFullscreen());
	}

	public static String getScore(int score) {
		if(score < 10) {
			return "00" + score;
		} else if(score < 100) {
			return "0" + score;
		} else {
			return "" + score;
		}
	}
	
	@Override
	public void dispose() {
		gsm.dispose();
		res.removeAll();
		s.setHighscore(Game.HIGHSCORE);
		s.setScore(Game.SCORE);
		try {
		 	FileOutputStream fileOut = new FileOutputStream("oglasav.dat");
		 	ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 	out.writeObject(s);
		 	out.close();
		 	fileOut.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
