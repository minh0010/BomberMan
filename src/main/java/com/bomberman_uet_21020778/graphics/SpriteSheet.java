package com.bomberman_uet_21020778.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.FloatBuffer;

public class SpriteSheet {

  private final String path;
  public final int SIZE;
  public int[] pixels;

  public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);

  public SpriteSheet(String path, int size) {
    this.path = path;
    this.SIZE = size;
    pixels = new int[size * size];
    load();
  }

  private void load() {
    try {
      URL a = SpriteSheet.class.getResource(path);
      BufferedImage image = ImageIO.read(a);
      int w = image.getWidth();
      int h = image.getHeight();
      image.getRGB(0, 0, w, h, pixels, 0, w);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }
}
