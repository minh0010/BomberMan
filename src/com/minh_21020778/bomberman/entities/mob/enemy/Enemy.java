package com.minh_21020778.bomberman.entities.mob.enemy;

import java.awt.Color;

import com.minh_21020778.bomberman.Board;
import com.minh_21020778.bomberman.Game;
import com.minh_21020778.bomberman.entities.Entity;
import com.minh_21020778.bomberman.entities.Message;
import com.minh_21020778.bomberman.entities.bomb.DirectionalExplosion;
import com.minh_21020778.bomberman.entities.mob.Mob;
import com.minh_21020778.bomberman.entities.mob.Player;
import com.minh_21020778.bomberman.entities.mob.enemy.ai.AI;
import com.minh_21020778.bomberman.graphics.Screen;
import com.minh_21020778.bomberman.graphics.Sprite;
import com.minh_21020778.bomberman.level.Coordinates;

public abstract class Enemy extends Mob {

	protected int _points;
	
	protected double _speed; //Speed should change on level transition
	protected AI _ai;
	
	//necessary to correct move
	protected final double MAX_STEPS;
	protected final double rest;
	protected double _steps;
	
	protected int _finalAnimation = 30;
	protected Sprite _deadSprite;
	
	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		
		_points = points;
		_speed = speed;
		
		MAX_STEPS = Game.TILES_SIZE / _speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		_steps = MAX_STEPS;
		
		_timeAfter = 20;
		_deadSprite = dead;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Render & Update
	|--------------------------------------------------------------------------
	 */
	@Override
	public void update() {
		animate();
		
		if(_alive == false) {
			afterKill();
			return;
		}
		
		if(_alive)
			calculateMove();
	}
	
	@Override
	public void render(Screen screen) {
		
		if(_alive)
			chooseSprite();
		else {
			if(_timeAfter > 0) {
				_sprite = _deadSprite;
				_animate = 0;
			} else {
				_sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, _animate, 60);
			}
				
		}
			
		screen.renderEntity((int)_x, (int)_y - _sprite.SIZE, this);
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Move
	|--------------------------------------------------------------------------
	 */
	@Override
	public void calculateMove() {
		int xa = 0, ya = 0;
		if(_steps <= 0){
			_direction = _ai.calculateDirection();
			_steps = MAX_STEPS;
		}
			
		if(_direction == 0) ya--; 
		if(_direction == 2) ya++;
		if(_direction == 3) xa--;
		if(_direction == 1) xa++;
		
		if(canMove(xa, ya)) {
			_steps -= 1 + rest;
			move(xa * _speed, ya * _speed);
			_moving = true;
		} else {
			_steps = 0;
			_moving = false;
		}
	}
	
	@Override
	public void move(double xa, double ya) {
		if(!_alive) return;

			_y += ya;
			_x += xa;
	}
	
	@Override
	public boolean canMove(double x, double y) {
		double xr = _x, yr = _y - 16;
		// giảm tọa độ y để tính toán được chính xác hơn

		// khi trừ 15 thay cho 16 - kích thước một sprite
		// chúng ta sẽ xác định được ô tiếp theo
		// tránh được sự chênh lệch giá trị khi
		// xác định tọa độ trong map
		if(_direction == 0) { yr += _sprite.getSize() -1 ; xr += _sprite.getSize()/2; } 
		if(_direction == 1) {yr += _sprite.getSize()/2; xr += 1;}
		if(_direction == 2) { xr += _sprite.getSize()/2; yr += 1;}
		if(_direction == 3) { xr += _sprite.getSize() -1; yr += _sprite.getSize()/2;}
		
		int xx = Coordinates.pixelToTile(xr) +(int)x;
		int yy = Coordinates.pixelToTile(yr) +(int)y;

		// vật thể ở vị trí muốn tới
		Entity a = _board.getEntity(xx, yy, this);

		return a.collide(this);
	}
	
	/*
	|--------------------------------------------------------------------------
	| Mob Colide & Kill
	|--------------------------------------------------------------------------
	 */
	@Override
	public boolean collide(Entity e) {
		// gặp phải hướng nổ của flame
		if(e instanceof DirectionalExplosion) {
			kill();
			return false;
		}

		// gặp người chơi
		if(e instanceof Player) {
			((Player) e).kill();
			return false;
		}
		return true;
	}
	
	@Override
	public void kill() {
		if(!_alive) return;
		_alive = false;
		
		_board.addPoints(_points);

		Message msg = new Message("+" + _points, getXMessage(), getYMessage(), 2, Color.white, 14);
		_board.addMessage(msg);
	}
	
	
	@Override
	protected void afterKill() {
		if(_timeAfter > 0) --_timeAfter;
		else {
			
			if(_finalAnimation > 0) --_finalAnimation;
			else
				remove();
		}
	}
	
	protected abstract void chooseSprite();
}
