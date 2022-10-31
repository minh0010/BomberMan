package com.minh_21020778.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// tiếp nhận và xử lý thông tin từ bàn phím nhập vào
public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[120]; // bằng này phím là đủ cho game này rồi
	public boolean up, down, left, right, space; // các phím cơ bản

	// cật nhật phím mới
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		space = keys[KeyEvent.VK_SPACE] || keys[KeyEvent.VK_X];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	// xác định phím được nhấn
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	// xác định phím được thả ra
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
}
