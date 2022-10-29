package com.bomberman_uet_21020778.entities.tile.item;


import com.bomberman_uet_21020778.entities.tile.Tile;
import com.bomberman_uet_21020778.graphics.Sprite;

public abstract class Item extends Tile {

  protected boolean  _active = false;

  public Item(int x, int y, Sprite sprite) {
    super(x, y, sprite);
  }
  // kiểm tra có hoạt động không
  public boolean isActive() {
    return _active;
  }

  // setter
  public void setActive(boolean active) {
    this._active = active;
  }
  // hàm abstrac  sét sức mạnh
  public abstract void setValues();
}
