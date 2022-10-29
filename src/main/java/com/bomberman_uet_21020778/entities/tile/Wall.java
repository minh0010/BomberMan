package com.bomberman_uet_21020778.entities.tile;

import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.graphics.Sprite;

public class Wall extends Tile {

  public Wall(int x, int y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public boolean collide(Entity e) {
    return false;
  }

}
