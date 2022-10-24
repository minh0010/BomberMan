package com.bomberman_uet_21020778.entities;

import com.bomberman_uet_21020778.graphics.Interface_Render;
import com.bomberman_uet_21020778.graphics.Screen;
import com.bomberman_uet_21020778.graphics.Sprite;

public abstract class Entity implements Interface_Render {
  protected double x, y;
  protected boolean removed = false;
  protected Sprite sprite;

  /**
   * Update được gọi liên tục trong vòng lặp game
   * Mục đích chính để xử lý sự kiện và cật nhật trạng thái của thực thể
   */
  @Override
  public abstract void update();

  @Override
  public abstract void render(Screen screen);

  public void remove() {
    removed = true;
  }

  public abstract boolean collision(Entity e);
  public boolean isRemoved() {
    return removed;
  }

  public Sprite getSprite() {
    return sprite;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
}
