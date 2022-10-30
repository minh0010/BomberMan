package com.minh_21020778.bomberman.entities.tile;


import com.minh_21020778.bomberman.entities.Entity;
import com.minh_21020778.bomberman.graphics.Sprite;

public class GrassTile extends Tile {

	public GrassTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		return true;
	}
}
