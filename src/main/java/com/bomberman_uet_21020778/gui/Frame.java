package com.bomberman_uet_21020778.gui;

import com.bomberman_uet_21020778.Game;
import com.bomberman_uet_21020778.menu.Menu;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {


  public GamePanel gamepane;
  private JPanel containerpane;
  private InfoPanel infopanel;

  private Game game;

  public Frame() {
    setJMenuBar(new Menu(this));

    containerpane = new JPanel(new BorderLayout());
    gamepane = new GamePanel(this);
    infopanel = new InfoPanel(gamepane.getGame());

    containerpane.add(infopanel, BorderLayout.PAGE_START);
    containerpane.add(gamepane, BorderLayout.PAGE_END);

    game = gamepane.getGame();

    add(containerpane);

    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pack();
    setLocationRelativeTo(null);
    setVisible(true);

    game.start();
  }

  public void setLives(int lives){
    infopanel.setLives(lives);
  }

  public void setTime(int time) {
    infopanel.setTime(time);
  }

  public void setPoints(int points) {
    infopanel.setPoints(points);
  }

  public void pauseGame() {
    game.getBoard().gamePause();
  }

  public void resumeGame() {
    game.getBoard().gameResume();
  }

  public boolean isRunning() {
    return game.isRunning();
  }

  public void newGame() {
    game.getBoard().newGame();
  }
}