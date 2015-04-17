package ynachum;

import java.awt.Robot;

public class RobotDrag {

	public static void robotDragMouse(Robot bot, int fromX, int fromY, int toX, int toY, int time, int frames, int prevAutoDelay){
		int deltaT = time/frames;
		double deltaX = (toX - fromX)/frames;
		double deltaY = (toY - fromY)/frames;
		int newX = fromX;
		int newY = fromY;
		
		bot.setAutoDelay(deltaT);
		for(int i = 0; i < frames; i++){
			newX += deltaX;
			newY += deltaY;
			bot.mouseMove(newX, newY);
		}
		bot.setAutoDelay(prevAutoDelay);
	}
}
