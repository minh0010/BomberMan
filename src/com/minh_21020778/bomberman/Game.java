package com.minh_21020778.bomberman;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


import com.minh_21020778.bomberman.exceptions.BombermanException;
import com.minh_21020778.bomberman.graphics.Screen;
import com.minh_21020778.bomberman.gui.Frame;
import com.minh_21020778.bomberman.input.Keyboard;

// tạo gameloop, lưu trữ các thông số toàn cục cho game
// gọi các phương thức render(), update() ... cho các entity
public class Game extends Canvas {

	/*
	|--------------------------------------------------------------------------
	| các thông số toàn cục
	|--------------------------------------------------------------------------
	 */
	public static final double VERSION = 0.1;
	// kích thước một ô
	public static final int TILES_SIZE = 16;
	// chiều dài chiều rộng màn hình hiển thị
	public static final int WIDTH = TILES_SIZE * (int)(31 / 2), HEIGHT = 13 * TILES_SIZE;
	public static int SCALE = 3; // tỉ lệ, thay đổi cái này để game to hay nhỏ nhé, 3 chắc là đẹp nhất rồi
	public static final String TITLE = "Bomberman_21020778" + VERSION; // tên game nè
	private static final int BOMBRATE = 1; // số bom có thể đặt
	private static final int BOMBRADIUS = 1; // độ dài vụ nổ
	private static final double PLAYERSPEED = 1.0; // tốc độ di chuyển nhân vật chính - player
	public static final int TIME = 200; // giới hạn thời gian chơi
	public static final int POINTS = 0; // số điểm của người chơi
	public static final int LIVES = 3; // số mạng của người chơi
	protected static int SCREENDELAY = 1; // độ trễ chờ màn hình
	
	
	// thông số của người chơi, cái này không để final để còn thay đổi khi chơi game
	protected static int bombRate = BOMBRATE;
	protected static int bombRadius = BOMBRADIUS;
	protected static double playerSpeed = PLAYERSPEED;
	
	
	// thông số về thời gian chơi trong màn chơi
 	protected int _screenDelay = SCREENDELAY; // độ trễ
	private final Keyboard _input; // phím vào
	private boolean _running = false; // nhân vật di chuyển hay không
	private boolean _paused = true; // dừng game
	
	private final Board _board;
	private final Screen screen;
	private final Frame _frame;
	
	// các biến render game
	private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private final int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game(Frame frame) throws BombermanException {
		_frame = frame;
		_frame.setTitle(TITLE);
		
		screen = new Screen(WIDTH, HEIGHT);
		_input = new Keyboard();
		
		_board = new Board(this, _input, screen);
		addKeyListener(_input);
	}
	
	
	private void renderGame() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		_board.render(screen);

		System.arraycopy(screen._pixels, 0, pixels, 0, pixels.length);
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		_board.renderMessages(g);
		
		g.dispose();
		bs.show();
	}
	
	private void renderScreen() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();

		Graphics g = bs.getDrawGraphics();

		_board.drawScreen(g);

		g.dispose();
		bs.show();
	}

	private void update() {
		_input.update();
		_board.update();
	}
	
	public void start() {
		_running = true;
		
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; //chia thời gian dạng nanosecond (60s/second)
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while(_running) {
			// lấy thời gian chơi này
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			// denta =1 ; now -lastTime = ns  => thời gian của của 1 vòng loob = T chu kì chuẩn
			while(delta >= 1) {
				update();
				updates++;
				delta--;
			}

			// tạm dừng, render màn hình trước khi vào chơi
			if(_paused) {
				if(_screenDelay <= 0) {
					_board.setShow(-1);
					_paused = false;
				}
				renderScreen();
			} else {
				renderGame();
			}

			frames++;
			// kiểm tra mỗi giây một lần
			if(System.currentTimeMillis() - timer > 1000) {
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				_frame.setLives(_board.getLives());
				timer += 1000;
				_frame.setTitle(TITLE + " | " + updates + " rate");
				updates = 0;
				frames = 0;
				
				if(_board.getShow() == 2)
					--_screenDelay;
			}
		}
	}
	
	/*
	|--------------------------------------------------------------------------
	| các hàm getter và setter
	|--------------------------------------------------------------------------
	 */
	public static double getPlayerSpeed() {
		return playerSpeed;
	}
	
	public static int getBombRate() {
		return bombRate;
	}
	
	public static int getBombRadius() {
		return bombRadius;
	}
	
	public static void addPlayerSpeed(double i) {
		playerSpeed += i;
	}
	
	public static void addBombRadius(int i) {
		bombRadius += i;
	}
	
	public static void addBombRate(int i) {
		bombRate += i;
	}
	
	//screen delay
	public int getScreenDelay() {
		return _screenDelay;
	}
	
	public void decreaseScreenDelay() {
		_screenDelay--;
	}
	
	public void resetScreenDelay() {
		_screenDelay = SCREENDELAY;
	}

	public Keyboard getInput() {
		return _input;
	}
	
	public Board getBoard() {
		return _board;
	}
	
	public void run() {
		_running = true;
		_paused = false;
	}
	
	public void stop() {
		_running = false;
	}
	
	public boolean isRunning() {
		return _running;
	}
	
	public boolean isPaused() {
		return _paused;
	}
	
	public void pause() {
		_paused = true;
	}
	
}
