package com.bomberman_uet_21020778.entities.tile.item;

import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.entities.character.Bomber;
import com.bomberman_uet_21020778.graphics.Sprite;

public class SpeedItem extends Item {

  public SpeedItem(int x, int y, Sprite sprite) {
    super(x, y, sprite);
  }

  @Override
  public boolean collide(Entity e) {
    if(e instanceof Bomber) { // kiểm tra entity có phải là bomberman ko
      // add thêm  sức mạnh của flame
      ((Bomber) e).addPowerup(this);
      remove();
      Sound.playGetNewItem();
      return true;
    }

    return false;
  }

  @Override
  public void setValues() {
    this.setActive(true);

    Game.addBomberSpeed(0.4);
    // Game.addBombRadius(1);
    //System.out.println("SpeedItem Add complete");
    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}