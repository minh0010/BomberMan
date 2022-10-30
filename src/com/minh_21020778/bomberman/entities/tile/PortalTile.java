package com.minh_21020778.bomberman.entities.tile;

import com.minh_21020778.bomberman.Board;
import com.minh_21020778.bomberman.entities.Entity;
import com.minh_21020778.bomberman.entities.mob.Player;
import com.minh_21020778.bomberman.graphics.Sprite;

// cái cổng hết game
public class PortalTile extends Tile {

	protected Board _board;
	
	public PortalTile(int x, int y, Board board, Sprite sprite) {
		super(x, y, sprite);
		_board = board;
	}
	
	@Override
	public boolean collide(Entity e) {
		if(e instanceof Player ) {
			if(!_board.detectNoEnemies())
				return false;
			
			if(e.getXTile() == getX() && e.getYTile() == getY()) {
				if(_board.detectNoEnemies())
					_board.nextLevel();
			}
			
			return true;
		}
		
		return false;
	}

}
