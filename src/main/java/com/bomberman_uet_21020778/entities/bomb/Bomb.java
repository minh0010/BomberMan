package com.bomberman_uet_21020778.entities.bomb;

import com.bomberman_uet_21020778.Board;
import com.bomberman_uet_21020778.Game;
import com.bomberman_uet_21020778.entities.AnimatedEntitiy;
import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.graphics.Screen;
import com.bomberman_uet_21020778.graphics.Sprite;
import com.bomberman_uet_21020778.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

  protected double timeToExplode = 120; //2 seconds
  public int timeAfter = 20; // thời gian cho vụ nổ kết thúc

  protected Board board;
  protected Flame[] flames; // tầm ảnh hưởng của bom
  protected boolean exploded = false; // đã nổ ?
  protected boolean allowedToPassThroght = true; // có thể đi qua

  public boolean isExploded() {
    return exploded;
  }

  public boolean isAllowedToPassThroght() {
    return allowedToPassThroght;
  }
  public Bomb(int x, int y, Board board) {
    //  System.out.println(" xbom=" + _x +" ybom = " + y  );
    this.x = x;
    this.y = y;
    this.board = board;
    this.sprite = Sprite.bomb;
  }

  @Override
  public void update() {
    if(timeToExplode > 0)
      timeToExplode--;
    else {
      if(!exploded)
        explode();
      else
        updateFlames();

      if(timeAfter > 0)
        timeAfter--;
      else
        remove();
    }

    animate();
  }

  @Override
  public void render(Screen screen) {
    if(exploded) {
      sprite =  Sprite.bomb_exploded2;
      renderFlames(screen);
    } else
      sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);

    int xt = (int)x << 4;
    int yt = (int)y << 4;

    screen.renderEntity(xt, yt , this);
  }

  public void renderFlames(Screen screen) {
    for (int i = 0; i < flames.length; i++) {
      flames[i].render(screen);
    }
  }

  public void updateFlames() {
    for (int i = 0; i < flames.length; i++) {
      flames[i].update();
    }
  }

  protected void explode() {
    exploded = true;
    allowedToPassThroght = true;

    int Xb = this.board.getBomber().getXTile();
    int Yb = this.board.getBomber().getYTile();
    // nếu tọa độ trùng nhau thì giết
    if ( Xb == this.getXTile() && this.getYTile()== Yb  ){
      this.board.getBomber().kill();
      Sound.playBomberDie();
    }

    //  tạo các Flame nổ 4 chiều
    flames = new Flame[4];
    for (int i = 0; i < flames.length; i++) {
      flames[i] = new Flame((int)x, (int)y, i, Game.getBombRadius(), board);
    }
    Sound.playBombExplose();
  }

  public FlameSegment flameAt(int x, int y) {
    if(!exploded) return null;

    for (int i = 0; i < flames.length; i++) {
      if(flames[i] == null) return null;
      FlameSegment e = flames[i].flameSegmentAt(x, y);
      if(e != null) return e;
    }

    return null;
  }

  @Override
  public boolean collide(Entity e) {
    // xử lý khi Bomber đi ra sau khi vừa đặt bom (_allowedToPassThroght)

    if(e instanceof Bomber) { // thực thể có phải là bomberman
      double diffX = e.getX() - Coordinates.tileToPixel(getX());
      double diffY = e.getY() - Coordinates.tileToPixel(getY());

      // kiểm tra xem thằng boomber đã rời vị chí quả bom vừa đặt lúc nãy chưa
      if(!( diffY >= 1 && diffY <= 30 && diffX >= -10 && diffX < 16 )) {
        // trả về ko thê qua vị chí đó đc nữa
        allowedToPassThroght = false;
      }


      // trả về true ; qua dược
      return allowedToPassThroght;

    }
    if ( e instanceof Flame){
      this.timeToExplode =0;
      // cho quả bom này thời gian nổ về 0
      return true;
    }
    if ( e instanceof FlameSegment){
      this.timeToExplode =0;
      // cho bom có thời gian nổ về 0
      return true;
    }


    if ( e instanceof Bomb ){
      //truong hop bom dat cung 1 luc
      // if ( ((Bomb) e).isExploded()==false) return true;
      if ( ((Bomb) e).isExploded()== true) {
        this.timeToExplode =0;
        return true;
      }


    }

    return false;
  }


  // thêm 2 hàm cho AI

  /**
   *
   * @return tọa độ x  trong hệ tọa độ ô vuông
   */
  @Override
  public int getXTile(){
    return (int) this.x;
  }
  /**
   *
   * @return  tọa độ y trong hệ tọa dộ ô vuông
   */
  @Override
  public int getYTile(){
    return (int) this.y;
  }


}