package com.bomberman_uet_21020778.entities.tile.destroyable;

import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.entities.bomb.Flame;
import com.bomberman_uet_21020778.entities.tile.Tile;
import com.bomberman_uet_21020778.graphics.Sprite;

public class DestroyableTile extends Tile {

  private final int MAX_ANIMATE = 7500;
  private int animate = 0;
  protected boolean destroyed = false; // bị phá phá hủy hay không
  protected int timeToDisapear = 20; // thời gian để biến mất của 1 bức tường gạch
  protected Sprite belowSprite = Sprite.grass; // hình ảnh bên dưới


  public DestroyableTile(int x, int y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public void update() {
    // nếu bị destroy
    if(destroyed) {
      if(animate < MAX_ANIMATE) animate++; // thay đổi ảnh khi mà bom nổ
      else animate = 0;

      if(timeToDisapear > 0)
        timeToDisapear--;

      else
        remove();
    }
  }

  public void destroy() {
    destroyed = true;
  }

  @Override
  public boolean collide(Entity e) {

    if (e instanceof Flame)
      this.destroy();

    return false;
  }

  public void addBelowSprite(Sprite sprite) {
    belowSprite = sprite;
  }

  protected Sprite movingSprite(Sprite normal, Sprite x1, Sprite x2) {
    int calc = animate % 30;

    if(calc < 10) {
      return normal;
    }

    if(calc < 20) {
      return x1;
    }

    return x2;
  }

}