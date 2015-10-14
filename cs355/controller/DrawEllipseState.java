package cs355.controller;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import cs355.model.drawing.Ellipse;
import cs355.model.drawing.Model;
import cs355.view.ViewRefresher;

public class DrawEllipseState extends ControllerState {

	private boolean keepShape;
	
	public DrawEllipseState(Model m, ViewRefresher v, Color selCol) {
		super(m, v, selCol);
		keepShape = false;
	}

	@Override
	public void mousePressed(Double point) {
		clickPoint = point;
		activeShape = new Ellipse(selectedColor, clickPoint, 0, 0);
		shapeIndex = model.addShape(activeShape);
	}

	@Override
	public void mouseDragged(Double point) {
		keepShape = true;
		Ellipse el = (Ellipse)activeShape;
		double width = Math.abs(point.x - clickPoint.x);
		double height = Math.abs(point.y - clickPoint.y);
		el.setWidth(width);
		el.setHeight(height);
		width = width/2;
		height = height/2;
		if (point.x < clickPoint.x) {
			if (point.y < clickPoint.y)
				el.setCenter(new Point2D.Double(clickPoint.x-width, clickPoint.y-height));
			else 
				el.setCenter(new Point2D.Double(clickPoint.x-width, clickPoint.y+height));
		}
		else if (point.y < clickPoint.y) {
			el.setCenter(new Point2D.Double(clickPoint.x+width, clickPoint.y-height));
		}
		else {
			el.setCenter(new Point2D.Double(clickPoint.x+width, clickPoint.y+height));
		}
		activeShape = el;
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
