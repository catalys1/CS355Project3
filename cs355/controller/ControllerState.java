package cs355.controller;

import java.awt.Color;
import java.awt.geom.Point2D;

import cs355.model.drawing.Model;
import cs355.model.drawing.Shape;
import cs355.view.ViewRefresher;

public abstract class ControllerState {
	
	public static final int CLICK_TOLERANCE = 4;
	public static final double TWO_PI = Math.PI * 2;
	
	protected Shape activeShape;
	protected Point2D.Double clickPoint;
	protected Model model;
	protected Color selectedColor;
	protected int shapeIndex;
	protected ViewRefresher view;
	
	public ControllerState(Model m, ViewRefresher v, Color selCol) {
		activeShape = null;
		clickPoint = null;
		model = m;
		selectedColor = selCol;
		view = v;
	}
	
	public void changeColor(Color c) {
		selectedColor = c;
	}
	
	public abstract void mousePressed(Point2D.Double point);
	
	public abstract void mouseDragged(Point2D.Double point);
	
	public abstract void mouseReleased();
	
	public void moveForward() {}
	
	public void moveBackward() {}
	
	public void moveToFront() {}
	
	public void moveToBack() {}
	
	public boolean deleteShape() { return false; }

}
