package com.bomberman_uet_21020778.entities.tile;


import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.graphics.Screen;
import com.bomberman_uet_21020778.graphics.Sprite;
import com.bomberman_uet_21020778.level.Coordinates;

public abstract class Tile extends Entity {

  public Tile(int x, int y, Sprite sprite) {
    this.x = x;
    this.y = y;
    this.sprite = sprite;
  }
  @Override
  public boolean collide(Entity e) {
    return false;
  }

  @Override
  public void render(Screen screen) {
    screen.renderEntity( Coordinates.tileToPixel(this.x), Coordinates.tileToPixel(this.y), this);
  }

  @Override
  public void update() {}
}
