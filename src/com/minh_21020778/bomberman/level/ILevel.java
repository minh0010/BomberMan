package com.minh_21020778.bomberman.level;


import com.minh_21020778.bomberman.exceptions.LoadLevelException;

public interface ILevel {

	public void loadLevel(String path) throws LoadLevelException;
}
