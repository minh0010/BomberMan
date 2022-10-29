package com.bomberman_uet_21020778.menu;

import com.bomberman_uet_21020778.Game;
import com.bomberman_uet_21020778.gui.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Options extends JMenu implements ChangeListener{
  Frame frame;

  public Options(Frame frame) {
    super("Pause/Resume");

    this.frame = frame;
    // pause
    JMenuItem pause = new JMenuItem("Pause");
    pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
    pause.addActionListener(new MenuActionListener(frame));
    add(pause);

    // résume
    JMenuItem resume = new JMenuItem("Resume");
    resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
    resume.addActionListener(new MenuActionListener(frame));
    add(resume);


  }



  @Override
  public void stateChanged(ChangeEvent e) {
    JSlider source = (JSlider)e.getSource();
    if (!source.getValueIsAdjusting()) {
      int fps = (int)source.getValue();

      Game.SCALE = fps;
      System.out.println( Game.SCALE);

      frame.gamepane.changeSize();
      frame.revalidate();
      frame.pack();
    }
  }

  class MenuActionListener implements ActionListener {
    public Frame _frame;
    public MenuActionListener(Frame frame) {
      _frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

      if(e.getActionCommand().equals("Pause")) {
        _frame.pauseGame();
      }

      if(e.getActionCommand().equals("Resume")) {
        _frame.resumeGame();
      }
    }
  }
}
