package com.bomberman_uet_21020778.level;

import com.bomberman_uet_21020778.Board;
import com.bomberman_uet_21020778.exceptions.LoadLevelException;


/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {
  protected int width , height ;
  protected int level;
  protected Board board;

  public LevelLoader(Board board, int level) throws LoadLevelException {
    this.board = board;
    loadLevel(level);
  }

  public abstract void loadLevel(int level) throws LoadLevelException;

  public abstract void createEntities();

  public int getWidth() {
    return _width;
  }

  public int getHeight() {
    return _height;
  }

  public int getLevel() {
    return level;
  }

}
