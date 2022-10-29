package com.bomberman_uet_21020778;

import com.bomberman_uet_21020778.graphics.Screen;
import com.bomberman_uet_21020778.input.IOClass;
import com.bomberman_uet_21020778.input.Keyboard;
import com.bomberman_uet_21020778.gui.Frame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import static java.lang.Integer.parseInt;

public class Game extends Canvas {

  public static final int TILES_SIZE = 16, // độ to của 1 ô gạch

  // thông số dài rộng theo cái titles size
  WIDTH = TILES_SIZE * (31/2), // 13 ô chiều ngang
          HEIGHT = 13 * TILES_SIZE; // 13 viên  chiều dọc

  public static int WIDTHTile = 31;
  public static int HEIGHTTile = 13;

  // tỉ lệ ? 
  public static int SCALE = 3;

  // tên cửa sổ
  public static final String TITLE = "Bomberman_21020778";

  // chỉ số vể nhân vật là hằng số  mặc định
  private static final int BOMBRATE = 1; // số bom có thể đặt
  private static final int BOMBRADIUS =1; // độ dài của vụ nổ
  private static final double BOMBERSPEED = 1.0; // tốc độ của nhân vật bomber

  public static final int TIME = 200; // thời gian hết một màn
  public static final int POINTS = 0; // điểm đạt được    
  public static  int LIVES = 3; // mang

  // đổ lại hướng di chuyển
  public static  boolean REVERSE = false;


  public static final int HIGHSCORE = parseInt(IOClass.Read()); // đọc và điểm cao nhất

  protected static int SCREENDELAY = 3; // delay màn hình

  //----------------------------------------------------------------------





  // chỉ số nhân vật ,  chắc là  thể thay đổi trong tương lai
  protected static int bombRate = BOMBRATE;   // số quả bom đc dặt
  protected static int bombRadius = BOMBRADIUS;
  protected static double bomberSpeed = BOMBERSPEED;
  protected static int lives=LIVES;

  protected int _screenDelay = SCREENDELAY;
  //-------------------------------------------


  private Keyboard input;
  //nút pause
  private boolean running = false;
  private boolean paused = true;


  private Board board;
  private Screen screen;
  private Frame frame;

  private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
  private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

  public Game(Frame frame) {
    this.frame = frame;
    this.frame.setTitle(TITLE);
    screen = new Screen(WIDTH, HEIGHT);
    input = new Keyboard();

    board = new Board(this, input, screen);
    addKeyListener(input);
  }


  private void renderGame() {
    BufferStrategy bs = getBufferStrategy();
    if(bs == null) {
      createBufferStrategy(3);
      return;
    }
    screen.clear();
    board.render(screen);

    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = screen.pixels[i];
    }

    Graphics g = bs.getDrawGraphics();

    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    board.renderMessages(g);

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

    board.drawScreen(g);

    g.dispose();
    bs.show();
  }

  private void update() {

    input.update();
    board.update();
  }

  public void start() {
    running = true;

    long  lastTime = System.nanoTime(); // thời gian thực trong quá khư , vòng loob cũ
    long timer = System.currentTimeMillis();
    final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second ( tần số f) vậy 1/ns là thời gian load 1 frame
    double delta = 0;
    int frames = 0;
    int updates = 0;

    requestFocus();
    Keyboard _input = new Keyboard();


    while(running) {
      long now = System.nanoTime(); // thời gian bây h
      delta += (now - lastTime) / ns;   // denta t tức là thời gian  vòng loob cũ .  đơn vị  s^2 
      // denta =1 ; now -lastTime = ns  => thời gian của của 1 vòng loob = T chu kì chuẩn


      lastTime = now; // để last time  cho vòng loob while sau tính

      // đo vòng while  loob lớn  trong bao lâu

      // nếu denta t >=1  thì thơi gian mỗi vòng loob sẽ = với T thời gian 1 vòng loob chuẩn

      while(delta >= 1) {
        update();
        updates++;
        delta--;
      }

      //------------------------------------------------------
      // nút pause
      if(paused) {
        if(_screenDelay <= 0) { // nếu thời gian plause  hết thì tắt nút plause
          board.setShow(-1);
          paused = false;
        }

        // hiển thị cái screen
        renderScreen();
      } else {
        // hển thị game bình thường
        renderGame();
        if(_input.pause){

        }
      }
      //------------------------------------------------------


      frames++;

      if(System.currentTimeMillis() - timer > 1000) {
        frame.setTime(board.subtractTime());
        frame.setPoints(board.getPoints());
        frame.setLives(board.getLives());
        // quy trình : info goi Pannel goi frame goi trong day 

        timer += 1000;
        // in thông số 
        frame.setTitle(TITLE + " | " + updates + " rate, " + frames + " fps");
        updates = 0;
        frames = 0;

        if(board.getShow() == 2)
          --_screenDelay;
      }

    }

  }

  //----------------------------------------------------------------------
  // getter method

  public Board getBoard() {
    return board;
  }

  public static double getBomberSpeed() {
    return bomberSpeed;
  }

  public static int getBombRate() {
    return bombRate;
  }

  public static int getBombRadius() {
    return bombRadius;
  }

  //----------------------------------------------------------------------

  // khi ăn iteam sẽ tăng tốc lên i

  public static void addBomberSpeed(double i) {
    bomberSpeed += i;
  }

  public static void addBombRadius(int i) {
    bombRadius += i;
  }

  public static void addBombRate(int i) {
    bombRate += i;
  }
  // theem mang
  public static void addLives(int i){
    lives += i;
  }

  //---------------------------------------------------------------------

  public void resetScreenDelay() {
    _screenDelay = SCREENDELAY;
  }

  // nút pause
  //----------------------------------------------------------------------

  public boolean isPaused() {
    return paused;
  }

  public void pause() {
    paused = true;
  }

  public void unpause(){
    paused =false;
  }

  public void run() {
    running = true;
    paused = false;
  }

  public boolean isRunning() {
    return running;
  }

  // khi chết đi sống lại
  public static void resestAllPower(){
    bomberSpeed =BOMBERSPEED;
    bombRadius  = BOMBRADIUS;
    bombRate = BOMBRATE;
    Game.REVERSE = false;
  }
  public static void setReverser(){
    Game.REVERSE = true;
  }

}