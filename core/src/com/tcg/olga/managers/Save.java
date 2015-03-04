package com.tcg.olga.managers;

import java.io.Serializable;

public class Save implements Serializable {

	private static final long serialVersionUID = 3157080294172718144L;
	
	private int score, highscore;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

}
