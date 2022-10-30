package com.minh_21020778.bomberman.gui.menu;

import javax.swing.JMenuBar;

import com.minh_21020778.bomberman.gui.Frame;

public class Menu extends JMenuBar {
	
	public Menu(Frame frame) {
		add( new Game(frame) );
		add( new Options(frame) );
		add( new Help(frame) );
	}
	
}
