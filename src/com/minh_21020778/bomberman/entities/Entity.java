package com.minh_21020778.bomberman.entities;

import com.minh_21020778.bomberman.graphics.IRender;
import com.minh_21020778.bomberman.graphics.Screen;
import com.minh_21020778.bomberman.graphics.Sprite;
import com.minh_21020778.bomberman.level.Coordinates;

// lớp đại diện cho tất cả thực thể trong game
public abstract class Entity implements IRender {
	protected double _x, _y;
	protected boolean _removed = false;
	protected Sprite _sprite;

	// phương thức update sẽ được gọi trong vòng lặp game
	// mục đích để cật nhật trạng thái của các entity
	@Override
	public abstract void update();

	// thực hiện cật nhật và render hình ảnh của entity ra màn hình
	@Override
	public abstract void render(Screen screen);

	// cài lệnh xóa entity
	public void remove() {
		_removed = true;
	}

	// xác định xem entity có lệnh xóa hay không
	public boolean isRemoved() {
		return _removed;
	}

	public Sprite getSprite() {
		return _sprite;
	}

	// hàm kiểm tra va chạm vật thể
	public abstract boolean collide(Entity e);
	
	public double getX() {
		return _x;
	}
	
	public double getY() {
		return _y;
	}

	public int getXTile() {
		return Coordinates.pixelToTile(_x + _sprite.SIZE / 2);
		// cộng thêm một nửa để tăng thêm độ chính xác
	}
	
	public int getYTile() {
		return Coordinates.pixelToTile(_y - _sprite.SIZE / 2);
		// cộng thêm một nửa để tăng độ chính xác
	}
}
