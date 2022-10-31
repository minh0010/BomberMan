package com.minh_21020778.bomberman.entities.mob.enemy.ai;

public class AILow extends AI {
	// di chuyển ngẫu nhiên theo 4 hướng
	@Override
	public int calculateDirection() {
		return random.nextInt(4);
	}

}
