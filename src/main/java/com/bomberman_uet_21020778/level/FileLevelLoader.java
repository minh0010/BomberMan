package com.bomberman_uet_21020778.level;

import com.bomberman_uet_21020778.Board;
import com.bomberman_uet_21020778.entities.LayeredEntity;
import com.bomberman_uet_21020778.entities.tile.Grass;
import com.bomberman_uet_21020778.entities.tile.Wall;
import com.bomberman_uet_21020778.entities.tile.destroyable.Brick;
import com.bomberman_uet_21020778.exceptions.LoadLevelException;
import com.bomberman_uet_21020778.graphics.Screen;
import com.bomberman_uet_21020778.graphics.Sprite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class FileLevelLoader extends LevelLoader {
  public static char[][] map;

  public FileLevelLoader(Board board, int level) throws LoadLevelException {
    super(board, level);
  }

  @Override
  public void loadLevel(int level) throws LoadLevelException {
    try {
      URL absPath = FileLevelLoader.class.getResource("/levels/Level" + Integer.toString(level) + ".txt");
      BufferedReader in = new BufferedReader( new InputStreamReader(absPath.openStream() ) );

      String data = in.readLine();

      level = Integer.parseInt(data.substring(0,1));
      height = Integer.parseInt(data.substring(2,4));
      width = Integer.parseInt(data.substring(5,7));

      map = new char[height][width];
      for (int i = 0; i < height; i++ ) {
        data = in.readLine();

        for (int j = 0; j < width; j++ ) {
          map[i][j] = data.charAt(j);
        }
        //this.boardGame.add(data);
      }
      in.close();

    } catch (IOException e) {
      throw new LoadLevelException("Error loading level " + level, e);
    }

  }

  @Override
  public void createEntities() {
    for(int y = 0; y < height ; y++ ) {
      for(int x = 0; x < width ; x++) {

        int pos = x + y * width;
        char printSprite = map[y][x];
        // load hình ảnh
        switch (printSprite) {
          // thêm tường
          case '#':
            board.addEntity(pos, new Wall(x,y, Sprite.wall));
            break;
          // thêm cái brick . gạch có thể phá
          case '*':
            board.addEntity(pos, new LayeredEntity(x, y, new Grass(x ,y, Sprite.grass), new Brick(x ,y, Sprite.brick)));
            break;

          //   nhân vật bomber
          case 'p':
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            board.addCharacter(new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            //vị chí mặc định ô đầu tiên
            Screen.setOffset(0, 0);
            break;
          //Item
          // đảo ngược hướng di chuyển
          case 'r':
            board.addEntity(pos
                    ,  new LayeredEntity(x, y, new Grass(x, y, Sprite.grass)
                            , new ReverseDirectionItem(x, y, Sprite.powerup_flamepass),
                            new Brick(x, y, Sprite.brick)));
            break;


          // flame của bom
          case 'f':
            board.addEntity(pos
                    ,  new LayeredEntity(x, y, new Grass(x, y, Sprite.grass)
                            , new FlameItem(x, y, Sprite.powerup_flames),
                            new Brick(x, y, Sprite.brick)));
            break;

          // so luong bom
          case 'b':
            board.addEntity(pos,
                    new LayeredEntity( x , y ,  new Grass(x, y, Sprite.grass) ,  new BombItem( x , y ,  Sprite.powerup_bombs  ), new Brick(x, y, Sprite.brick)));
            break;
          // tốc độ
          case 's':
            board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new SpeedItem(x, y, Sprite.powerup_speed), new Brick(x, y, Sprite.brick)));
            break;

          // live
          case 'l':
            board.addEntity(pos, new LayeredEntity(x, y, new Grass(x, y, Sprite.grass), new LiveItem(x, y, Sprite.powerup_detonator), new Brick(x, y, Sprite.brick)));
            break;



          // cổng portal
          case 'x':
            board.addEntity(pos
                    , new LayeredEntity(x, y, new Grass(x, y, Sprite.grass)
                            , new Portal(x, y,board ,Sprite.portal), new Brick(x, y, Sprite.brick)));
            break;
          // bos ngu
          case '1':
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            break;
          // tạo boss ngu 2
          case '2':
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            break;
//                                      tao Doll
          case '3':
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            board.addCharacter(new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            break;
          //Mivo
          case '4':
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            board.addCharacter(new Minvo(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            break;

          // kondoria
          case '5':
            board.addEntity(pos, new Grass(x, y, Sprite.grass));
            board.addCharacter(new Kondoria(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, board));
            break;

          // cỏ
          default:
            board.addEntity(pos, new Grass(x, y, Sprite.grass) );
            break;
        }
      }



    }

  }
}