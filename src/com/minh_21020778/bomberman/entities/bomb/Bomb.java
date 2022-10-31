package com.minh_21020778.bomberman.entities.bomb;

import com.minh_21020778.bomberman.Board;
import com.minh_21020778.bomberman.Game;
import com.minh_21020778.bomberman.entities.AnimatedEntitiy;
import com.minh_21020778.bomberman.entities.Entity;
import com.minh_21020778.bomberman.entities.mob.Mob;
import com.minh_21020778.bomberman.entities.mob.Player;
import com.minh_21020778.bomberman.graphics.Screen;
import com.minh_21020778.bomberman.graphics.Sprite;
import com.minh_21020778.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

	// các cài đặt
	protected double _timeToExplode = 120; //2 giây
	public int _timeAfter = 20; // thời gian tồn tại sau khi nổ
	
	protected Board _board;
	protected boolean _allowedToPassThru = true;
	protected DirectionalExplosion[] _explosions = null;
	protected boolean _exploded = false;
	
	public Bomb(int x, int y,Board board) {
		_x = x;
		_y = y;
		_board = board;
		_sprite = Sprite.bomb;
	}

	// cật nhật tình trạng bomb
	@Override
	public void update() {
		if(_timeToExplode > 0) 
			_timeToExplode--;
		else {
			if(!_exploded) 
				explosion();
			else
				updateExplosions();
			
			if(_timeAfter > 0) 
				_timeAfter--;
			else
				remove();
		}
			
		animate();
	}

	// render hình ảnh bomb
	@Override
	public void render(Screen screen) {
		if(_exploded) {
			_sprite =  Sprite.bomb_exploded2;
			renderExplosions(screen);
		} else
			_sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);
		
		int xt = (int)_x << 4;
		int yt = (int)_y << 4;
		
		screen.renderEntity(xt, yt , this);
	}

	// render bomb nổ
	public void renderExplosions(Screen screen) {
		for (DirectionalExplosion explosion : _explosions) {
			explosion.render(screen);
		}
	}

	// update vụ nổ
	public void updateExplosions() {
		for (DirectionalExplosion explosion : _explosions) {
			explosion.update();
		}
	}

	public void explode() {
		_timeToExplode = 0;
	}
	
	protected void explosion() {
		_allowedToPassThru = true;
		_exploded = true;
		
		Mob a = _board.getMobAt(_x, _y);
		if(a != null)  {
			a.kill();
		}
		
		_explosions = new DirectionalExplosion[4];
		
		for (int i = 0; i < _explosions.length; i++) {
			_explosions[i] = new DirectionalExplosion((int)_x, (int)_y, i, Game.getBombRadius(), _board);
		}
	}
	
	public Explosion explosionAt(int x, int y) {
		if(!_exploded) return null;
		
		for (int i = 0; i < _explosions.length; i++) {
			if(_explosions[i] == null) return null;
			Explosion e = _explosions[i].explosionAt(x, y);
			if(e != null) return e;
		}
		
		return null;
	}
	
	public boolean isExploded() {
		return _exploded;
	}

	// xác định va chạm thực thể với vụ nổ
	@Override
	public boolean collide(Entity e) {
		
		if(e instanceof Player) {
			double diffX = e.getX() - Coordinates.tileToPixel(getX());
			double diffY = e.getY() - Coordinates.tileToPixel(getY());
			
			if(!(diffX >= -10 && diffX < 16 && diffY >= 1 && diffY <= 28)) { // differences to see if the player has moved out of the bomb, tested values
				_allowedToPassThru = false;
			}
			
			return _allowedToPassThru;
		}
		
		if(e instanceof DirectionalExplosion) {
			explode();
			return true;
		}
		
		return false;
	}
}
