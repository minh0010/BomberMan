package com.bomberman_uet_21020778.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class SpriteSheet {
  private String Path;
  public final int SIZE;
  public int[] pixels;
  public static SpriteSheet tiles = new SpriteSheet("resources/textures/classic.png", 256);

  public SpriteSheet(String path, int size) {
    this.Path = path;
    this.SIZE = size;
    this.pixels = new int[this.SIZE * this.SIZE];
    load();
  }

  private void load() {
    try {
      URL a = SpriteSheet.class.getResource(Path);
      BufferedImage image = ImageIO.read(a);
      int w = image.getWidth();
      int h = image.getHeight();
      image.getRGB(0,0,w,h,pixels,0,w);
    }
    catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
