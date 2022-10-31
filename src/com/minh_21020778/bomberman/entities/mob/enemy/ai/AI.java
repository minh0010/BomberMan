package com.minh_21020778.bomberman.entities.mob.enemy.ai;

import java.util.Random;

//thực hiện tính toán di chuyển cho enemy
public abstract class AI {

  protected Random random = new Random();

  public abstract int calculateDirection();
}
