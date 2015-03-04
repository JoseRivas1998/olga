package com.tcg.olga.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tcg.olga.managers.MyInput;

public class Cursor extends Entity {

	public Cursor() {
		super();
		setWidth(10);
		setHeight(10);
	}
	
	public void update() {
		setPosition(MyInput.getGameCursorPos());
	}

	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		// TODO Auto-generated method stub
		sr.rect(getX(), getY(), 10, 10);
	}

}
