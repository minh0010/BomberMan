package com.bomberman_uet_21020778.gui;

import com.bomberman_uet_21020778.Game;

import javax.swing.*;
import java.awt.*;

/**
 * Swing Panel chứa cảnh game
 */
public class GamePanel extends JPanel {

  private Game game;

  public GamePanel(Frame frame) {
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));

    game = new Game(frame);

    add(game);

    game.setVisible(true);

    setVisible(true);
    setFocusable(true);

  }

  public Game getGame() {
    return game;
  }
  // thêm method change size
  public void changeSize() {
    setPreferredSize(new Dimension(Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE));
    revalidate();
    repaint();
  }



}