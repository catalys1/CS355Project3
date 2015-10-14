package cs355.controller;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Model;
import cs355.model.drawing.Square;
import cs355.view.ViewRefresher;

public class DrawSquareState extends ControllerState {

	private boolean keepShape;
	
	public DrawSquareState(Model m, ViewRefresher v, Color selCol) {
		super(m, v, selCol);
		keepShape = false;
	}

	@Override
	public void mousePressed(Double point) {
		clickPoint = point;
		activeShape = new Square(selectedColor, clickPoint, 0);
		shapeIndex = model.addShape(activeShape);
	}

	@Override
	public void mouseDragged(Double point) {
		keepShape = true;
		Square sq = (Square)activeShape;
		double xdif = Math.abs(point.x - clickPoint.x);
		double ydif = Math.abs(point.y - clickPoint.y);
		double size = xdif < ydif ? xdif : ydif;
		sq.setSize(size); 
		if (point.x < clickPoint.x) {	// TO the left
			if (point.y < clickPoint.y)	// Top left quadrant
				sq.setCenter(new Point2D.Double(clickPoint.x-size/2, clickPoint.y-size/2));
			else 				// Bottom left quadrant
				sq.setCenter(new Point2D.Double(clickPoint.x-size/2, clickPoint.y+size/2));
		}
		else if (point.y < clickPoint.y) {	// Top right quadrant
			sq.setCenter(new Point2D.Double(clickPoint.x+size/2, clickPoint.y-size/2));
		}
		else {						// Bottom right quadrant
			sq.setCenter(new Point2D.Double(clickPoint.x+size/2, clickPoint.y+size/2));
		}
		activeShape = sq;
		model.updateShape(activeShape);
	}

	@Override
	public void mouseReleased() {
		clickPoint = null;
		if (!keepShape) {
			model.deleteShape(shapeIndex);
		}
		keepShape = false;
	}
}
