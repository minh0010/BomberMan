package com.bomberman_uet_21020778.entities.bomb;

import com.bomberman_uet_21020778.Board;
import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.graphics.Screen;

public class Flame extends Entity {

  protected Board board;
  protected int direction;
  private int radius;
  protected int xOrigin, yOrigin;
  protected FlameSegment[] flameSegments = new FlameSegment[0];

  public Flame(int x, int y, int direction, int radius, Board board) {
    this.xOrigin = x;
    this.yOrigin = y;
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.radius = radius;
    this.board = board;
    createFlameSegments();
  }

  private void createFlameSegments() {
    flameSegments = new FlameSegment[calculatePermitedDistance()];


    boolean last;

    //  tạo các cạnh dưới đây
    int x = (int)this.x;
    int y = (int)this.y;
    for (int i = 0; i < flameSegments.length; i++) {
      last = i == flameSegments.length - 1;

      switch (direction) {
        case 0 -> y--;
        case 1 -> x++;
        case 2 -> y++;
        case 3 -> x--;
      }
      flameSegments[i] = new FlameSegment(x, y, direction, last);
    }


  }

  private int calculatePermitedDistance() {
    //thực hiện tính toán độ dài của Flame khi bom nổ
    int radius = 0;
    int x = (int)this.x;
    int y = (int)this.y;
    while(radius < this.radius) {
      if(direction == 0) y--;// lên trên
      if(direction == 1) x++;// sang phải
      if(direction == 2) y++;// sang phải
      if(direction == 3) x--;// sang trái

      Entity a = board.getEntity(x, y, null);

      if(a.collide(this) == false)
        // không thể bị đi qua
        break;

      ++radius;
    }
    return radius;

  }

  public FlameSegment flameSegmentAt(int x, int y) {
    for (int i = 0; i < flameSegments.length; i++) {
      if(flameSegments[i].getX() == x && flameSegments[i].getY() == y)
        return flameSegments[i];
    }
    return null;
  }

  @Override
  public void update() {}

  @Override
  public void render(Screen screen) {
    for (int i = 0; i < flameSegments.length; i++) {
      flameSegments[i].render(screen);
    }
  }

  @Override
  public boolean collide(Entity e) {
    if(e instanceof Enemy) {
      ((Enemy) e).kill();
      // âm thanh lúc enemy chết
      Sound.playMosterDie();
      return false;
    }

    if(e instanceof Bomber) {//  xử lý va chạm với Bomber,
      ((Bomber)e).kill();
      // thêm âm thanh bom chết
      Sound.playBomberDie();
      return false;
    }



    return true;

  }
}