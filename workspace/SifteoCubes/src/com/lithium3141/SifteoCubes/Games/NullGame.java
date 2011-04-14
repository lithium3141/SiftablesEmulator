package com.lithium3141.SifteoCubes.Games;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.lithium3141.SifteoCubes.Cube;

public class NullGame extends Game {
	@Override
	public int getRequiredCubes() {
		return 0;
	}
	
	@Override
	public String getTitle() {
		return "";
	}
	
	@Override
	public void load() {}
	
	@Override
	public void unload() {}
	
	@Override
	public void renderCube(Cube cube, Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.BLACK);
		Dimension d = new Dimension(128, 128);
		g2.setFont(new Font("Courier", Font.BOLD, 12));
		drawCenteredString("SELECT GAME", d.width, d.height, g);
	}
	
	public void drawCenteredString(String s, int w, int h, Graphics g) {
	    FontMetrics fm = g.getFontMetrics();
	    int x = (w - fm.stringWidth(s)) / 2;
	    int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
	    g.drawString(s, x, y);
	}

	@Override
	public void cubesJoined(Cube c1, int e1, Cube c2, int e2) {}

	@Override
	public void cubesSeparated(Cube c1, int e1, Cube c2, int e2) {}
}