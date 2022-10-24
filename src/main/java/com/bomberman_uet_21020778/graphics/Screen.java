package com.bomberman_uet_21020778.graphics;

import com.bomberman_uet_21020778.Board;
import com.bomberman_uet_21020778.BomberManGame;
import com.bomberman_uet_21020778.Game;
import com.bomberman_uet_21020778.entities.Entity;
import com.bomberman_uet_21020778.entities.Character.Bomber;

import java.awt.*;
import java.util.Arrays;

public class Screen {
  protected int width, height;
  public int[] pixels;
  private final int TransparentColor = 0xffff00ff;
  public static int xOffset = 0, yOffset = 0;

  public Screen(int width, int height) {
    this.width = width;
    this.height = height;

    this.pixels = new int[width * height];
  }

  public void clear() {
    Arrays.fill(pixels, 0);
  }

  public void renderEntity(int xp, int yp, Entity entity) {
    xp -= xOffset;
    yp -= yOffset;

    for (int _y = 0; _y < entity.getSprite().getSize(); ++_y) {
      int y1 = _y + yp;
      for (int _x = 0; _x < entity.getSprite().getSize(); ++_x) {
        int x1 = _x + xp;
        if (x1 < -entity.getSprite().getSize()
                || x1 >= width
                || y1 < 0
                || y1 >= height) {
          break;
        }
        if (x1 < 0) x1 = 0;
        int color = entity.getSprite().getPixels(_x + _y * entity.getSprite().getSize());
        if (color != TransparentColor) pixels[x1 + y1 * width] = color;
      }
    }
  }

  public void renderEntityWithSprite(int xp, int yp, Entity entity, Sprite sprite) {
    xp -= xOffset;
    yp -= yOffset;

    for (int _y = 0; _y < entity.getSprite().getSize(); ++_y) {
      int y1 = _y + yp;
      for (int _x = 0; _x < entity.getSprite().getSize(); ++_x) {
        int x1 = _x + xp;
        if (x1 < -entity.getSprite().getSize()
                || x1 >= width
                || y1 < 0
                || y1 >= height) {
          break;
        }
        int color = entity.getSprite().getPixels(_x + _y * entity.getSprite().getSize());
        if (color != TransparentColor) {
          pixels[x1 + y1 * width] = color;
        }
        else {
          pixels[x1 + y1 * width] = sprite.getPixels(_x + _y * sprite.getSize());
        }
      }
    }
  }

  public static void setOffset(int x, int y) {
    xOffset = x;
    yOffset = y;
  }

  public static int calculateXOffset(Board board, Bomber bomber) {
    return 0;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
