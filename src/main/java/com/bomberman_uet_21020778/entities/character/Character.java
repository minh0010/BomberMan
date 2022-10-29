package com.bomberman_uet_21020778.entities.character;


import com.bomberman_uet_21020778.Board;
import com.bomberman_uet_21020778.Game;
import com.bomberman_uet_21020778.entities.AnimatedEntitiy;
import com.bomberman_uet_21020778.graphics.Screen;

/**
 * Bao gồm Bomber và Enemy
 */
public abstract class Character extends AnimatedEntitiy {

  public Board board;
  protected int direction;
  protected boolean alive = true;
  protected boolean moving = false;
  public int _timeAfter = 40;

  public Character(int x, int y, Board board) {
    this.x = x;
    this.y = y;
    this.board = board;
  }

  @Override
  public abstract void update();

  @Override
  public abstract void render(Screen screen);

  /**
   * Tính toán hướng đi
   */
  protected abstract void calculateMove();

  protected abstract void move(double xa, double ya);

  /**
   * Được gọi khi đối tượng bị tiêu diệt
   */
  public abstract void kill();

  /**
   * Xử lý hiệu ứng bị tiêu diệt
   */
  protected abstract void afterKill();

  /**
   * Kiểm tra xem đối tượng có di chuyển tới vị trí đã tính toán hay không
   * @param x
   * @param y
   * @return
   */
  protected abstract boolean canMove(double x, double y);

  protected double getXMessage() {
    return (x * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
  }

  protected double getYMessage() {
    return (y * Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
  }

}