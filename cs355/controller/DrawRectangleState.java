package cs355.controller;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Model;
import cs355.model.drawing.Rectangle;
import cs355.view.ViewRefresher;

public class DrawRectangleState extends ControllerState {

	boolean keepShape;
	
	public DrawRectangleState(Model m, ViewRefresher v, Color selCol) {
		super(m, v, selCol);
		keepShape = false;
	}

	@Override
	public void mousePressed(Double point) {
		clickPoint = point;
		activeShape = new Rectangle(selectedColor, clickPoint, 0, 0);
		shapeIndex = model.addShape(activeShape);
	}

	@Override
	public void mouseDragged(Double point) {
		keepShape = true;
		Rectangle rec = (Rectangle)activeShape;
		double width = Math.abs(point.x - clickPoint.x);
		double height = Math.abs(point.y - clickPoint.y);
		rec.setWidth(width);
		rec.setHeight(height);
		if (point.x < clickPoint.x) {
			if (point.y < clickPoint.y)
				rec.setCenter(new Point2D.Double(clickPoint.x-width/2, clickPoint.y-height/2));
			else 
				rec.setCenter(new Point2D.Double(clickPoint.x-width/2, clickPoint.y+height/2));
		}
		else if (point.y < clickPoint.y) {
				rec.setCenter(new Point2D.Double(clickPoint.x+width/2, clickPoint.y-height/2));
		}
		else {
			rec.setCenter(new Point2D.Double(clickPoint.x+width/2, clickPoint.y+height/2));
		}
		activeShape = rec;
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
