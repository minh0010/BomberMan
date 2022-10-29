package com.bomberman_uet_21020778.menu;

import com.bomberman_uet_21020778.Game;
import com.bomberman_uet_21020778.gui.Frame;

import javax.swing.JMenuBar;

/**
 *
 * @author DoQuangTrung
 */
public class Menu extends JMenuBar {
  public Menu(Frame frame) {
    add( new Game(frame));
    add( new Options(frame) );
  }
}