package com.bomberman_uet_21020778.entities.tile.destroyable;

import com.bomberman_uet_21020778.Game;
import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.entities.bomb.Bomb;
import com.bomberman_uet_21020778.entities.bomb.Flame;
import com.bomberman_uet_21020778.entities.character.Bomber;
import com.bomberman_uet_21020778.graphics.Screen;
import com.bomberman_uet_21020778.graphics.Sprite;
import com.bomberman_uet_21020778.level.Coordinates;

import java.util.ArrayList;

public class Brick extends DestroyableTile {


  public static ArrayList<Integer> Xgachvo = new ArrayList();
  public static ArrayList<Integer> Ygachvo = new ArrayList();


  public Brick(int x, int y, Sprite sprite) {
    super(x, y, sprite);
  }

  public static void addXgachvo(int x) {
    Xgachvo.add(x);
  }

  public static void addYgachvo(int y) {
    Ygachvo.add(y);

  }

  @Override
  public void update() {
    super.update();
  }

  @Override
  public void render(Screen screen) {
    int x = Coordinates.tileToPixel(this.x);
    int y = Coordinates.tileToPixel(this.y);

    if(destroyed) {
      sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);

      screen.renderEntityWithBelowSprite(x, y, this, sprite);
    }
    else
      screen.renderEntity( x, y, this);
  }

  @Override
  public boolean collide(Entity e) {
    // nếu tính năng đảo ngược kích hoạt . thì bomber có thể đi  xuyên tường
    if (Game.REVERSE){
      if( e instanceof Bomber){
        return true;
      }
    }
    // khi ma hack
    if( e instanceof Bomb){
      if(((Bomb) e).isExploded()){
        destroy();
      }

    }
    if(e instanceof Flame){

      addXgachvo(this.getXtile());
      addYgachvo(this.getYtile());
      destroy();
      return false; // cho qua khi no xong
    }
    // khoong cho qua
    return false;
  }
  public int getXtile(){
    return (int) this._x;
  }
  public int getYtile(){
    return (int) this._y;
  }

}