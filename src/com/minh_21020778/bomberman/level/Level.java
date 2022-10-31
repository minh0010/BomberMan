package com.minh_21020778.bomberman.level;

import com.minh_21020778.bomberman.Board;
import com.minh_21020778.bomberman.exceptions.LoadLevelException;

// load và lưu trữ thông tin các màn chơi
public abstract class Level {
	protected int _width, _height, _level;
	protected String[] _lineTiles;
	protected Board _board;

	public Level(String path, Board board) throws LoadLevelException {
		loadLevel(path);
		_board = board;
	}

	public abstract void loadLevel(String path) throws LoadLevelException;
	
	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public int getLevel() {
		return _level;
	}

}
