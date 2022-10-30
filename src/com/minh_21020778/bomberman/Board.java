package com.minh_21020778.bomberman;

import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.minh_21020778.bomberman.entities.Entity;
import com.minh_21020778.bomberman.entities.Message;
import com.minh_21020778.bomberman.entities.bomb.Bomb;
import com.minh_21020778.bomberman.entities.bomb.Explosion;
import com.minh_21020778.bomberman.entities.mob.Mob;
import com.minh_21020778.bomberman.entities.mob.Player;
import com.minh_21020778.bomberman.entities.tile.powerup.Powerup;
import com.minh_21020778.bomberman.exceptions.LoadLevelException;
import com.minh_21020778.bomberman.graphics.IRender;
import com.minh_21020778.bomberman.graphics.Screen;
import com.minh_21020778.bomberman.input.Keyboard;
import com.minh_21020778.bomberman.level.FileLevel;
import com.minh_21020778.bomberman.level.Level;

public class Board implements IRender {

	public int _width, _height;
	protected Level _level;
	protected Game _game;
	protected Keyboard _input;
	protected Screen _screen;
	
	public Entity[] _entities;
	public List<Mob> _mobs = new ArrayList<Mob>();
	protected List<Bomb> _bombs = new ArrayList<Bomb>();
	private final List<Message> _messages = new ArrayList<Message>();
	
	private int _screenToShow = -1; //1:endgame, 2:changelevel, 3:paused
	
	private int _time = Game.TIME;
	private int _points = Game.POINTS;
	private int _lives = Game.LIVES;
	
	public Board(Game game, Keyboard input, Screen screen) {
		_game = game;
		_input = input;
		_screen = screen;
		
		changeLevel(1); //start in level 1
	}
	
	/*
	|--------------------------------------------------------------------------
	| Render & Update
	|--------------------------------------------------------------------------
	 */
	@Override
	public void update() {
		if( _game.isPaused() ) return;
		
		updateEntities();
		updateMobs();
		updateBombs();
		updateMessages();
		detectEndGame();
		
		for (int i = 0; i < _mobs.size(); i++) {
			Mob a = _mobs.get(i);
			if(((Entity)a).isRemoved()) _mobs.remove(i);
		}
	}

	@Override
	public void render(Screen screen) {
		if( _game.isPaused() ) return;
		//only render the visible part of screen
		int x0 = Screen.xOffset >> 4; //tile precision, -> left X
		int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
		int y0 = Screen.yOffset >> 4;
		int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE; //render one tile plus to fix black margins
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				_entities[x + y * _level.getWidth()].render(screen);
			}
		}
		
		renderBombs(screen);
		renderMobs(screen);
	}
	
	/*
	|--------------------------------------------------------------------------
	| ChangeLevel
	|--------------------------------------------------------------------------
	 */
	public void newGame() {
		resetProperties();
		changeLevel(1);
	}
	
	@SuppressWarnings("static-access")
	private void resetProperties() {
		_points = Game.POINTS;
		_lives = Game.LIVES;
		Player._powerups.clear();
		
		_game.playerSpeed = 1.0;
		_game.bombRadius = 1;
		_game.bombRate = 1;
		
	}

	public void restartLevel() {
		changeLevel(_level.getLevel());
	}
	
	public void nextLevel() {
		changeLevel(_level.getLevel() + 1);
	}
	
	public void changeLevel(int level) {
		_time = Game.TIME;
		_screenToShow = 2;
		_game.resetScreenDelay();
		_game.pause();
		_mobs.clear();
		_bombs.clear();
		_messages.clear();
		
		try {
			_level = new FileLevel("levels/Level" + level + ".txt", this);
			_entities = new Entity[_level.getHeight() * _level.getWidth()];
			
			_level.createEntities();
		} catch (LoadLevelException e) {
			endGame();
		}
	}
	
	public boolean isPowerupUsed(int x, int y, int level) {
		Powerup p;
		for (int i = 0; i < Player._powerups.size(); i++) {
			p = Player._powerups.get(i);
			if(p.getX() == x && p.getY() == y && level == p.getLevel())
				return false;
		}
		
		return true;
	}

	protected void detectEndGame() {
		if(_time <= 0)
			restartLevel();
	}
	
	public void endGame() {
		_screenToShow = 1;
		_game.resetScreenDelay();
		_game.pause();
	}
	
	public boolean detectNoEnemies() {
		int total = 0;
		for (Mob mob : _mobs) {
			if (!(mob instanceof Player))
				++total;
		}
		
		return total == 0;
	}
	
	/*
	|--------------------------------------------------------------------------
	| Pause & Resume
	|--------------------------------------------------------------------------
	 */
	public void gamePause() {
		_game.resetScreenDelay();
		if(_screenToShow <= 0)
			_screenToShow = 3;
		_game.pause();
	}
	
	public void gameResume() {
		_game.resetScreenDelay();
		_screenToShow = -1;
		_game.run();
	}
	
	/*
	|--------------------------------------------------------------------------
	| Screens
	|--------------------------------------------------------------------------
	 */
	public void drawScreen(Graphics g) {
		switch (_screenToShow) {
			case 1:
				_screen.drawEndGame(g, _points);
				break;
			case 2:
				_screen.drawChangeLevel(g, _level.getLevel());
				break;
			case 3:
				_screen.drawPaused(g);
				break;
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Getters And Setters
	|--------------------------------------------------------------------------
	 */
	public Entity getEntity(double x, double y, Mob m) {
		
		Entity res = null;
		
		res = getExplosionAt((int)x, (int)y);
		if( res != null) return res;
		
		res = getBombAt(x, y);
		if( res != null) return res;
		
		res = getMobAtExcluding((int)x, (int)y, m);
		if( res != null) return res;
		
		res = getEntityAt((int)x, (int)y);
		
		return res;
	}
	
	public List<Bomb> getBombs() {
		return _bombs;
	}
	
	public Bomb getBombAt(double x, double y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			if(b.getX() == (int)x && b.getY() == (int)y)
				return b;
		}
		
		return null;
	}
	
	public Mob getMobAt(double x, double y) {
		Iterator<Mob> itr = _mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur.getXTile() == x && cur.getYTile() == y)
				return cur;
		}
		
		return null;
	}
	
	public Player getPlayer() {
		Iterator<Mob> itr = _mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			
			if(cur instanceof Player)
				return (Player) cur;
		}
		
		return null;
	}
	
	public Mob getMobAtExcluding(int x, int y, Mob a) {
		Iterator<Mob> itr = _mobs.iterator();
		
		Mob cur;
		while(itr.hasNext()) {
			cur = itr.next();
			if(cur == a) {
				continue;
			}
			
			if(cur.getXTile() == x && cur.getYTile() == y) {
				return cur;
			}
				
		}
		
		return null;
	}
	
	public Explosion getExplosionAt(int x, int y) {
		Iterator<Bomb> bs = _bombs.iterator();
		Bomb b;
		while(bs.hasNext()) {
			b = bs.next();
			
			Explosion e = b.explosionAt(x, y);
			if(e != null) {
				return e;
			}
				
		}
		
		return null;
	}
	
	public Entity getEntityAt(double x, double y) {
		return _entities[(int)x + (int)y * _level.getWidth()];
	}
	
	/*
	|--------------------------------------------------------------------------
	| Adds and Removes
	|--------------------------------------------------------------------------
	 */
	public void addEntitie(int pos, Entity e) {
		_entities[pos] = e;
	}
	
	public void addMob(Mob e) {
		_mobs.add(e);
	}
	
	public void addBomb(Bomb e) {
		_bombs.add(e);
	}
	
	public void addMessage(Message e) {
		_messages.add(e);
	}
	
	/*
	|--------------------------------------------------------------------------
	| Renders
	|--------------------------------------------------------------------------
	 */
	protected void renderEntities(Screen screen) {
		for (Entity entity : _entities) {
			entity.render(screen);
		}
	}
	
	protected void renderMobs(Screen screen) {
		for (Mob mob : _mobs) mob.render(screen);
	}
	
	protected void renderBombs(Screen screen) {
		for (Bomb bomb : _bombs) bomb.render(screen);
	}
	
	public void renderMessages(Graphics g) {
		Message m;
		for (Message message : _messages) {
			m = message;

			g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
			g.setColor(m.getColor());
			g.drawString(m.getMessage(), (int) m.getX() - Screen.xOffset * Game.SCALE, (int) m.getY());
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Updates
	|--------------------------------------------------------------------------
	 */
	protected void updateEntities() {
		if( _game.isPaused() ) return;
		for (Entity entity : _entities) {
			entity.update();
		}
	}
	
	protected void updateMobs() {
		if( _game.isPaused() ) return;
		Iterator<Mob> itr = _mobs.iterator();
		
		while(itr.hasNext() && !_game.isPaused())
			itr.next().update();
	}
	
	protected void updateBombs() {
		if( _game.isPaused() ) return;

		for (Bomb bomb : _bombs) bomb.update();
	}
	
	protected void updateMessages() {
		if( _game.isPaused() ) return;
		Message m;
		int left = 0;
		for (int i = 0; i < _messages.size(); i++) {
			m = _messages.get(i);
			left = m.getDuration();
			
			if(left > 0) 
				m.setDuration(--left);
			else
				_messages.remove(i);
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| Getters & Setters
	|--------------------------------------------------------------------------
	 */
	public Keyboard getInput() {
		return _input;
	}
	
	public Level getLevel() {
		return _level;
	}
	
	public Game getGame() {
		return _game;
	}
	
	public int getShow() {
		return _screenToShow;
	}
	
	public void setShow(int i) {
		_screenToShow = i;
	}
	
	public int getTime() {
		return _time;
	}
	
	public int getLives() {
		return _lives;
	}

	public int subtractTime() {
		// giảm dần thời gian chơi của màn chơi
		if(_game.isPaused())
			return this._time;
		else
			return this._time--;
	}

	// lấy điểm hiện tại
	public int getPoints() {
		return _points;
	}

	// tăng điểm khi giết quái
	public void addPoints(int points) {
		this._points += points;
	}

	public void reduceLives() {
		this._lives--;
	}
	
	public int getWidth() {
		return _level.getWidth();
	}

	public int getHeight() {
		return _level.getHeight();
	}
	
}
