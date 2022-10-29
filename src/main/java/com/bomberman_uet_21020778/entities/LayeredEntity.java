package com.bomberman_uet_21020778.entities;

import com.bomberman_uet_21020778.entities.tile.destroyable.DestroyableTile;
import com.bomberman_uet_21020778.graphics.Screen;

import java.util.LinkedList;
public class LayeredEntity extends Entity {

  protected LinkedList<Entity> _entities = new LinkedList<>();

  public LayeredEntity(int x, int y, Entity ... entities) {
    this.x = x;
    this.y = y;

    for (int i = 0; i < entities.length; i++) {
      _entities.add(entities[i]);

      if(i > 1) {
        if(entities[i] instanceof DestroyableTile)
          ((DestroyableTile)entities[i]).addBelowSprite(entities[i-1].getSprite());
      }
    }
  }

  @Override
  public void update() {
    clearRemoved();
    getTopEntity().update();
  }

  @Override
  public void render(Screen screen) {
    getTopEntity().render(screen);
  }

  public Entity getTopEntity() {

    return _entities.getLast();
  }

  private void clearRemoved() {
    Entity top  = getTopEntity();

    if(top.isRemoved())  {
      _entities.removeLast();
    }
  }

  public void addBeforeTop(Entity e) {
    _entities.add(_entities.size() - 1, e);
  }

  @Override
  public boolean collide(Entity e) {
    //  lấy entity trên cùng ra để xử lý va chạm
    return getTopEntity().collide(e);

  }

}