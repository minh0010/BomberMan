package com.bomberman_uet_21020778.entities;

import java.util.ArrayList;
import com.bomberman_uet_21020778.graphics.IRender;
import com.bomberman_uet_21020778.graphics.Screen;
import com.bomberman_uet_21020778.graphics.Sprite;
import com.bomberman_uet_21020778.level.Coordinates;

public abstract class Entity implements IRender {
  protected double x, y;
  protected boolean removed = false;
  protected Sprite sprite;

  @Override
  public abstract void update();

  @Override
  public abstract void render(Screen screen);

  public void remove() {
    this.removed = true;
  }

  public boolean isRemoved() {
    return this.removed;
  }

  public Sprite getSprite() {
    return this.sprite;
  }

  public abstract boolean collide(Entity e);

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  // tọa độ trong hệ tọa độ ô/
  public int getXTile() {
    return Coordinates.pixelToTile(x + sprite.SIZE / 2);
  }

  public int getYTile() {
    return Coordinates.pixelToTile(y - sprite.SIZE / 2);
  }
}
